package com.novacroft.nemo.tfl.common.constant;

/**
 * Sequence codes
 */
public enum SequenceCode {
	CURRENT_ANONYMOUS_GOODWILL_REFUND_USER_ID("currentAnonymousGoodwillRefundUserId");
    
    private SequenceCode(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
