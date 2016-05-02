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

	public void chech() {
		jdbcTemplate.setDataSource(dataSource);

		logger.info("CheckMySQLInitialize start ");
		logger.info("-------------------------------");
		final String INSERT_SQL = "insert into animals (name) values (?)";
		final String name = "Rob";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, name);
				return ps;
			}
		}, keyHolder);

		logger.info("row inserted with id " + keyHolder.getKey());
		logger.info("CheckMySQLInitialize finish ");

	}

}
