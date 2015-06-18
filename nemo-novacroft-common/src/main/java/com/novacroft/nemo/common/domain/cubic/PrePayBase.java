package com.novacroft.nemo.common.domain.cubic;

public class PrePayBase {
    protected Integer currency;
    protected Integer pickupLocation;
    protected String realTimeFlag;
    protected Integer requestSequenceNumber;
	public Integer getCurrency() {
		return currency;
	}
	public void setCurrency(Integer currency) {
		this.currency = currency;
	}
	public Integer getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(Integer pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public String getRealTimeFlag() {
		return realTimeFlag;
	}
	public void setRealTimeFlag(String realTimeFlag) {
		this.realTimeFlag = realTimeFlag;
	}
	public Integer getRequestSequenceNumber() {
		return requestSequenceNumber;
	}
	public void setRequestSequenceNumber(Integer requestSequenceNumber) {
		this.requestSequenceNumber = requestSequenceNumber;
	}
}
