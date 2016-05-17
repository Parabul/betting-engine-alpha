package kz.nmbet.betradar.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed;
import com.sportradar.sdk.feed.lcoo.interfaces.LcooFeedListener;
import com.sportradar.sdk.feed.liveodds.interfaces.LiveOddsFeed;
import com.sportradar.sdk.feed.liveodds.interfaces.LiveOddsFeedListener;
import com.sportradar.sdk.feed.liveodds.interfaces.RaceFeed;
import com.sportradar.sdk.feed.liveodds.interfaces.RaceFeedListener;
import com.sportradar.sdk.feed.liveodds.interfaces.outrights.LiveOddsWithOutrightsFeed;
import com.sportradar.sdk.feed.liveodds.interfaces.outrights.LiveOddsWithOutrightsListener;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.feed.oddscreator.entities.IdNameEntity;
import com.sportradar.sdk.feed.oddscreator.exceptions.OddsCreatorException;
import com.sportradar.sdk.feed.oddscreator.interfaces.OddsCreatorFeed;
import com.sportradar.sdk.feed.sdk.Sdk;

import kz.nmbet.betradar.api.LiveOddsOutrightsFeedListener;
import kz.nmbet.betradar.api.LiveOddsRaceFeedListenerImpl;
import kz.nmbet.betradar.api.LiveScoutFeedListenerImpl;

@Component
public class SdkInitialize implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SdkInitialize.class);

	@Autowired
	private LcooFeedListener lcooFeedListener;
	
	@Autowired
	private LiveOddsFeedListener liveOddsFeedListener;

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

		final LiveOddsFeed liveOddsFeed = sdk.getLiveOdds();
		final LiveOddsFeed liveOddsVblFeed = sdk.getLiveOddsVbl();
		final LiveOddsFeed liveOddsVflFeed = sdk.getLiveOddsVfl();
		final RaceFeed liveOddsVhcFeed = sdk.getLiveOddsVhc();
		final RaceFeed liveOddsVdrFeed = sdk.getLiveOddsVdr();
		final LiveOddsFeed liveOddsBetpal = sdk.getLiveOddsBetpal();
		final LiveOddsFeed liveOddsLivePlex = sdk.getLiveOddsLivePlex();
		final LiveOddsFeed liveOddsSoccerRouletteFeed = sdk.getLiveOddsSoccerRoulette();
		final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();
		final LiveOddsWithOutrightsFeed liveOddsVfcFeed = sdk.getLiveOddsVfc();

		

		final RaceFeedListener oddsRaceFeedListener = new LiveOddsRaceFeedListenerImpl();

		final LiveScoutFeedListener scoutFeedListener = new LiveScoutFeedListenerImpl();

		final LiveOddsWithOutrightsListener liveOddsWithOutrightsListener = new LiveOddsOutrightsFeedListener();

		if (liveScoutFeed != null) {
			liveScoutFeed.open(scoutFeedListener);
		}

		if (liveOddsFeed != null) {
			liveOddsFeed.open(liveOddsFeedListener);
		}

		if (liveOddsVblFeed != null) {
			liveOddsVblFeed.open(liveOddsFeedListener);
		}

		if (liveOddsVflFeed != null) {
			liveOddsVflFeed.open(liveOddsFeedListener);
		}

		if (liveOddsVhcFeed != null) {
			liveOddsVhcFeed.open(oddsRaceFeedListener);
		}

		if (liveOddsVdrFeed != null) {
			liveOddsVdrFeed.open(oddsRaceFeedListener);
		}

		if (liveOddsBetpal != null) {
			liveOddsBetpal.open(liveOddsFeedListener);
		}

		if (liveOddsLivePlex != null) {
			liveOddsLivePlex.open(liveOddsFeedListener);
		}

		if (liveOddsSoccerRouletteFeed != null) {
			liveOddsSoccerRouletteFeed.open(liveOddsFeedListener);
		}

		if (liveOddsVfcFeed != null) {
			liveOddsVfcFeed.open(liveOddsWithOutrightsListener);
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

		logger.info("SdkInitialize finish ");

	}
}
