package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveOutright {

	public static final String query = "SELECT sport_name, group_name, outright_id FROM v_active_outrights";

	private Integer outrightId;
	private String groupName;
	private String sportName;

	public ActiveOutright(ResultSet rs, int rowNum) throws SQLException {
		this.outrightId = rs.getInt("outright_id");
		this.groupName = rs.getString("group_name");
		this.sportName = rs.getString("sport_name");
	}

	public ActiveOutright() {

	}

	public Integer getOutrightId() {
		return outrightId;
	}

	public void setOutrightId(Integer outrightId) {
		this.outrightId = outrightId;
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

}
