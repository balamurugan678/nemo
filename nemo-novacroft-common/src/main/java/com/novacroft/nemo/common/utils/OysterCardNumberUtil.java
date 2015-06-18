package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.utils.StringUtil.replicate;
import static com.novacroft.nemo.common.utils.StringUtil.right;

import com.novacroft.nemo.common.constant.CommonPrivateError;

/**
 * Oyster card number utilities
 */
public final class OysterCardNumberUtil {
    private static final int EIGHTHDIGIT = 8;
    protected static final int TWELVE_DIGIT_NUMBER_LENGTH = 12;
    protected static final int ELEVEN_DIGIT_NUMBER_LENGTH = 11;
    protected static final int NINE_DIGIT_NUMBER_LENGTH = 9;
    protected static final int EIGHT_DIGIT_NUMBER_LENGTH = 8;
    protected static final int FIVE_DIGIT_NUMBER_LENGTH = 5;
    protected static final String ZERO = "0";
    protected static final String PREFIX_FIVE_ZEROS = "00000";
    
    public static String calculateCheckDigits(String cardNumber) {
        return calculateCheckDigits(Long.parseLong(cardNumber));
    }

    public static String calculateCheckDigits(Long cardNumber) {
        String hexOysterNo = Long.toHexString(cardNumber).toUpperCase();
        int length = hexOysterNo.length();
        if (length > EIGHTHDIGIT) {
            hexOysterNo = hexOysterNo.substring(0, EIGHTHDIGIT);
        } else if (length < EIGHTHDIGIT) {
            hexOysterNo = replicate('0', EIGHTHDIGIT - length) + hexOysterNo;
        }
        int[] hexValues = new int[EIGHTHDIGIT];
        for (int n = 0; n < EIGHTHDIGIT; n++) {
            String digit = hexOysterNo.substring(n, n + 1);
            hexValues[n] = Integer.parseInt(digit, 16);
        }
        long total = (hexValues[7] + hexValues[5] + hexValues[3] + hexValues[1]) * 19 +
                (hexValues[6] + hexValues[4] + hexValues[2] + hexValues[0]);
        return right(Long.toString(total), 2);
    }

    public static String getFullCardNumber(String cardNumber) {
        if (isNineDigitNumber(cardNumber)) {
            return ZERO + cardNumber + calculateCheckDigits(cardNumber);
        } else if (isElevenDigitNumber(cardNumber)) {
            return ZERO + cardNumber;
        } else if (isTwelveDigitNumber(cardNumber)) {
            return cardNumber;
        } else if(isEightDigitNumber(cardNumber)) {
        	return ZERO + ZERO + cardNumber + calculateCheckDigits(cardNumber);
        } else if (isFiveDigitNumber(cardNumber)) {
            return PREFIX_FIVE_ZEROS + cardNumber + calculateCheckDigits(cardNumber);
        }
        throw new IllegalArgumentException(CommonPrivateError.INVALID_CARD_NUMBER_LENGTH.message());
    }

    public static String getFullCardNumber(Long cardNumber) {
        return getFullCardNumber(Long.toString(cardNumber));
    }

    public static String getNineDigitNumber(String twelveDigitCardNumber) {
        if (TWELVE_DIGIT_NUMBER_LENGTH != twelveDigitCardNumber.length()) {
            throw new IllegalArgumentException(CommonPrivateError.INVALID_TWELVE_DIGIT_CARD_NUMBER.message());
        }
        return twelveDigitCardNumber.substring(1, NINE_DIGIT_NUMBER_LENGTH + 1);
    }

    public static Integer getNineDigitNumberAsInteger(String twelveDigitNumber) {
        return Integer.valueOf(getNineDigitNumber(twelveDigitNumber));
    }

    public static Long getNineDigitNumberAsLong(String twelveDigitNumber) {
        return Long.parseLong(getNineDigitNumber(twelveDigitNumber));
    }

    protected static boolean isNineDigitNumber(String value) {
        return NINE_DIGIT_NUMBER_LENGTH == value.length();
    }

    protected static boolean isElevenDigitNumber(String value) {
        return ELEVEN_DIGIT_NUMBER_LENGTH == value.length();
    }

    protected static boolean isTwelveDigitNumber(String value) {
        return TWELVE_DIGIT_NUMBER_LENGTH == value.length();
    }
    
    protected static boolean isEightDigitNumber(String value) {
        return EIGHT_DIGIT_NUMBER_LENGTH == value.length();
    }
    
    protected static boolean isFiveDigitNumber(String value) {
        return FIVE_DIGIT_NUMBER_LENGTH == value.length();
    }
    
    private OysterCardNumberUtil() {
    }

}
