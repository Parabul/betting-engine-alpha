package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;
import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;
import kz.nmbet.betradar.dao.domain.types.TeamType;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.lcoo.entities.CategoryEntity;
import com.sportradar.sdk.feed.lcoo.entities.OddsEntity;
import com.sportradar.sdk.feed.lcoo.entities.OutrightEntity;
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
	private GlCategoryEntityRepository categoryEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Transactional
	public List<GlOutrightEntity> create(OutrightsEntity outrights) {
		List<GlOutrightEntity> glOutrightEntities = new ArrayList<GlOutrightEntity>();
		for (OutrightEntity entity : outrights.getOutrightEntities()) {
			glOutrightEntities.add(create(entity));
		}

		return glOutrightEntities;
	}

	@Transactional
	public GlOutrightEntity create(OutrightEntity outright) {
		GlOutrightEntity glOutrightEntity = outrightEntityRepository
				.findByOutrightId(outright.getId());
		if (glOutrightEntity != null) {
			logger.info("Already saved " + outright.getId());
			return glOutrightEntity;
		}
		logger.info("Save new " + outright.getId());
		glOutrightEntity = new GlOutrightEntity();

		glOutrightEntity.setOutrightId(outright.getId());

		glOutrightEntity.setCategory(find(outright.getCategory()));

		List<TextsEntity> competitorsTexts = outright.getFixture()
				.getCompetitors().getTexts();

		List<GlCompetitorEntity> competitors = new ArrayList<GlCompetitorEntity>();

		for (TextsEntity competitorsText : competitorsTexts) {
			competitors.add(create(competitorsText, glOutrightEntity));
		}

		glOutrightEntity.setCompetitors(competitors);

		glOutrightEntity = outrightEntityRepository.save(glOutrightEntity);

		DateTime dt = outright.getFixture().getEventInfo().getEventDate();
		glOutrightEntity.setEventDate(dt.toDate());

		List<GlOutrightOddEntity> odds = new ArrayList<GlOutrightOddEntity>();
		int oddsType = outright.getOdds().getOddsType();
		OutrightOddsType outrightOddsType = OutrightOddsType.find(oddsType);
		for (OddsEntity odd : outright.getOdds().getOdds()) {
			logger.error("----------------------------");
			logger.error(odd.toString());
			logger.error("----------------------------");
			GlOutrightOddEntity outrightOddEntity = new GlOutrightOddEntity();
			outrightOddEntity.setOddsType(outrightOddsType);
			outrightOddEntity.setOutright(glOutrightEntity);
			logger.error(" odd.getTeamId() " + odd.getId());
			try {
				outrightOddEntity.setTeamId(Long.valueOf(odd.getId()));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				outrightOddEntity.setValue(Double.valueOf(odd.getValue()));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			outrightOddEntity.setSpecialBetValue(odd.getSpecialBetValue());

		}

		glOutrightEntity.setOdds(odds);

		glOutrightEntity.setEventInfo(outright.getFixture().getEventInfo()
				.getEventName().toString());

		return glOutrightEntity;

	}

	@Transactional
	public GlCategoryEntity find(CategoryEntity category) {
		GlCategoryEntity categoryEntity = categoryEntityRepository
				.findByCategoryId(category.getId());
		if (categoryEntity == null) {
			categoryEntity = new GlCategoryEntity();
			categoryEntity.setCategoryId(category.getId());
			categoryEntity
					.setNameEn(textsEntityUtils.getDefaultValue(category));
			categoryEntity = categoryEntityRepository.save(categoryEntity);
		}
		return categoryEntity;

	}

	@Transactional
	public GlCompetitorEntity create(TextsEntity entity,
			GlOutrightEntity OutrightEntity) {
		TextEntity text = entity.getTexts().get(0);

		Integer superTeamId = text.getSuperid();
		Integer teamId = text.getTeamId();

		logger.info(MessageFormat.format("superTeamId = {0}, teamId = {1}",
				superTeamId, teamId));

		GlCompetitorEntity competitor = new GlCompetitorEntity();
		if (superTeamId != null) {
			competitor.setSuperId(superTeamId.longValue());
			GlTeamEntity team = teamService.find(competitor.getSuperId());
			competitor.setTeam(team);
		}
		if (teamId != null) {
			competitor.setTeamId(teamId.longValue());
		}

		competitor.setTitle(text.getValue());
		competitor.setOutright(OutrightEntity);
		competitor.setTeamType(TeamType.find(text.getType()));

		return competitor;

	}
}
