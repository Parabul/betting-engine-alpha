package kz.nmbet.betradar.api;

import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.feed.liveodds.classes.EventDataPackage;
import com.sportradar.sdk.feed.liveodds.entities.common.AliveEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetCancelEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetCancelUndoEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetClearEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetClearRollbackEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetStartEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetStopEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.IrrelevantOddsChangeEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.MetaInfoEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.OddsChangeEntity;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.LiveOddsMetaData;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.MatchHeaderEntity;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.MatchHeaderInfoEntity;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.ScoreCardSummaryEntity;
import com.sportradar.sdk.feed.liveodds.interfaces.LiveOddsFeed;
import com.sportradar.sdk.feed.liveodds.interfaces.LiveOddsFeedListener;
import com.sportradar.sdk.feed.liveodds.interfaces.LiveOddsTestManager;

import kz.nmbet.betradar.dao.service.PrivateLiveService;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveOddsMatchFeedListenerImpl extends LiveOddsMatchBaseListener<LiveOddsFeed>
		implements LiveOddsFeedListener {

	@Autowired
	private PrivateLiveService privateLiveService;

	private static final Logger logger = LoggerFactory.getLogger(LiveOddsMatchFeedListenerImpl.class);

	@Override
	public void onAliveReceived(LiveOddsFeed sender, AliveEntity entity) {
//		logger.info("On alive with {} matches", entity.getEventHeaders().size());
		privateLiveService.aliveReceived(entity);
	}

	@Override
	public void onScoreCardReceived(LiveOddsFeed sender, ScoreCardSummaryEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
		privateLiveService.save(entity);
		logger.info("On score card match id : {} with match time: {} number of cards : {} number of scores : {}",
				entity.getEventId(), matchTime, entity.getCardSummaryByTime().size(),
				entity.getScoreSummaryByTime().size());
	}

	@Override
	public void onClosed(LiveOddsFeed sender) {
//		logger.info("On closed");
	}

	@Override
	public void onFeedEvent(LiveOddsFeed sender, FeedEventType feedEventType) {
		logger.info("Feed event occurred. Event: {}", feedEventType);
	}

	/**
	 * Invoked by the observed live feed when the feed is opened.
	 */
	@Override
	public void onOpened(LiveOddsFeed sender) {

		logger.info("On opened");
	}

	/**
	 * Invoked when the feed is initialized and you can accept bets. It is
	 * invoked exactly once after the feed has been opened.
	 *
	 * @param sender
	 *            - originating feed
	 */
	@Override
	public void onInitialized(final LiveOddsFeed sender) {
		logger.info("On initialized");

		// Any method call can block due to request limits, we don't want to
		// block SDK dispatcher thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (sender.getTestManager() instanceof LiveOddsTestManager) {
					((LiveOddsTestManager) sender.getTestManager()).startAuto();
				}
				sender.getEventList(DateTime.now().minus(Duration.standardHours(12).getMillis()),
						DateTime.now().plus(Duration.standardHours(12).getMillis()), true);
			}
		}).start();

	}

	@Override
	public void onBetCancel(LiveOddsFeed sender, BetCancelEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
//		logger.info("On bet cancel match id : {} with match time: {}", entity.getEventId(), matchTime);
		privateLiveService.betCancel(entity);
	}

	@Override
	public void onBetCancelUndone(LiveOddsFeed sender, BetCancelUndoEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
//		logger.info("On bet cancel undone match id : {} with match time: {}", entity.getEventId(), matchTime);
		privateLiveService.betCancelUndone(entity);
	}

	@Override
	public void onBetClear(LiveOddsFeed sender, BetClearEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
//		logger.info("On bet clear match id : {} with match time: {}", entity.getEventId(), matchTime);
		privateLiveService.betClear(entity);
	}

	@Override
	public void onBetClearRollback(LiveOddsFeed sender, BetClearRollbackEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
//		logger.info("On bet clear rollback match id : {} with match time: {}", entity.getEventId(), matchTime);
		privateLiveService.betClearRollback(entity);
	}

	@Override
	public void onBetStart(LiveOddsFeed sender, BetStartEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
		logger.info("On bet start match id : {} with match time: {}", entity.getEventId(), matchTime);
		privateLiveService.startBet(entity);

	}

	@Override
	public void onBetStop(LiveOddsFeed sender, BetStopEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
		logger.info("On bet stop(artificial : {}) match id : {} with match time: {}", entity.isArtificial(),
				entity.getEventId(), matchTime);
		privateLiveService.stopBet(entity);
	}

	@Override
	public void onEventMessagesReceived(LiveOddsFeed sender, EventDataPackage entity) {
		logger.info("On match messages. Received messages count {}", entity.getMessages().size());
		privateLiveService.save(entity);
	}

	@Override
	public void onEventStatusReceived(LiveOddsFeed sender, EventDataPackage entity) {
		logger.info("On match status. Received messages count {}", entity.getMessages().size());
		privateLiveService.save(entity);
	}

	@Override
	public void onIrrelevantOddsChange(LiveOddsFeed sender, IrrelevantOddsChangeEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
		logger.info("On irrelevant odds change match id : {} with match time: {}", entity.getEventId(), matchTime);
	}

	@Override
	public void onMetaInfoReceived(LiveOddsFeed sender, MetaInfoEntity entity) {
		List<MatchHeaderInfoEntity> matches = ((LiveOddsMetaData) entity.getMetaInfoDataContainer())
				.getMatchHeaderInfos();
		logger.info("On meta info with {} matches", matches.size());
		privateLiveService.save(entity);
	}

	@Override
	public void onOddsChange(LiveOddsFeed sender, OddsChangeEntity entity) {
		Long matchTime = ((MatchHeaderEntity) entity.getEventHeader()).getMatchTime();
//		logger.info("---------On start odds change  match id : {} with match time: {}", entity.getEventId(), matchTime);
		privateLiveService.save(entity);
//		logger.info("-------- On end odds change match id : {} with match time: {}", entity.getEventId(), matchTime);
	}

}
