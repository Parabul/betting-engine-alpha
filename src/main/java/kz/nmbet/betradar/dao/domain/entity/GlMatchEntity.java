package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.List;
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
public class GlMatchEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private Long matchId;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
	private List<GlCompetitorEntity> competitors;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchOddEntity> odds;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchResultEntity> results;
	
	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchBetResultEntity> betResults;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_tournament_id")
	private GlTournamentEntity tournament;

	@Column(length = 512)
	private String title;
	

	
	private boolean isActive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public List<GlCompetitorEntity> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<GlCompetitorEntity> competitors) {
		this.competitors = competitors;
	}

	public Set<GlMatchOddEntity> getOdds() {
		return odds;
	}

	public void setOdds(Set<GlMatchOddEntity> odds) {
		this.odds = odds;
	}

	public Set<GlMatchResultEntity> getResults() {
		return results;
	}

	public void setResults(Set<GlMatchResultEntity> results) {
		this.results = results;
	}

	public Set<GlMatchBetResultEntity> getBetResults() {
		return betResults;
	}

	public void setBetResults(Set<GlMatchBetResultEntity> betResults) {
		this.betResults = betResults;
	}

	public GlTournamentEntity getTournament() {
		return tournament;
	}

	public void setTournament(GlTournamentEntity tournament) {
		this.tournament = tournament;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
