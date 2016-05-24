package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.lcoo.entities.CategoryEntity;
import com.sportradar.sdk.feed.lcoo.entities.PlayerEntity;
import com.sportradar.sdk.feed.lcoo.entities.SportEntity;
import com.sportradar.sdk.feed.lcoo.entities.TournamentEntity;

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
	public void initData(Iterable<CSVRecord> records) {
		boolean first = true;
		for (CSVRecord record : records) {
			if (first) {
				first = false;
				continue;
			}
			TournamentCsvBean bean = new TournamentCsvBean(record);
			if (bean.isNotEmpty()) {
				create(bean);
				GlSportEntity sport = createSport(bean);
				GlCategoryEntity category = createCategory(bean, sport);
				createTournament(bean, category);
			}
		}

	}

	@Transactional
	public void initDataForce(Iterable<CSVRecord> records) {
		boolean first = true;
		Map<Integer, GlTeamEntity> teams = new HashMap<>();
		Map<Integer, GlSportEntity> sports = new HashMap<>();
		Map<Long, TournamentCsvBean> categories = new HashMap<>();
		Map<Integer, TournamentCsvBean> tournaments = new HashMap<>();
		List<GlCategoryEntity> categoryEntities = new ArrayList<>();
		List<GlTournamentEntity> tournamentEntities = new ArrayList<>();
		for (CSVRecord record : records) {
			if (first) {
				first = false;
				continue;
			}
			TournamentCsvBean bean = new TournamentCsvBean(record);

			if (bean.isNotEmpty()) {

				GlTeamEntity teamEntity = new GlTeamEntity();
				teamEntity.setSuperTeamId(bean.getSuperTeamId());
				teamEntity.setNameRu(bean.getTeamName());
				teams.put(bean.getSuperTeamId(), teamEntity);

				if (!sports.containsKey(bean.getSportId())) {
					GlSportEntity sport = new GlSportEntity();
					sport.setSportId(bean.getSportId());
					sport.setNameRu(bean.getSport());
					sports.put(sport.getSportId(), sport);
				}

				if (!categories.containsKey(bean.getCategoryId())) {
					categories.put(bean.getCategoryId(), bean);
				}
				if (!tournaments.containsKey(bean.getTournamentId())) {
					tournaments.put(bean.getTournamentId(), bean);
				}
			}
		}
		logger.info("start save teams " + teams.values().size());
		teamEntityRepository.save(teams.values());
		logger.info("start save sports " + sports.values().size());
		List<GlSportEntity> sportEntities = sportEntityRepository.save(sports.values());

		for (Entry<Long, TournamentCsvBean> categoryBean : categories.entrySet()) {
			GlSportEntity sport = null;
			for (GlSportEntity sportEntity : sportEntities) {
				if (sportEntity.getSportId().equals(categoryBean.getValue().getSportId())) {
					sport = sportEntity;
				}
			}
			categoryEntities.add(createCategory(categoryBean.getValue(), sport));
		}
		logger.info("categoryEntities saved " + categoryEntities.size());
		for (Entry<Integer, TournamentCsvBean> tournamentBean : tournaments.entrySet()) {
			GlCategoryEntity category = null;
			for (GlCategoryEntity categoryEntity : categoryEntities) {
				if (categoryEntity.getCategoryId().equals(tournamentBean.getValue().getCategoryId())) {
					category = categoryEntity;
				}
			}
			tournamentEntities.add(createTournament(tournamentBean.getValue(), category));
		}
		logger.info("tournamentBean saved " + tournamentEntities.size());
	}

	@Transactional
	public GlTeamEntity create(TournamentCsvBean csvBean) {
		GlTeamEntity teamEntity = teamEntityRepository.findBySuperTeamId(csvBean.getSuperTeamId());
		if (teamEntity == null) {
			teamEntity = new GlTeamEntity();
			teamEntity.setSuperTeamId(csvBean.getSuperTeamId());
		}

		teamEntity.setNameRu(csvBean.getTeamName());

		teamEntity = teamEntityRepository.save(teamEntity);
		return teamEntity;

	}

	@Transactional
	public GlSportEntity createSport(TournamentCsvBean csvBean) {
		GlSportEntity sport = sportEntityRepository.findBySportId(csvBean.getSportId());
		if (sport == null) {
			sport = new GlSportEntity();
			sport.setSportId(csvBean.getSportId());
		}

		sport.setNameRu(csvBean.getSport());

		sport = sportEntityRepository.save(sport);
		return sport;
	}

	@Transactional
	public GlCategoryEntity createCategory(TournamentCsvBean csvBean, GlSportEntity sport) {
		GlCategoryEntity category = categoryEntityRepository.findByCategoryId(csvBean.getCategoryId());
		if (category == null) {
			category = new GlCategoryEntity();
			category.setCategoryId(csvBean.getCategoryId());
		}

		category.setNameRu(csvBean.getCategory());
		category.setSport(sport);
		category = categoryEntityRepository.save(category);
		return category;
	}

	@Transactional
	public GlTournamentEntity createTournament(TournamentCsvBean csvBean, GlCategoryEntity category) {
		GlTournamentEntity tournament = tournamentEntityRepository.findByTournamentId(csvBean.getTournamentId());
		if (tournament == null) {
			tournament = new GlTournamentEntity();
			tournament.setTournamentId(csvBean.getTournamentId());
		}

		tournament.setNameRu(csvBean.getTournament());
		tournament.setCategory(category);

		tournament = tournamentEntityRepository.save(tournament);
		return tournament;
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
