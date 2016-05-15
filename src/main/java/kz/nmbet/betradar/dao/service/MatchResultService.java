package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportradar.sdk.feed.common.entities.HomeAway;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchGoalsEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchResultEntity;
import kz.nmbet.betradar.dao.domain.types.ScoreType;
import kz.nmbet.betradar.dao.domain.types.ThreeWaysOutComeType;
import kz.nmbet.betradar.dao.domain.types.TotalsOutComeType;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchOddEntityRepository;
import kz.nmbet.betradar.web.beans.ThreeWayOdds;

@Service
public class MatchResultService {

	private static final Logger logger = LoggerFactory.getLogger(MatchResultService.class);

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;
	@Autowired
	private GlMatchOddEntityRepository oddEntityRepository;

	private String getScore(GlMatchEntity match, ScoreType scoreType) {
		logger.info("getScore " + scoreType.getType());
		for (GlMatchResultEntity result : match.getResults()) {
			if (scoreType.getType().equals(result.getScoreTypeValue())) {
				return result.getScore();
			}
		}
		return null;
	}

	@Transactional
	public List<Integer> check(Integer id) {
		List<Integer> odds = new ArrayList<>();
		List<GlMatchOddEntity> changed = new ArrayList<>();
		GlMatchEntity match = matchEntityRepository.findOne(id);
		Set<GlMatchResultEntity> results = match.getResults();
		if (results != null && results.size() > 0) {
			String fullTimeScore = getScore(match, ScoreType.TYPE_FT);
			Integer homeFullTimeScore = null;
			Integer awayFullTimeScore = null;
			Integer total = null;
			if (StringUtils.isNotBlank(fullTimeScore)) {
				homeFullTimeScore = Integer.parseInt(StringUtils.split(fullTimeScore, ":")[0]);
				awayFullTimeScore = Integer.parseInt(StringUtils.split(fullTimeScore, ":")[1]);
				total = homeFullTimeScore + awayFullTimeScore;
			}
			Set<GlMatchGoalsEntity> goals = match.getGoals();
			GlMatchGoalsEntity firstGoal = null;
			for (GlMatchGoalsEntity goal : goals) {
				if (firstGoal == null || firstGoal.getGoalTime().compareTo(goal.getGoalTime()) > 0) {
					firstGoal = goal;

				}
			}
			if (firstGoal != null)
				logger.info("firstGoal " + firstGoal.getGoalTime());

			String overTimeScore = getScore(match, ScoreType.TYPE_OT);
			logger.info("fullTimeScore " + fullTimeScore);
			for (GlMatchOddEntity odd : match.getOdds()) {
				switch (odd.getMatchOddsType()) {
				case 2:
					if (StringUtils.isNotBlank(fullTimeScore)) {
						odd.setOddResult(odd.getOutCome().equals(fullTimeScore));
						odds.add(odd.getId());
						changed.add(odd);
					}
					break;
				case 10:
				case 381:
					if (homeFullTimeScore != null && awayFullTimeScore != null) {
						ThreeWaysOutComeType oddResult = null;
						if (homeFullTimeScore > awayFullTimeScore) {
							oddResult = ThreeWaysOutComeType.HOME;
						} else if (homeFullTimeScore.equals(awayFullTimeScore)) {
							oddResult = ThreeWaysOutComeType.DRAW;
						} else {
							oddResult = ThreeWaysOutComeType.AWAY;
						}

						odd.setOddResult(odd.getOutCome().equals(oddResult.getType()));
						odds.add(odd.getId());
						changed.add(odd);
					}
					break;

				case 20:
				case 382:
					if (homeFullTimeScore != null && awayFullTimeScore != null) {
						ThreeWaysOutComeType oddResult = null;
						if (homeFullTimeScore > awayFullTimeScore) {
							oddResult = ThreeWaysOutComeType.HOME;
						} else {
							oddResult = ThreeWaysOutComeType.AWAY;
						}

						odd.setOddResult(odd.getOutCome().equals(oddResult.getType()));
						odds.add(odd.getId());
						changed.add(odd);
					}
					break;
				case 60:
				case 383:
					if (total != null) {
						Double betTotal = Double.parseDouble(odd.getSpecialBetValue());
						TotalsOutComeType oddResult = null;
						if (total > betTotal) {
							oddResult = TotalsOutComeType.Over;
						} else {
							oddResult = TotalsOutComeType.Under;
						}

						odd.setOddResult(odd.getOutCome().equals(oddResult.toString()));
						odds.add(odd.getId());
						changed.add(odd);
					}
					break;
				case 01:
				case 384:
					if (homeFullTimeScore != null && awayFullTimeScore != null) {
						int homeWithHandicap = Integer.parseInt(StringUtils.split(odd.getSpecialBetValue(), ":")[0])
								+ homeFullTimeScore;
						int awayWithHandicap = Integer.parseInt(StringUtils.split(odd.getSpecialBetValue(), ":")[1])
								+ awayFullTimeScore;
						ThreeWaysOutComeType oddResult = null;
						if (homeWithHandicap > awayWithHandicap) {
							oddResult = ThreeWaysOutComeType.HOME;
						} else if (homeWithHandicap == awayWithHandicap) {
							oddResult = ThreeWaysOutComeType.DRAW;
						} else {
							oddResult = ThreeWaysOutComeType.AWAY;
						}

						odd.setOddResult(odd.getOutCome().equals(oddResult.getType()));
						odds.add(odd.getId());
						changed.add(odd);
					}
					break;
				default:
					break;
				}
			}
		}
		oddEntityRepository.save(changed);
		return odds;
	}

}
