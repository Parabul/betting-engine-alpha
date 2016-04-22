package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.types.MatchOddsType;
import kz.nmbet.betradar.dao.domain.types.TotalsOutComeType;

public class TotalOdd {

	private String specialValue;
	private Double overValue;
	private Double underValue;

	public TotalOdd(GlMatchOddEntity oddEntity) {
		fill(oddEntity);
	}

	public void fill(GlMatchOddEntity oddEntity) {
		specialValue = oddEntity.getSpecialBetValue();
		if (MatchOddsType.totals.equals(oddEntity.getOddsType()) && !oddEntity.getOutCome().equals("-1")) {
			switch (TotalsOutComeType.valueOf(oddEntity.getOutCome())) {
				case Over :
					overValue = oddEntity.getValue();
					break;
				case Under :
					underValue = oddEntity.getValue();
					break;

			}
		}
	}

	public String getSpecialValue() {
		return specialValue;
	}

	public void setSpecialValue(String specialValue) {
		this.specialValue = specialValue;
	}

	public Double getOverValue() {
		return overValue;
	}

	public void setOverValue(Double overValue) {
		this.overValue = overValue;
	}

	public Double getUnderValue() {
		return underValue;
	}

	public void setUnderValue(Double underValue) {
		this.underValue = underValue;
	}

}
