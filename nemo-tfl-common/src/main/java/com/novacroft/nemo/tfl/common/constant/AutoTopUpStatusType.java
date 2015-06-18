package com.novacroft.nemo.tfl.common.constant;

/**
 * Auto top-up activity types for the auto top-up history table
 */
public enum AutoTopUpStatusType {
    AUTO_TOP_UP_ADDED("Auto top-up added"),
    AUTO_TOP_UP_CHANGED("Auto top-up changed"),
    AUTO_TOP_UP_SUCCEEDED("Auto top-up succeeded"),
    AUTO_TOP_UP_FAILED("Auto top-up failed"),
    AUTO_TOP_UP_CANCELLED("Auto top-up cancelled");
    
    private AutoTopUpStatusType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return this.code;
    }
}
