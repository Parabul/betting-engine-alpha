package kz.nmbet.betradar.dao.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.domain.types.BetType;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddFieldRepository;

@Service
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private GlBetRepository betRepository;

	@Autowired
	private GlMatchLiveOddFieldRepository liveOddFieldRepository;

	@Transactional
	public GlBet createLiveBet(Integer oddId, double amount, GlUser user) {
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
			return bet;
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}
}
