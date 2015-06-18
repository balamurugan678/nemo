package com.novacroft.nemo.tfl.common.constant;

/**
 * Payment Card Status
 */
public enum PaymentCardStatus {
    ACTIVE("active"),
    IN_ACTIVE("inActive");

    private String code;

    private PaymentCardStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static PaymentCardStatus getStatus(String code) {
        for (PaymentCardStatus paymentCardStatus : PaymentCardStatus.values()) {
            if (code.equalsIgnoreCase(paymentCardStatus.code())) {
                return paymentCardStatus;
            }
        }
        return null;
    }
}
