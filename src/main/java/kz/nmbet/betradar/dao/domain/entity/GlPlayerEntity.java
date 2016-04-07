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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long playerId;

	@Column(length = 512)
	private String givenname;

	@Column(length = 512)
	private String surname;

	@Column(length = 512)
	private String translation;

	private long teamId;

	@Column(length = 512)
	private String shirtnumber;

	private long sportid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
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

	public long getSportid() {
		return sportid;
	}

	public void setSportid(long sportid) {
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
