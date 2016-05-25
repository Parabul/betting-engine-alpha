package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.domain.types.BetType;
import kz.nmbet.betradar.dao.domain.views.ShortMatch;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddFieldRepository;
import kz.nmbet.betradar.dao.repository.GlMatchOddEntityRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightOddEntityRepository;
import kz.nmbet.betradar.dao.repository.GlSportEntityRepository;
import kz.nmbet.betradar.utils.MessageByLocaleService;
import kz.nmbet.betradar.utils.TextsEntityUtils;
import kz.nmbet.betradar.web.beans.ShortOdd;

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
	private GlOutrightOddEntityRepository outrightOddEntityRepository;

	@Autowired
	private GlMatchOddEntityRepository matchOddEntityRepository;

	@Autowired
	private RemoteStoreService remoteStoreService;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@Autowired
	private GlMatchLiveOddFieldRepository liveOddFieldRepository;

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
		return categoryEntityRepository.getBySportId(sportId).stream()
				.collect(Collectors.toMap(item -> item.getId(), item -> TextsEntityUtils.getName(item)));
	}

	@Transactional(readOnly = true)
	public Map<Integer, String> getOutrightEntities(Integer categoryId) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return categoryEntityRepository.findOne(categoryId).getOutrights().stream()
				.collect(Collectors.toMap(item -> item.getId(),
						item -> new String(sdf.format(item.getEventDate()) + " : " + item.getEventInfo())));
	}

	@Transactional(readOnly = true)
	public Map<Integer, String> getTournamentEntities(Integer categoryId) {
		return categoryEntityRepository.findOne(categoryId).getTournaments().stream()
				.collect(Collectors.toMap(item -> item.getId(), item -> TextsEntityUtils.getName(item)));

	}

	@Transactional(readOnly = true)
	public List<ShortMatch> getMatchesBySport(Integer sportId, String q) {
		q = q.replaceAll("\\s", "%");
		q = "%" + q + "%";
		List<ShortMatch> result = new ArrayList<ShortMatch>();
		jdbcTemplate.query(ShortMatch.query, new Object[] { sportId, q },
				(resultSet, rowNum) -> result.add(new ShortMatch(resultSet, rowNum)));
		return result;
	}

	@Transactional(readOnly = true)
	public List<ShortMatch> getLiveMatches(String q) {
		q = q.replaceAll("\\s", "%");
		q = "%" + q + "%";
		List<ShortMatch> result = new ArrayList<ShortMatch>();
		jdbcTemplate.query(ShortMatch.live_query, new Object[] { q },
				(resultSet, rowNum) -> result.add(new ShortMatch(resultSet, rowNum)));
		return result;
	}

	private ShortOdd getOddInfo(GlMatchOddEntity matchOdd) {
		StringBuilder builder = new StringBuilder();
		try {
			String type = messageByLocaleService.getMessage("match.odds.types.type" + matchOdd.getMatchOddsType());
			builder.append(type);
		} catch (NoSuchMessageException e) {
			builder.append(matchOdd.getMatchOddsType());
			logger.error(e.getMessage(), e);
		}
		builder.append(": ");
		builder.append(matchOdd.getOutCome());

		if (StringUtils.isNotBlank(matchOdd.getSpecialBetValue())) {
			builder.append(" (");
			builder.append(matchOdd.getSpecialBetValue());
			builder.append(")");
		}
		builder.append(" - ");
		builder.append(matchOdd.getValue());

		return new ShortOdd(matchOdd.getId(), builder.toString());
	}

	@Transactional(readOnly = true)
	public List<ShortOdd> getMatchOddEntities(Integer matchId) {
		return matchOddEntityRepository.findByMatchIdOrderByMatchOddsTypeAsc(matchId).stream()
				.map(item -> getOddInfo(item)).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	public List<ShortOdd> getMatchLiveOdds(Integer matchId) {
		return liveOddFieldRepository
				.findByLiveOddMatchIdAndActiveTrueAndLiveOddActiveTrueOrderByLiveOddIdAscViewIndexAsc(matchId).stream()
				.map(item -> getOddInfo(item)).collect(Collectors.toList());

	}

	private ShortOdd getOddInfo(GlMatchLiveOddField item) {
		StringBuilder builder = new StringBuilder();
		builder.append(item.getLiveOdd().getName());
		builder.append(": ");
		builder.append(item.getType());

		if (StringUtils.isNotBlank(item.getLiveOdd().getSpecialOddsValue())) {
			builder.append(" (");
			builder.append(item.getLiveOdd().getSpecialOddsValue());
			builder.append(")");
		}
		builder.append(" - ");
		builder.append(item.getValue());

		return new ShortOdd(item.getId(), builder.toString());
	}

	private boolean checkHash(String login, Integer cashierId, String hash) {
		String checkString = DigestUtils.md5DigestAsHex(new String(login + cashierId + salt).getBytes());
		return checkString.equalsIgnoreCase(hash);
	}

	@Transactional
	public GlUser autologin(String login, Integer cashierId, String hash) {
		if (!checkHash(login, cashierId, hash)) {
			throw new BadCredentialsException(
					MessageFormat.format("Wrong hash: {0} for ({1}, {2})", hash, login, cashierId));
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
			Long remoteId = remoteStoreService.duplicateBet(bet, "test", RemoteStoreService.PREMATCH_TYPE);
			bet.setBetType(BetType.OUTRIGHT);
			bet.setRemoteId(remoteId);
			bet = betRepository.save(bet);
			return bet;
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

	@Transactional
	public GlBet createLiveBets(String matchOddIds, double amount, GlUser user, String preview) {
		String[] stringIds = StringUtils.split(matchOddIds, ",");
		List<Integer> ids = Stream.of(stringIds).map(Integer::valueOf).collect(Collectors.toList());
		List<GlMatchLiveOddField> odds = liveOddFieldRepository.findByIdInAndActiveTrue(ids);
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
			Long remoteId = remoteStoreService.duplicateBet(bet, preview, RemoteStoreService.LIVE_TYPE);
			bet.setRemoteId(remoteId);
			bet = betRepository.save(bet);
			return bet;
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

	@Transactional
	public GlBet createMatchBets(String matchOddIds, double amount, GlUser user, String preview) {
		String[] stringIds = StringUtils.split(matchOddIds, ",");
		List<Integer> ids = Stream.of(stringIds).map(Integer::valueOf).collect(Collectors.toList());
		List<GlMatchOddEntity> odds = matchOddEntityRepository.findByIdIn(ids);
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
			Long remoteId = remoteStoreService.duplicateBet(bet, preview, RemoteStoreService.PREMATCH_TYPE);
			bet.setRemoteId(remoteId);
			bet = betRepository.save(bet);
			return bet;
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

}
