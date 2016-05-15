package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortMatch {

	public static final String query = "select sport_id, description, match_id from v_matches where sport_id = ? and upper(description) like upper(?) limit 20";

	private Integer sportId;
	private Integer matchId;
	private String description;

	public ShortMatch(ResultSet rs, int rowNum) throws SQLException {
		this.sportId = rs.getInt("sport_id");
		this.matchId = rs.getInt("match_id");
		this.description = rs.getString("description");
	}

	public ShortMatch() {

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static String getQuery() {
		return query;
	}

}
