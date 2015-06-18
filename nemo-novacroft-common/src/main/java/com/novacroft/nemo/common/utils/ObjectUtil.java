package com.novacroft.nemo.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilities for working with objects
 */
public final class ObjectUtil {

    private ObjectUtil() {
    }

    /**
     * Return true if Object varargs is all nulls or blank strings
     */
    public static boolean isObjectArgsNotBlank(Object... args) {
        if (args.length == 0) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (!isObjectNullOrBlankString(args[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if Object is null or a blank string
     */
    public static boolean isObjectNullOrBlankString(Object arg) {
        return arg == null || isObjectBlankString(arg);
    }

    /**
     * Return true if Object is a blank string
     */
    public static boolean isObjectBlankString(Object arg) {
        return (arg instanceof String) ? StringUtils.isBlank(String.valueOf(arg)) : false;
    }
}
