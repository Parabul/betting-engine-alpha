package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.sportradar.sdk.feed.liveodds.entities.common.OddsEntity;
import com.sportradar.sdk.feed.liveodds.enums.OddsType;

@Entity
public class GlMatchLiveOdd implements Comparable<GlMatchLiveOdd> {

	@Id
	@SequenceGenerator(name = "GL_MATCH_LIVE_ODD_ID_GENERATOR", sequenceName = "GL_MATCH_LIVE_ODD_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GL_MATCH_LIVE_ODD_ID_GENERATOR")
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

	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "liveOdd", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<GlMatchLiveOddField> oddFields;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;

	public GlMatchLiveOdd() {

	}

	public GlMatchLiveOdd(OddsEntity oddsEntity, String name, GlMatchEntity match) {
		this.match = match;
		this.betradarId = oddsEntity.getId();
		this.name = name;
		update(oddsEntity);
	}

	public void update(OddsEntity oddsEntity) {
		this.active = oddsEntity.isActive();
		this.changed = oddsEntity.hasChanged();
		this.combination = oddsEntity.getCombination();
		this.forTheRest = oddsEntity.getForTheRest();
		this.freeText = oddsEntity.getFreeText();
		this.mostBalanced = oddsEntity.isMostBalanced();
		this.specialOddsValue = oddsEntity.getSpecialOddsValue();
		this.subType = oddsEntity.getSubType();
		this.oddsType = oddsEntity.getType();
		this.typeId = oddsEntity.getTypeId();
		this.clearedScore = oddsEntity.getClearedScore();

	}

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

	public List<GlMatchLiveOddField> getOddFields() {
		return oddFields;
	}

	public void setOddFields(List<GlMatchLiveOddField> oddFields) {
		this.oddFields = oddFields;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Override
	public int compareTo(GlMatchLiveOdd o) {
		return Long.compare(betradarId, o.getBetradarId());
	}

}
