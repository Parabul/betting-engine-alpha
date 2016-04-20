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

import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;

@Entity
public class GlOutrightOddEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private Integer teamId;

	private Double value;

	private Double oldValue;

	private String specialBetValue;

	private String outCome;

	private String outcomeId;
	
	private boolean isDeleted;

	@Enumerated(EnumType.STRING)
	private OutrightOddsType oddsType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_outright_id")
	private GlOutrightEntity outright;

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

	public OutrightOddsType getOddsType() {
		return oddsType;
	}

	public void setOddsType(OutrightOddsType oddsType) {
		this.oddsType = oddsType;
	}

	public GlOutrightEntity getOutright() {
		return outright;
	}

	public void setOutright(GlOutrightEntity outright) {
		this.outright = outright;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oddsType == null) ? 0 : oddsType.hashCode());
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
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
		GlOutrightOddEntity other = (GlOutrightOddEntity) obj;
		if (oddsType != other.oddsType)
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
			return false;
		return true;
	}

	public Double getOldValue() {
		return oldValue;
	}

	public void setOldValue(Double oldValue) {
		this.oldValue = oldValue;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
