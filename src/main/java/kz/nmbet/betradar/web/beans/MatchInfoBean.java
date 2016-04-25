package kz.nmbet.betradar.web.beans;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.utils.TextsEntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchInfoBean {
	private static final Logger logger = LoggerFactory.getLogger(MatchInfoBean.class);

	private Integer matchId;

	private Long betRadarMatchId;
	private String homeTeamName;
	private String awayTeamName;
	private Date matchDate;

	private ThreeWayOdds threeWayOdds;
	private TwoWayOdds twoWayOdds;
	private Map<String, Double> correctScoreOdds;
	private List<HandicapOdd> handicapOdds;
	private Map<String, TotalOdd> totalsOdds;

	public MatchInfoBean(GlMatchEntity matchEntity, boolean key) {
		matchId = matchEntity.getId();
		betRadarMatchId = matchEntity.getMatchId();
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
	}

	public String getTitle() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return MessageFormat.format("{0} : {1} - {2}", sdf.format(matchDate), homeTeamName, awayTeamName);
	}

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
					if (handicapOdds == null)
						handicapOdds = new ArrayList<HandicapOdd>();
					handicapOdds.add(new HandicapOdd(oddEntity));
					break;
				case totals :
					if (totalsOdds == null) {
						totalsOdds = new HashMap<String, TotalOdd>();
					}
					String key = oddEntity.getSpecialBetValue();
					if (totalsOdds.containsKey(key)) {
						TotalOdd item = totalsOdds.get(key);
						item.fill(oddEntity);
						totalsOdds.put(key, item);
					} else {
						totalsOdds.put(key, new TotalOdd(oddEntity));
					}
					break;
			}
		}
	}

	public boolean isEmpty() {
		return threeWayOdds == null && twoWayOdds == null && correctScoreOdds == null && handicapOdds == null && totalsOdds == null;
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

	public List<HandicapOdd> getHandicapOdds() {
		return handicapOdds;
	}

	public void setHandicapOdds(List<HandicapOdd> handicapOdds) {
		this.handicapOdds = handicapOdds;
	}

	public Map<String, TotalOdd> getTotalsOdds() {
		return totalsOdds;
	}

	public void setTotalsOdds(Map<String, TotalOdd> totalsOdds) {
		this.totalsOdds = totalsOdds;
	}

	public Long getBetRadarMatchId() {
		return betRadarMatchId;
	}

	public void setBetRadarMatchId(Long betRadarMatchId) {
		this.betRadarMatchId = betRadarMatchId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((betRadarMatchId == null) ? 0 : betRadarMatchId.hashCode());
		result = prime * result + ((matchId == null) ? 0 : matchId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchInfoBean other = (MatchInfoBean) obj;
		if (betRadarMatchId == null) {
			if (other.betRadarMatchId != null)
				return false;
		} else if (!betRadarMatchId.equals(other.betRadarMatchId))
			return false;
		if (matchId == null) {
			if (other.matchId != null)
				return false;
		} else if (!matchId.equals(other.matchId))
			return false;
		return true;
	}

}
