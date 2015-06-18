package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;

public class RefundCaseCmd {
    protected String caseNumber;
    protected Long totalRefund;
    protected String createdDate;
    protected String effectiveDate;
    protected String paidDate;
    protected String status;
    protected Boolean held;
    protected String heldSince;
    protected String agent;

    protected String sapNumber;
    protected String customerName;
    protected String customerUsername;
    protected List<String> customerAddress;
    protected String cardNumber;

    protected String paymentType;
    protected String bacsNumber;
    protected String chequeNumber;

    protected List<CaseHistoryNote> caseNotes;
    protected String processInstanceId;

    public RefundCaseCmd() {

    }

    public RefundCaseCmd(String caseNumber, Long totalRefund, String createdDate, String effectiveDate, String paidDate, String status, Boolean held,
                    String heldSince, String agent, String sapNumber, String customerName, String customerUsername, List<String> customerAddress,
                    String cardNumber, String paymentType, String bacsNumber, String chequeNumber, List<CaseHistoryNote> caseNotes) {
        this.caseNumber = caseNumber;

        this.totalRefund = totalRefund;
        this.createdDate = createdDate;
        this.effectiveDate = effectiveDate;
        this.paidDate = paidDate;
        this.status = status;
        this.held = held;
        this.heldSince = heldSince;
        this.agent = (agent != null) ? agent : StringUtil.EMPTY_STRING;
        this.sapNumber = sapNumber;
        this.customerName = customerName;
        this.customerUsername = customerUsername;
        this.customerAddress = customerAddress;
        this.cardNumber = cardNumber;
        this.paymentType = paymentType;
        this.bacsNumber = bacsNumber;
        this.chequeNumber = chequeNumber;
        this.caseNotes = caseNotes;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public Long getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Long totalRefund) {
        this.totalRefund = totalRefund;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getHeld() {
        return held;
    }

    public void setHeld(Boolean held) {
        this.held = held;
    }

    public String getHeldSince() {
        return heldSince;
    }

    public void setHeldSince(String heldSince) {
        this.heldSince = heldSince;
    }

    public String getSapNumber() {
        return sapNumber;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public void setSapNumber(String sapNumber) {
        this.sapNumber = sapNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public List<String> getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(List<String> customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public List<CaseHistoryNote> getCaseNotes() {
        return caseNotes;
    }

    public void setCaseNotes(List<CaseHistoryNote> caseNotes) {
        this.caseNotes = caseNotes;
    }
}
