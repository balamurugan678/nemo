package com.novacroft.nemo.tfl.common.constant;

/**
 * Journey History email statement frequency
 */
public enum JourneyHistoryFrequency {
    NONE("None"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly");

    private JourneyHistoryFrequency(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
