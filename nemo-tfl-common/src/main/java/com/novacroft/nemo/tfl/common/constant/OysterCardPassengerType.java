package com.novacroft.nemo.tfl.common.constant;

/**
 * Oyster passenger type
 */
public enum OysterCardPassengerType {
    ADULT("Adult");

    private OysterCardPassengerType(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
