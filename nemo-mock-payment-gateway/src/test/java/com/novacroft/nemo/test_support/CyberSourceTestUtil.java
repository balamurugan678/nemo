package com.novacroft.nemo.test_support;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostRequestParameterName;

import java.util.HashMap;
import java.util.Map;

/**
 * CyberSource test fixtures and utilities
 */
public final class CyberSourceTestUtil {
    public final static String TEST_ACCESS_KEY = "1";
    public final static String TEST_AMOUNT = "2";
    public final static String TEST_CURRENCY = "0";
    public final static String TEST_LOCALE = "en_US";
    public final static String TEST_PROFILE_ID = ProfileTestUtil.TEST_ID_1;
    public final static String TEST_REFERENCE_NUMBER = "3";
    public final static String TEST_SIGNATURE = "X";
    public final static String TEST_SIGNED_DATE_TIME = "X";
    public final static String TEST_SIGNED_FIELD_NAMES = "X";
    public final static String TEST_TRANSACTION_TYPE = "X";
    public final static String TEST_TRANSACTION_UUID = "X";
    public final static String TEST_UNSIGNED_FIELD_NAMES = "X";
    public final static String TEST_CARD_TYPE = "X";
    public final static String TEST_CARD_NUMBER = "X";
    public final static String TEST_CARD_EXPIRY_DATE = "X";
    public final static String TEST_CARD_VERIFICATION_NUMBER = "X";
    public final static String TEST_BILL_TO_FIRST_NAME = "X";
    public final static String TEST_BILL_TO_LAST_NAME = "X";
    public final static String TEST_BILL_TO_EMAIL = "X";
    public final static String TEST_BILL_TO_ADDRESS_CITY = "X";
    public final static String TEST_BILL_TO_ADDRESS_COUNTRY = "X";
    public final static String TEST_BILL_TO_ADDRESS_LINE1 = "X";
    public final static String TEST_BILL_TO_ADDRESS_LINE2 = "X";
    public final static String TEST_BILL_TO_ADDRESS_POSTCODE = "X";
    public final static String TEST_BILL_TO_ADDRESS_STATE = "X";

    public static Map<String, String> getTestRequestMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(PostRequestParameterName.ACCESS_KEY.code(), TEST_ACCESS_KEY);
        map.put(PostRequestParameterName.AMOUNT.code(), TEST_AMOUNT);
        map.put(PostRequestParameterName.CURRENCY.code(), TEST_CURRENCY);
        map.put(PostRequestParameterName.LOCALE.code(), TEST_LOCALE);
        map.put(PostRequestParameterName.PROFILE_ID.code(), TEST_PROFILE_ID);
        map.put(PostRequestParameterName.REFERENCE_NUMBER.code(), TEST_REFERENCE_NUMBER);
        map.put(PostRequestParameterName.SIGNATURE.code(), TEST_SIGNATURE);
        map.put(PostRequestParameterName.SIGNED_DATE_TIME.code(), TEST_SIGNED_DATE_TIME);
        map.put(PostRequestParameterName.SIGNED_FIELD_NAMES.code(), TEST_SIGNED_FIELD_NAMES);
        map.put(PostRequestParameterName.TRANSACTION_TYPE.code(), TEST_TRANSACTION_TYPE);
        map.put(PostRequestParameterName.TRANSACTION_UUID.code(), TEST_TRANSACTION_UUID);
        map.put(PostRequestParameterName.UNSIGNED_FIELD_NAMES.code(), TEST_UNSIGNED_FIELD_NAMES);
        map.put(PostRequestParameterName.CARD_TYPE.code(), TEST_CARD_TYPE);
        map.put(PostRequestParameterName.CARD_NUMBER.code(), TEST_CARD_NUMBER);
        map.put(PostRequestParameterName.CARD_EXPIRY_DATE.code(), TEST_CARD_EXPIRY_DATE);
        map.put(PostRequestParameterName.CARD_CVN.code(), TEST_CARD_VERIFICATION_NUMBER);
        map.put(PostRequestParameterName.BILL_TO_FORENAME.code(), TEST_BILL_TO_FIRST_NAME);
        map.put(PostRequestParameterName.BILL_TO_SURNAME.code(), TEST_BILL_TO_LAST_NAME);
        map.put(PostRequestParameterName.BILL_TO_EMAIL.code(), TEST_BILL_TO_EMAIL);
        map.put(PostRequestParameterName.BILL_TO_ADDRESS_CITY.code(), TEST_BILL_TO_ADDRESS_CITY);
        map.put(PostRequestParameterName.BILL_TO_ADDRESS_COUNTRY.code(), TEST_BILL_TO_ADDRESS_COUNTRY);
        map.put(PostRequestParameterName.BILL_TO_ADDRESS_LINE1.code(), TEST_BILL_TO_ADDRESS_LINE1);
        map.put(PostRequestParameterName.BILL_TO_ADDRESS_LINE2.code(), TEST_BILL_TO_ADDRESS_LINE2);
        map.put(PostRequestParameterName.BILL_TO_ADDRESS_POSTCODE.code(), TEST_BILL_TO_ADDRESS_POSTCODE);
        map.put(PostRequestParameterName.BILL_TO_ADDRESS_STATE.code(), TEST_BILL_TO_ADDRESS_STATE);
        return map;
    }

    private CyberSourceTestUtil() {
    }
}
