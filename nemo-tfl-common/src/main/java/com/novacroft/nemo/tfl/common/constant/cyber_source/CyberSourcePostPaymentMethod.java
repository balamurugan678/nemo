package com.novacroft.nemo.tfl.common.constant.cyber_source;

/**
 * CyberSource payment gateway HTTP POST payment methods
 */
public enum CyberSourcePostPaymentMethod {
    CARD("card");

    private CyberSourcePostPaymentMethod(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }

}
