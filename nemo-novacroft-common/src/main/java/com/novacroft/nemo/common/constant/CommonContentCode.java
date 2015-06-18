package com.novacroft.nemo.common.constant;

/**
 * Content (message) code constants.
 */
public enum CommonContentCode {
    CONFIRMATION_MISMATCH("confirmationMisMatch"),
    INVALID_DATE_PATTERN("invalid.date.pattern"),
    INVALID_EMAIL("invalid.email"),
    INVALID_PATTERN("invalid.pattern"),
    INVALID_POSTCODE("invalidPostcode"),
    MANDATORY_FIELD_EMPTY("mandatoryFieldEmpty"),
    MANDATORY_FIELD_EMPTY_USING_AN("mandatoryFieldEmptyUsingAn"),
    MANDATORY_SELECT_FIELD_EMPTY("mandatorySelectFieldEmpty"),
    NOT_EQUAL("notEqual"),
    INVALID_SORTCODE_PATTERN("invalidSortCode"),
    INVALID_ACCOUNT_NUMBER("invalidAccountNumber"),
    VALUE_BELOW_MINIMUM("valueBelowMinimum"),
    VALUE_ABOVE_MAXIMUM("valueAboveMaximum"),
    WEB_CREDIT_NOT_AVAILABLE("webCreditNotAvailable"),
    WEB_CREDIT_AVAILABLE_AMOUNT_MAX_DECIMAL_VALUE("webCreditApplyAmount.maxDecimalValue.Display"),
    PAY_AS_YOU_GO_AMOUNT_MAX_DECIMAL_VALUE("payAsYouGo.maxDecimalValue.Display"),
    INVALID_INPUT_FIELD_MINIMUM_LENGTH("invalidInputFieldMinimumLength"),
    HOTLISTED_CARD_MULTIPLE_REFUND_RESTRICTION_CODE("hotlisted.card.multiple.refund.restriction.message"),
    INVALID_NULL_POSTCODE_ADDRESS("postCode.address.unavailable.message");
    
    private CommonContentCode(String codeStem) {
        this.codeStem = codeStem;
    }

    private static final String SEPARATOR = ".";
    public static final String ERROR = "error";

    private String codeStem;

    public String errorCode() {
        return buildCode(this.codeStem, ERROR);
    }

    protected String buildCode(String stem, String... suffixes) {
        StringBuilder code = new StringBuilder();
        code.append(stem);
        for (String suffix : suffixes) {
            code.append(SEPARATOR);
            code.append(suffix);
        }
        return code.toString();
    }
}
