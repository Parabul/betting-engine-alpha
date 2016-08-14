package kz.nmbet.betradar.dao.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class GlCashbox {

	@Id
	@SequenceGenerator(name = "GL_CASHBOX_ID_GENERATOR", sequenceName = "GL_CASHBOX_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GL_CASHBOX_ID_GENERATOR")
	private Integer id;

	private boolean enabled;

	private String title;

	private String address;

	private Double maxAmount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

}
