package kz.nmbet.betradar.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.utils.TextsEntityUtils;

public class OutrightInfo {
	private Integer id;
	private String title;
	private Date date;
	private String category;
	private List<Team> teams;
	private String sport;

	private Team findTeamById(Integer teamId) {
		for (Team team : teams) {
			if (team.getTeamId().equals(teamId))
				return team;
		}
		return null;
	}

	public OutrightInfo(GlOutrightEntity outrightEntity) {
		setId(outrightEntity.getOutrightId());
		title = outrightEntity.getEventInfo();
		category = outrightEntity.getCategory().getNameEn();
		setSport(outrightEntity.getCategory().getSport().getNameEn());
		date = outrightEntity.getEventDate();
		teams = new ArrayList<Team>();
		for (GlCompetitorEntity competitor : outrightEntity.getCompetitors()) {
			Team team = new Team();
			team.setTeamId(competitor.getTeamId());
			team.setTitle(TextsEntityUtils.getName(competitor.getTeam()));
			teams.add(team);
		}

		for (GlOutrightOddEntity odd : outrightEntity.getOdds()) {
			Team team = findTeamById(odd.getTeamId());
			if (team != null) {
				team.setOdd(odd.getValue());
				team.setOutrightOddsType(odd.getOddsType());
				team.setOddId(odd.getId());
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

}
