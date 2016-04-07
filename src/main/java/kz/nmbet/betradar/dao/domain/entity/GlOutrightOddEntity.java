package kz.nmbet.betradar.dao.domain.entity;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long teamId;

	private Double value;

	@Enumerated(EnumType.STRING)
	private OutrightOddsType oddsType;

	@ManyToOne
	@JoinColumn(name = "gl_outright_id")
	private GlOutrightEntity outright;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
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

}
