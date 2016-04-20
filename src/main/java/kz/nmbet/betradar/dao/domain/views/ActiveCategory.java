package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveCategory {

	public static final String query = "SELECT sport_name, group_name, category_id FROM v_active_categories";

	private Integer categoryId;
	private String groupName;
	private String sportName;

	public ActiveCategory(ResultSet rs, int rowNum) throws SQLException {
		this.categoryId = rs.getInt("category_id");
		this.groupName = rs.getString("group_name");
		this.sportName = rs.getString("sport_name");
	}

	public ActiveCategory() {

	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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
