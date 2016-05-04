package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchOddEntityRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightOddEntityRepository;
import kz.nmbet.betradar.dao.repository.GlSportEntityRepository;
import kz.nmbet.betradar.dao.repository.GlTournamentEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;
import kz.nmbet.betradar.web.beans.MatchInfoBean;

@Service
public class CashierService {

	private static final Logger logger = LoggerFactory.getLogger(CashierService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private GlBetRepository betRepository;

	@Autowired
	private GlSportEntityRepository sportEntityRepository;

	@Autowired
	private GlCategoryEntityRepository categoryEntityRepository;

	@Autowired
	private GlTournamentEntityRepository tournamentEntityRepository;

	@Autowired
	private GlMatchEntityRepository glMatchEntityRepository;


	@Autowired
	private GlOutrightOddEntityRepository outrightOddEntityRepository;

	@Autowired
	private GlMatchOddEntityRepository matchOddEntityRepository;

@Autowired
private RemoteStoreService remoteStoreService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Value("${autologin.salt}")
	private String salt;

	@Transactional
	public GlBet getBet(Integer id) {
		return betRepository.findOne(id);
	}

	@Transactional
	public List<GlBet> getLastCashierBets(GlUser user) {
		Pageable page = new PageRequest(0, 100);
		return betRepository.findByOwner(user, page);
	}

	@Transactional(readOnly = true)
	public Map<Integer, String> getSportEntities() {
		return sportEntityRepository.findAll(new Sort(Sort.Direction.ASC, "nameEn")).stream()
				.collect(Collectors.toMap(item -> item.getId(), item -> TextsEntityUtils.getName(item)));
	}
	@Transactional(readOnly = true)
	public Map<Integer, String> getCategoryEntities(Integer sportId) {
		return categoryEntityRepository.getBySportId(sportId).stream().collect(Collectors.toMap(item -> item.getId(), item -> TextsEntityUtils.getName(item)));
	}

	@Transactional(readOnly = true)
	public Map<Integer, String> getOutrightEntities(Integer categoryId) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return categoryEntityRepository.findOne(categoryId).getOutrights().stream()
				.collect(Collectors.toMap(item -> item.getId(), item -> new String(sdf.format(item.getEventDate()) + " : " + item.getEventInfo())));
	}
	@Transactional(readOnly = true)
	public Map<Integer, String> getTournamentEntities(Integer categoryId) {
		return categoryEntityRepository.findOne(categoryId).getTournaments().stream()
				.collect(Collectors.toMap(item -> item.getId(), item -> TextsEntityUtils.getName(item)));

	}

	@Transactional(readOnly = true)
	public Map<Integer, String> getMatchEntities(Integer tournamentId) {
		return tournamentEntityRepository.findOne(tournamentId).getMatches().stream()
				.collect(Collectors.toMap(item -> item.getId(), item -> new MatchInfoBean(item, true).getTitle()));

	}

	private String getOddInfo(GlMatchOddEntity matchOdd) {
		StringBuilder builder = new StringBuilder();
		builder.append(matchOdd.getOddsType());
		builder.append(": ");
		builder.append(matchOdd.getOutCome());

		if (StringUtils.isNotBlank(matchOdd.getSpecialBetValue())) {
			builder.append(" (");
			builder.append(matchOdd.getSpecialBetValue());
			builder.append(")");
		}
		builder.append(" - ");
		builder.append(matchOdd.getValue());

		return builder.toString();
	}
	@Transactional(readOnly = true)
	public Map<Integer, String> getMatchOddEntities(Integer matchId) {
		return glMatchEntityRepository.findOne(matchId).getOdds().stream().collect(Collectors.toMap(item -> item.getId(), item -> getOddInfo(item)));

	}

	private boolean checkHash(String login, Integer cashierId, String hash) {
		String checkString = DigestUtils.md5DigestAsHex(new String(login + cashierId + salt).getBytes());
		return checkString.equalsIgnoreCase(hash);
	}

	@Transactional
	public GlUser autologin(String login, Integer cashierId, String hash) {
		if (!checkHash(login, cashierId, hash)) {
			throw new BadCredentialsException(MessageFormat.format("Wrong hash: {0} for ({1}, {2})", hash, login, cashierId));
		}
		GlUser cashier = userService.findByCashierId(cashierId);
		if (cashier == null)
			cashier = userService.create(login, null, UserService.CASHIER_ROLES, cashierId);
		return cashier;
	}

	@Transactional
	public GlBet createOutrightBet(Integer outrightOddId, double amount, GlUser user) {
		GlOutrightOddEntity odd = outrightOddEntityRepository.findOne(outrightOddId);
		if (odd != null) {
			GlBet bet = new GlBet();
			bet.setOutrightOddEntity(odd);
			bet.setBetAmount(amount);
			bet.setCreateDate(new Date());
			bet.setOddValue(odd.getValue());
			bet.setOwner(user);
			bet = betRepository.save(bet);
			remoteStoreService.duplicateBet(bet);
			return bet;
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

	@Transactional
	public GlBet createMatchBet(Integer matchOddId, double amount, GlUser user) {
		GlMatchOddEntity odd = matchOddEntityRepository.findOne(matchOddId);
		if (odd != null) {
			GlBet bet = new GlBet();
			bet.setMatchOddEntity(odd);
			bet.setBetAmount(amount);
			bet.setCreateDate(new Date());
			bet.setOddValue(odd.getValue());
			bet.setOwner(user);
			bet = betRepository.save(bet);
			remoteStoreService.duplicateBet(bet);
			return bet;
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

	
}
