package com.novacroft.nemo.tfl.common.constant.cyber_source;

/**
 * CyberSource Secure Acceptance payment gateway reply decision codes
 */
public enum CyberSourceDecision {
    ACCEPT("ACCEPT"),
    REVIEW("REVIEW"),
    DECLINE("DECLINE"),
    ERROR("ERROR"),
    REJECT("REJECT"),
    CANCEL("CANCEL");

    private CyberSourceDecision(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }

    public static CyberSourceDecision getDecision(String code) {
        for (CyberSourceDecision decision : CyberSourceDecision.values()) {
            if (code.equalsIgnoreCase(decision.code())) {
                return decision;
            }
        }
        return null;
    }
}
