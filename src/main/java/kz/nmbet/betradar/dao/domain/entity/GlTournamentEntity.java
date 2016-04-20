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

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlTournamentEntity implements LocalizedEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;

	private Integer tournamentId;

	@Column(length = 512)
	private String nameRu;

	@Column(length = 512)
	private String nameKz;

	@Column(length = 512)
	private String nameEn;
	
	@OneToMany(mappedBy = "tournament")
	private List<GlMatchEntity> matches;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_category_id")
	private GlCategoryEntity category;


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

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

	public List<GlMatchEntity> getMatches() {
		return matches;
	}

	public void setMatches(List<GlMatchEntity> matches) {
		this.matches = matches;
	}

	public GlCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(GlCategoryEntity category) {
		this.category = category;
	}

}
