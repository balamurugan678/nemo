package com.novacroft.nemo.tfl.common.constant.cyber_source;

/**
 * CyberSource Secure Acceptance payment gateway reply decision codes
 */
public enum CyberSourcePostTransactionType {
    AUTHORIZATION("authorization"),
    SALE("sale"),
    AUTHORIZATION_AND_CREATE_PAYMENT_TOKEN("authorization,create_payment_token"),
    SALE_AND_CREATE_PAYMENT_TOKEN("sale,create_payment_token"),
    AUTHORIZATION_AND_UPDATE_PAYMENT_TOKEN("authorization,update_payment_token"),
    SALE_AND_UPDATE_PAYMENT_TOKEN("sale,update_payment_token"),
    CREATE_PAYMENT_TOKEN("create_payment_token"),
    UPDATE_PAYMENT_TOKEN("update_payment_token");

    private CyberSourcePostTransactionType(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }

    public static CyberSourcePostTransactionType getTransactionType(String code) {
        for (CyberSourcePostTransactionType transactionType : CyberSourcePostTransactionType.values()) {
            if (code.equals(transactionType.code())) {
                return transactionType;
            }
        }
        return null;
    }
}
