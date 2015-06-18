package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.Date;

public class TapResponseDTO {
    private String busRouteId;
    private String deviceCategory;
    private Boolean isSyntheticTap;
    private Integer locationNLC;
    private Integer rolloverSequenceNo;
    private Integer sequenceNo;
    private Date transactionDateTime;
    private Integer transactionOffset;
    private Integer transactionType;
	public String getBusRouteId() {
		return busRouteId;
	}
	public void setBusRouteId(String busRouteId) {
		this.busRouteId = busRouteId;
	}
	public String getDeviceCategory() {
		return deviceCategory;
	}
	public void setDeviceCategory(String deviceCategory) {
		this.deviceCategory = deviceCategory;
	}
	public Boolean getIsSyntheticTap() {
		return isSyntheticTap;
	}
	public void setIsSyntheticTap(Boolean isSyntheticTap) {
		this.isSyntheticTap = isSyntheticTap;
	}
	public Integer getLocationNLC() {
		return locationNLC;
	}
	public void setLocationNLC(Integer locationNLC) {
		this.locationNLC = locationNLC;
	}
	public Integer getRolloverSequenceNo() {
		return rolloverSequenceNo;
	}
	public void setRolloverSequenceNo(Integer rolloverSequenceNo) {
		this.rolloverSequenceNo = rolloverSequenceNo;
	}
	public Integer getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public Date getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public Integer getTransactionOffset() {
		return transactionOffset;
	}
	public void setTransactionOffset(Integer transactionOffset) {
		this.transactionOffset = transactionOffset;
	}
	public Integer getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}
    
    
}
