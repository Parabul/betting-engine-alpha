package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Entity
public class GlPlayerEntity implements LocalizedEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;

	private Integer playerId;

	@Column(length = 512)
	private String givenname;

	@Column(length = 512)
	private String surname;

	@Column(length = 512)
	private String translation;

	private Integer teamId;

	@Column(length = 512)
	private String shirtnumber;

	private Integer sportid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getShirtnumber() {
		return shirtnumber;
	}

	public void setShirtnumber(String shirtnumber) {
		this.shirtnumber = shirtnumber;
	}

	public Integer getSportid() {
		return sportid;
	}

	public void setSportid(Integer sportid) {
		this.sportid = sportid;
	}

	@Override
	public String getNameRu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameKz() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameEn() {
		// TODO Auto-generated method stub
		return null;
	}

}
