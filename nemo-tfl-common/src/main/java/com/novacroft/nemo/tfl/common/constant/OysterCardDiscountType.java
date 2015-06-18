package com.novacroft.nemo.tfl.common.constant;

/**
 * Oyster card discount type
 */
public enum OysterCardDiscountType {
    EIGHTEEN_PLUS("18+"),
    APPRENTICE("Apprentice"),
    JOB_CENTRE_PLUS("JCP"),
    NO_DISCOUNT("No Discount");

    private OysterCardDiscountType(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
    
    public static OysterCardDiscountType findByCode(String code) {
        if (code != null) {
            for (OysterCardDiscountType oysterCardDiscountType : OysterCardDiscountType.values()) {
                if (code.equalsIgnoreCase(oysterCardDiscountType.code)) {
                    return oysterCardDiscountType;
                }
            }
        }
        return null;
    }
}
