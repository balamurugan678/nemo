package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.RefundTestUtil.REFUNDED_JOURNEY_ON_1;
import static com.novacroft.nemo.test_support.RefundTestUtil.REFUND_AMOUNT_IN_PENCE_1;
import static com.novacroft.nemo.test_support.RefundTestUtil.PICK_UP_EXPIRES_ON;
import static com.novacroft.nemo.test_support.RefundTestUtil.PICK_UP_LOCATION_NAME_1;
import static com.novacroft.nemo.test_support.RefundTestUtil.REFUND_REASON_1;
import static com.novacroft.nemo.test_support.RefundTestUtil.REFUND_REFERENCE_1;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.EMAIL_ADDRESS_1;

import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * Fixtures and utilities for email tests
 */
public class EmailTestUtil {

    public static final String TEST_SALUTATION = FIRST_NAME_1 + " " + LAST_NAME_1;
    public static final String TEST_EMAIL_MESSAGE_BODY = "Test";
    public static final String TEST_FROM = "test@test.com";
    
    public static final String FORMATTED_REFUND_AMOUNT_IN_EMAIL = "34.56";
    public static final String FORMATTED_PICKUP_EXPIRE_DATE_IN_EMAIL = "21/08/2013";
    
    public static EmailArgumentsDTO getTestRefundEmailArgumentDTO() {
        return new EmailArgumentsDTO(EMAIL_ADDRESS_1, TEST_SALUTATION, PICK_UP_LOCATION_NAME_1, 
                        REFUND_AMOUNT_IN_PENCE_1, REFUNDED_JOURNEY_ON_1, REFUND_REASON_1, 
                        REFUND_REFERENCE_1, OYSTER_NUMBER_1, PICK_UP_EXPIRES_ON,
                        BASE_URI_1);
    }
}
