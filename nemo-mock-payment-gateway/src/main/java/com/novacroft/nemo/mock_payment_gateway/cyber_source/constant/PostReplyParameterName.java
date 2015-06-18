package com.novacroft.nemo.mock_payment_gateway.cyber_source.constant;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Payment gateway reply parameters
 */
public enum PostReplyParameterName {
    AUTH_AMOUNT("auth_amount", 15),
    AUTH_AVS_CODE("auth_avs_code", 1),
    AUTH_AVS_CODE_RAW("auth_avs_code_raw", 10),
    AUTH_CODE("auth_code", 7),
    AUTH_CV_RESULT("auth_cv_result", 1),
    AUTH_CV_RESULT_RAW("auth_cv_result_raw", 10),
    AUTH_RESPONSE("auth_response", 10),
    AUTH_TIME("auth_time", 20),
    AUTH_TRANS_REF_NO("auth_trans_ref_no", 60),
    BILL_TRANS_REF_NO("bill_trans_ref_no", 60),
    DECISION("decision", 7, "ACCEPT,REVIEW,DECLINE,ERROR,CANCEL"),
    ECHECK_DEBIT_REF_NO("echeck_debit_ref_no", 60),
    ECHECK_DEBIT_SUBMIT_TIME("echeck_debit_submit_time", 20),
    INVALID_FIELDS("invalid_fields", 64),
    MESSAGE("message", 64),
    PAYER_AUTHENTICATION_CAVV("payer_authentication_cavv", 50),
    PAYER_AUTHENTICATION_ECI("payer_authentication_eci", 3),
    PAYER_AUTHENTICATION_PROOF_XM("payer_authentication_proof_xm", 64),
    PAYER_AUTHENTICATION_UAD("payer_authentication_uad", 32),
    PAYER_AUTHENTICATION_UCI("payer_authentication_uci", 1),
    PAYER_AUTHENTICATION_XID("payer_authentication_xid", 28),
    PAYMENT_TOKEN("payment_token", 26),
    REASON_CODE("reason_code", 5, "100,110,200,201,230,205,234,574,102,"),
    REQ_ACCESS_KEY("req_access_key", 32),
    REQ_AMOUNT("req_amount", 15),
    REQ_BILL_PAYMENT("req_bill_payment", 1),
    REQ_BILL_TO_ADDRESS_CITY("req_bill_to_address_city", 50),
    REQ_BILL_TO_ADDRESS_COUNTRY("req_bill_to_address_country", 2),
    REQ_BILL_TO_ADDRESS_LINE1("req_bill_to_address_line1", 60),
    REQ_BILL_TO_ADDRESS_LINE2("req_bill_to_address_line2", 60),
    REQ_BILL_TO_ADDRESS_POSTAL_CODE("req_bill_to_address_postal_code", 10),
    REQ_BILL_TO_ADDRESS_STATE("req_bill_to_address_state", 60),
    REQ_BILL_TO_COMPANY_NAME("req_bill_to_company_name", 40),
    REQ_BILL_TO_EMAIL("req_bill_to_email", 64),
    REQ_BILL_TO_FORENAME("req_bill_to_forename", 60),
    REQ_BILL_TO_PHONE("req_bill_to_phone", 15),
    REQ_BILL_TO_SURNAME("req_bill_to_surname", 60),
    REQ_CARD_EXPIRY_DATE("req_card_expiry_date", 7),
    REQ_CARD_NUMBER("req_card_number", 20),
    REQ_CARD_TYPE("req_card_type", 3),
    REQ_COMPANY_TAX_ID("req_company_tax_id", 9),
    REQ_COMPLETE_ROUTE("req_complete_route", 64),
    REQ_CONSUMER_ID("req_consumer_id", 50),
    REQ_CURRENCY("req_currency", 5),
    REQ_CUSTOMER_COOKIES_ACCEPTED("req_customer_cookies_accepted", 5),
    REQ_CUSTOMER_GIFT_WRAP("req_customer_gift_wrap", 5),
    REQ_CUSTOMER_IP_ADDRESS("req_customer_ip_address"),
    REQ_DATE_OF_BIRTH("req_date_of_birth", 8),
    REQ_DEBT_INDICATOR("req_debt_indicator", 5),
    REQ_DEPARTURE_TIME("req_departure_time", 29),
    REQ_DEVICE_FINGERPRINT_ID("req_device_fingerprint_id", 88),
    REQ_DRIVER_LICENSE_NUMBER("req_driver_license_number", 30),
    REQ_DRIVER_LICENSE_STATE("req_driver_license_state", 2),
    REQ_ECHECK_ACCOUNT_NUMBER("req_echeck_account_number", 17),
    REQ_ECHECK_ACCOUNT_TYPE("req_echeck_account_type", 1),
    REQ_ECHECK_CHECK_NUMBER("req_echeck_check_number", 8),
    REQ_ECHECK_ROUTING_NUMBER("req_echeck_routing_number", 9),
    REQ_ECHECK_SEC_CODE("req_echeck_sec_code", 3),
    REQ_IGNORE_AVS("req_ignore_avs", 5),
    REQ_IGNORE_CVN("req_ignore_cvn", 5),
    REQ_ITEM_1_CODE("req_item_1_code"),
    REQ_ITEM_1_NAME("req_item_1_name", 64),
    REQ_ITEM_1_QUANTITY("req_item_1_quantity", 10),
    REQ_ITEM_1_SKU("req_item_1_sku", 64),
    REQ_ITEM_1_TAX_AMOUNT("req_item_1_tax_amount", 15),
    REQ_ITEM_1_UNIT_PRICE("req_item_1_unit_price", 15),
    REQ_JOURNEY_LEG1_DEST("req_journey_leg1_dest", 3),
    REQ_JOURNEY_LEG1_ORIG("req_journey_leg1_orig", 3),
    REQ_JOURNEY_TYPE("req_journey_type"),
    REQ_LINE_ITEM_COUNT("req_line_item_count", 2),
    REQ_LOCALE("req_locale", 5),
    REQ_MERCHANT_DEFINED_DATA1("req_merchant_defined_data1", 64),
    REQ_MERCHANT_SECURE_DATA1("req_merchant_secure_data1", 64),
    REQ_MERCHANT_SECURE_DATA2("req_merchant_secure_data2", 64),
    REQ_MERCHANT_SECURE_DATA3("req_merchant_secure_data3", 64),
    REQ_MERCHANT_SECURE_DATA4("req_merchant_secure_data4", 64),
    REQ_OVERRIDE_CUSTOM_RECEIPT_PAGE("req_override_custom_receipt_page", 64),
    REQ_PAYMENT_METHOD("req_payment_method", 30),
    REQ_PAYMENT_TOKEN("req_payment_token", 26),
    REQ_PAYMENT_TOKEN_COMMENTS("req_payment_token_comments", 64),
    REQ_PAYMENT_TOKEN_TITLE("req_payment_token_title", 60),
    REQ_PROFILE_ID("req_profile_id", 7),
    REQ_RECURRING_AMOUNT("req_recurring_amount", 15),
    REQ_RECURRING_FREQUENCY("req_recurring_frequency", 20),
    REQ_RECURRING_NUMBER_OF_INSTALLMENTS("req_recurring_number_of_installments", 3),
    REQ_RECURRING_START_DATE("req_recurring_start_date", 8),
    REQ_REFERENCE_NUMBER("req_reference_number", 50),
    REQ_RETURNS_ACCEPTED("req_returns_accepted", 5),
    REQ_SHIP_TO_ADDRESS_CITY("req_ship_to_address_city", 50),
    REQ_SHIP_TO_ADDRESS_COUNTRY("req_ship_to_address_country", 2),
    REQ_SHIP_TO_ADDRESS_LINE1("req_ship_to_address_line1", 60),
    REQ_SHIP_TO_ADDRESS_LINE2("req_ship_to_address_line2", 60),
    REQ_SHIP_TO_ADDRESS_POSTAL_CODE("req_ship_to_address_postal_code", 10),
    REQ_SHIP_TO_ADDRESS_STATE("req_ship_to_address_state", 2),
    REQ_SHIP_TO_COMPANY_NAME("req_ship_to_company_name", 40),
    REQ_SHIP_TO_FORENAME("req_ship_to_forename", 60),
    REQ_SHIP_TO_PHONE("req_ship_to_phone", 15),
    REQ_SHIP_TO_SURNAME("req_ship_to_surname", 60),
    REQ_SHIPPING_METHOD("req_shipping_method", 10),
    REQ_SKIP_DECISION_MANAGER("req_skip_decision_manager", 5),
    REQ_TAX_AMOUNT("req_tax_amount", 15),
    REQ_TRANSACTION_TYPE("req_transaction_type", 60),
    REQ_TRANSACTION_UUID("req_transaction_uuid", 50),
    REQUIRED_FIELDS("required_fields"),
    SIGNATURE("signature", 44),
    SIGNED_DATE_TIME("signed_date_time", 20),
    SIGNED_FIELD_NAMES("signed_field_names"),
    SERVICE_FEE_AMOUNT("service_fee_amount", 15),
    SERVICE_FEE_RETURN_URL("service_fee_return_url"),
    TRANSACTION_ID("transaction_id", 26);

    private String code;
    private String size;
    private String options;

    private PostReplyParameterName(String code) {
        this.code = code;
        this.size = "32";
        this.options = "";
    }

    private PostReplyParameterName(String code, int size) {
        this.code = code;
        this.size = String.valueOf(size);
        this.options = "";
    }

    private PostReplyParameterName(String code, int size, String options) {
        this.code = code;
        this.size = String.valueOf(size);
        this.options = options;
    }

    public String code() {
        return this.code;
    }

    public String size() {
        return this.size;
    }

    public List<String> options() {
        return Arrays.asList(this.options.split(","));
    }

    public boolean hasOptions() {
        return isNotBlank(this.options);
    }
}
