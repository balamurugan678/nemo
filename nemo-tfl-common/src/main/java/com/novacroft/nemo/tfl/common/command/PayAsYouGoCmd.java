package com.novacroft.nemo.tfl.common.command;

/**
 * Pay as you go command interface TfL definition
 */
public interface PayAsYouGoCmd {
    Long getCardId();

    Integer getCreditBalance();

    Integer getAutoTopUpCreditBalance();

    Integer getAutoTopUpAmt();
    
    Integer getExistingCreditBalance();
}
