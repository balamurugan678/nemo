package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.utils.StringUtil;

public class RefundSearchResultDTO {
    protected String caseNumber;
    protected String dateCreated;
    protected String agent;
    protected String customerName;
    protected String cardNumber;
    protected String paymentMethod;
    protected String status;

    public RefundSearchResultDTO(String caseNumber, String dateCreated, String agent, String customerName, String cardNumber, String paymentMethod,
                    String status) {
        this.caseNumber = caseNumber;
        this.dateCreated = dateCreated;
        this.agent = (agent != null) ? agent : StringUtil.EMPTY_STRING;
        this.customerName = customerName;
        this.cardNumber = cardNumber;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
