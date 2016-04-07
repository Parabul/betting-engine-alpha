package kz.nmbet.betradar.api;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.service.PrivateMatchService;
import kz.nmbet.betradar.utils.TextsEntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.feed.common.LiveFeed;
import com.sportradar.sdk.feed.lcoo.entities.BatchCompleted;
import com.sportradar.sdk.feed.lcoo.entities.MatchEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightsEntity;
import com.sportradar.sdk.feed.lcoo.entities.ThreeBallEntity;
import com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed;
import com.sportradar.sdk.feed.lcoo.interfaces.LcooFeedListener;

@Service
public class LcooFeedListenerImpl implements LcooFeedListener {

	@Autowired
	private PrivateMatchService privateMatchService;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	private final static Logger logger = LoggerFactory.getLogger(LcooFeedListenerImpl.class);

	/**
	 * Invoked by the observed
	 * {@link com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed} instance when
	 * new {@link com.sportradar.sdk.feed.lcoo.entities.MatchEntity} is received
	 * from the server.
	 *
	 * @param sender
	 *            The {@link com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed}
	 *            instance which received the entity.
	 * @param match
	 *            The {@link com.sportradar.sdk.feed.lcoo.entities.MatchEntity}
	 *            representing the received match
	 */

	public void onMatchReceived(LcooFeed sender, MatchEntity match) {
		logger.info("On match with match id: {}", match.getMatchId());
		logger.info(match.toString());
		privateMatchService.create(match);
	}

	/**
	 * Invoked by the observed
	 * {@link com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed} instance when
	 * new {@link com.sportradar.sdk.feed.lcoo.entities.ThreeBallEntity} is
	 * received from the server.
	 *
	 * @param sender
	 *            The {@link com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed}
	 *            instance which received the entity.
	 * @param threeBall
	 *            The
	 *            {@link com.sportradar.sdk.feed.lcoo.entities.ThreeBallEntity}
	 *            representing the received threeBall
	 */

	public void onThreeBallReceived(LcooFeed sender, ThreeBallEntity threeBall) {
		logger.info("On three ball with three ball id: {}", threeBall.getId());
		logger.info(threeBall.toString());
	}

	public void onBatchCompleted(LcooFeed lcooFeed, BatchCompleted batchCompleted) {
		// Only used for two phase commit.
	}

	/**
	 * Invoked by the observed
	 * {@link com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed} instance when
	 * new {@link com.sportradar.sdk.feed.lcoo.entities.OutrightsEntity} is
	 * received from the server.
	 *
	 * @param sender
	 *            The {@link com.sportradar.sdk.feed.lcoo.interfaces.LcooFeed}
	 *            instance which received the entity.
	 * @param outrights
	 *            The
	 *            {@link com.sportradar.sdk.feed.lcoo.entities.OutrightsEntity}
	 *            representing the received outrights.
	 */

	public void onOutrightsReceived(LcooFeed sender, OutrightsEntity outrights) {
		logger.info("On outrights with nr of {} outrights", outrights.getOutrightEntities().size());
		logger.info(outrights.toString());
	}

	/**
	 * Invoked by the observed live feed when it encounters an special event
	 * related to the behavior of the server.
	 *
	 * @param eventType
	 *            The {@link com.sportradar.sdk.common.enums.FeedEventType}
	 *            member specifying the type of the occurred event.
	 */

	public void onFeedEvent(LiveFeed sender, FeedEventType eventType) {
		logger.info("Lcoo feed event occurred. Event: {}", eventType);
		logger.info(eventType.toString());
	}

	public void onOpened(LiveFeed sender) {
		logger.info("On opened");
		logger.info(sender.getConnectionParams().toString());
	}

	public void onClosed(LiveFeed sender) {
		logger.info("On closed");

	}
}
