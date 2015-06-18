package com.novacroft.nemo.tfl.common.constant;

/**
 * Card Type
 */
public enum CardType {
    FIRST_ISSUE("FirstIssue"),
    REPLACEMENT("Replacement"),
    NONE("None");
    
    private CardType(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
