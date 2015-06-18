package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;

public class RefundSearchCmd {
    protected String caseNumber;
    protected String agentLastName;
    protected String agentFirstName;
    protected String sapNumber;
    protected String customerLastName;
    protected String customerFirstName;
    protected String cardNumber;
    protected String paymentMethod;
    protected String bacsNumber;
    protected String chequeNumber;
    protected String status;
    protected String exact;

    protected List<RefundSearchResultDTO> refunds;

    public RefundSearchCmd() {

    }

    public RefundSearchCmd(String caseNumber, String agentLastName, String agentFirstName, String sapNumber,
                    String customerLastName, String customerFirstName, String cardNumber, String bacsNumber, String chequeNumber) {
        this.caseNumber = caseNumber;
        this.agentLastName = agentLastName;
        this.agentFirstName = agentFirstName;
        this.sapNumber = sapNumber;
        this.customerLastName = customerLastName;
        this.customerFirstName = customerFirstName;
        this.cardNumber = cardNumber;
        this.bacsNumber = bacsNumber;
        this.chequeNumber = chequeNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getAgentLastName() {
        return agentLastName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName = agentLastName;
    }

    public String getAgentFirstName() {
        return agentFirstName;
    }

    public void setAgentFirstName(String agentFirstName) {
        this.agentFirstName = agentFirstName;
    }

    public String getSapNumber() {
        return sapNumber;
    }

    public void setSapNumber(String sapNumber) {
        this.sapNumber = sapNumber;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
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

    public String getBacsNumber() {
        return bacsNumber;
    }

    public void setBacsNumber(String bacsNumber) {
        this.bacsNumber = bacsNumber;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExact() {
        return exact;
    }

    public void setExact(String exact) {
        this.exact = exact;
    }

    public List<RefundSearchResultDTO> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundSearchResultDTO> refunds) {
        this.refunds = refunds;
    }

    public void toUpperCase() {
        this.caseNumber = caseNumber.toUpperCase();
        this.agentFirstName = agentFirstName.toUpperCase();
        this.agentLastName = agentLastName.toUpperCase();
        this.customerFirstName = customerFirstName.toUpperCase();
        this.customerLastName = customerLastName.toUpperCase();
    }
}
