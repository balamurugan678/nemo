package com.novacroft.nemo.mock_cubic.domain.card;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

/**
 * OysterCardPending domain class to hold the data mapping to the table defined
 * in @Table.
 */

@Entity
@Table(name = "MOCK_OYSTERCARDPENDING")
public class OysterCardPending extends AbstractBaseEntity {
	private static final long serialVersionUID = 1129051193840005690L;

	protected String prestigeId;
	protected Long requestSequenceNumber;
	protected String realtimeFlag;
	protected Long prePayValue;
	protected Long productCode;
	protected Long productPrice;
	protected Date startDate;
	protected Date expiryDate;
	protected Long currency;
	protected Long pickupLocation;

	@SequenceGenerator(name = "mock_oystercardpending_seq", sequenceName = "mock_oystercardpending_seq", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mock_oystercardpending_seq")
	@Column(name = "ID")
	@Override
	public Long getId() {
		return id;
	}

	public String getPrestigeId() {
		return prestigeId;
	}

	public void setPrestigeId(String prestigeId) {
		this.prestigeId = prestigeId;
	}

	public Long getRequestSequenceNumber() {
		return requestSequenceNumber;
	}

	public void setRequestSequenceNumber(Long requestSequenceNumber) {
		this.requestSequenceNumber = requestSequenceNumber;
	}

	public String getRealtimeFlag() {
		return realtimeFlag;
	}

	public void setRealtimeFlag(String realtimeFlag) {
		this.realtimeFlag = realtimeFlag;
	}

	public Long getPrePayValue() {
		return prePayValue;
	}

	public void setPrePayValue(Long prePayValue) {
		this.prePayValue = prePayValue;
	}

	public Long getProductCode() {
		return productCode;
	}

	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}

	public Long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Long getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(Long pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

}