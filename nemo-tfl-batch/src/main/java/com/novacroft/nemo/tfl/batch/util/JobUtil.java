package com.novacroft.nemo.tfl.batch.util;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.formatDateAsISO8601;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Utilities for Quartz jobs
 */
public final class JobUtil {
    public static final String SEPARATOR = "--";

    public static String createIdentity(String name, String identifier) {
        assert (isNotBlank(name));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        if (isNotBlank(identifier)) {
            stringBuilder.append(SEPARATOR);
            stringBuilder.append(identifier);
        }
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(formatDateAsISO8601(new Date()));
        return stringBuilder.toString();
    }

    public static String createIdentity(String name) {
        assert (isNotBlank(name));
        return createIdentity(name, null);
    }

    private JobUtil() {
    }
}
