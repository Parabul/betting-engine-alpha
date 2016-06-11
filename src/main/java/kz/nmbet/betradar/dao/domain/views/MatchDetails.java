package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchDetails {

	public static final String live_query = "SELECT sport_id, match_id, sport_name, category_name, tournament_name, event_date, home_team_name, away_team_name  FROM public.v_live_match_infos  ";

	private Integer sportId;
	private Integer matchId;
	private String sport;
	private String category;
	private String tournament;
	private String eventDate;
	private String homeTeam;
	private String awayTeam;

	public MatchDetails(ResultSet rs, int rowNum) throws SQLException {
		this.sportId = rs.getInt("sport_id");
		this.matchId = rs.getInt("match_id");
		this.sport = rs.getString("sport_name");
		this.category = rs.getString("category_name");
		this.tournament = rs.getString("tournament_name");
		this.eventDate = rs.getString("event_date");
		this.homeTeam = rs.getString("home_team_name");
		this.awayTeam = rs.getString("away_team_name");
	}

	public MatchDetails() {

	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTournament() {
		return tournament;
	}

	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

}
