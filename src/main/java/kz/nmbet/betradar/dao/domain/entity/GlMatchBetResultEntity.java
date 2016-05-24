package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.sportradar.sdk.feed.lcoo.entities.BetResultEntity;

@Entity
public class GlMatchBetResultEntity {

	@Id
	@SequenceGenerator(name="GL_MATCH_BET_RESULT_ID_GENERATOR", sequenceName="GL_MATCH_BET_RESULT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_MATCH_BET_RESULT_ID_GENERATOR")
	private Integer id;

	private long oddsType;
	private String outcome;
	private String outcomeId;
	private String playerId;
	private String reason;
	private String specialBetValue;
	private Boolean status;
	private String teamId;
	private Double voidFactor;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	public GlMatchBetResultEntity() {
	}

	public GlMatchBetResultEntity(BetResultEntity betResultEntity) {
		this.oddsType = betResultEntity.getOddsType();
		this.outcome = betResultEntity.getOutcome();
		this.outcomeId = betResultEntity.getOutcomeId();
		this.playerId = betResultEntity.getPlayerId();
		this.reason = betResultEntity.getReason();
		this.specialBetValue = betResultEntity.getSpecialBetValue();
		this.status = betResultEntity.getStatus();
		this.teamId = betResultEntity.getTeamId();
		this.voidFactor = betResultEntity.getVoidFactor();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getOddsType() {
		return oddsType;
	}

	public void setOddsType(long oddsType) {
		this.oddsType = oddsType;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getOutcomeId() {
		return outcomeId;
	}

	public void setOutcomeId(String outcomeId) {
		this.outcomeId = outcomeId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSpecialBetValue() {
		return specialBetValue;
	}

	public void setSpecialBetValue(String specialBetValue) {
		this.specialBetValue = specialBetValue;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Double getVoidFactor() {
		return voidFactor;
	}

	public void setVoidFactor(Double voidFactor) {
		this.voidFactor = voidFactor;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

}
