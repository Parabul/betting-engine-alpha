package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;

public class HandicapOdd {

	private String label;
	private String team;
	private Double value;

	public HandicapOdd(GlMatchOddEntity oddEntity) {
		label = oddEntity.getSpecialBetValue();
		team = oddEntity.getOutCome();
		value = oddEntity.getValue();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
