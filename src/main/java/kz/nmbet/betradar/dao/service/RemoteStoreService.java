package kz.nmbet.betradar.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

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

@Service
public class RemoteStoreService {

	private static final Logger logger = LoggerFactory.getLogger(RemoteStoreService.class);

	public static final String PREMATCH_TYPE = "sport prematch";
	public static final String LIVE_TYPE = "sport live";

	private JdbcTemplate jdbcTemplate;

	@Qualifier("secondaryDataSource")
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Long duplicateBet(GlBet bet, String preview, String type) {
		jdbcTemplate.setDataSource(dataSource);

		final String INSERT_SQL = "INSERT INTO day_bets(cashierid,created,round,bets,summ, will_win,game_type) VALUES (?, now(), ?, ?, ?, ?, ? );";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setInt(1, bet.getOwner().getCashierId());
				ps.setInt(2, bet.getId());
				ps.setString(3, preview);
				ps.setDouble(4, bet.getBetAmount());
				ps.setString(5, bet.getBetAmount() * bet.getOddValue() + "");
				ps.setString(6, type);

				return ps;
			}
		}, keyHolder);

		logger.info(MessageFormat.format("duplicateBet - {0}, inserted with id = {1}", bet.getId().toString(),
				keyHolder.getKey().toString()));
		return keyHolder.getKey().longValue();
	}

	public void updateWithResult(GlBet bet) {
		jdbcTemplate.setDataSource(dataSource);
		final String UPDATE_SQL = "UPDATE day_bets SET utys=?, is_active=? WHERE id=?";

		int result = jdbcTemplate.update(UPDATE_SQL, new Object[] { bet.getWinAmount(), true, bet.getRemoteId() });

		logger.info(MessageFormat.format("rupdateWithResult - {0}, result = {1}", bet.getId().toString(), result + ""));

	}

}
