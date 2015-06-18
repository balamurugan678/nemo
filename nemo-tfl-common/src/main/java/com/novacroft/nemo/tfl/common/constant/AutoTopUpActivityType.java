package com.novacroft.nemo.tfl.common.constant;

/**
 * Auto top-up activity types for the auto top-up history table
 */
public enum AutoTopUpActivityType {
    SET_UP("Set-up"),
    AUTOLOAD("Top-up"),
    CONFIG_CHANGE("Config change"),
    RESETTLEMENT("Resettlement"),
    CANCELLATION("Cancellation");
    
    private AutoTopUpActivityType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return this.code;
    }
}
