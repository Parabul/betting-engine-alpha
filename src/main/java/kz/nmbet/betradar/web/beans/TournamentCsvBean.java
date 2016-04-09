package kz.nmbet.betradar.web.beans;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

public class TournamentCsvBean {

	private Integer sportId;
	private Integer categoryId;
	private Integer tournamentId;
	private Integer uniqueTournamentId;
	private String sport;
	private String category;
	private String tournament;
	private String uniqueTournamentName;
	private Integer teamId;
	private String teamName;
	private Integer superTeamId;

	public TournamentCsvBean(CSVRecord record) {
		if (StringUtils.isNotBlank(record.get(0)))
			this.sportId = Integer.parseInt(record.get(0));
		if (StringUtils.isNotBlank(record.get(1)))
			this.categoryId = Integer.parseInt(record.get(1));
		if (StringUtils.isNotBlank(record.get(2)))
			this.tournamentId = Integer.parseInt(record.get(2));
		if (StringUtils.isNotBlank(record.get(3)))
			this.uniqueTournamentId = Integer.parseInt(record.get(3));
		if (StringUtils.isNotBlank(record.get(4)))
			this.sport = StringUtils.trim(record.get(4));
		if (StringUtils.isNotBlank(record.get(5)))
			this.category = StringUtils.trim(record.get(5));
		if (StringUtils.isNotBlank(record.get(6)))
			this.tournament = StringUtils.trim(record.get(6));
		if (StringUtils.isNotBlank(record.get(7)))
			this.uniqueTournamentName = StringUtils.trim(record.get(7));
		if (StringUtils.isNotBlank(record.get(8)))
			this.teamId = Integer.parseInt(record.get(8));
		if (StringUtils.isNotBlank(record.get(9)))
			this.teamName = StringUtils.trim(record.get(9));
		if (StringUtils.isNotBlank(record.get(10)))
			this.superTeamId = Integer.parseInt(record.get(10));
	}

	public boolean isNotEmpty() {
		return superTeamId != null;
	}

	@Override
	public String toString() {
		return "TournamentCsvBean [sportId=" + sportId + ", categoryId="
				+ categoryId + ", tournamentId=" + tournamentId
				+ ", uniqueTournamentId=" + uniqueTournamentId + ", sport="
				+ sport + ", category=" + category + ", tournament="
				+ tournament + ", uniqueTournamentName=" + uniqueTournamentName
				+ ", teamId=" + teamId + ", teamName=" + teamName
				+ ", superTeamId=" + superTeamId + "]";
	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Integer getUniqueTournamentId() {
		return uniqueTournamentId;
	}

	public void setUniqueTournamentId(Integer uniqueTournamentId) {
		this.uniqueTournamentId = uniqueTournamentId;
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

	public String getUniqueTournamentName() {
		return uniqueTournamentName;
	}

	public void setUniqueTournamentName(String uniqueTournamentName) {
		this.uniqueTournamentName = uniqueTournamentName;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getSuperTeamId() {
		return superTeamId;
	}

	public void setSuperTeamId(Integer superTeamId) {
		this.superTeamId = superTeamId;
	}

}
