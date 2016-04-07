package kz.nmbet.betradar.dao.domain.entity;

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

@Entity
public class GlMatchEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long matchId;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
	private List<GlCompetitorEntity> competitors;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_category_id")
	private GlCategoryEntity category;

	@Column(length = 512)
	private String title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
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

}
