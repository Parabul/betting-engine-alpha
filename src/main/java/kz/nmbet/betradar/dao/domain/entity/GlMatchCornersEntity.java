package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.sportradar.sdk.feed.common.enums.Team;
import com.sportradar.sdk.feed.lcoo.entities.CornerEntity;

@Entity
public class GlMatchCornersEntity {

	@Id
	@SequenceGenerator(name="GL_MATCH_CORNERS_ID_GENERATOR", sequenceName="GL_MATCH_CORNERS_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_MATCH_CORNERS_ID_GENERATOR")
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;


	@Enumerated(EnumType.STRING)
	private Team cornerTeam;

	private int cornerCount;

	private String cornerType;

	public GlMatchCornersEntity() {

	}

	public GlMatchCornersEntity(CornerEntity cornerEntity) {
		this.cornerCount = cornerEntity.getCount();
		this.cornerTeam = cornerEntity.getTeam();
		this.cornerType = cornerEntity.getType();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

	public Team getCornerTeam() {
		return cornerTeam;
	}

	public void setCornerTeam(Team cornerTeam) {
		this.cornerTeam = cornerTeam;
	}

	public int getCornerCount() {
		return cornerCount;
	}

	public void setCornerCount(int cornerCount) {
		this.cornerCount = cornerCount;
	}

	public String getCornerType() {
		return cornerType;
	}

	public void setCornerType(String cornerType) {
		this.cornerType = cornerType;
	}

	

}
