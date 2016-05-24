package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class GlBet {
	@Id
	@SequenceGenerator(name="GL_BET_ID_GENERATOR", sequenceName="GL_BET_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GL_BET_ID_GENERATOR")
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_outright_odd_id")
	private GlOutrightOddEntity outrightOddEntity;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mm_bet_match_odds", joinColumns = @JoinColumn(name = "bet_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "match_odd_id", referencedColumnName = "id"))
	private List<GlMatchOddEntity> matchOddEntity;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mm_bet_live_odd_fields", joinColumns = @JoinColumn(name = "bet_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "live_odd_field_id", referencedColumnName = "id"))
	private List<GlMatchLiveOddField> liveOdds;

	private Double oddValue;

	private Double betAmount;

	private Double winAmount;

	private Boolean wins;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_owner_id")
	private GlUser owner;
	
	private Long remoteId;

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

	public Double getOddValue() {
		return oddValue;
	}

	public void setOddValue(Double oddValue) {
		this.oddValue = oddValue;
	}

	public GlUser getOwner() {
		return owner;
	}

	public void setOwner(GlUser owner) {
		this.owner = owner;
	}

	public List<GlMatchOddEntity> getMatchOddEntity() {
		return matchOddEntity;
	}

	public void setMatchOddEntity(List<GlMatchOddEntity> matchOddEntity) {
		this.matchOddEntity = matchOddEntity;
	}

	public List<GlMatchLiveOddField> getLiveOdds() {
		return liveOdds;
	}

	public void setLiveOdds(List<GlMatchLiveOddField> liveOdds) {
		this.liveOdds = liveOdds;
	}

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}

}
