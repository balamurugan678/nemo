package com.novacroft.nemo.common.constant;

/**
 * Common (i.e. not client specific) system parameter codes
 */
public enum CommonSystemParameterCode {
    EMAIL_MESSAGE_FROM_ADDRESS("emailMessageFromAddress"),
    DEFAULT_COUNTRY_CODE("defaultCountryCode");

    private String code;

    private CommonSystemParameterCode(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
