package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;

import java.util.Date;

/**
 * Settlement using web account credit: payment or refund against an order.
 */
public class WebCreditSettlementDTO extends SettlementDTO {

    protected String transactionType;
    protected String transactionUuid;
    protected String decision;
    protected String message;
    protected String reasonCode;
    protected String transactionId;

    protected Integer authorisedAmount;
    protected Date authorisationTime;
    protected String authorisationTransactionReferenceNumber;

    public WebCreditSettlementDTO() {
    }

    public WebCreditSettlementDTO(String transactionType, String transactionUuid, String decision,
                                    String message, String reasonCode, String transactionId, Integer authorisedAmount,
                                    Date authorisationTime, String authorisationTransactionReferenceNumber) {
        this.transactionType = transactionType;
        this.transactionUuid = transactionUuid;
        this.decision = decision;
        this.message = message;
        this.reasonCode = reasonCode;
        this.transactionId = transactionId;
        this.authorisedAmount = authorisedAmount;
        this.authorisationTime = authorisationTime;
        this.authorisationTransactionReferenceNumber = authorisationTransactionReferenceNumber;
    }

    public WebCreditSettlementDTO(Long orderId, Integer amount, String transactionUuid) {
        this.orderId = orderId;
        this.settlementDate = new Date();
        this.amount = amount;
        this.status = SettlementStatus.REQUESTED.code();
        this.transactionType = CyberSourcePostTransactionType.SALE.code();
        this.transactionUuid = transactionUuid;
    }
    
    
    public WebCreditSettlementDTO(Long orderId, Integer amount) {
        this.orderId = orderId;
        this.settlementDate = new Date();
        this.amount = amount;
        this.status = SettlementStatus.REQUESTED.code();
        this.transactionType = CyberSourcePostTransactionType.SALE.code();
    }

	public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAuthorisedAmount() {
        return authorisedAmount;
    }

    public void setAuthorisedAmount(Integer authorisedAmount) {
        this.authorisedAmount = authorisedAmount;
    }

    public Date getAuthorisationTime() {
        return authorisationTime;
    }

    public void setAuthorisationTime(Date authorisationTime) {
        this.authorisationTime = authorisationTime;
    }

    public String getAuthorisationTransactionReferenceNumber() {
        return authorisationTransactionReferenceNumber;
    }

    public void setAuthorisationTransactionReferenceNumber(String authorisationTransactionReferenceNumber) {
        this.authorisationTransactionReferenceNumber = authorisationTransactionReferenceNumber;
    }

	
}