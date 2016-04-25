package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveCategory {

	public static final String query = "SELECT sport_name, group_name, tournament_id FROM v_active_categories";

	private Integer tournamentId;
	private String groupName;
	private String sportName;

	public ActiveCategory(ResultSet rs, int rowNum) throws SQLException {
		this.tournamentId = rs.getInt("tournament_id");
		this.groupName = rs.getString("group_name");
		this.sportName = rs.getString("sport_name");
	}

	public ActiveCategory() {

	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

}
