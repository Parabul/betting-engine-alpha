package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class GlBet {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_outright_odd_id")
	private GlOutrightOddEntity outrightOddEntity;

	private Double betAmount;

	private Double winAmount;

	private Boolean wins;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GlOutrightOddEntity getOutrightOddEntity() {
		return outrightOddEntity;
	}

	public void setOutrightOddEntity(GlOutrightOddEntity outrightOddEntity) {
		this.outrightOddEntity = outrightOddEntity;
	}

	public Double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Boolean getWins() {
		return wins;
	}

	public void setWins(Boolean wins) {
		this.wins = wins;
	}

	@Override
	public String toString() {
		return "GlBet [id=" + id + ", outrightOddEntity=" + outrightOddEntity
				+ ", betAmount=" + betAmount + ", winAmount=" + winAmount
				+ ", wins=" + wins + ", createDate=" + createDate
				+ ", checkDate=" + checkDate + "]";
	}

}
