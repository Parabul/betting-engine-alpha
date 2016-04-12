package kz.nmbet.betradar.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.repository.GlTeamEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;
import kz.nmbet.betradar.web.beans.TournamentCsvBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.lcoo.entities.PlayerEntity;

@Service
public class TeamService {

	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	@Autowired
	private GlTeamEntityRepository teamEntityRepository;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

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
}
