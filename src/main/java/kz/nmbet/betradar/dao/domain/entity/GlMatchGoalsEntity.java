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

import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.lcoo.entities.GoalEntity;

@Entity
public class GlMatchGoalsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private Team scoringTeam;

	private Integer scoreHome;

	private Integer scoreAway;

	private String goalTime;

	private Long playerTeamId;

	private String playerName;

	private Integer playerSuperId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	public GlMatchGoalsEntity() {

	}

	public GlMatchGoalsEntity(GoalEntity goalEntity) {
		this.scoringTeam = goalEntity.getScoringTeam();
		this.scoreHome = goalEntity.getScore().getHome();
		this.scoreAway = goalEntity.getScore().getAway();

		this.goalTime = goalEntity.getTime();
		this.playerTeamId = goalEntity.getPlayer().getTeamId();
		this.playerName = goalEntity.getPlayer().getName().getInternational();
		this.playerSuperId = goalEntity.getPlayer().getSuperId();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Team getScoringTeam() {
		return scoringTeam;
	}

	public void setScoringTeam(Team scoringTeam) {
		this.scoringTeam = scoringTeam;
	}

	public Integer getScoreHome() {
		return scoreHome;
	}

	public void setScoreHome(Integer scoreHome) {
		this.scoreHome = scoreHome;
	}

	public Integer getScoreAway() {
		return scoreAway;
	}

	public void setScoreAway(Integer scoreAway) {
		this.scoreAway = scoreAway;
	}

	public String getGoalTime() {
		return goalTime;
	}

	public void setGoalTime(String goalTime) {
		this.goalTime = goalTime;
	}

	public Long getPlayerTeamId() {
		return playerTeamId;
	}

	public void setPlayerTeamId(Long playerTeamId) {
		this.playerTeamId = playerTeamId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getPlayerSuperId() {
		return playerSuperId;
	}

	public void setPlayerSuperId(Integer playerSuperId) {
		this.playerSuperId = playerSuperId;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

}
