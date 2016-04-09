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

import kz.nmbet.betradar.dao.domain.types.OutrightOddsType;

@Entity
public class GlOutrightOddEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer teamId;

	private Double value;

	private String specialBetValue;

	private String outCome;
	
	private String outcomeId;

	@Enumerated(EnumType.STRING)
	private OutrightOddsType oddsType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_outright_id")
	private GlOutrightEntity outright;

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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public OutrightOddsType getOddsType() {
		return oddsType;
	}

	public void setOddsType(OutrightOddsType oddsType) {
		this.oddsType = oddsType;
	}

	public GlOutrightEntity getOutright() {
		return outright;
	}

	public void setOutright(GlOutrightEntity outright) {
		this.outright = outright;
	}

	public String getSpecialBetValue() {
		return specialBetValue;
	}

	public void setSpecialBetValue(String specialBetValue) {
		this.specialBetValue = specialBetValue;
	}

	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	public String getOutcomeId() {
		return outcomeId;
	}

	public void setOutcomeId(String outcomeId) {
		this.outcomeId = outcomeId;
	}

}
