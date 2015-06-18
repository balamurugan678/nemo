package com.novacroft.nemo.common.utils;

import com.novacroft.nemo.test_support.DateTestUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * CyberSourceDateUtil unit tests
 */
public class CyberSourceDateUtilTest {
    protected static final String ZULU_AUG_20_NOON_IN_CYBER_SOURCE_FORMAT = "2013-08-20T12:00:00Z";

    @Test
    public void shouldParseCyberSourceDateAndTime() {
        assertEquals(DateTestUtil.getZuluAug20(),
                CyberSourceDateUtil.parseDateAndTime(ZULU_AUG_20_NOON_IN_CYBER_SOURCE_FORMAT));
    }

    @Test
    public void convertDateAndTimeAsStringToDateAndTimeAsDateShouldReturnNull() {
        assertNull(CyberSourceDateUtil.parseDateAndTime("Rubbish!"));
    }

    @Test
    public void formatCyberSourceDateAndTimeShouldConvertZulu() {
        assertEquals(ZULU_AUG_20_NOON_IN_CYBER_SOURCE_FORMAT,
                CyberSourceDateUtil.formatDateAndTime(DateTestUtil.getZuluAug20()));
    }

    @Test
    public void formatCyberSourceDateAndTimeShouldConvertNonZulu() {
        TimeZone HourAndQuarterBeforeGMTTimeZone = TimeZone.getTimeZone("GMT-0115");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(HourAndQuarterBeforeGMTTimeZone);
        // 10:45 on 20 Aug 2013 in 1 hr 15 min before GMT time zone
        calendar.set(2013, Calendar.AUGUST, 20, 10, 45, 0);
        assertEquals(ZULU_AUG_20_NOON_IN_CYBER_SOURCE_FORMAT, CyberSourceDateUtil.formatDateAndTime(calendar.getTime()));
    }
}
