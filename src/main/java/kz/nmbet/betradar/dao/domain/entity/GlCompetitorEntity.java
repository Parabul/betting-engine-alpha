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

import kz.nmbet.betradar.dao.domain.types.TeamType;

@Entity
public class GlCompetitorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String title;

	private Integer teamId;

	private Integer superId;
	
	private boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "gl_team_id")
	private GlTeamEntity team;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_outright_id")
	private GlOutrightEntity outright;

	@Enumerated(EnumType.STRING)
	private TeamType teamType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	public GlTeamEntity getTeam() {
		return team;
	}

	public void setTeam(GlTeamEntity team) {
		this.team = team;
	}

	public TeamType getTeamType() {
		return teamType;
	}

	public void setTeamType(TeamType teamType) {
		this.teamType = teamType;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

	public GlOutrightEntity getOutright() {
		return outright;
	}

	public void setOutright(GlOutrightEntity outright) {
		this.outright = outright;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((superId == null) ? 0 : superId.hashCode());
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
		GlCompetitorEntity other = (GlCompetitorEntity) obj;
		if (superId == null) {
			if (other.superId != null)
				return false;
		} else if (!superId.equals(other.superId))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
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
