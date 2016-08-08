package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.types.MatchOddsType;
import kz.nmbet.betradar.dao.domain.types.ThreeWaysOutComeType;

public class ThreeWayOdds {

	private Double homeOdd;
	private Integer homeOddId;
	private Double drawOdd;
	private Integer drawOddId;
	private Double awayOdd;
	private Integer awayOddId;
	
	public ThreeWayOdds(GlMatchOddEntity oddEntity) {
		fill(oddEntity);
	}

	public void fill(GlMatchOddEntity oddEntity) {
		if (MatchOddsType.three_way.equals(oddEntity.getOddsType()) && !oddEntity.getOutCome().equals("-1")) {
			switch (ThreeWaysOutComeType.find(oddEntity.getOutCome())) {
				case HOME :
					homeOdd = oddEntity.getValue();
					homeOddId=oddEntity.getId();
					break;
				case DRAW :
					drawOdd = oddEntity.getValue();
					drawOddId=oddEntity.getId();					
					break;
				case AWAY :
					awayOdd = oddEntity.getValue();
					awayOddId=oddEntity.getId();
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

	public Integer getHomeOddId() {
		return homeOddId;
	}

	public void setHomeOddId(Integer homeOddId) {
		this.homeOddId = homeOddId;
	}

	public Integer getDrawOddId() {
		return drawOddId;
	}

	public void setDrawOddId(Integer drawOddId) {
		this.drawOddId = drawOddId;
	}

	public Integer getAwayOddId() {
		return awayOddId;
	}

	public void setAwayOddId(Integer awayOddId) {
		this.awayOddId = awayOddId;
	}

}
