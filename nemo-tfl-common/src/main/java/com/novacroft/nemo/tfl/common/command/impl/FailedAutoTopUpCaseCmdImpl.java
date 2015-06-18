package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;

/**
 * TfL Failed AutoTopUp
 */
public class FailedAutoTopUpCaseCmdImpl extends CommonOrderCardCmd {
	
	protected Long failedAutoTopUpCaseId;
	protected String caseStatus;
	protected String caseProgressionStatus;
	protected String customerLiability;
    protected Boolean customerNotLiableFlag = Boolean.FALSE;
    protected Integer failedAutoTopUpAmount = 0;
    protected String oysterCardNumber;
    protected Date resettlementEndDate;
    protected Integer emailAttempts=0;
    protected Date resettlementAttemptDate;
    protected Long caseReferenceNumber;
    protected Date hotListDate;
    protected Date refundDate;
    protected Boolean mailNotificationStatus = Boolean.FALSE;
    protected Boolean mobileNotificationStatus = Boolean.FALSE;
    protected String actor;
    protected Integer paymentAttempt;
    protected Integer wacAmountBalance=0;
    protected Long paymentCardId;
	protected String caseNote;
	protected Integer resettlementPeriod=0;
	protected Integer resettlementAmount=0;
    protected Long customerId;
    protected String paymentType;
    
	public FailedAutoTopUpCaseCmdImpl() {
		
	}

	public FailedAutoTopUpCaseCmdImpl(Long failedAutoTopUpCaseId, String caseStatus, String caseProgressionStatus, String customerLiability, Integer failedAutoTopUpAmount,
			String oysterCardNumber, Date resettlementEndDate, Date resettlementAttemptDate, Date hotListDate, Date refundDate, Boolean mailNotificationStatus, 
			Boolean mobileNotificationStatus, String fatuActors, Integer paymentAttempt, Integer wacAmountBalance) {
				this.failedAutoTopUpCaseId = failedAutoTopUpCaseId;
				this.caseStatus = caseStatus;
				this.caseProgressionStatus = caseProgressionStatus;
				this.customerLiability = customerLiability;
				this.failedAutoTopUpAmount = failedAutoTopUpAmount;
				this.oysterCardNumber = oysterCardNumber;
				this.resettlementEndDate = resettlementEndDate;
				this.resettlementAttemptDate = resettlementAttemptDate;
				this.hotListDate = hotListDate;
				this.refundDate = refundDate;
				this.mailNotificationStatus = mailNotificationStatus;
				this.mobileNotificationStatus = mobileNotificationStatus;
				this.actor = fatuActors;
				this.paymentAttempt = paymentAttempt;
				this.wacAmountBalance = wacAmountBalance;
	}

	public Long getFailedAutoTopUpCaseId() {
		return failedAutoTopUpCaseId;
	}

	public void setFailedAutoTopUpCaseId(Long failedAutoTopUpCaseId) {
		this.failedAutoTopUpCaseId = failedAutoTopUpCaseId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCaseProgressionStatus() {
		return caseProgressionStatus;
	}

	public void setCaseProgressionStatus(String caseProgressionStatus) {
		this.caseProgressionStatus = caseProgressionStatus;
	}

	public String getCustomerLiability() {
		return customerLiability;
	}

	public void setCustomerLiability(String customerLiability) {
		this.customerLiability = customerLiability;
	}

	public Integer getFailedAutoTopUpAmount() {
		return failedAutoTopUpAmount;
	}

	public void setFailedAutoTopUpAmount(Integer failedAutoTopUpAmount) {
		this.failedAutoTopUpAmount = failedAutoTopUpAmount;
	}

	public String getOysterCardNumber() {
		return oysterCardNumber;
	}

	public void setOysterCardNumber(String oysterCardNumber) {
		this.oysterCardNumber = oysterCardNumber;
	}

	public Date getResettlementEndDate() {
		return resettlementEndDate;
	}

	public void setResettlementEndDate(Date resettlementEndDate) {
		this.resettlementEndDate = resettlementEndDate;
	}

	public Integer getEmailAttempts() {
		return emailAttempts;
	}

	public void setEmailAttempts(Integer emailAttempts) {
		this.emailAttempts = emailAttempts;
	}

	public Date getResettlementAttemptDate() {
		return resettlementAttemptDate;
	}

	public void setResettlementAttemptDate(Date resettlementAttemptDate) {
		this.resettlementAttemptDate = resettlementAttemptDate;
	}

	public Long getCaseReferenceNumber() {
		return caseReferenceNumber;
	}

	public void setCaseReferenceNumber(Long caseReferenceNumber) {
		this.caseReferenceNumber = caseReferenceNumber;
	}

	public Date getHotListDate() {
		return hotListDate;
	}

	public void setHotListDate(Date hotListDate) {
		this.hotListDate = hotListDate;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Boolean getMailNotificationStatus() {
		return mailNotificationStatus;
	}

	public void setMailNotificationStatus(Boolean mailNotificationStatus) {
		this.mailNotificationStatus = mailNotificationStatus;
	}

	public Boolean getMobileNotificationStatus() {
		return mobileNotificationStatus;
	}

	public void setMobileNotificationStatus(Boolean mobileNotificationStatus) {
		this.mobileNotificationStatus = mobileNotificationStatus;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Integer getPaymentAttempt() {
		return paymentAttempt;
	}

	public void setPaymentAttempt(Integer paymentAttempt) {
		this.paymentAttempt = paymentAttempt;
	}

	public Integer getWacAmountBalance() {
		return wacAmountBalance;
	}

	public void setWacAmountBalance(Integer wacAmountBalance) {
		this.wacAmountBalance = wacAmountBalance;
	}

	public Long getPaymentCardId() {
		return paymentCardId;
	}

	public void setPaymentCardId(Long paymentCardId) {
		this.paymentCardId = paymentCardId;
	}

	public String getCaseNote() {
		return caseNote;
	}

	public void setCaseNote(String caseNote) {
		this.caseNote = caseNote;
	}

	public Integer getResettlementPeriod() {
		return resettlementPeriod;
	}

	public void setResettlementPeriod(Integer resettlementPeriod) {
		this.resettlementPeriod = resettlementPeriod;
	}

	public Integer getResettlementAmount() {
		return resettlementAmount;
	}

	public void setResettlementAmount(Integer resettlementAmount) {
		this.resettlementAmount = resettlementAmount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Boolean getCustomerNotLiableFlag() {
		return customerNotLiableFlag;
	}

	public void setCustomerNotLiableFlag(Boolean customerNotLiableFlag) {
		this.customerNotLiableFlag = customerNotLiableFlag;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
}