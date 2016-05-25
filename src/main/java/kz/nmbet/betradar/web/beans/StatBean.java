package kz.nmbet.betradar.web.beans;

import java.util.List;

import kz.nmbet.betradar.dao.domain.views.StatView;

public class StatBean {

	private List<StatView> sports;
	private List<StatView> betTypes;
	private List<StatView> betAmounts;
	private List<StatView> winAmounts;
	
	

	public List<StatView> getSports() {
		return sports;
	}

	public void setSports(List<StatView> sports) {
		this.sports = sports;
	}

	public List<StatView> getBetTypes() {
		return betTypes;
	}

	public void setBetTypes(List<StatView> betTypes) {
		this.betTypes = betTypes;
	}

	public List<StatView> getBetAmounts() {
		return betAmounts;
	}

	public void setBetAmounts(List<StatView> betAmounts) {
		this.betAmounts = betAmounts;
	}

	public List<StatView> getWinAmounts() {
		return winAmounts;
	}

	public void setWinAmounts(List<StatView> winAmounts) {
		this.winAmounts = winAmounts;
	}

}
