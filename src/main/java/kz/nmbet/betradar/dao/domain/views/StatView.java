package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatView {

	public static final String sports_query = "select sport_name title, count(1) as  num from v_match_infos group by sport_name";
	public static final String bet_types_query = "select bet_type title, count(1) as num from gl_bet group by bet_type";
	public static final String bet_amounts_query = "select bet_type title, sum(bet_amount) as num from gl_bet group by bet_type";
	public static final String win_amounts_query = "select bet_type title, sum(win_amount) as num from gl_bet group by bet_type";

	private String label;
	private Double value;

	public StatView(ResultSet rs, int rowNum) throws SQLException {

		this.label = rs.getString("title");
		this.value = rs.getDouble("num");
	}

	public StatView() {

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
