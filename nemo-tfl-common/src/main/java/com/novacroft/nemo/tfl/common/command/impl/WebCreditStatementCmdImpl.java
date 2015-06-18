package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

/**
 * Web account credit statement command (MVC model) class
 */
public class WebCreditStatementCmdImpl {
    protected Integer currentBalance;
    protected List<WebCreditStatementLineCmdImpl> statementLines;

    public WebCreditStatementCmdImpl() {
    }

    public WebCreditStatementCmdImpl(Integer currentBalance, List<WebCreditStatementLineCmdImpl> statementLines) {
        this.currentBalance = currentBalance;
        this.statementLines = statementLines;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<WebCreditStatementLineCmdImpl> getStatementLines() {
        return statementLines;
    }

    public void setStatementLines(List<WebCreditStatementLineCmdImpl> statementLines) {
        this.statementLines = statementLines;
    }
}
