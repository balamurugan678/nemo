package com.novacroft.nemo.common.utils;


/**
 * Journey utilities
 */
public final class NumberUtil {

    private NumberUtil() {
    }

    public static int convertLongToInt(Long value) {
        Long LongValueObject = Long.parseLong(String.valueOf(value));
        int IntegerValue = Integer.parseInt(String.valueOf(LongValueObject));

        return IntegerValue;
    }

}