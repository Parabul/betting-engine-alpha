package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.domain.types.BetType;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddFieldRepository;
import kz.nmbet.betradar.dao.repository.GlMatchOddEntityRepository;
import kz.nmbet.betradar.dao.repository.UserRepository;
import kz.nmbet.betradar.utils.MessageByLocaleService;
import kz.nmbet.betradar.web.beans.MatchInfoBean;
import kz.nmbet.betradar.web.beans.ShortBetInfo;
import kz.nmbet.betradar.web.beans.ShortOdd;

@Service
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private GlBetRepository betRepository;

	@Autowired
	private GlMatchLiveOddFieldRepository liveOddFieldRepository;

	@Autowired
	private GlMatchOddEntityRepository matchOddEntityRepository;

	@Autowired
	private GlMatchOddEntityRepository oddRepository;

	@Autowired
	private CashierService cashierService;

	@Transactional
	public String getPrematchOddInfo(Integer oddId) {
		GlMatchOddEntity odd = oddRepository.findOne(oddId);

		ShortOdd oddInfo = cashierService.getOddInfo(odd);

		MatchInfoBean matchInfoBean = new MatchInfoBean(odd.getMatch(), true);
		String homeTeam = matchInfoBean.getHomeTeamName();
		String awayTeam = matchInfoBean.getAwayTeamName();

		return MessageFormat.format("[{0}] {1}-{2} : {3}", matchInfoBean.getMatchId() + "", homeTeam, awayTeam,
				oddInfo.getInfo());
	}

	@Transactional
	private void papulateWithOddsInfo(GlBet bet, ShortBetInfo betInfo) {
		if (bet.getLiveOdds() != null) {
			for (GlMatchLiveOddField oddField : bet.getLiveOdds()) {

				betInfo.getLiveOddInfos().add(new ShortOdd(oddField));
			}
		}
		if (bet.getMatchOddEntity() != null) {
			for (GlMatchOddEntity odd : bet.getMatchOddEntity()) {
				betInfo.getPrematchOddInfos().add(cashierService.getOddInfo(odd));
			}
		}
	}

	@Transactional
	public String getLiveOddInfo(Integer oddId) {
		GlMatchLiveOddField odd = liveOddFieldRepository.findOne(oddId);

		Double value = odd.getValue();
		String name = odd.getLiveOdd().getName();

		MatchInfoBean matchInfoBean = new MatchInfoBean(odd.getLiveOdd().getMatch(), true);
		String homeTeam = matchInfoBean.getHomeTeamName();
		String awayTeam = matchInfoBean.getAwayTeamName();

		return MessageFormat.format("[{0}] {1}-{2} : {3} {4}", matchInfoBean.getMatchId() + "", homeTeam, awayTeam,
				name, NumberFormat.getInstance().format(value));
	}

	@Transactional
	public ShortBetInfo createLiveBet(Integer oddId, double amount, GlUser user) {

		userService.withdraw(amount,  user);
		List<GlMatchLiveOddField> odds = liveOddFieldRepository.findByIdAndActiveTrue(oddId);
		double oddValue = 1.0d;
		for (GlMatchLiveOddField odd : odds) {
			oddValue = oddValue * odd.getValue();
		}
		if (odds != null && odds.size() > 0) {
			GlBet bet = new GlBet();
			bet.setLiveOdds(odds);
			bet.setBetAmount(amount);
			bet.setCreateDate(new Date());
			bet.setOddValue(oddValue);
			bet.setOwner(user);
			bet.setBetType(BetType.LIVE);
			bet = betRepository.save(bet);
			return new ShortBetInfo(bet);
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

	@Transactional
	public ShortBetInfo createMatchBet(Integer oddId, double amount, GlUser user) {
		userService.withdraw(amount,  user);
		List<GlMatchOddEntity> odds = new ArrayList<GlMatchOddEntity>();
		odds.add(matchOddEntityRepository.findOne(oddId));
		double oddValue = 1.0d;
		for (GlMatchOddEntity odd : odds) {
			oddValue = oddValue * odd.getValue();
		}
		if (odds != null && odds.size() > 0) {
			GlBet bet = new GlBet();
			bet.setMatchOddEntity(odds);
			bet.setBetAmount(amount);
			bet.setCreateDate(new Date());
			bet.setOddValue(oddValue);
			bet.setOwner(user);
			bet.setBetType(BetType.PREMATCH);
			bet = betRepository.save(bet);
			return new ShortBetInfo(bet);
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

	@Transactional(readOnly = true)
	public List<ShortBetInfo> getHistory(GlUser user, Pageable page) {

		List<ShortBetInfo> betInfos = new ArrayList<>();
		for (GlBet bet : betRepository.findByOwnerOrderByIdDesc(user, page)) {
			ShortBetInfo betInfo = new ShortBetInfo(bet);
			papulateWithOddsInfo(bet, betInfo);
			betInfos.add(betInfo);
		}
		return betInfos;

	}

}
