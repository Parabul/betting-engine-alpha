package kz.nmbet.betradar.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;
import kz.nmbet.betradar.dao.domain.entity.GlSportEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTournamentEntity;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlSportEntityRepository;
import kz.nmbet.betradar.dao.repository.GlTeamEntityRepository;
import kz.nmbet.betradar.dao.repository.GlTournamentEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;
import kz.nmbet.betradar.web.beans.TournamentCsvBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.lcoo.entities.CategoryEntity;
import com.sportradar.sdk.feed.lcoo.entities.PlayerEntity;
import com.sportradar.sdk.feed.lcoo.entities.SportEntity;
import com.sportradar.sdk.feed.lcoo.entities.TournamentEntity;

@Service
public class TeamService {

	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	@Autowired
	private GlTeamEntityRepository teamEntityRepository;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Autowired
	private GlSportEntityRepository sportEntityRepository;

	@Autowired
	private GlCategoryEntityRepository categoryEntityRepository;

	@Autowired
	private GlTournamentEntityRepository tournamentEntityRepository;

	@Transactional
	public GlTeamEntity create(TournamentCsvBean csvBean) {
		GlTeamEntity teamEntity = teamEntityRepository.findBySuperTeamId(csvBean.getSuperTeamId());
		if (teamEntity != null) {
			logger.info("Already saved teamEntity " + csvBean.getSuperTeamId());
			return teamEntity;
		}
		logger.info("Save new " + csvBean.getSuperTeamId());
		teamEntity = new GlTeamEntity();

		teamEntity.setNameRu(csvBean.getTeamName());
		teamEntity.setSuperTeamId(csvBean.getSuperTeamId());

		teamEntity = teamEntityRepository.save(teamEntity);
		return teamEntity;

	}

	@Transactional
	public GlTeamEntity find(Integer id) {
		GlTeamEntity teamEntity = teamEntityRepository.findBySuperTeamId(id);
		return teamEntity;
	}

	@Transactional
	public GlTeamEntity find(PlayerEntity playerEntity) {
		GlTeamEntity teamEntity = teamEntityRepository.findBySuperTeamId(playerEntity.getSuperId());
		if (teamEntity != null) {
			return teamEntity;
		}
		teamEntity = new GlTeamEntity();

		teamEntity.setNameRu(playerEntity.getName().getInternational());
		teamEntity.setSuperTeamId(playerEntity.getSuperId());

		teamEntity = teamEntityRepository.save(teamEntity);
		return teamEntity;

	}

	@Transactional
	public void save(List<GlTeamEntity> entities) {
		teamEntityRepository.save(entities);

	}

	@Transactional
	public GlTeamEntity save(GlTeamEntity entity) {
		return teamEntityRepository.save(entity);
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

	@Transactional
	public GlCategoryEntity find(CategoryEntity category, SportEntity sport) {
		GlSportEntity sportEntity = find(sport);
		return find(category, sportEntity);
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
	public GlTournamentEntity find(TournamentEntity tournamentEntity, GlCategoryEntity categoryEntity) {
		if (tournamentEntity != null) {
			int id = tournamentEntity.getId();
			GlTournamentEntity glTournamentEntity = tournamentEntityRepository.findByTournamentId(id);
			if (glTournamentEntity == null) {
				glTournamentEntity = new GlTournamentEntity();
				glTournamentEntity.setTournamentId(id);
				glTournamentEntity.setNameEn(textsEntityUtils.getCDefaultValue(tournamentEntity.getTexts()));
				glTournamentEntity.setCategory(categoryEntity);
				glTournamentEntity = tournamentEntityRepository.save(glTournamentEntity);
			}
			return glTournamentEntity;
		}
		return null;
	}
}
