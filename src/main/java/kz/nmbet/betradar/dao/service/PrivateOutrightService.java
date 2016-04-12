package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;
import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightResultEntity;
import kz.nmbet.betradar.dao.domain.entity.GlSportEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;
import kz.nmbet.betradar.dao.domain.types.TeamType;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;
import kz.nmbet.betradar.dao.repository.GlSportEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.lcoo.entities.CategoryEntity;
import com.sportradar.sdk.feed.lcoo.entities.OddsEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightResultEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightsEntity;
import com.sportradar.sdk.feed.lcoo.entities.SportEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;

@Service
public class PrivateOutrightService {

	private static final Logger logger = LoggerFactory.getLogger(PrivateOutrightService.class);

	@Autowired
	private GlOutrightEntityRepository outrightEntityRepository;

	@Autowired
	private GlCategoryEntityRepository categoryEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Autowired
	private GlSportEntityRepository sportEntityRepository;

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
		GlOutrightEntity glOutrightEntity = outrightEntityRepository.findByOutrightId(outright.getId());
		if (glOutrightEntity == null) {
			glOutrightEntity = new GlOutrightEntity();
			glOutrightEntity.setOutrightId(outright.getId());
			glOutrightEntity.setCategory(find(outright.getCategory(), find(outright.getSport())));
		}

		return update(outright, glOutrightEntity);

	}

	@Transactional
	public GlOutrightEntity update(OutrightEntity outright, GlOutrightEntity glOutrightEntity) {

		if (glOutrightEntity.getCompetitors() == null)
			glOutrightEntity.setCompetitors(new HashSet<GlCompetitorEntity>());

		for (TextsEntity competitorsText : outright.getFixture().getCompetitors().getTexts()) {
			GlCompetitorEntity competitor = create(competitorsText, glOutrightEntity);
			glOutrightEntity.getCompetitors().add(competitor);
		}

		DateTime dt = outright.getFixture().getEventInfo().getEventDate();
		glOutrightEntity.setEventDate(dt.toDate());

		List<GlOutrightOddEntity> odds = new ArrayList<GlOutrightOddEntity>();
		glOutrightEntity.setOdds(odds);

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

			odds.add(outrightOddEntity);

		}

		glOutrightEntity.setEventInfo(textsEntityUtils.getDefaultValue(outright.getFixture().getEventInfo()
				.getEventName()));

		List<GlOutrightResultEntity> results = new ArrayList<GlOutrightResultEntity>();
		if (outright.getResult() != null)
			for (OutrightResultEntity item : outright.getResult()) {
				GlOutrightResultEntity resultEntity = new GlOutrightResultEntity();
				resultEntity.setOutright(glOutrightEntity);
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

				results.add(resultEntity);
			}
		glOutrightEntity.setResults(results);

		glOutrightEntity = outrightEntityRepository.save(glOutrightEntity);
		return glOutrightEntity;
	}

	@Transactional
	public GlCategoryEntity find(CategoryEntity category, GlSportEntity sportEntity) {
		GlCategoryEntity categoryEntity = categoryEntityRepository.findByCategoryId(category.getId());
		if (categoryEntity == null) {
			categoryEntity = new GlCategoryEntity();
			categoryEntity.setCategoryId(category.getId());
			categoryEntity.setNameEn(textsEntityUtils.getDefaultValue(category));
			categoryEntity.setSport(sportEntity);
			categoryEntity = categoryEntityRepository.save(categoryEntity);
		}
		return categoryEntity;
	}

	@Transactional
	public GlCompetitorEntity create(TextsEntity entity, GlOutrightEntity OutrightEntity) {
		TextEntity text = entity.getTexts().get(0);

		Integer superTeamId = text.getSuperid();
		Integer teamId = text.getId();

		logger.info(MessageFormat.format("superTeamId = {0}, teamId = {1}", superTeamId, teamId));

		GlCompetitorEntity competitor = new GlCompetitorEntity();
		if (superTeamId != null) {
			competitor.setSuperId(superTeamId.intValue());
			GlTeamEntity team = teamService.find(competitor.getSuperId());
			if (team == null) {
				team = new GlTeamEntity();
				team.setSuperTeamId(superTeamId);
				team.setNameEn(textsEntityUtils.getCDefaultValue(entity.getTexts()));

				team = teamService.save(team);
			}
			competitor.setTeam(team);

		}
		if (teamId != null) {
			competitor.setTeamId(teamId.intValue());
		}

		competitor.setTitle(text.getValue());
		competitor.setOutright(OutrightEntity);
		competitor.setTeamType(TeamType.find(text.getType()));

		return competitor;

	}

	@Transactional
	public GlSportEntity find(SportEntity sport) {
		GlSportEntity sportEntity = sportEntityRepository.findBySportId(sport.getId());
		if (sportEntity == null) {
			sportEntity = new GlSportEntity();
			sportEntity.setSportId(sport.getId());
			sportEntity.setNameEn(textsEntityUtils.getDefaultValue(sport));
			sportEntity = sportEntityRepository.save(sportEntity);
		}
		return sportEntity;

	}
}
