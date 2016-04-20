package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kz.nmbet.betradar.dao.domain.types.MatchOddsType;

@Entity
public class GlMatchOddEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private Integer teamId;

	private String playerId;

	private Double value;

	private Double oldValue;

	private String specialBetValue;

	private String outCome;

	private String outcomeId;

	private Integer matchOddsType;
	
	
	private boolean isDeleted;

	@Enumerated(EnumType.STRING)
	private MatchOddsType oddsType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public MatchOddsType getOddsType() {
		return oddsType;
	}

	public void setOddsType(MatchOddsType oddsType) {
		this.oddsType = oddsType;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

	public String getSpecialBetValue() {
		return specialBetValue;
	}

	public void setSpecialBetValue(String specialBetValue) {
		this.specialBetValue = specialBetValue;
	}

	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	public String getOutcomeId() {
		return outcomeId;
	}

	public void setOutcomeId(String outcomeId) {
		this.outcomeId = outcomeId;
	}

	public Double getOldValue() {
		return oldValue;
	}

	public void setOldValue(Double oldValue) {
		this.oldValue = oldValue;
	}

	public Integer getMatchOddsType() {
		return matchOddsType;
	}

	public void setMatchOddsType(Integer matchOddsType) {
		this.matchOddsType = matchOddsType;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchOddsType == null) ? 0 : matchOddsType.hashCode());
		result = prime * result + ((outCome == null) ? 0 : outCome.hashCode());
		result = prime * result + ((specialBetValue == null) ? 0 : specialBetValue.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlMatchOddEntity other = (GlMatchOddEntity) obj;
		if (matchOddsType == null) {
			if (other.matchOddsType != null)
				return false;
		} else if (!matchOddsType.equals(other.matchOddsType))
			return false;
		if (outCome == null) {
			if (other.outCome != null)
				return false;
		} else if (!outCome.equals(other.outCome))
			return false;
		if (specialBetValue == null) {
			if (other.specialBetValue != null)
				return false;
		} else if (!specialBetValue.equals(other.specialBetValue))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
