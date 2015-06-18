package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

public class FailedAutoTopUpCaseDTO extends AbstractBaseDTO {

    private static final long serialVersionUID = 4024721582718850324L;
    private Long caseReferenceNumber;
    private String caseStatus;
    private String caseProgressionStatus;
    private String customerLiability;
    private Double failedAutoTopUpAmount;
    private Long customerId;
    private Long cardId;
    private String cardNumber;
    private FailedAutoTopUpPaymentDetailDTO failedAutoTopUpPaymentDetail;
    private Date resettlementEndDate;
    private String caseNote;


    public Long getCaseReferenceNumber() {
        return caseReferenceNumber;
    }

    public void setCaseReferenceNumber(Long caseReferenceNumber) {
        this.caseReferenceNumber = caseReferenceNumber;
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

    public Double getFailedAutoTopUpAmount() {
        return failedAutoTopUpAmount;
    }

    public void setFailedAutoTopUpAmount(Double failedAutoTopUpAmount) {
        this.failedAutoTopUpAmount = failedAutoTopUpAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public FailedAutoTopUpPaymentDetailDTO getFailedAutoTopUpPaymentDetail() {
        return failedAutoTopUpPaymentDetail;
    }

    public void setFailedAutoTopUpPaymentDetail(FailedAutoTopUpPaymentDetailDTO failedAutoTopUpPaymentDetail) {
        this.failedAutoTopUpPaymentDetail = failedAutoTopUpPaymentDetail;
    }

    public Date getResettlementEndDate() {
        return resettlementEndDate;
    }

    public void setResettlementEndDate(Date resettlementEndDate) {
        this.resettlementEndDate = resettlementEndDate;
    }

	public String getCaseNote() {
		return caseNote;
	}

	public void setCaseNote(String caseNote) {
		this.caseNote = caseNote;
	}
}
