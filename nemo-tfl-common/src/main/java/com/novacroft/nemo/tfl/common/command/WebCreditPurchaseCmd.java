package com.novacroft.nemo.tfl.common.command;

/**
 * Specification for command classes that involve a purchase using web credit
 */
public interface WebCreditPurchaseCmd {
    Integer getWebCreditAvailableAmount();

    Integer getWebCreditApplyAmount();

    Integer getTotalAmt();
}
