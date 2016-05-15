package kz.nmbet.betradar.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;

@Service
public class RemoteStoreService {

	private static final Logger logger = LoggerFactory.getLogger(RemoteStoreService.class);

	private JdbcTemplate jdbcTemplate;

	@Qualifier("secondaryDataSource")
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Long duplicateBet(GlBet bet, String preview) {
		jdbcTemplate.setDataSource(dataSource);

		logger.info("duplicateBet start ");
		logger.info("-------------------------------");
		final String INSERT_SQL = "INSERT INTO day_bets(cashierid,created,round,bets,summ, will_win,game_type) VALUES (?, now(), ?, ?, ?, ?, 'sport' );";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setInt(1, bet.getOwner().getCashierId());
				ps.setInt(2, bet.getId());
				ps.setString(3, preview);
				ps.setDouble(4, bet.getBetAmount());
				ps.setString(5, bet.getBetAmount() * bet.getOddValue() + "");

				return ps;
			}
		}, keyHolder);

		logger.info("row inserted with id " + keyHolder.getKey());
		logger.info("duplicateBet finish ");

		return keyHolder.getKey().longValue();

	}

}
