package kz.nmbet.betradar.dao.service;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.liveodds.classes.EventDataPackage;
import com.sportradar.sdk.feed.liveodds.entities.common.AliveEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetCancelEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetCancelUndoEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetClearEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetClearRollbackEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetStartEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetStopEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.MetaInfoEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.OddsChangeEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.OddsEntity;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.ScoreCardSummaryEntity;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.repository.GlCompetitorEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

@Service
public class PrivateLiveService {

	private static final Logger logger = LoggerFactory.getLogger(PrivateLiveService.class);

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private GlCompetitorEntityRepository competitorEntityRepository;

	@Autowired
	private GlMatchLiveOddRepository liveOddRepository;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	public void save(MetaInfoEntity entity) {
		// TODO Auto-generated method stub

	}

	private GlMatchLiveOdd findOdd(GlMatchEntity glMatchEntity, Long betradarId) {
		for (GlMatchLiveOdd odd : glMatchEntity.getLiveOdds()) {
			if (betradarId.equals(odd.getBetradarId())) {
				return odd;
			}
		}
		return null;
	}

	public void save(OddsChangeEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());

		entity.getEventOdds();
		if (match.getLiveOdds() == null) {
			match.setLiveOdds(new HashSet<GlMatchLiveOdd>());
		}

		// HashSet<GlMatchOddEntity> newOdds = new HashSet<GlMatchOddEntity>();
//		OddsFieldEntity
//		for (OddsEntity odd : entity.getEventOdds()) {
//			GlMatchLiveOdd matchOddEntity = findOdd(match, odd.getId());
//			if (matchOddEntity == null) {
//				matchOddEntity = new GlMatchLiveOdd();
//				matchOddEntity.setBetradarId(odd.getId());
//				matchOddEntity.setOddsType(odd.get);
//				matchOddEntity.setMatch(glMatchEntity);
//				matchOddEntity.setMatchOddsType(oddsType);
//				matchOddEntity.setPlayerId(odd.getPlayerId());
//				matchOddEntity.setSpecialBetValue(odd.getSpecialBetValue());
//				matchOddEntity.setOutCome(odd.getOutCome());
//				matchOddEntity.setOutcomeId(odd.getOutcomeId());
//				matchOddEntity.setDeleted(false);
//			}
//			matchOddEntity.setOldValue(matchOddEntity.getValue());
//			if (odd.getValue().equalsIgnoreCase("OFF")) {
//				matchOddEntity.setDeleted(true);
//			} else {
//				try {
//
//					matchOddEntity.setValue(Double.valueOf(odd.getValue()));
//					matchOddEntity.setDeleted(false);
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
//			if (matchOddEntity.getId() == null) {
//				glMatchEntity.getOdds().add(matchOddEntity);
//			}
		}
	

	

	public void save(EventDataPackage entity) {
		// TODO Auto-generated method stub

	}

	public void stopBet(BetStopEntity entity) {
		// TODO Auto-generated method stub

	}

	public void startBet(BetStartEntity entity) {
		// TODO Auto-generated method stub

	}

	public void betClearRollback(BetClearRollbackEntity entity) {
		// TODO Auto-generated method stub

	}

	public void betClear(BetClearEntity entity) {
		// TODO Auto-generated method stub

	}

	public void betCancelUndone(BetCancelUndoEntity entity) {
		// TODO Auto-generated method stub

	}

	public void betCancel(BetCancelEntity entity) {
		// TODO Auto-generated method stub

	}

	public void save(ScoreCardSummaryEntity entity) {
		// TODO Auto-generated method stub

	}

	public void aliveReceived(AliveEntity entity) {
		// TODO Auto-generated method stub

	}

}
