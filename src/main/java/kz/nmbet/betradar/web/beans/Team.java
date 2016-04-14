package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;

public class Team {

	private String title;
	private Integer teamId;
	private Double odd;
	private Integer oddId;
	private OutrightOddsType outrightOddsType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Double getOdd() {
		return odd;
	}

	public void setOdd(Double odd) {
		this.odd = odd;
	}

	public OutrightOddsType getOutrightOddsType() {
		return outrightOddsType;
	}

	public void setOutrightOddsType(OutrightOddsType outrightOddsType) {
		this.outrightOddsType = outrightOddsType;
	}

	public Integer getOddId() {
		return oddId;
	}

	public void setOddId(Integer oddId) {
		this.oddId = oddId;
	}

}
