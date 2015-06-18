package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.common.command.OysterCardCmd;

/**
 * Command (MVC model) class for an oyster card
 */
public class CardCmdImpl implements OysterCardCmd {
    protected String cardNumber;

    @Override
    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
