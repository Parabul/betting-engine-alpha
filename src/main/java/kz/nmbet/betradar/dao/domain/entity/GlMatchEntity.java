package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class GlMatchEntity {

	@Id
	@SequenceGenerator(name="GL_MATCH_ID_GENERATOR", sequenceName="GL_MATCH_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_MATCH_ID_GENERATOR")
	private Integer id;

	private Long matchId;
	
	private Boolean liveStarted;
	
	private Boolean liveStoped;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<GlCompetitorEntity> competitors;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchOddEntity> odds;
	
	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchLiveOdd> liveOdds;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchResultEntity> results;
	
	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchGoalsEntity> goals;
	
	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchCardsEntity> cards;
	
	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchCornersEntity> corners;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GlMatchBetResultEntity> betResults;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "gl_tournament_id")
	private GlTournamentEntity tournament;

	@Column(length = 512)
	private String title;

	private boolean isActive;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date liveCheckDate;
	
	

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((matchId == null) ? 0 : matchId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlMatchEntity other = (GlMatchEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (matchId == null) {
			if (other.matchId != null)
				return false;
		} else if (!matchId.equals(other.matchId))
			return false;
		return true;
	}

	public Set<GlMatchGoalsEntity> getGoals() {
		return goals;
	}

	public void setGoals(Set<GlMatchGoalsEntity> goals) {
		this.goals = goals;
	}

	public Set<GlMatchCardsEntity> getCards() {
		return cards;
	}

	public void setCards(Set<GlMatchCardsEntity> cards) {
		this.cards = cards;
	}

	public Set<GlMatchCornersEntity> getCorners() {
		return corners;
	}

	public void setCorners(Set<GlMatchCornersEntity> corners) {
		this.corners = corners;
	}

	public Set<GlMatchLiveOdd> getLiveOdds() {
		return liveOdds;
	}

	public void setLiveOdds(Set<GlMatchLiveOdd> liveOdds) {
		this.liveOdds = liveOdds;
	}

	public Boolean getLiveStarted() {
		return liveStarted;
	}

	public void setLiveStarted(Boolean liveStarted) {
		this.liveStarted = liveStarted;
	}

	public Boolean getLiveStoped() {
		return liveStoped;
	}

	public void setLiveStoped(Boolean liveStoped) {
		this.liveStoped = liveStoped;
	}

	public Date getLiveCheckDate() {
		return liveCheckDate;
	}

	public void setLiveCheckDate(Date liveCheckDate) {
		this.liveCheckDate = liveCheckDate;
	}

}
