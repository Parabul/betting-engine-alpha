package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.Set;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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

	@OneToMany(mappedBy = "outright", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlCompetitorEntity> competitors;

	@OneToMany(mappedBy = "outright", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlOutrightOddEntity> odds;

	@OneToMany(mappedBy = "outright", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlOutrightResultEntity> results;

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

	public Set<GlCompetitorEntity> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(Set<GlCompetitorEntity> competitors) {
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

	public Set<GlOutrightOddEntity> getOdds() {
		return odds;
	}

	public void setOdds(Set<GlOutrightOddEntity> odds) {
		this.odds = odds;
	}

	public Set<GlOutrightResultEntity> getResults() {
		return results;
	}

	public void setResults(Set<GlOutrightResultEntity> results) {
		this.results = results;
	}

}
