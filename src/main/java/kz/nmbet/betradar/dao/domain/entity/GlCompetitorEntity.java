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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private Long teamId;

	private Long superId;

	@ManyToOne
	@JoinColumn(name = "gl_team_id")
	private GlTeamEntity team;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	@Enumerated(EnumType.STRING)
	private TeamType teamType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
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

}
