package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;

import java.util.Date;

/**
 * Settlement using payment card: payment or refund against an order.
 */
public class PaymentCardSettlementDTO extends SettlementDTO {
    protected Long paymentCardId;
    /**
     * CyberSource Secure Acceptance payment gateway (request) transaction type
     */
    protected String transactionType;
    /**
     * CyberSource Secure Acceptance payment gateway (request) unique identifier
     */
    protected String transactionUuid;
    /**
     * CyberSource Secure Acceptance payment gateway (reply) result
     */
    protected String decision;
    /**
     * CyberSource Secure Acceptance payment gateway (reply) message
     */
    protected String message;
    /**
     * CyberSource Secure Acceptance payment gateway (reply) result code
     */
    protected String reasonCode;
    /**
     * CyberSource Secure Acceptance payment gateway (reply) transaction identifier
     */
    protected String transactionId;

    /**
     * CyberSource Secure Acceptance payment gateway (reply) authorised amount
     */
    protected Integer authorisedAmount;
    /**
     * CyberSource Secure Acceptance payment gateway (reply) authorisation date and time
     */
    protected Date authorisationTime;
    /**
     * CyberSource Secure Acceptance payment gateway (reply) authorisation transaction reference number
     */
    protected String authorisationTransactionReferenceNumber;

    public PaymentCardSettlementDTO() {
    }

    public PaymentCardSettlementDTO(Long paymentCardId, String transactionType, String transactionUuid, String decision,
                                    String message, String reasonCode, String transactionId, Integer authorisedAmount,
                                    Date authorisationTime, String authorisationTransactionReferenceNumber) {
        this.paymentCardId = paymentCardId;
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

    public PaymentCardSettlementDTO(Long orderId, Integer amount, String transactionUuid) {
        this.orderId = orderId;
        this.settlementDate = new Date();
        this.amount = amount;
        this.status = SettlementStatus.REQUESTED.code();
        this.transactionType = CyberSourcePostTransactionType.SALE.code();
        this.transactionUuid = transactionUuid;
    }
    public PaymentCardSettlementDTO(Long orderId, Integer amount, String transactionUuid,Long paymentCardId) {
        this.paymentCardId=paymentCardId;
    	this.orderId = orderId;
        this.settlementDate = new Date();
        this.amount = amount;
        this.status = SettlementStatus.REQUESTED.code();
        this.transactionType = CyberSourcePostTransactionType.SALE.code();
        this.transactionUuid = transactionUuid;
    }
    
    public Long getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(Long paymentCardId) {
        this.paymentCardId = paymentCardId;
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
