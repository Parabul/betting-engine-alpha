package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class GlOutrightResultEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long teamId;

	private int result;

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

}
