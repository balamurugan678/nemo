package com.novacroft.nemo.tfl_database.test_data;

import com.novacroft.nemo.common.utils.OysterCardNumberUtil;

/**
 * Generate valid oyster card numbers
 */
public final class OysterCardNumberGenerator {

    public static void main(String[] args) {
        createOysterCardNumbers(getArgument(args, 100000000), getArgument(args, 100000032));
    }

    private static void createOysterCardNumbers(long rangeFrom, long rangeTo) {
        for (long i = rangeFrom; i <= rangeTo; i++) {
            System.out.println(OysterCardNumberUtil.getFullCardNumber(i));
        }
    }

    private static Integer getArgument(String[] args, Integer defaultValue) {
        try {
            return Integer.valueOf(args[0]);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
