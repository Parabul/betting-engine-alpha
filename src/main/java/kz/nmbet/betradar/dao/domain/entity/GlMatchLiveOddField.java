package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.sportradar.sdk.feed.liveodds.entities.common.OddsFieldEntity;

@Entity
public class GlMatchLiveOddField implements Comparable<GlMatchLiveOddField> {

	@Id
	@SequenceGenerator(name="GL_MATCH_LIVE_ODD_FIELD_ID_GENERATOR", sequenceName="GL_MATCH_LIVE_ODD_FIELD_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_MATCH_LIVE_ODD_FIELD_ID_GENERATOR")
	private Integer id;
	
	private String code;

	private boolean active;
	private Boolean outcome;
	private Integer playerId;
	private Double probability;

	private String type;

	private Integer typeId;

	private Double oldValue;
	private Double value;

	private int viewIndex;
	private Double voidFactor;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_live_odd_id")
	private GlMatchLiveOdd liveOdd;

	public GlMatchLiveOddField() {

	}

	public GlMatchLiveOddField(OddsFieldEntity fieldEntity, String code, String type, GlMatchLiveOdd liveOdd) {
		this.code = code;
		this.type = type;
		this.liveOdd = liveOdd;
	}

	public void update(OddsFieldEntity fieldEntity) {
		this.active = fieldEntity.isActive();
		this.outcome = fieldEntity.getOutcome();
		this.playerId = fieldEntity.getPlayerId();
		this.probability = fieldEntity.getProbability();
		this.typeId = fieldEntity.getTypeId();
		if (fieldEntity.getValue() != null) {
			this.oldValue = this.value;
			this.value = fieldEntity.getValue().doubleValue();
		}
		this.viewIndex = fieldEntity.getViewIndex();
		this.voidFactor = fieldEntity.getVoidFactor();

	}

	public void update(GlMatchLiveOddField fieldEntity) {
		this.active = fieldEntity.isActive();
		this.outcome = fieldEntity.getOutcome();
		this.playerId = fieldEntity.getPlayerId();
		this.probability = fieldEntity.getProbability();
		this.typeId = fieldEntity.getTypeId();
		if (fieldEntity.getValue() != null)
			this.value = fieldEntity.getValue().doubleValue();
		this.viewIndex = fieldEntity.getViewIndex();
		this.voidFactor = fieldEntity.getVoidFactor();

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

	public Boolean getOutcome() {
		return outcome;
	}

	public void setOutcome(Boolean outcome) {
		this.outcome = outcome;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public int getViewIndex() {
		return viewIndex;
	}

	public void setViewIndex(int viewIndex) {
		this.viewIndex = viewIndex;
	}

	public Double getVoidFactor() {
		return voidFactor;
	}

	public void setVoidFactor(Double voidFactor) {
		this.voidFactor = voidFactor;
	}

	public GlMatchLiveOdd getLiveOdd() {
		return liveOdd;
	}

	public void setLiveOdd(GlMatchLiveOdd liveOdd) {
		this.liveOdd = liveOdd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int compareTo(GlMatchLiveOddField o) {
		return Integer.compare(this.viewIndex, o.viewIndex);
	}

	public Double getOldValue() {
		return oldValue;
	}

	public void setOldValue(Double oldValue) {
		this.oldValue = oldValue;
	}

}
