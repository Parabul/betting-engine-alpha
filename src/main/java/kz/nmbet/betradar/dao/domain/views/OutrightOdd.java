package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;

public class OutrightOdd {

	public static final String query="SELECT id, old_value, value, odds_type, event_date, event_info, team_name, category_name, sport_name  FROM v_outright_odds";
	
	private Integer id;
	private Double old_value;
	private Double value;
	private OutrightOddsType odds_type;
	private Date event_date;
	private String event_info;
	private String team_name;
	private String category_name;
	private String sport_name;

	public OutrightOdd(ResultSet rs, int rowNum) throws SQLException {
		this.id = rs.getInt("id");
		this.old_value = rs.getDouble("old_value");
		this.value = rs.getDouble("value");
		this.odds_type = OutrightOddsType.valueOf(rs.getString("odds_type"));
		this.event_date = rs.getDate("event_date");
		this.event_info = rs.getString("event_info");
		this.team_name = rs.getString("team_name");
		this.category_name = rs.getString("category_name");
		this.sport_name = rs.getString("sport_name");
	}

	public OutrightOdd() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getOld_value() {
		return old_value;
	}

	public void setOld_value(Double old_value) {
		this.old_value = old_value;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public OutrightOddsType getOdds_type() {
		return odds_type;
	}

	public void setOdds_type(OutrightOddsType odds_type) {
		this.odds_type = odds_type;
	}

	public Date getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}

	public String getEvent_info() {
		return event_info;
	}

	public void setEvent_info(String event_info) {
		this.event_info = event_info;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getSport_name() {
		return sport_name;
	}

	public void setSport_name(String sport_name) {
		this.sport_name = sport_name;
	}

}
