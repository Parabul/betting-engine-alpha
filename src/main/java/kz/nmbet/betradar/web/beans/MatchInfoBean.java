package kz.nmbet.betradar.web.beans;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.utils.TextsEntityUtils;

public class MatchInfoBean {
	private Integer matchId;
	private String homeTeamName;
	private String awayTeamName;
	private Date matchDate;

	private ThreeWayOdds threeWayOdds;
	private TwoWayOdds twoWayOdds;
	private Map<String, Double> correctScoreOdds;

	public MatchInfoBean(GlMatchEntity matchEntity) {
		matchId = matchEntity.getId();
		matchDate = matchEntity.getEventDate();
		for (GlCompetitorEntity competitorEntity : matchEntity.getCompetitors()) {
			switch (competitorEntity.getTeamType()) {
				case HOME :
					homeTeamName = TextsEntityUtils.getName(competitorEntity.getTeam());
					break;
				case AWAY :
					awayTeamName = TextsEntityUtils.getName(competitorEntity.getTeam());
					break;
			}
		}

		for (GlMatchOddEntity oddEntity : matchEntity.getOdds()) {
			switch (oddEntity.getOddsType()) {
				case three_way :
					if (threeWayOdds == null)
						threeWayOdds = new ThreeWayOdds(oddEntity);
					else
						threeWayOdds.fill(oddEntity);
					break;
				case two_way :
					if (twoWayOdds == null)
						twoWayOdds = new TwoWayOdds(oddEntity);
					else
						twoWayOdds.fill(oddEntity);
					break;
				case correct_score :
					if (correctScoreOdds == null) {
						correctScoreOdds = new TreeMap<String, Double>();
					}
					correctScoreOdds.put(oddEntity.getOutCome(), oddEntity.getValue());
					break;
				case handicap :
					break;
				case totals :
					break;
			}
		}
	}

	public ThreeWayOdds getThreeWayOdds() {
		return threeWayOdds;
	}

	public void setThreeWayOdds(ThreeWayOdds threeWayOdds) {
		this.threeWayOdds = threeWayOdds;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public TwoWayOdds getTwoWayOdds() {
		return twoWayOdds;
	}

	public void setTwoWayOdds(TwoWayOdds twoWayOdds) {
		this.twoWayOdds = twoWayOdds;
	}

	public Map<String, Double> getCorrectScoreOdds() {
		return correctScoreOdds;
	}

	public void setCorrectScoreOdds(Map<String, Double> correctScoreOdds) {
		this.correctScoreOdds = correctScoreOdds;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

}
