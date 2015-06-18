package com.novacroft.nemo.tfl.common.constant;

public enum RefundCalculationBasis {
    PRO_RATA("Pro Rata")
    , ORDINARY("Ordinary")
    , TRADE_UP("Trade Up")
    , TRADE_DOWN("Trade Down")
    , SPECIAL("Special")
    , ORDINARY_AFTER_TRADE_UP("Ordinary after Trade Up")
    , ORDINARY_AFTER_TRADE_DOWN("Ordinary after Trade Down")
    ;

    private String code;

    RefundCalculationBasis(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
    
    public static RefundCalculationBasis find(String code) {
        if (code != null) {
            for (RefundCalculationBasis refundrefundCalculationBasis : RefundCalculationBasis.values()) {
                if (code.equalsIgnoreCase(refundrefundCalculationBasis.code)) {
                    return refundrefundCalculationBasis;
                }
            }
        }
        return null;
    }
    
    
}