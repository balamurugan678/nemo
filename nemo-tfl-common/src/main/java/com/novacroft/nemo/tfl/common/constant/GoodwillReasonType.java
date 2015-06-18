package com.novacroft.nemo.tfl.common.constant;

public enum GoodwillReasonType {
    OYSTER("Oyster"), CONTACTLESS_PAYMENT_CARD("ContactlessPaymentCard");

    private String code;

    GoodwillReasonType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}