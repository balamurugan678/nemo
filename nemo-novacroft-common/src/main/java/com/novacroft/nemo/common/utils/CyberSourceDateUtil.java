package com.novacroft.nemo.common.utils;

import java.util.Date;
import java.util.TimeZone;

import static com.novacroft.nemo.common.constant.DateConstant.CYBER_SOURCE_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.UTC_TIME_ZONE_ID;

/**
 * CyberSource payment gateway date utilities
 */
public final class CyberSourceDateUtil {
    public static Date parseDateAndTime(String value) {
        return DateUtil.parse(value, CYBER_SOURCE_DATE_PATTERN, TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
    }

    public static String formatDateAndTime(Date value) {
        return DateUtil.formatDateTime(value, CYBER_SOURCE_DATE_PATTERN, TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
    }

    private CyberSourceDateUtil() {
    }
}
