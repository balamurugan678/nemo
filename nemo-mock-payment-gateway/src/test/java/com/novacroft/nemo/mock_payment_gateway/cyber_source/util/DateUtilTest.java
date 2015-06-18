package com.novacroft.nemo.mock_payment_gateway.cyber_source.util;

import com.novacroft.nemo.test_support.DateTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for DateUtil
 */
public class DateUtilTest {

    @Test
    public void shouldConvertDateToString() {
        assertEquals("2013-08-19T120000Z", DateUtil.dateToString(DateTestUtil.getAug19()));
    }
}
