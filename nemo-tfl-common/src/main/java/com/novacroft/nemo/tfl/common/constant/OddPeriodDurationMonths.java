package com.novacroft.nemo.tfl.common.constant;

public enum OddPeriodDurationMonths {
	ONE_MONTH("1Month"), TWO_MONTH("2Month"), THREE_MONTH("3Month"), FOUR_MONTH("4Month"), FIVE_MONTH("5Month"), SIX_MONTH("6Month"), 
	SEVEN_MONTH("7Month"), EIGHT_MONTH("8Month"), NINE_MONTH("9Month"), TEN_MONTH("10Month"), ELEVEN_MONTH("11Month"), TWELVE_MONTH("12Month");

    private String code;

    OddPeriodDurationMonths(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static OddPeriodDurationMonths lookUpPaymentType(String code) {
        if (code != null) {
            for (OddPeriodDurationMonths month : OddPeriodDurationMonths.values()) {
                if (code.equalsIgnoreCase(month.code)) {
                    return month;
                }
            }
        }
        return null;
    }
}
