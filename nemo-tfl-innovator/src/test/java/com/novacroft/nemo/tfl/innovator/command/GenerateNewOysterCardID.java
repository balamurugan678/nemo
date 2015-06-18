package com.novacroft.nemo.tfl.innovator.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateNewOysterCardID {
    private static final Logger logger = LoggerFactory.getLogger(GenerateNewOysterCardID.class);

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        for (int i = 0; i < 10; i++) {

            long number = (long) Math.floor(Math.random() * 900000000L) + 100000000L;
            String checkDigits = calculateCheckDigits(number);
            String cardString = "0" + String.valueOf(number) + checkDigits;

            logger.debug("Oyster Card number : " + cardString);
        }
    }

    public static String calculateCheckDigits(Long cardNumber) {
        String hexOysterNo = Long.toHexString(cardNumber).toUpperCase();
        int length = hexOysterNo.length();
        if (length > 8) {
            hexOysterNo = hexOysterNo.substring(0, 8);
        } else if (length < 8) {
            hexOysterNo = replicate('0', 8 - length) + hexOysterNo;
        }
        int[] hexValues = new int[8];
        for (int n = 0; n < 8; n++) {
            String digit = hexOysterNo.substring(n, n + 1);
            hexValues[n] = Integer.parseInt(digit, 16);
        }
        long total = (hexValues[7] + hexValues[5] + hexValues[3] + hexValues[1]) * 19 +
                (hexValues[6] + hexValues[4] + hexValues[2] + hexValues[0]);
        return right(Long.toString(total), 2);
    }

    public static String replicate(char c, int n) {
        StringBuffer sb = new StringBuffer();
        while (n > 0) {
            sb.append(c);
            n--;
        }
        return sb.toString();
    }

    public static String right(String string, int length) {
        if (Math.abs(length) > string.length()) {
            return string;
        } else {
            return length < 0 ? string.substring(-length) : string.substring(string.length() - length);
        }
    }

}
