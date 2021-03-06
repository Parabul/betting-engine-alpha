package kz.nmbet.betradar.runner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.service.TeamService;
import kz.nmbet.betradar.dao.service.UserService;
import kz.nmbet.betradar.web.beans.TournamentCsvBean;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitialize implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DataInitialize.class);

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService;

	@Override
	public void run(String... arg0) throws Exception {

		logger.info("DataInitialize start ");
		logger.info("-------------------------------");
		GlUser user = userService.findByEmail("77781767411");
		if (user == null) {
			userService.create("77781767411", "123456", UserService.ADMIN_ROLES, 0);
		}

		//initTeams();
		logger.info("DataInitialize finish ");

	}

	private void initTeams() throws IOException {
		InputStream tournaments = this.getClass().getClassLoader().getResourceAsStream("data/AllTournamentsIDs.csv");
		InputStreamReader reader = new InputStreamReader(tournaments, "UTF-8");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').parse(reader);
		teamService.initDataForce(records);

		reader.close();
		tournaments.close();
	}
}
