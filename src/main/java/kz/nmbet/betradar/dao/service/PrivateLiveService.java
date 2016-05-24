package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.liveodds.classes.EventDataPackage;
import com.sportradar.sdk.feed.liveodds.entities.common.AliveEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetCancelEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetCancelUndoEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetClearEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetClearRollbackEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetStartEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.BetStopEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.EventHeaderEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.MetaInfoEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.OddsChangeEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.OddsEntity;
import com.sportradar.sdk.feed.liveodds.entities.common.OddsFieldEntity;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.ScoreCardSummaryEntity;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

@Service
public class PrivateLiveService {

	private static final Logger logger = LoggerFactory.getLogger(PrivateLiveService.class);

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;

	@Autowired
	private GlMatchLiveOddRepository liveOddRepository;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Scheduled(fixedRate = 20000)
	@Transactional
	public void updateInactive() {
		liveOddRepository.updateInactive();
	}

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

	private Map<String, GlMatchLiveOddField> getFields(GlMatchLiveOdd matchOddEntity) {
		Map<String, GlMatchLiveOddField> fields = new HashMap<String, GlMatchLiveOddField>();
		if (matchOddEntity.getOddFields() != null) {
			for (GlMatchLiveOddField oddField : matchOddEntity.getOddFields()) {
				fields.put(oddField.getCode(), oddField);
			}
		}

		return fields;
	}

	private void updateFields(GlMatchLiveOdd matchOddEntity, Map<String, GlMatchLiveOddField> fields) {
		if (matchOddEntity.getOddFields() == null) {
			matchOddEntity.setOddFields(new ArrayList<GlMatchLiveOddField>());
		}

		for (GlMatchLiveOddField oddField : matchOddEntity.getOddFields()) {
			fields.get(oddField.getCode()).update(fields.get(oddField.getCode()));
		}
	}

	@Transactional
	public void save(OddsChangeEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());
		if (match == null)
			return;

		if (match.getLiveOdds() == null) {
			match.setLiveOdds(new HashSet<GlMatchLiveOdd>());
		}

		for (OddsEntity odd : entity.getEventOdds()) {
			GlMatchLiveOdd matchOddEntity = findOdd(match, odd.getId());

			if (matchOddEntity == null) {

				matchOddEntity = new GlMatchLiveOdd(odd, textsEntityUtils.getName(odd.getName()), match);
				matchOddEntity.setOddFields(new ArrayList<GlMatchLiveOddField>());
				if (odd.getOddFields() != null) {
					for (Entry<String, OddsFieldEntity> oddFieldEntrySet : odd.getOddFields().entrySet()) {
						matchOddEntity.getOddFields()
								.add(new GlMatchLiveOddField(oddFieldEntrySet.getValue(), oddFieldEntrySet.getKey(),
										textsEntityUtils.getName(oddFieldEntrySet.getValue().getType()),
										matchOddEntity));
					}
				}
			} else {
				matchOddEntity.update(odd);
			}
			matchOddEntity.setCheckDate(new Date());

			Map<String, GlMatchLiveOddField> oddFields = getFields(matchOddEntity);
			if (odd.getOddFields() != null) {
				for (Entry<String, OddsFieldEntity> oddFieldEntrySet : odd.getOddFields().entrySet()) {
					if (!oddFields.containsKey(oddFieldEntrySet.getKey())) {
						oddFields.put(oddFieldEntrySet.getKey(),
								new GlMatchLiveOddField(oddFieldEntrySet.getValue(), oddFieldEntrySet.getKey(),
										textsEntityUtils.getName(oddFieldEntrySet.getValue().getType()),
										matchOddEntity));
					} else {
						oddFields.get(oddFieldEntrySet.getKey()).update(oddFieldEntrySet.getValue());
					}
				}
			}
			updateFields(matchOddEntity, oddFields);
			match.getLiveOdds().add(matchOddEntity);
		}
		matchEntityRepository.save(match);
	}

	public void save(EventDataPackage entity) {
		// TODO Auto-generated method stub

	}

	public void stopBet(BetStopEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());
		if (match == null)
			return;
		match.setLiveStoped(true);
	}

	public void startBet(BetStartEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());
		if (match == null)
			return;
		match.setLiveStarted(true);
	}

	public void betClearRollback(BetClearRollbackEntity entity) {
		logger.info("#### betClearRollback");

	}

	@Transactional
	public void betClear(BetClearEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());
		if (match == null)
			return;
		for (OddsEntity odd : entity.getEventOdds()) {
			GlMatchLiveOdd matchOdd = findOdd(match, odd.getId());
			if (matchOdd == null)
				continue;
			for (Entry<String, OddsFieldEntity> oddFieldEntrySet : odd.getOddFields().entrySet()) {
				GlMatchLiveOddField matchOddField = findOddField(matchOdd, oddFieldEntrySet.getKey());
				if (matchOddField == null)
					continue;
				matchOddField.setOutcome(oddFieldEntrySet.getValue().getOutcome());
				matchOddField.setActive(false);
				matchOddField.setVoidFactor(oddFieldEntrySet.getValue().getVoidFactor());
				matchOdd.setActive(false);
			}

		}
		matchEntityRepository.save(match);
	}

	private GlMatchLiveOddField findOddField(GlMatchLiveOdd matchOdd, String key) {
		for (GlMatchLiveOddField field : matchOdd.getOddFields()) {
			if (field.getCode().equals(key)) {
				return field;
			}
		}

		return null;
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

	@Transactional
	public void aliveReceived(AliveEntity entity) {
		if (entity != null && entity.getEventHeaders() != null) {
			for (EventHeaderEntity eventHeader : entity.getEventHeaders()) {
				GlMatchEntity match = matchEntityRepository.findByMatchId(eventHeader.getEventId());
				if (match != null) {
					liveOddRepository.keepAlive(match.getId());
				}
			}
		}
	}

}
