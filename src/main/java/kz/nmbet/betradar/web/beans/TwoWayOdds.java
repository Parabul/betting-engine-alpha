package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.types.MatchOddsType;
import kz.nmbet.betradar.dao.domain.types.ThreeWaysOutComeType;

public class TwoWayOdds {

	private Double homeOdd;
	private Double awayOdd;

	public TwoWayOdds(GlMatchOddEntity oddEntity) {
		fill(oddEntity);
	}

	public void fill(GlMatchOddEntity oddEntity) {
		if (MatchOddsType.three_way.equals(oddEntity.getOddsType())) {
			switch (ThreeWaysOutComeType.valueOf(oddEntity.getOutCome())) {
				case HOME :
					homeOdd = oddEntity.getValue();
					break;
				case AWAY :
					awayOdd = oddEntity.getValue();
					break;
				default :
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

}
