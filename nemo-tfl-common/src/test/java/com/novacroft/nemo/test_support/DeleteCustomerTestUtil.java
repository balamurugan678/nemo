package com.novacroft.nemo.test_support;

import java.util.Date;

/**
 * Utilities for DeleteCustomer tests - used in nemo-tfl-services
 */
public final class DeleteCustomerTestUtil {
        
    public static final Long EXTERNAL_USER_ID = 1L;
    public static final Long EXTERNAL_CUSTOMER_ID = 3489L;
    public static final Date DELETED_DATE_TIME = DateTestUtil.get1Jan();
    public static final String DELETED_REASON_CODE = "123ABC";
    public static final String DELETED_REFERENCE_NUMBER = "98765";
    public static final String DELETED_NOTE = "Moved away from the Big Smoke";
    public static final Long CUSTOMER_ID_9 = 4998L;


}
