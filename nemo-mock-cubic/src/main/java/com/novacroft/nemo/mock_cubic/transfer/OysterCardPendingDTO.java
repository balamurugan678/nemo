package com.novacroft.nemo.mock_cubic.transfer;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class OysterCardPendingDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 4106029039825769372L;
	private static final int HASH_INITIAL = 191;
	private static final int HASH_MULTIPLIER = 193;

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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		OysterCardPpvPendingDTO that = (OysterCardPpvPendingDTO) object;

		return new EqualsBuilder().append(prestigeId, that.prestigeId)
				.append(requestSequenceNumber, that.requestSequenceNumber)
				.append(realtimeFlag, that.realtimeFlag)
				.append(prePayValue, that.prePayValue)
				.append(currency, that.currency)
				.append(pickupLocation, that.pickupLocation).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(HASH_INITIAL, HASH_MULTIPLIER)
				.append(prestigeId).append(requestSequenceNumber)
				.append(realtimeFlag).append(prePayValue).append(currency)
				.append(pickupLocation).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(prestigeId)
				.append(requestSequenceNumber).append(realtimeFlag)
				.append(prePayValue).append(currency).append(pickupLocation)
				.toString();
	}
}
