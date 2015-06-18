package com.novacroft.nemo.tfl.common.constant;

public enum PaymentType {
    WEB_ACCOUNT_CREDIT("WebAccountCredit"), AD_HOC_LOAD("AdhocLoad"), PAYMENT_CARD("PaymentCard"), BACS("BACS"), CHEQUE("Cheque");

    private String code;

    PaymentType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static PaymentType lookUpPaymentType(String code) {
        if (code != null) {
            for (PaymentType paymentType : PaymentType.values()) {
                if (code.equalsIgnoreCase(paymentType.code)) {
                    return paymentType;
                }
            }
        }
        return null;
    }

}