package kz.nmbet.betradar.runner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.repository.UserRepository;
import kz.nmbet.betradar.dao.service.TeamService;
import kz.nmbet.betradar.web.beans.TournamentCsvBean;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitialize implements CommandLineRunner {

	private static final Logger logger = LoggerFactory
			.getLogger(DataInitialize.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeamService teamService;

	@Override
	public void run(String... arg0) throws Exception {

		logger.info("DataInitialize start ");
		logger.info("-------------------------------");
		GlUser user = userRepository.findByEmail("anarbek");
		if (user == null) {
			user = new GlUser();
			user.setEmail("anarbek");
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode("123456"));
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
			user.setRoles(Arrays.asList("USER, ADMIN"));
			userRepository.save(user);
		}

		// initTeams();

		logger.info("DataInitialize finish ");

	}

	private void initTeams() throws IOException {
		InputStream tournaments = this.getClass().getClassLoader()
				.getResourceAsStream("data/AllTournamentsIDs.csv");
		InputStreamReader reader = new InputStreamReader(tournaments);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';')
				.parse(reader);
		List<TournamentCsvBean> tournamentCsvBeans = new ArrayList<TournamentCsvBean>();
		boolean first = true;
		for (CSVRecord record : records) {
			if (first) {
				first = false;
				continue;
			}
			TournamentCsvBean bean = new TournamentCsvBean(record);
			tournamentCsvBeans.add(bean);
			logger.info(bean.toString());
			if (bean.isNotEmpty())
				teamService.create(bean);
			logger.info("-----------");
		}
		reader.close();
		tournaments.close();
	}
}
