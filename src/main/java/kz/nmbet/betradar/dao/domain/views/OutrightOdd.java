package kz.nmbet.betradar.dao.domain.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;

public class OutrightOdd {

	public static final String query = "SELECT outright_id, odd_id, old_value, value, odds_type, event_date, event_info, team_name FROM v_outright_odds where outright_id = ?";

	private Integer outrightId;
	private Integer oddId;
	private Double oldValue;
	private Double value;
	private OutrightOddsType oddsType;
	private Date eventDate;
	private String eventInfo;
	private String teamName;

	public OutrightOdd(ResultSet rs, int rowNum) throws SQLException {
		this.outrightId = rs.getInt("outright_id");
		this.oddId = rs.getInt("odd_id");
		this.oldValue = rs.getDouble("old_value");
		this.value = rs.getDouble("value");
		this.oddsType = OutrightOddsType.valueOf(rs.getString("odds_type"));
		this.eventDate = rs.getDate("event_date");
		this.eventInfo = rs.getString("event_info");
		this.teamName = rs.getString("team_name");
	}

	public OutrightOdd() {

	}

	public Integer getOutrightId() {
		return outrightId;
	}

	public void setOutrightId(Integer outrightId) {
		this.outrightId = outrightId;
	}

	public Integer getOddId() {
		return oddId;
	}

	public void setOddId(Integer oddId) {
		this.oddId = oddId;
	}

	public Double getOldValue() {
		return oldValue;
	}

	public void setOldValue(Double oldValue) {
		this.oldValue = oldValue;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public OutrightOddsType getOddsType() {
		return oddsType;
	}

	public void setOddsType(OutrightOddsType oddsType) {
		this.oddsType = oddsType;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventInfo() {
		return eventInfo;
	}

	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public static String getQuery() {
		return query;
	}

}
