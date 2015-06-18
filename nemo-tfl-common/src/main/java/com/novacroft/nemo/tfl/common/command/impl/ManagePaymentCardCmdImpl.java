package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

/**
 * Manage Payment Card command class
 */
public class ManagePaymentCardCmdImpl {
    protected List<PaymentCardCmdImpl> paymentCards;
    protected Long paymentCardId;
    protected PaymentCardCmdImpl paymentCardCmd;

    public ManagePaymentCardCmdImpl() {
    }

    public ManagePaymentCardCmdImpl(List<PaymentCardCmdImpl> paymentCards) {
        this.paymentCards = paymentCards;
    }

    public List<PaymentCardCmdImpl> getPaymentCards() {
        return paymentCards;
    }

    public void setPaymentCards(List<PaymentCardCmdImpl> paymentCards) {
        this.paymentCards = paymentCards;
    }

    public Long getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(Long paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    public PaymentCardCmdImpl getPaymentCardCmd() {
        return paymentCardCmd;
    }

    public void setPaymentCardCmd(PaymentCardCmdImpl paymentCardCmd) {
        this.paymentCardCmd = paymentCardCmd;
    }
}
