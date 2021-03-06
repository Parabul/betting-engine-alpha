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
import javax.persistence.SequenceGenerator;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlCategoryEntity implements LocalizedEntity {

	@Id
	@SequenceGenerator(name="GL_CATEGORY_ID_GENERATOR", sequenceName="GL_CATEGORY_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_CATEGORY_ID_GENERATOR")
	private Integer id;

	private Long categoryId;

	@Column(length = 512)
	private String nameRu;

	@Column(length = 512)
	private String nameKz;

	@Column(length = 512)
	private String nameEn;

	@OneToMany(mappedBy = "category")
	private List<GlOutrightEntity> outrights;

	@OneToMany(mappedBy = "category")
	private List<GlTournamentEntity> tournaments;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_sport_id")
	private GlSportEntity sport;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getNameKz() {
		return nameKz;
	}

	public void setNameKz(String nameKz) {
		this.nameKz = nameKz;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<GlOutrightEntity> getOutrights() {
		return outrights;
	}

	public void setOutrights(List<GlOutrightEntity> outrights) {
		this.outrights = outrights;
	}

	public GlSportEntity getSport() {
		return sport;
	}

	public void setSport(GlSportEntity sport) {
		this.sport = sport;
	}

	public List<GlTournamentEntity> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<GlTournamentEntity> tournaments) {
		this.tournaments = tournaments;
	}

}
