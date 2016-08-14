package kz.nmbet.betradar.dao.domain.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kz.nmbet.betradar.dao.domain.types.PaymentOrderStatus;

@Entity
public class GlPaymentOrder {

	@Id
	@SequenceGenerator(name = "GL_CASHBOX_ID_GENERATOR", sequenceName = "GL_CASHBOX_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GL_CASHBOX_ID_GENERATOR")
	private Integer id;

	@Enumerated(EnumType.STRING)
	private PaymentOrderStatus orderStatus;

	private String note;

	private Double amount;

	private String secret;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "gl_cashbox_id")
	private GlCashbox cashbox;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gl_owner_id")
	private GlUser owner;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PaymentOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(PaymentOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public GlCashbox getCashbox() {
		return cashbox;
	}

	public void setCashbox(GlCashbox cashbox) {
		this.cashbox = cashbox;
	}

	public GlUser getOwner() {
		return owner;
	}

	public void setOwner(GlUser owner) {
		this.owner = owner;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
