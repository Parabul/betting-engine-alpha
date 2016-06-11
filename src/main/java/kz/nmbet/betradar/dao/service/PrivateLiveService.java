package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.common.entities.IdNameTuple;
import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;
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
import com.sportradar.sdk.feed.liveodds.entities.liveodds.LiveOddsMetaData;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.MatchHeaderInfoEntity;
import com.sportradar.sdk.feed.liveodds.entities.liveodds.ScoreCardSummaryEntity;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.types.TeamType;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddRepository;
import kz.nmbet.betradar.dao.repository.GlTournamentEntityRepository;
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

	@Autowired
	private GlTournamentEntityRepository tournamentEntityRepository;

	@Autowired
	private TeamService teamService;

	// @Scheduled(fixedRate = 20000)
	@Transactional
	public void updateInactive() {
		liveOddRepository.updateInactive();
	}

	@Transactional
	public void save(MetaInfoEntity entity) {
		LiveOddsMetaData data = (LiveOddsMetaData) entity.getMetaInfoDataContainer();
		GlMatchEntity match = matchEntityRepository
				.findByMatchId(data.getMatchHeaderInfos().get(0).getMatchHeader().getEventId());
		if (match == null) {
			match = new GlMatchEntity();
			match.setActive(true);
			match.setLiveStarted(true);
			match.setMatchId(data.getMatchHeaderInfos().get(0).getMatchHeader().getEventId());
		}

		for (MatchHeaderInfoEntity header : data.getMatchHeaderInfos()) {

			if (match.getCompetitors() == null) {
				match.setCompetitors(new ArrayList<GlCompetitorEntity>());
				try {
					match.getCompetitors().add(create(header.getMatchInfo().getAwayTeam(), TeamType.AWAY, match));
					match.getCompetitors().add(create(header.getMatchInfo().getHomeTeam(), TeamType.HOME, match));
					match.setTournament(tournamentEntityRepository
							.findByTournamentId(header.getMatchInfo().getTournament().getUniqueId().intValue()));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			}
		}
		matchEntityRepository.save(match);

	}

	private GlCompetitorEntity create(IdNameTuple awayTeam, TeamType teamType, GlMatchEntity matchEntity) {
		GlCompetitorEntity competitor = new GlCompetitorEntity();
		GlTeamEntity team = teamService.find(awayTeam.getUniqueId().intValue());
		competitor.setTeam(team);
		competitor.setTitle(textsEntityUtils.getName(awayTeam.getName()));
		competitor.setMatch(matchEntity);
		competitor.setTeamType(teamType);
		return competitor;
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
		if (match == null) {
			match = new GlMatchEntity();
			match.setActive(true);
			match.setLiveStarted(true);
			match.setMatchId(entity.getEventId().getEventId());
		}

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
		match.setLiveCheckDate(new Date());
		matchEntityRepository.save(match);
	}

	public void save(EventDataPackage entity) {
		// TODO Auto-generated method stub

	}

	@Transactional
	public void stopBet(BetStopEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());
		if (match == null)
			return;
		match.setLiveStoped(true);
		matchEntityRepository.save(match);
	}

	@Transactional
	public void startBet(BetStartEntity entity) {
		GlMatchEntity match = matchEntityRepository.findByMatchId(entity.getEventId().getEventId());
		if (match == null)
			return;
		match.setLiveCheckDate(new Date());
		match.setLiveStarted(true);
		matchEntityRepository.save(match);
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
		logger.info("###ScoreCardSummaryEntity###" + entity.toString());

	}

	@Transactional
	public void aliveReceived(AliveEntity entity) {
		if (entity != null && entity.getEventHeaders() != null) {
			Date currentDate = new Date();
			for (EventHeaderEntity eventHeader : entity.getEventHeaders()) {
				GlMatchEntity match = matchEntityRepository.findByMatchId(eventHeader.getEventId());
				if (match != null) {
					match.setLiveCheckDate(currentDate);
					match.setLiveStarted(true);
					matchEntityRepository.save(match);
				}
			}
		}
	}

}
