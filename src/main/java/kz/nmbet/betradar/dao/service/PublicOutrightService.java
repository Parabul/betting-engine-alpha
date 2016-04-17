package kz.nmbet.betradar.dao.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightResultEntity;
import kz.nmbet.betradar.dao.domain.views.OutrightOdd;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightOddEntityRepository;
import kz.nmbet.betradar.web.beans.OutrightInfo;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class PublicOutrightService {

	private static final Logger logger = LoggerFactory
			.getLogger(PublicOutrightService.class);

	@Autowired
	private GlOutrightEntityRepository outrightEntityRepository;

	@Autowired
	private GlOutrightOddEntityRepository outrightOddEntityRepository;

	@Autowired
	private GlBetRepository betRepository;

	@Autowired
	private DSLContext dslContext;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional
	public List<OutrightInfo> findAll() {
		logger.debug("PublicOutrightService findAll");
		return outrightEntityRepository.findAll().stream()
				.map(elt -> new OutrightInfo(elt)).collect(Collectors.toList());

	}

	@Transactional
	public List<OutrightOdd> findAllOutrightOdds() {
		logger.debug("PublicOutrightService findAllOutrightOdds");
		List<OutrightOdd> result = new ArrayList<OutrightOdd>();
		jdbcTemplate.query(OutrightOdd.query, (resultSet, rowNum) -> result
				.add(new OutrightOdd(resultSet, rowNum)));

		return result;
	}

	@Transactional
	public GlBet createBet(Integer outrightOddId, double amount) {
		GlOutrightOddEntity odd = outrightOddEntityRepository
				.findOne(outrightOddId);
		if (odd != null) {
			GlBet bet = new GlBet();
			bet.setOutrightOddEntity(odd);
			bet.setBetAmount(amount);
			bet.setCreateDate(new Date());
			bet = betRepository.save(bet);
			return bet;
		} else
			throw new IllegalArgumentException(
					"Номер ставки не указан не верно. Либо данные устарели");
	}

	private boolean hasMininalPlace(Set<GlOutrightResultEntity> results,
			Integer teamId, Integer place) {
		for (GlOutrightResultEntity result : results) {
			if (result.getTeamId().equals(teamId)) {
				if (result.getResult() <= place) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional
	public GlBet checkBet(Integer betId) {
		GlBet bet = betRepository.findOne(betId);

		if (bet != null) {
			bet.setCheckDate(new Date());

			GlOutrightOddEntity odd = bet.getOutrightOddEntity();
			Set<GlOutrightResultEntity> results = bet.getOutrightOddEntity()
					.getOutright().getResults();
			if (results != null && results.size() > 0) {
				boolean win = false;
				switch (odd.getOddsType()) {
				case championship_outrights:
					win = hasMininalPlace(results, odd.getTeamId(), 1);
					break;
				case podium_finish:
					win = hasMininalPlace(results, odd.getTeamId(), 3);
					break;
				case short_term_outrights:
					win = hasMininalPlace(results, odd.getTeamId(), 1);
					break;
				}
				bet.setWins(win);
				if (win) {
					bet.setWinAmount(bet.getBetAmount() * odd.getValue());
				}

			}
			return betRepository.save(bet);
		} else
			throw new IllegalArgumentException(
					"Номер ставки не указан не верно. Либо данные устарели");
	}
}
