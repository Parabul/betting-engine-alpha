package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.types.MatchOddsType;
import kz.nmbet.betradar.dao.domain.types.ThreeWaysOutComeType;

public class TwoWayOdds {

	private Double homeOdd;
	private Integer homeOddId;
	private Double awayOdd;
	private Integer awayOddId;

	public TwoWayOdds(GlMatchOddEntity oddEntity) {
		fill(oddEntity);
	}

	public void fill(GlMatchOddEntity oddEntity) {
		if (MatchOddsType.three_way.equals(oddEntity.getOddsType()) && !oddEntity.getOutCome().equals("-1")) {
			switch (ThreeWaysOutComeType.find(oddEntity.getOutCome())) {
			case HOME:
				homeOdd = oddEntity.getValue();
				homeOddId = oddEntity.getId();
				break;
			case AWAY:
				awayOdd = oddEntity.getValue();
				awayOddId = oddEntity.getId();
				break;
			default:
				break;

			}
		}
	}

	public Double getHomeOdd() {
		return homeOdd;
	}

	public void setHomeOdd(Double homeOdd) {
		this.homeOdd = homeOdd;
	}

	public Double getAwayOdd() {
		return awayOdd;
	}

	public void setAwayOdd(Double awayOdd) {
		this.awayOdd = awayOdd;
	}

	public Integer getHomeOddId() {
		return homeOddId;
	}

	public void setHomeOddId(Integer homeOddId) {
		this.homeOddId = homeOddId;
	}

	public Integer getAwayOddId() {
		return awayOddId;
	}

	public void setAwayOddId(Integer awayOddId) {
		this.awayOddId = awayOddId;
	}
}
