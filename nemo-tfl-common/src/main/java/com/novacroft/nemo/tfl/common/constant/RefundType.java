package com.novacroft.nemo.tfl.common.constant;

public enum RefundType {
    FAILED_CARD("failedCard"), LOST("lost"), STOLEN("stolen"), UNKNOWN("unknown");

    private String code;

    RefundType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static RefundType lookUpRefundType(String code) {
        if (code != null) {
            for (RefundType refundType : RefundType.values()) {
                if (code.equalsIgnoreCase(refundType.code)) {
                    return refundType;
                }
            }
        }
        return null;
    }

}