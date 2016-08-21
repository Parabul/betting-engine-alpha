package kz.nmbet.betradar.dao.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlSportEntity implements LocalizedEntity {

	@Id
	@SequenceGenerator(name="GL_SPORT_ID_GENERATOR", sequenceName="GL_SPORT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_SPORT_ID_GENERATOR")
	private Integer id;

	private Integer sportId;

	@Column(length = 512)
	private String nameRu;

	@Column(length = 512)
	private String nameKz;

	@Column(length = 512)
	private String nameEn;

	@OneToMany(mappedBy = "sport")
	private List<GlCategoryEntity> categories;
	
	private Boolean isDisabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(Integer sportId) {
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

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

}
