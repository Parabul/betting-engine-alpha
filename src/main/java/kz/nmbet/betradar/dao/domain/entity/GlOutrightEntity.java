package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.List;

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
	private long id;

	private long outrightId;

	@OneToMany(mappedBy = "match")
	private List<GlCompetitorEntity> competitors;

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

	@OneToMany(mappedBy = "outright")
	private List<GlOutrightOddEntity> odds;

	@OneToMany(mappedBy = "outright")
	private List<GlOutrightResultEntity> results;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOutrightId() {
		return outrightId;
	}

	public void setOutrightId(long outrightId) {
		this.outrightId = outrightId;
	}

}
