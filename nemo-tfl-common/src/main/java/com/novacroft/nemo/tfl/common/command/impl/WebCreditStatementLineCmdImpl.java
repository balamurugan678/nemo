package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;

import static java.lang.Math.abs;

/**
 * Web account credit statement line command (MVC model) class
 */
public class WebCreditStatementLineCmdImpl {
    protected Date transactionDate;
    protected String item;
    protected String referenceNumber;
    protected Integer creditAmount;
    protected Integer debitAmount;
    protected Integer cumulativeBalanceAmount;

    public WebCreditStatementLineCmdImpl() {
    }

    public WebCreditStatementLineCmdImpl(Date transactionDate, String item, String referenceNumber, Integer creditAmount,
                                                Integer debitAmount, Integer cumulativeBalanceAmount) {
        this.transactionDate = transactionDate;
        this.item = item;
        this.referenceNumber = referenceNumber;
        this.creditAmount = creditAmount;
        this.debitAmount = debitAmount;
        this.cumulativeBalanceAmount = cumulativeBalanceAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Integer debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Integer getCumulativeBalanceAmount() {
        return cumulativeBalanceAmount;
    }

    public void setCumulativeBalanceAmount(Integer cumulativeBalanceAmount) {
        this.cumulativeBalanceAmount = cumulativeBalanceAmount;
    }

    public Integer getAbsoluteDebitAmount() {
        return debitAmount == null ? null : abs(debitAmount);
    }

    public Integer getAbsoluteCreditAmount() {
        return creditAmount == null ? null : abs(creditAmount);
    }
}
