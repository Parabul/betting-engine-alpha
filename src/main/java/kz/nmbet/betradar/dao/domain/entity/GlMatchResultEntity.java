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
import javax.persistence.SequenceGenerator;

import kz.nmbet.betradar.dao.domain.types.ScoreType;

@Entity
public class GlMatchResultEntity {

	@Id
	@SequenceGenerator(name="GL_MATCH_RESULT_ID_GENERATOR", sequenceName="GL_MATCH_RESULT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_MATCH_RESULT_ID_GENERATOR")
	private Integer id;

	private String comment;

	private String score;

	@Enumerated(EnumType.STRING)
	private ScoreType scoreType;

	private String scoreTypeValue;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	private Boolean decidedByFa;

	private String reason;

	private String outcome;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public ScoreType getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}

	public String getScoreTypeValue() {
		return scoreTypeValue;
	}

	public void setScoreTypeValue(String scoreTypeValue) {
		this.scoreTypeValue = scoreTypeValue;
	}

	public Boolean getDecidedByFa() {
		return decidedByFa;
	}

	public void setDecidedByFa(Boolean decidedByFa) {
		this.decidedByFa = decidedByFa;
	}

}
