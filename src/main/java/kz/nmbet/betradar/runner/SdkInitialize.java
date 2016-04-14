package kz.nmbet.betradar.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kz.nmbet.betradar.dao.repository.UserRepository;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed;
import com.sportradar.sdk.feed.lcoo.interfaces.LcooFeedListener;
import com.sportradar.sdk.feed.oddscreator.entities.IdNameEntity;
import com.sportradar.sdk.feed.oddscreator.exceptions.OddsCreatorException;
import com.sportradar.sdk.feed.oddscreator.interfaces.OddsCreatorFeed;
import com.sportradar.sdk.feed.sdk.Sdk;

@Component
public class SdkInitialize implements CommandLineRunner {

	private static final Logger logger = LoggerFactory
			.getLogger(SdkInitialize.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DSLContext create;

	@Autowired
	private LcooFeedListener lcooFeedListener;

	@Override
	public void run(String... arg0) throws Exception {

		logger.info("SdkInitialize start ");
		logger.info("-------------------------------");

		final Sdk sdk = Sdk.getInstance();

		final LcooFeed lcooFeed = sdk.getLcoo();
		final OddsCreatorFeed oddsCreatorFeed = sdk.getOddsCreator();

		if (lcooFeed != null) {			
			lcooFeed.open(lcooFeedListener);
			lcooFeed.clearQueue();
			
		}

		if (oddsCreatorFeed != null) {
			try {
				logger.info("Listing all sports :");
				for (IdNameEntity sports : oddsCreatorFeed.getSports()) {
					logger.info(sports.getName());
				}
			} catch (OddsCreatorException e) {
				e.printStackTrace();
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		logger.info("The sdk is running. Hit any key to exit");
		try {
			reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Closing the sdk");
		sdk.close();
		logger.info("Sdk successfully closed. Main thread will now exit");

		logger.info("SdkInitialize finish ");

	}
}
