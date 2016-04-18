package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.sportradar.sdk.feed.common.entities.TypeValueTuple;
import com.sportradar.sdk.feed.lcoo.entities.BetEntity;
import com.sportradar.sdk.feed.lcoo.entities.BetResultEntity;
import com.sportradar.sdk.feed.lcoo.entities.MatchEntity;
import com.sportradar.sdk.feed.lcoo.entities.OddsEntity;
import com.sportradar.sdk.feed.lcoo.entities.PlayerEntity;
import com.sportradar.sdk.feed.lcoo.entities.ResultEntity;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchBetResultEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchResultEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;
import kz.nmbet.betradar.dao.domain.types.MatchOddsType;
import kz.nmbet.betradar.dao.domain.types.TeamType;
import kz.nmbet.betradar.dao.repository.GlCompetitorEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;

@Service
public class PrivateMatchService {

	private static final Logger logger = LoggerFactory.getLogger(PrivateMatchService.class);

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private GlCompetitorEntityRepository competitorEntityRepository;

	@Transactional
	public GlMatchEntity save(MatchEntity entity) {
		logger.info(entity.toString());

		GlMatchEntity glMatchEntity = matchEntityRepository.findByMatchId(entity.getMatchId());

		if (glMatchEntity == null) {
			glMatchEntity = new GlMatchEntity();
			glMatchEntity.setMatchId(entity.getMatchId());
			glMatchEntity.setCategory(teamService.find(entity.getCategory(), entity.getSport()));

			if (glMatchEntity.getCompetitors() == null)
				glMatchEntity.setCompetitors(new ArrayList<GlCompetitorEntity>());

			for (PlayerEntity player : entity.getFixture().getCompetitors().getPlayers()) {
				if (player != null)
					glMatchEntity.getCompetitors().add(create(player, glMatchEntity));
			}

		}
		updateOdds(entity, glMatchEntity);
		updateResult(entity, glMatchEntity);
		glMatchEntity = matchEntityRepository.save(glMatchEntity);

		return glMatchEntity;

	}

	private void updateResult(MatchEntity match, GlMatchEntity glMatchEntity) {
		if (glMatchEntity.getResults() == null) {
			glMatchEntity.setResults(new HashSet<GlMatchResultEntity>());
		}
		if (glMatchEntity.getBetResults() == null) {
			glMatchEntity.setBetResults(new HashSet<GlMatchBetResultEntity>());
		}
		ResultEntity result = match.getResult();
		if (result != null && result.getScoresInfo() != null)
			for (TypeValueTuple scoresInfo : result.getScoresInfo().getScores()) {
				GlMatchResultEntity matchResultEntity = new GlMatchResultEntity();

				matchResultEntity.setDecidedByFa(result.getScoresInfo().getDecidedByFa());
				matchResultEntity.setScore(scoresInfo.getValue());
				matchResultEntity.setScoreTypeValue(scoresInfo.getType());
				matchResultEntity.setMatch(glMatchEntity);
				if (result.getWinningOutcome() != null) {
					matchResultEntity.setOutcome(result.getWinningOutcome().getOutcome());
					matchResultEntity.setReason(result.getWinningOutcome().getReason());
				}
				glMatchEntity.getResults().add(matchResultEntity);

			}

		List<BetResultEntity> betResults = match.getBetResults();

		if (betResults != null)
			for (BetResultEntity betResult : betResults) {
				GlMatchBetResultEntity betResultEntity = new GlMatchBetResultEntity(betResult);
				betResultEntity.setMatch(glMatchEntity);
				glMatchEntity.getBetResults().add(betResultEntity);
			}
	}

	private void updateOdds(MatchEntity match, GlMatchEntity glMatchEntity) {
		if (glMatchEntity.getOdds() == null) {
			glMatchEntity.setOdds(new HashSet<GlMatchOddEntity>());
		}

		HashSet<GlMatchOddEntity> newOdds = new HashSet<GlMatchOddEntity>();
		if (match.getOdds() != null)
			for (BetEntity bet : match.getOdds()) {
				int oddsType = bet.getOddsType();
				MatchOddsType matchOddsType = MatchOddsType.find(oddsType);
				for (OddsEntity odd : bet.getOdds()) {
					GlMatchOddEntity matchOddEntity = new GlMatchOddEntity();
					matchOddEntity.setOddsType(matchOddsType);
					matchOddEntity.setMatch(glMatchEntity);
					matchOddEntity.setMatchOddsType(oddsType);
					matchOddEntity.setPlayerId(odd.getPlayerId());
					try {
						matchOddEntity.setValue(Double.valueOf(odd.getValue()));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					matchOddEntity.setSpecialBetValue(odd.getSpecialBetValue());
					matchOddEntity.setOutCome(odd.getOutCome());
					matchOddEntity.setOutcomeId(odd.getOutcomeId());

					newOdds.add(matchOddEntity);

				}
			}

		//SetView<GlMatchOddEntity> toDelete = Sets.difference(glMatchEntity.getOdds(), newOdds);
		SetView<GlMatchOddEntity> toAdd = Sets.difference(newOdds, glMatchEntity.getOdds());

		// ImmutableSet<GlMatchOddEntity> toDeleteCopy =
		// toDelete.immutableCopy();
		ImmutableSet<GlMatchOddEntity> toAddCopy = toAdd.immutableCopy();

		// glMatchEntity.getOdds().removeAll(toDeleteCopy);
		glMatchEntity.getOdds().addAll(toAddCopy);
		newOdds.removeAll(toAdd);

		for (GlMatchOddEntity newValue : newOdds) {
			for (GlMatchOddEntity oldValue : glMatchEntity.getOdds()) {
				if (newValue.equals(oldValue) && !oldValue.getValue().equals(newValue.getValue())) {
					oldValue.setOldValue(oldValue.getValue());
					oldValue.setValue(newValue.getValue());
				}
			}
		}

	}

	@Transactional
	public GlCompetitorEntity create(PlayerEntity playerEntity, GlMatchEntity matchEntity) {

		Integer superTeamId = playerEntity.getSuperId();
		Long teamId = playerEntity.getTeamId();
		Long id = playerEntity.getId();

		logger.info(MessageFormat.format("superTeamId = {0}, teamId = {1}, teamId = {1}", superTeamId, teamId, id));
		if (matchEntity != null && matchEntity.getId() != null) {
			GlCompetitorEntity competitor = competitorEntityRepository
					.findByMatchIdAndSuperIdAndTeamId(matchEntity.getId(), superTeamId, teamId);
			return competitor;
		}
		GlCompetitorEntity competitor = new GlCompetitorEntity();
		if (superTeamId != null) {
			competitor.setSuperId(superTeamId);
			GlTeamEntity team = teamService.find(competitor.getSuperId());
			if (team == null) {
				team = new GlTeamEntity();
				team.setSuperTeamId(superTeamId);
				team.setNameEn(playerEntity.getName().getInternational());

				team = teamService.save(team);
			}
			competitor.setTeam(team);

		}
		if (teamId != null) {
			competitor.setTeamId(teamId);
		}

		competitor.setTitle(playerEntity.getName().getInternational());
		competitor.setMatch(matchEntity);
		competitor.setTeamType(TeamType.find(playerEntity.getOrder()));

		return competitor;

	}

}
