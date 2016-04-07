package kz.nmbet.betradar.dao.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlSportEntity implements LocalizedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long sportId;

	@Column(length = 512)
	private String nameRu;

	@Column(length = 512)
	private String nameKz;

	@Column(length = 512)
	private String nameEn;

	@OneToMany(mappedBy = "sport")
	private List<GlCategoryEntity> categories;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSportId() {
		return sportId;
	}

	public void setSportId(long sportId) {
		this.sportId = sportId;
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

}
