package com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification;

import java.util.Date;

import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

public class IncompleteJourneyNotificationDTO  {
	protected String agentUserName;
    protected Boolean eligibleForSSR;
    protected String ineligibilityNarrative;
    protected Integer linkedCappingScheme;
    protected Integer linkedCardTypeKey;
    protected Integer linkedDailyCappingFlag;
    protected Integer linkedDayKey;
    protected Date linkedDayKeyAsDate;
    protected Integer linkedPPTProductCodeKey;
    protected Integer linkedPassengerAgeKey;
    protected Integer linkedPassengerType;
    protected Integer linkedPrepayUsed;
    protected Integer linkedRolloverSequenceNo;
    protected Integer linkedSequenceNo;
    protected Integer linkedStationKey;
    protected Integer linkedSubsystemID;
    protected Integer linkedTKTUsed;
    protected Date linkedTransactionDateTime;
    protected Integer linkedTransactionType;
    protected NotificationStatus ssrAutoFillNotificationStatus;
    protected Date ssrAutoFillNotificationStatusChangeDateTime;
    protected String ssrCaseReference;
    protected Boolean ssrSubmittedByAgent;
	public String getAgentUserName() {
		return agentUserName;
	}
	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}
	public Boolean getEligibleForSSR() {
		return eligibleForSSR;
	}
	public void setEligibleForSSR(Boolean eligibleForSSR) {
		this.eligibleForSSR = eligibleForSSR;
	}
	public String getIneligibilityNarrative() {
		return ineligibilityNarrative;
	}
	public void setIneligibilityNarrative(String ineligibilityNarrative) {
		this.ineligibilityNarrative = ineligibilityNarrative;
	}
	public Integer getLinkedCappingScheme() {
		return linkedCappingScheme;
	}
	public void setLinkedCappingScheme(Integer linkedCappingScheme) {
		this.linkedCappingScheme = linkedCappingScheme;
	}
	public Integer getLinkedCardTypeKey() {
		return linkedCardTypeKey;
	}
	public void setLinkedCardTypeKey(Integer linkedCardTypeKey) {
		this.linkedCardTypeKey = linkedCardTypeKey;
	}
	public Integer getLinkedDailyCappingFlag() {
		return linkedDailyCappingFlag;
	}
	public void setLinkedDailyCappingFlag(Integer linkedDailyCappingFlag) {
		this.linkedDailyCappingFlag = linkedDailyCappingFlag;
	}
	public Integer getLinkedDayKey() {
		return linkedDayKey;
	}
	public void setLinkedDayKey(Integer linkedDayKey) {
		this.linkedDayKey = linkedDayKey;
	}
	public Date getLinkedDayKeyAsDate() {
		return linkedDayKeyAsDate;
	}
	public void setLinkedDayKeyAsDate(Date linkedDayKeyAsDate) {
		this.linkedDayKeyAsDate = linkedDayKeyAsDate;
	}
	public Integer getLinkedPPTProductCodeKey() {
		return linkedPPTProductCodeKey;
	}
	public void setLinkedPPTProductCodeKey(Integer linkedPPTProductCodeKey) {
		this.linkedPPTProductCodeKey = linkedPPTProductCodeKey;
	}
	public Integer getLinkedPassengerAgeKey() {
		return linkedPassengerAgeKey;
	}
	public void setLinkedPassengerAgeKey(Integer linkedPassengerAgeKey) {
		this.linkedPassengerAgeKey = linkedPassengerAgeKey;
	}
	public Integer getLinkedPassengerType() {
		return linkedPassengerType;
	}
	public void setLinkedPassengerType(Integer linkedPassengerType) {
		this.linkedPassengerType = linkedPassengerType;
	}
	public Integer getLinkedPrepayUsed() {
		return linkedPrepayUsed;
	}
	public void setLinkedPrepayUsed(Integer linkedPrepayUsed) {
		this.linkedPrepayUsed = linkedPrepayUsed;
	}
	public Integer getLinkedRolloverSequenceNo() {
		return linkedRolloverSequenceNo;
	}
	public void setLinkedRolloverSequenceNo(Integer linkedRolloverSequenceNo) {
		this.linkedRolloverSequenceNo = linkedRolloverSequenceNo;
	}
	public Integer getLinkedSequenceNo() {
		return linkedSequenceNo;
	}
	public void setLinkedSequenceNo(Integer linkedSequenceNo) {
		this.linkedSequenceNo = linkedSequenceNo;
	}
	public Integer getLinkedStationKey() {
		return linkedStationKey;
	}
	public void setLinkedStationKey(Integer linkedStationKey) {
		this.linkedStationKey = linkedStationKey;
	}
	public Integer getLinkedSubsystemID() {
		return linkedSubsystemID;
	}
	public void setLinkedSubsystemID(Integer linkedSubsystemID) {
		this.linkedSubsystemID = linkedSubsystemID;
	}
	public Integer getLinkedTKTUsed() {
		return linkedTKTUsed;
	}
	public void setLinkedTKTUsed(Integer linkedTKTUsed) {
		this.linkedTKTUsed = linkedTKTUsed;
	}
	public Date getLinkedTransactionDateTime() {
		return linkedTransactionDateTime;
	}
	public void setLinkedTransactionDateTime(Date linkedTransactionDateTime) {
		this.linkedTransactionDateTime = linkedTransactionDateTime;
	}
	public Integer getLinkedTransactionType() {
		return linkedTransactionType;
	}
	public void setLinkedTransactionType(Integer linkedTransactionType) {
		this.linkedTransactionType = linkedTransactionType;
	}
	public NotificationStatus getSsrAutoFillNotificationStatus() {
		return ssrAutoFillNotificationStatus;
	}
	public void setSsrAutoFillNotificationStatus(NotificationStatus ssrAutoFillNotificationStatus) {
		this.ssrAutoFillNotificationStatus = ssrAutoFillNotificationStatus;
	}
	public Date getSsrAutoFillNotificationStatusChangeDateTime() {
		return ssrAutoFillNotificationStatusChangeDateTime;
	}
	public void setSsrAutoFillNotificationStatusChangeDateTime(
			Date ssrAutoFillNotificationStatusChangeDateTime) {
		this.ssrAutoFillNotificationStatusChangeDateTime = ssrAutoFillNotificationStatusChangeDateTime;
	}
	public String getSsrCaseReference() {
		return ssrCaseReference;
	}
	public void setSsrCaseReference(String ssrCaseReference) {
		this.ssrCaseReference = ssrCaseReference;
	}
	public Boolean getSsrSubmittedByAgent() {
		return ssrSubmittedByAgent;
	}
	public void setSsrSubmittedByAgent(Boolean ssrSubmittedByAgent) {
		this.ssrSubmittedByAgent = ssrSubmittedByAgent;
	}
    
    
}
