package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;
import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlSportEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.types.TeamType;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlSportEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.lcoo.entities.CategoryEntity;
import com.sportradar.sdk.feed.lcoo.entities.MatchEntity;
import com.sportradar.sdk.feed.lcoo.entities.SportEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;

@Service
public class PrivateMatchService {

	private static final Logger logger = LoggerFactory.getLogger(PrivateMatchService.class);

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Transactional
	public GlMatchEntity save(MatchEntity entity) {
		logger.info(entity.toString());

		GlMatchEntity glMatchEntity = matchEntityRepository.findByMatchId(entity.getMatchId());

		if (glMatchEntity == null) {
			glMatchEntity = new GlMatchEntity();
			glMatchEntity.setMatchId(entity.getMatchId());			
			glMatchEntity.setCategory(teamService.find(entity.getCategory(), entity.getSport()));

			List<TextsEntity> competitorsTexts = entity.getFixture().getCompetitors().getTexts();

			List<GlCompetitorEntity> competitors = new ArrayList<GlCompetitorEntity>();

			for (TextsEntity competitorsText : competitorsTexts) {
				competitors.add(create(competitorsText, glMatchEntity));
			}

			glMatchEntity.setCompetitors(competitors);
		}

		glMatchEntity = matchEntityRepository.save(glMatchEntity);

		return glMatchEntity;

	}

	@Transactional
	public GlCompetitorEntity create(TextsEntity entity, GlMatchEntity matchEntity) {
		TextEntity text = entity.getTexts().get(0);

		Integer superTeamId = text.getSuperid();
		Integer teamId = text.getTeamId();
		Integer id = text.getId();

		logger.info(MessageFormat.format("superTeamId = {0}, teamId = {1} , id = {2}", superTeamId, teamId, id));

		GlCompetitorEntity competitor = new GlCompetitorEntity();
		if (superTeamId != null) {
			competitor.setSuperId(superTeamId.intValue());
			GlTeamEntity team = teamService.find(competitor.getSuperId());
			competitor.setTeam(team);
		}
		if (teamId != null) {
			competitor.setTeamId(teamId.intValue());
		}

		competitor.setTitle(text.getValue());
		competitor.setMatch(matchEntity);
		competitor.setTeamType(TeamType.find(text.getType()));

		return competitor;

	}
}
