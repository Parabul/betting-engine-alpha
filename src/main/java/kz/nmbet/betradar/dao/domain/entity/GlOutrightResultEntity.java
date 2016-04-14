package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GlOutrightResultEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;

	private Integer teamId;

	private int result;

	private Double deadHeatFactor;

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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public GlOutrightEntity getOutright() {
		return outright;
	}

	public void setOutright(GlOutrightEntity outright) {
		this.outright = outright;
	}

	public Double getDeadHeatFactor() {
		return deadHeatFactor;
	}

	public void setDeadHeatFactor(Double deadHeatFactor) {
		this.deadHeatFactor = deadHeatFactor;
	}

	@Override
	public String toString() {
		return "GlOutrightResultEntity [teamId=" + teamId + ", result="
				+ result + "]";
	}
	
	

}
