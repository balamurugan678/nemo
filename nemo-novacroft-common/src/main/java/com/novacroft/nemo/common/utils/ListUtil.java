package com.novacroft.nemo.common.utils;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * List utilities
 */
public final class ListUtil {
    private static final String COMMA_DELIMITER = ",";
    private static final String COMMA_DELIMITER_REG_EXP = "\\s*,\\s*";

    @SuppressWarnings("unchecked")
    public static List<String> getCommaDelimitedStringAsList(String value) {
        return isNotBlank(value) ? asList(value.split(COMMA_DELIMITER_REG_EXP)) : Collections.EMPTY_LIST;
    }

    public static String getListAsCommaDelimitedString(List<String> values) {
        return (isNotEmpty(values)) ? join(values, COMMA_DELIMITER) : "";
    }

    private ListUtil() {
    }
}
