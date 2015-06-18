package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * Fixtures and utilities for CyberSource soap request unit tests
 */
public final class CyberSourceSoapRequestTestUtil {
    public static final String BILL_TO_BUILDING_NUMBER_1 = "number-1";
    public static final String BILL_TO_CITY_1 = "city-1";
    public static final String BILL_TO_COUNTRY_1 = "country-1";
    public static final String BILL_TO_CUSTOMER_ID_1 = "customer-1";
    public static final String BILL_TO_EMAIL_1 = "email-1";
    public static final String BILL_TO_FIRST_NAME_1 = "first-name-1";
    public static final String BILL_TO_IP_ADDRESS_1 = "ip-address-1";
    public static final String BILL_TO_LAST_NAME_1 = "last-name-1";
    public static final String BILL_TO_PHONE_NUMBER_1 = "phone-number-1";
    public static final String BILL_TO_POSTAL_CODE_1 = "postal-code-1";
    public static final String BILL_TO_STREET_1_1 = "street-line-1-1";
    public static final String BILL_TO_STREET_2_1 = "street-line-2-1";
    public static final Boolean RUN_AUTHORIZATION_SERVICE_1 = Boolean.TRUE;
    public static final Boolean RUN_CAPTURE_SERVICE_1 = Boolean.TRUE;
    public static final String ORDER_NUMBER_1 = "order-number-1";
    public static final String MERCHANT_ID_1 = "merchant-id-1";
    public static final String TRANSACTION_UUID_1 = "transaction-uuid-1";
    public static final String CURRENCY_1 = "Groat";
    public static final Integer TOTAL_AMOUNT_IN_PENCE_1 = 1234;
    public static final String TOTAL_AMOUNT_IN_PENCE_AS_STRING_1 = "12.34";

    public static CyberSourceSoapRequestDTO getTestCyberSourceSoapRequestDTO1() {
        return new CyberSourceSoapRequestDTO(BILL_TO_BUILDING_NUMBER_1, BILL_TO_CITY_1, BILL_TO_COUNTRY_1,
                BILL_TO_CUSTOMER_ID_1, BILL_TO_EMAIL_1, BILL_TO_FIRST_NAME_1, BILL_TO_IP_ADDRESS_1, BILL_TO_LAST_NAME_1,
                BILL_TO_PHONE_NUMBER_1, BILL_TO_POSTAL_CODE_1, BILL_TO_STREET_1_1, BILL_TO_STREET_2_1,
                RUN_AUTHORIZATION_SERVICE_1, RUN_CAPTURE_SERVICE_1, ORDER_NUMBER_1, MERCHANT_ID_1, TRANSACTION_UUID_1,
                CURRENCY_1, TOTAL_AMOUNT_IN_PENCE_1, CustomerTestUtil.CUSTOMER_ID_1);
    }

    private CyberSourceSoapRequestTestUtil() {
    }
}
