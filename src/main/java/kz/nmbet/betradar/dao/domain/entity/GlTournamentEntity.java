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
public class GlTournamentEntity implements LocalizedEntity {

	@Id
	@SequenceGenerator(name = "GL_TOURNAMENT_ID_GENERATOR", sequenceName = "GL_TOURNAMENT_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GL_TOURNAMENT_ID_GENERATOR")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tournamentId == null) ? 0 : tournamentId.hashCode());
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
		GlTournamentEntity other = (GlTournamentEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tournamentId == null) {
			if (other.tournamentId != null)
				return false;
		} else if (!tournamentId.equals(other.tournamentId))
			return false;
		return true;
	}

}
