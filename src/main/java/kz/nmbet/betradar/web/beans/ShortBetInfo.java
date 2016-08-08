package kz.nmbet.betradar.web.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;

public class ShortBetInfo {
	private Integer id;

	private Double oddValue;

	private Double betAmount;

	private Double winAmount;

	private Boolean wins;

	private Date createDate;

	public ShortBetInfo(GlBet bet) {
		this.id = bet.getId();
		this.oddValue = bet.getOddValue();
		this.betAmount = bet.getBetAmount();
		this.winAmount = bet.getWinAmount();
		this.wins = bet.getWins();
		this.setCreateDate(bet.getCreateDate());
		for (GlMatchLiveOddField odds : bet.getLiveOdds()) {

		}
		for (GlMatchOddEntity odds : bet.getMatchOddEntity()) {

		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getOddValue() {
		return oddValue;
	}

	public void setOddValue(Double oddValue) {
		this.oddValue = oddValue;
	}

	public Double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Boolean getWins() {
		return wins;
	}

	public void setWins(Boolean wins) {
		this.wins = wins;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
