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

import com.sportradar.sdk.feed.liveodds.enums.OddsType;

import kz.nmbet.betradar.dao.domain.types.MatchOddsType;

@Entity
public class GlMatchLiveOdd {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private Long betradarId;

	private boolean active;

	private Boolean changed;

	private Long combination;

	private String forTheRest;

	private String freeText;

	private Boolean mostBalanced;

	private String name;

	private String specialOddsValue;

	private Long subType;

	@Enumerated(EnumType.STRING)
	private OddsType oddsType;

	private long typeId;

	private String clearedScore;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Boolean getChanged() {
		return changed;
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}

	public Long getCombination() {
		return combination;
	}

	public void setCombination(Long combination) {
		this.combination = combination;
	}

	public String getForTheRest() {
		return forTheRest;
	}

	public void setForTheRest(String forTheRest) {
		this.forTheRest = forTheRest;
	}

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public Boolean getMostBalanced() {
		return mostBalanced;
	}

	public void setMostBalanced(Boolean mostBalanced) {
		this.mostBalanced = mostBalanced;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialOddsValue() {
		return specialOddsValue;
	}

	public void setSpecialOddsValue(String specialOddsValue) {
		this.specialOddsValue = specialOddsValue;
	}

	public Long getSubType() {
		return subType;
	}

	public void setSubType(Long subType) {
		this.subType = subType;
	}

	public OddsType getOddsType() {
		return oddsType;
	}

	public void setOddsType(OddsType oddsType) {
		this.oddsType = oddsType;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getClearedScore() {
		return clearedScore;
	}

	public void setClearedScore(String clearedScore) {
		this.clearedScore = clearedScore;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

	public Long getBetradarId() {
		return betradarId;
	}

	public void setBetradarId(Long betradarId) {
		this.betradarId = betradarId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((betradarId == null) ? 0 : betradarId.hashCode());
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
		GlMatchLiveOdd other = (GlMatchLiveOdd) obj;
		if (betradarId == null) {
			if (other.betradarId != null)
				return false;
		} else if (!betradarId.equals(other.betradarId))
			return false;
		return true;
	}

}
