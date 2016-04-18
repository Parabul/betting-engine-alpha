package kz.nmbet.betradar.dao.domain.entity;

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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_category_id")
	private GlCategoryEntity category;

	@Column(length = 512)
	private String title;

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

	public GlCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(GlCategoryEntity category) {
		this.category = category;
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

}
