package kz.nmbet.betradar.web.beans;

import kz.nmbet.betradar.dao.domain.entity.GlUser;

public class AccountInfo {

	private Boolean fastBetEnabled;
	private Double defaultBetAmount;
	private Double userAmount;

	public AccountInfo(GlUser user) {
		fastBetEnabled = user.getFastBetEnabled();
		defaultBetAmount = user.getDefaultBetAmount();
		userAmount = user.getAmount();
	}

	public Boolean isFastBetEnabled() {
		return fastBetEnabled;
	}

	public void setFastBetEnabled(Boolean fastBetEnabled) {
		this.fastBetEnabled = fastBetEnabled;
	}

	public Double getDefaultBetAmount() {
		return defaultBetAmount;
	}

	public void setDefaultBetAmount(Double defaultBetAmount) {
		this.defaultBetAmount = defaultBetAmount;
	}

	public Double getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(Double userAmount) {
		this.userAmount = userAmount;
	}
	
	
}
