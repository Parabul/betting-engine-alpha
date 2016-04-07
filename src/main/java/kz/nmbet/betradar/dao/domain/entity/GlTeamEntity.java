package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlTeamEntity implements LocalizedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long superTeamId;

	@Column(length = 512)
	private String nameRu;

	@Column(length = 512)
	private String nameKz;

	@Column(length = 512)
	private String nameEn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getSuperTeamId() {
		return superTeamId;
	}

	public void setSuperTeamId(Long superTeamId) {
		this.superTeamId = superTeamId;
	}

}
