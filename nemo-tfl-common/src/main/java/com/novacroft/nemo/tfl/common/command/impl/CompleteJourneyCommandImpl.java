package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyHistoryDTO;



public class CompleteJourneyCommandImpl extends JourneyHistoryItemCmdImpl {

	protected Integer missingStationId;
	
	protected String reasonForMissisng ;
	
	protected Long pickUpStation;
	
	protected Long preferredStation;
	
	protected Integer journeyId;
	
	protected boolean isAgentCompleted;
	
	protected IncompleteJourneyHistoryDTO incompleteJourneyHistoryDTO;
	
	@DateTimeFormat(pattern=DateConstant.SHORT_DATE_PATTERN)
	protected Date journeyDate;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	protected Date linkedTransactionTime;
	
	protected Integer linkedStationKey;
	
	public Integer getMissingStationId() {
		return missingStationId;
	}

	public void setMissingStationId(Integer missingStationId) {
		this.missingStationId = missingStationId;
	}

	public String getReasonForMissisng() {
		return reasonForMissisng;
	}

	public void setReasonForMissisng(String reasonForMissisng) {
		this.reasonForMissisng = reasonForMissisng;
	}

	public Long getPickUpStation() {
		return pickUpStation;
	}

	public void setPickUpStation(Long pickUpStation) {
		this.pickUpStation = pickUpStation;
	}

	public Long getPreferredStation() {
		return preferredStation;
	}

	public void setPreferredStation(Long preferredStation) {
		this.preferredStation = preferredStation;
	}

	public Date getLinkedTransactionTime() {
		return linkedTransactionTime;
	}

	public void setLinkedTransactionTime(Date linkedTransactionTime) {
		this.linkedTransactionTime = linkedTransactionTime;
	}

	public Integer  getLinkedStationKey() {
		return linkedStationKey;
	}

	public void setLinkedStationKey(Integer linkedStationKey) {
		this.linkedStationKey = linkedStationKey;
	}

	public Integer getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Integer journeyId) {
		this.journeyId = journeyId;
	}

	public Date getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public IncompleteJourneyHistoryDTO getIncompleteJourneyHistoryDTO() {
		return incompleteJourneyHistoryDTO;
	}

	public void setIncompleteJourneyHistoryDTO(IncompleteJourneyHistoryDTO incompleteJourneyHistoryDTO) {
		this.incompleteJourneyHistoryDTO = incompleteJourneyHistoryDTO;
	}

	public boolean isAgentCompleted() {
		return isAgentCompleted;
	}

	public void setAgentCompleted(boolean isAgentCompleted) {
		this.isAgentCompleted = isAgentCompleted;
	}
	
}
