package com.novacroft.nemo.mock_payment_gateway.cyber_source.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utilities
 */
public final class DateUtil {
    public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HHmmss'Z'";

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(API_DATE_FORMAT);
        return sdf.format(date);
    }

    private DateUtil() {
    }
}
