package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class GlOutrightEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer outrightId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;

	@Column(length = 1024)
	private String eventInfo;

	@ManyToOne
	@JoinColumn(name = "gl_tournament_id")
	private GlTournamentEntity tournament;

	@ManyToOne
	@JoinColumn(name = "gl_category_id")
	private GlCategoryEntity category;
	

	@OneToMany(mappedBy = "outright", cascade = CascadeType.ALL)
	private List<GlCompetitorEntity> competitors;

	@OneToMany(mappedBy = "outright", cascade = CascadeType.ALL)
	private List<GlOutrightOddEntity> odds;

	@OneToMany(mappedBy = "outright", cascade = CascadeType.ALL)
	private List<GlOutrightResultEntity> results;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOutrightId() {
		return outrightId;
	}

	public void setOutrightId(Integer outrightId) {
		this.outrightId = outrightId;
	}

	public List<GlCompetitorEntity> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<GlCompetitorEntity> competitors) {
		this.competitors = competitors;
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

	public GlTournamentEntity getTournament() {
		return tournament;
	}

	public void setTournament(GlTournamentEntity tournament) {
		this.tournament = tournament;
	}

	public GlCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(GlCategoryEntity category) {
		this.category = category;
	}

	public List<GlOutrightOddEntity> getOdds() {
		return odds;
	}

	public void setOdds(List<GlOutrightOddEntity> odds) {
		this.odds = odds;
	}

	public List<GlOutrightResultEntity> getResults() {
		return results;
	}

	public void setResults(List<GlOutrightResultEntity> results) {
		this.results = results;
	}

}
