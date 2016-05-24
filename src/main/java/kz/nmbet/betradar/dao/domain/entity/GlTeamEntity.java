package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlTeamEntity implements LocalizedEntity {

	@Id
	@SequenceGenerator(name="GL_TEAM_ID_GENERATOR", sequenceName="GL_TEAM_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_TEAM_ID_GENERATOR")
	private Integer id;

	private Integer superTeamId;

	@Column(length = 512)
	private String nameRu;

	@Column(length = 512)
	private String nameKz;

	@Column(length = 512)
	private String nameEn;

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

	public Integer getSuperTeamId() {
		return superTeamId;
	}

	public void setSuperTeamId(Integer superTeamId) {
		this.superTeamId = superTeamId;
	}

}
