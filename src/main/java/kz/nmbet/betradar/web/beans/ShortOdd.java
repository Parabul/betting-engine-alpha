package kz.nmbet.betradar.web.beans;

import org.apache.commons.lang3.StringUtils;

import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;

public class ShortOdd {

	private Integer id;
	private String info;
	private MatchInfoBean matchInfoBean;
	private Boolean wins;
	private Double oddValue;

	public ShortOdd(GlMatchLiveOddField item) {
		StringBuilder builder = new StringBuilder();
		builder.append(item.getLiveOdd().getName());
		builder.append(": ");
		builder.append(item.getType());

		if (StringUtils.isNotBlank(item.getLiveOdd().getSpecialOddsValue())) {
			builder.append(" (");
			builder.append(item.getLiveOdd().getSpecialOddsValue());
			builder.append(")");
		}
		builder.append(" - ");
		builder.append(item.getValue());

		this.info = builder.toString();
		this.id = item.getId();
		this.matchInfoBean = new MatchInfoBean(item.getLiveOdd().getMatch(), true);
		this.wins = item.getOutcome();
		this.oddValue = item.getValue();
	}

	public ShortOdd(GlMatchOddEntity matchOdd, String type) {
		StringBuilder builder = new StringBuilder();
		builder.append(type);
		builder.append(": ");
		builder.append(matchOdd.getOutCome());

		if (StringUtils.isNotBlank(matchOdd.getSpecialBetValue())) {
			builder.append(" (");
			builder.append(matchOdd.getSpecialBetValue());
			builder.append(")");
		}
		builder.append(" - ");
		builder.append(matchOdd.getValue());

		this.info = builder.toString();
		this.id = matchOdd.getId();
		this.matchInfoBean = new MatchInfoBean(matchOdd.getMatch(), true);
		this.wins = matchOdd.getOddResult();
		this.oddValue = matchOdd.getValue();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public MatchInfoBean getMatchInfoBean() {
		return matchInfoBean;
	}

	public void setMatchInfoBean(MatchInfoBean matchInfoBean) {
		this.matchInfoBean = matchInfoBean;
	}

	public Boolean getWins() {
		return wins;
	}

	public void setWins(Boolean wins) {
		this.wins = wins;
	}

	public Double getOddValue() {
		return oddValue;
	}

	public void setOddValue(Double oddValue) {
		this.oddValue = oddValue;
	}

}
