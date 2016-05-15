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

import com.sportradar.sdk.feed.lcoo.entities.CardEntity;
import com.sportradar.sdk.feed.lcoo.enums.CardType;

@Entity
public class GlMatchCardsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String cardTime;

	private Long playerTeamId;

	private String playerName;

	private Integer playerSuperId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_match_id")
	private GlMatchEntity match;

	@Enumerated(EnumType.STRING)
	private CardType cardType;

	public GlMatchCardsEntity() {

	}

	public GlMatchCardsEntity(CardEntity cardEntity) {
		this.cardType = cardEntity.getType();
		this.cardTime = cardEntity.getTime();
		this.playerTeamId = cardEntity.getPlayer().getTeamId();
		this.playerName = cardEntity.getPlayer().getName().getInternational();
		this.playerSuperId = cardEntity.getPlayer().getSuperId();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardTime() {
		return cardTime;
	}

	public void setCardTime(String cardTime) {
		this.cardTime = cardTime;
	}

	public Long getPlayerTeamId() {
		return playerTeamId;
	}

	public void setPlayerTeamId(Long playerTeamId) {
		this.playerTeamId = playerTeamId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getPlayerSuperId() {
		return playerSuperId;
	}

	public void setPlayerSuperId(Integer playerSuperId) {
		this.playerSuperId = playerSuperId;
	}

	public GlMatchEntity getMatch() {
		return match;
	}

	public void setMatch(GlMatchEntity match) {
		this.match = match;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

}
