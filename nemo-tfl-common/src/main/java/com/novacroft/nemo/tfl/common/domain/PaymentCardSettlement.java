package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Settlement using payment card: payment or refund against an order.
 */
@Entity
@DiscriminatorValue("PaymentCard")
public class PaymentCardSettlement extends Settlement {
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

    @Column(name = "AUTHORISATIONREFERENCENUMBER")
    public String getAuthorisationTransactionReferenceNumber() {
        return authorisationTransactionReferenceNumber;
    }

    public void setAuthorisationTransactionReferenceNumber(String authorisationTransactionReferenceNumber) {
        this.authorisationTransactionReferenceNumber = authorisationTransactionReferenceNumber;
    }
}
