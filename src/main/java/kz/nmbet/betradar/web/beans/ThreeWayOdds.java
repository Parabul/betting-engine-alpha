package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.types.MatchOddsType;
import kz.nmbet.betradar.dao.domain.types.ThreeWaysOutComeType;

public class ThreeWayOdds {

	private Double homeOdd;
	private Double drawOdd;
	private Double awayOdd;

	public ThreeWayOdds(GlMatchOddEntity oddEntity) {
		fill(oddEntity);
	}

	public void fill(GlMatchOddEntity oddEntity) {
		if (MatchOddsType.three_way.equals(oddEntity.getOddsType()) && !oddEntity.getOutCome().equals("-1")) {
			switch (ThreeWaysOutComeType.find(oddEntity.getOutCome())) {
				case HOME :
					homeOdd = oddEntity.getValue();
					break;
				case DRAW :
					drawOdd = oddEntity.getValue();
					break;
				case AWAY :
					awayOdd = oddEntity.getValue();
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
	public Double getDrawOdd() {
		return drawOdd;
	}
	public void setDrawOdd(Double drawOdd) {
		this.drawOdd = drawOdd;
	}
	public Double getAwayOdd() {
		return awayOdd;
	}
	public void setAwayOdd(Double awayOdd) {
		this.awayOdd = awayOdd;
	}

}
