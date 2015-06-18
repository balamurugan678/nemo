package com.novacroft.nemo.tfl.common.constant;

/**
 * 
 * Hotlisted card reason types
 * 
 */
public enum HotlistReasonTypes {
    LOST_CARD(1),
    STOLEN_CARD(2);
    
    private Integer code;

    private HotlistReasonTypes(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}