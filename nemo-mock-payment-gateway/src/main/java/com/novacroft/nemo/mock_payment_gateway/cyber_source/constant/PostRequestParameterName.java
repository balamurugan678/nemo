package com.novacroft.nemo.mock_payment_gateway.cyber_source.constant;

/**
 * Payment gateway request parameters
 */
public enum PostRequestParameterName {
    ACCESS_KEY("access_key"),
    AMOUNT("amount"),
    CURRENCY("currency"),
    LOCALE("locale"),
    PROFILE_ID("profile_id"),
    REFERENCE_NUMBER("reference_number"),
    SIGNATURE("signature"),
    SIGNED_DATE_TIME("signed_date_time"),
    SIGNED_FIELD_NAMES("signed_field_names"),
    TRANSACTION_TYPE("transaction_type"),
    TRANSACTION_UUID("transaction_uuid"),
    UNSIGNED_FIELD_NAMES("unsigned_field_names"),
    CARD_TYPE("card_type"),
    CARD_NUMBER("card_number"),
    CARD_EXPIRY_DATE("card_expiry_date"),
    CARD_CVN("card_cvn"),
    BILL_TO_FORENAME("bill_to_forename"),
    BILL_TO_SURNAME("bill_to_surname"),
    BILL_TO_EMAIL("bill_to_email"),
    BILL_TO_ADDRESS_CITY("bill_to_address_city"),
    BILL_TO_ADDRESS_COUNTRY("bill_to_address_country"),
    BILL_TO_ADDRESS_LINE1("bill_to_address_line1"),
    BILL_TO_ADDRESS_STATE("bill_to_address_state"),
    BILL_TO_ADDRESS_LINE2("bill_to_address_line2"),
    BILL_TO_ADDRESS_POSTCODE("bill_to_address_postal_code"),
    PAYMENT_METHOD("payment_method"),
    CONSUMER_ID("consumer_id"),
    CUSTOMER_COOKIES_ACCEPTED("customer_cookies_accepted"),
    CUSTOMER_IP_ADDRESS("customer_ip_address"),
    DATE_OF_BIRTH("date_of_birth"),
    DEVICE_FINGERPRINT_ID("device_fingerprint_id"),
    PAYMENT_TOKEN("payment_token"),
    PAYMENT_TOKEN_TITLE("payment_token_title"),
    OVERRIDE_CUSTOM_CANCEL_PAGE("override_custom_cancel_page"),
    OVERRIDE_CUSTOM_RECEIPT_PAGE("override_custom_receipt_page");

    private String code;

    private PostRequestParameterName(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
