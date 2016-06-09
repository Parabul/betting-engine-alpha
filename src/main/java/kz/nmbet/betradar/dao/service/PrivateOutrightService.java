package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightResultEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;
import kz.nmbet.betradar.dao.domain.types.TeamType;
import kz.nmbet.betradar.dao.repository.GlCompetitorEntityRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.sportradar.sdk.feed.lcoo.entities.OddsEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightResultEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightsEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;

@Service
public class PrivateOutrightService {

	private static final Logger logger = LoggerFactory
			.getLogger(PrivateOutrightService.class);

	@Autowired
	private GlOutrightEntityRepository outrightEntityRepository;

	@Autowired
	private GlCompetitorEntityRepository competitorEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Transactional
	public List<GlOutrightEntity> create(OutrightsEntity outrights) {
		List<GlOutrightEntity> glOutrightEntities = new ArrayList<GlOutrightEntity>();
		for (OutrightEntity entity : outrights.getOutrightEntities()) {
			glOutrightEntities.add(save(entity));
		}

		return glOutrightEntities;
	}

	@Transactional
	public GlOutrightEntity save(OutrightEntity outright) {
		GlOutrightEntity glOutrightEntity = outrightEntityRepository
				.findByOutrightId(outright.getId());
		if (glOutrightEntity == null) {
			glOutrightEntity = new GlOutrightEntity();
			glOutrightEntity.setOutrightId(outright.getId());
			glOutrightEntity.setCategory(teamService.find(
					outright.getCategory(), outright.getSport()));
			glOutrightEntity.setEventInfo(textsEntityUtils
					.getDefaultValue(outright.getFixture().getEventInfo()
							.getEventName()));
		}

		return update(outright, glOutrightEntity);

	}

	private void updateCompetitors(OutrightEntity outright,
			GlOutrightEntity glOutrightEntity) {
		if (glOutrightEntity.getCompetitors() == null) {
			glOutrightEntity.setCompetitors(new HashSet<GlCompetitorEntity>());

		}
		for (GlCompetitorEntity competitor : glOutrightEntity.getCompetitors()) {
			competitor.setDeleted(true);
		}

		for (TextsEntity competitor : outright.getFixture().getCompetitors()
				.getTexts()) {
			GlCompetitorEntity competitorEntity = create(competitor,
					glOutrightEntity);
			competitorEntity.setDeleted(false);
			if (competitorEntity.getId() == null) {
				glOutrightEntity.getCompetitors().add(competitorEntity);
			}
		}

	}

	private void updateOdds(OutrightEntity outright,
			GlOutrightEntity glOutrightEntity) {
		if (glOutrightEntity.getOdds() == null) {
			glOutrightEntity.setOdds(new HashSet<GlOutrightOddEntity>());
		}

		HashSet<GlOutrightOddEntity> newOdds = new HashSet<GlOutrightOddEntity>();
		if (outright.getOdds() == null)
			return;
		int oddsType = outright.getOdds().getOddsType();
		OutrightOddsType outrightOddsType = OutrightOddsType.find(oddsType);

		for (OddsEntity odd : outright.getOdds().getOdds()) {
			GlOutrightOddEntity outrightOddEntity = new GlOutrightOddEntity();
			outrightOddEntity.setOddsType(outrightOddsType);
			outrightOddEntity.setOutright(glOutrightEntity);

			try {
				outrightOddEntity.setTeamId(Integer.valueOf(odd.getId()));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			try {
				outrightOddEntity.setValue(Double.valueOf(odd.getValue()));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			outrightOddEntity.setSpecialBetValue(odd.getSpecialBetValue());
			outrightOddEntity.setOutCome(odd.getOutCome());
			outrightOddEntity.setOutcomeId(odd.getOutcomeId());

			newOdds.add(outrightOddEntity);

		}

		SetView<GlOutrightOddEntity> toDelete = Sets.difference(
				glOutrightEntity.getOdds(), newOdds);
		SetView<GlOutrightOddEntity> toAdd = Sets.difference(newOdds,
				glOutrightEntity.getOdds());

		ImmutableSet<GlOutrightOddEntity> toDeleteCopy = toDelete
				.immutableCopy();
		ImmutableSet<GlOutrightOddEntity> toAddCopy = toAdd.immutableCopy();

		for (GlOutrightOddEntity glOutrightOddEntity : toDeleteCopy) {
			glOutrightOddEntity.setDeleted(true);
		}
		glOutrightEntity.getOdds().addAll(toAddCopy);
		newOdds.removeAll(toAdd);

		for (GlOutrightOddEntity newValue : newOdds) {
			for (GlOutrightOddEntity oldValue : glOutrightEntity.getOdds()) {
				if (newValue.equals(oldValue)
						&& !oldValue.getValue().equals(newValue.getValue())) {
					oldValue.setOldValue(oldValue.getValue());
					oldValue.setValue(newValue.getValue());
				}
			}
		}

	}

	private void updateResult(OutrightEntity outright,
			GlOutrightEntity glOutrightEntity) {

		if (glOutrightEntity.getResults() == null) {
			glOutrightEntity.setResults(new HashSet<GlOutrightResultEntity>());
		}

		if (outright.getResult() != null) {
			for (OutrightResultEntity item : outright.getResult()) {
				GlOutrightResultEntity resultEntity = new GlOutrightResultEntity();
				resultEntity.setOutright(glOutrightEntity);
				if (item.getValue().equals("Cancelled")
						|| item.getId().equals("C")) {
					glOutrightEntity.setCancelled(true);
					continue;
				}
				try {
					resultEntity.setResult(Integer.valueOf(item.getValue()));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					resultEntity.setTeamId(Integer.valueOf(item.getId()));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				resultEntity.setDeadHeatFactor(item.getDeadHeatFactor());

				glOutrightEntity.getResults().add(resultEntity);
			}
		}
	}

	@Transactional
	public GlOutrightEntity update(OutrightEntity outright,
			GlOutrightEntity glOutrightEntity) {

		updateCompetitors(outright, glOutrightEntity);

		DateTime dt = outright.getFixture().getEventInfo().getEventDate();
		glOutrightEntity.setEventDate(dt.toDate());

		updateOdds(outright, glOutrightEntity);
		updateResult(outright, glOutrightEntity);

		glOutrightEntity = outrightEntityRepository.save(glOutrightEntity);
		return glOutrightEntity;
	}

	@Transactional
	public GlCompetitorEntity create(TextsEntity competitorTexts,
			GlOutrightEntity outrightEntity) {
		TextEntity teamData = competitorTexts.getTexts().get(0);
		Integer superTeamId = teamData.getSuperid();
		Integer teamId = teamData.getId();
		String name = textsEntityUtils.getCDefaultValue(teamData.getText());

		if (outrightEntity != null && outrightEntity.getId() != null) {
			GlCompetitorEntity competitor = competitorEntityRepository
					.findByOutrightIdAndSuperIdAndTeamId(
							outrightEntity.getId(), superTeamId, teamId);
			if (competitor != null)
				return competitor;
		}
		GlCompetitorEntity competitor = new GlCompetitorEntity();
		if (superTeamId != null) {
			competitor.setSuperId(superTeamId);
			GlTeamEntity team = teamService.find(competitor.getSuperId());
			if (team == null) {
				team = new GlTeamEntity();
				team.setSuperTeamId(superTeamId);
				team.setNameEn(name);

				team = teamService.save(team);
			}
			competitor.setTeam(team);

		}
		if (teamId != null) {
			competitor.setTeamId(teamId);
		}

		competitor.setTitle(name);
		competitor.setOutright(outrightEntity);
		competitor.setTeamType(TeamType.find(teamData.getType()));

		return competitor;

	}

}
