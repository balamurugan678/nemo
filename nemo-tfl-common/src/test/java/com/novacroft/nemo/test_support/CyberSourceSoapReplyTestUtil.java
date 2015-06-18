package com.novacroft.nemo.test_support;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * Fixtures and utilities for CyberSource soap reply unit tests
 */
public final class CyberSourceSoapReplyTestUtil {
    public static final String AUTHORIZED_AMOUNT_1 = "12.34";
    public static final Integer AUTHORIZED_AMOUNT_AS_INTEGER_1 = 1234;
    public static final String AUTHORIZATION_CODE_1 = "authorization-code-1";
    public static final String AUTHORIZED_AT_1 = "2014-04-01T12:30:45Z";
    public static final String AUTHORIZATION_PAYMENT_NETWORK_TRANSACTION_ID_1 =
            "authorization-payment-network-transaction-id-1";
    public static final BigInteger AUTHORIZATION_REASON_CODE_1 = BigInteger.valueOf(2L);
    public static final String AUTHORIZATION_REASON_CODE_AS_STRING_1 = "2";
    public static final String CAPTURE_AMOUNT_1 = "12.34";
    public static final Integer CAPTURE_AMOUNT_AS_INTEGER_1 = 1234;
    public static final BigInteger CAPTURE_REASON_CODE_1 = BigInteger.valueOf(4L);
    public static final String CAPTURE_REASON_CODE_AS_STRING_1 = "4";
    public static final String DECISION_1 = "ACCEPT";
    public static final String MERCHANT_REFERENCE_CODE_1 = "merchant-reference-code-1";
    public static final BigInteger REASON_CODE_1 = BigInteger.valueOf(8L);
    public static final String REASON_CODE_AS_STRING_1 = "8";
    public static final String REQUEST_ID_1 = "request-id-1";
    public static final List<String> INVALID_FIELDS_1 = Arrays.asList(new String[]{"field-1", "field-2"});
    public static final String INVALID_FIELDS_AS_STRING_1 = "field-1,field-2";
    public static final List<String> MISSING_FIELDS_1 = Arrays.asList(new String[]{"field-3", "field-4"});
    public static final String MISSING_FIELDS_AS_STRING_1 = "field-3,field-4";
    
    public static CyberSourcePostReplyDTO getTestCyberSourceSoapReplyDTO1() {
        return new CyberSourcePostReplyDTO(AUTHORIZED_AMOUNT_1, AUTHORIZED_AT_1, AUTHORIZATION_PAYMENT_NETWORK_TRANSACTION_ID_1, DECISION_1,
                REQUEST_ID_1, MISSING_FIELDS_AS_STRING_1, REASON_CODE_AS_STRING_1, null,
                MERCHANT_REFERENCE_CODE_1, REQUEST_ID_1, REQUEST_ID_1,
                AUTHORIZED_AT_1, REQUEST_ID_1, REQUEST_ID_1,
                REQUEST_ID_1);
    }
    
    private CyberSourceSoapReplyTestUtil() {
    }
}
