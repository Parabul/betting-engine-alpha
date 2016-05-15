package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.common.entities.TypeValueTuple;
import com.sportradar.sdk.feed.lcoo.entities.BetEntity;
import com.sportradar.sdk.feed.lcoo.entities.BetResultEntity;
import com.sportradar.sdk.feed.lcoo.entities.CardEntity;
import com.sportradar.sdk.feed.lcoo.entities.CardsEntity;
import com.sportradar.sdk.feed.lcoo.entities.CornerEntity;
import com.sportradar.sdk.feed.lcoo.entities.CornersEntity;
import com.sportradar.sdk.feed.lcoo.entities.GoalEntity;
import com.sportradar.sdk.feed.lcoo.entities.GoalsEntity;
import com.sportradar.sdk.feed.lcoo.entities.MatchEntity;
import com.sportradar.sdk.feed.lcoo.entities.OddsEntity;
import com.sportradar.sdk.feed.lcoo.entities.ResultEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;
import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchBetResultEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchCardsEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchCornersEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchGoalsEntity;
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

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Transactional
	public GlMatchEntity save(MatchEntity entity) {
		logger.info(entity.toString());

		GlMatchEntity glMatchEntity = matchEntityRepository.findByMatchId(entity.getMatchId());

		if (glMatchEntity == null) {
			glMatchEntity = new GlMatchEntity();
			glMatchEntity.setMatchId(entity.getMatchId());
			GlCategoryEntity category = teamService.find(entity.getCategory(), entity.getSport());

			glMatchEntity.setTournament(teamService.find(entity.getTournament(), category));

			if (glMatchEntity.getCompetitors() == null)
				glMatchEntity.setCompetitors(new ArrayList<GlCompetitorEntity>());

			for (TextsEntity competitor : entity.getFixture().getCompetitors().getTexts()) {

				if (competitor != null) {
					logger.info(competitor.toString());
					glMatchEntity.getCompetitors().add(create(competitor, glMatchEntity));
				}
			}

		}
		DateTime dt = entity.getFixture().getDateInfo().getMatchDate();
		glMatchEntity.setEventDate(dt.toDate());

		glMatchEntity.setActive(entity.getFixture().getStatusInfo().isActive());

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
		if (glMatchEntity.getGoals() == null) {
			glMatchEntity.setGoals(new HashSet<GlMatchGoalsEntity>());
		}
		if (glMatchEntity.getCards() == null) {
			glMatchEntity.setCards(new HashSet<GlMatchCardsEntity>());
		}
		if (glMatchEntity.getCorners() == null) {
			glMatchEntity.setCorners(new HashSet<GlMatchCornersEntity>());
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

		GoalsEntity goals = match.getGoals();
		if (goals != null && goals.getGoals() != null)
			for (GoalEntity goal : goals.getGoals()) {

				GlMatchGoalsEntity goalsEntity = new GlMatchGoalsEntity(goal);
				goalsEntity.setMatch(glMatchEntity);
				glMatchEntity.getGoals().add(goalsEntity);
			}

		CardsEntity cards = match.getCards();

		if (cards != null && cards.getCards() != null)
			for (CardEntity card : cards.getCards()) {
				GlMatchCardsEntity cardsEntity = new GlMatchCardsEntity(card);
				cardsEntity.setMatch(glMatchEntity);
				glMatchEntity.getCards().add(cardsEntity);
			}

		CornersEntity corners = match.getCorners();

		if (corners != null && corners.getCorners() != null)
			for (CornerEntity corner : corners.getCorners()) {
				GlMatchCornersEntity cornersEntity = new GlMatchCornersEntity(corner);
				cornersEntity.setMatch(glMatchEntity);
				glMatchEntity.getCorners().add(cornersEntity);
			}
	}

	private GlMatchOddEntity findOdd(GlMatchEntity glMatchEntity, Integer oddsType, String outCome,
			String specialBetValue) {
		for (GlMatchOddEntity odd : glMatchEntity.getOdds()) {
			if (specialBetValue != null) {
				if (oddsType.equals(odd.getMatchOddsType())
						&& specialBetValue.equalsIgnoreCase(odd.getSpecialBetValue())
						&& outCome.equalsIgnoreCase(odd.getOutCome())) {
					return odd;
				}
			} else {
				if (odd.getSpecialBetValue() == null && oddsType.equals(odd.getMatchOddsType())
						&& outCome.equalsIgnoreCase(odd.getOutCome())) {
					return odd;
				}
			}
		}
		return null;
	}

	private void updateOdds(MatchEntity match, GlMatchEntity glMatchEntity) {
		if (glMatchEntity.getOdds() == null) {
			glMatchEntity.setOdds(new HashSet<GlMatchOddEntity>());
		}
		// delete existed odds
		for (GlMatchOddEntity odd : glMatchEntity.getOdds()) {
			odd.setDeleted(true);
		}
		// HashSet<GlMatchOddEntity> newOdds = new HashSet<GlMatchOddEntity>();
		if (match.getOdds() != null) {
			for (BetEntity bet : match.getOdds()) {
				int oddsType = bet.getOddsType();
				MatchOddsType matchOddsType = MatchOddsType.find(oddsType);
				for (OddsEntity odd : bet.getOdds()) {
					GlMatchOddEntity matchOddEntity = findOdd(glMatchEntity, oddsType, odd.getOutCome(),
							odd.getSpecialBetValue());
					if (matchOddEntity == null) {
						matchOddEntity = new GlMatchOddEntity();
						matchOddEntity.setOddsType(matchOddsType);
						matchOddEntity.setMatch(glMatchEntity);
						matchOddEntity.setMatchOddsType(oddsType);
						matchOddEntity.setPlayerId(odd.getPlayerId());
						matchOddEntity.setSpecialBetValue(odd.getSpecialBetValue());
						matchOddEntity.setOutCome(odd.getOutCome());
						matchOddEntity.setOutcomeId(odd.getOutcomeId());
						matchOddEntity.setDeleted(false);
					}
					matchOddEntity.setOldValue(matchOddEntity.getValue());
					if (odd.getValue().equalsIgnoreCase("OFF")) {
						matchOddEntity.setDeleted(true);
					} else {
						try {

							matchOddEntity.setValue(Double.valueOf(odd.getValue()));
							matchOddEntity.setDeleted(false);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
					if (matchOddEntity.getId() == null) {
						glMatchEntity.getOdds().add(matchOddEntity);
					}
				}
			}
		}
	}

	@Transactional
	public GlCompetitorEntity create(TextsEntity competitorTexts, GlMatchEntity matchEntity) {
		TextEntity teamData = competitorTexts.getTexts().get(0);
		Integer superTeamId = teamData.getSuperid();
		Integer teamId = teamData.getId();
		String name = textsEntityUtils.getCDefaultValue(teamData.getText());

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
				team.setNameEn(name);

				team = teamService.save(team);
			}
			competitor.setTeam(team);

		}
		if (teamId != null) {
			competitor.setTeamId(teamId);
		}

		competitor.setTitle(name);
		competitor.setMatch(matchEntity);
		competitor.setTeamType(TeamType.find(teamData.getType()));

		return competitor;

	}

}
