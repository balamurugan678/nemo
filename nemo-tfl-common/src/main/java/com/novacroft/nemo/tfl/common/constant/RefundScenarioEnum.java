package com.novacroft.nemo.tfl.common.constant;

public enum RefundScenarioEnum {

    GOODWILL("Goodwill")
    , FAILEDCARD("FailedCardRefund")
    , LOST("LostRefund")
    , STOLEN("StolenRefund")
    , DESTROYED("Destroyed")
    , OVERLAP("Overlap")
    , OTHER("Other")
    , CANCEL_AND_SURRENDER("CancelAndSurrender")
    , ANONYMOUS_GOODWILL_REFUND("AnonymousGoodwillRefund")
    , STANDALONE_GOODWILL_REFUND("StandaloneGoodwillRefund");

    private String code;

    RefundScenarioEnum(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
    
    public static RefundScenarioEnum find(String code) {
        if (code != null) {
            for (RefundScenarioEnum scenario : RefundScenarioEnum.values()) {
                if (code.equalsIgnoreCase(scenario.code)) {
                    return scenario;
                }
            }
        }
        return null;
    }
}