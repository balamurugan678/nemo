package com.novacroft.nemo.common.utils;

import org.apache.commons.codec.language.DoubleMetaphone;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Metaphone utility - produces a phonetic encoding of a word for 'sounds like' matching
 */
public final class MetaphoneUtil {
    protected static final int MAX_CODE_LENGTH = 255;

    public static String encode(String value) {
        if (isBlank(value)) {
            return EMPTY;
        }
        DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
        doubleMetaphone.setMaxCodeLen(MAX_CODE_LENGTH);
        return doubleMetaphone.encode(value.toUpperCase()).toUpperCase();
    }

    private MetaphoneUtil() {
    }
}
