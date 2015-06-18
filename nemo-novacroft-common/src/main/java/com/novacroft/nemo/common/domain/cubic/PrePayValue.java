package com.novacroft.nemo.common.domain.cubic;

/**
 *  Card Pre Pay Value.
 */
public class PrePayValue extends PrePayBase implements PrePay{
    protected Integer prePayValue;
    protected Integer balance;


    public final Integer getPrePayValue() {
        return prePayValue;
    }

    public final void setPrePayValue(final Integer prePayValue) {
        this.prePayValue = prePayValue;
    }

    public final Integer getBalance() {
        return balance;
    }

    public final void setBalance(final Integer balance) {
        this.balance = balance;
    }
}
