package com.novacroft.nemo.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * OysterCardNumberUtil unit tests
 */
public class OysterCardNumberUtilTest {
    public static final String SHORT_CARD_NUMBER = "123";
    public static final String TWELVE_DIGIT_CARD_NUMBER = "012345678901";
    public static final int TWELVE_DIGIT_CARD_NUMBER_LENGTH = 12;
    public static final String ELEVEN_DIGIT_CARD_NUMBER = "12345678901";
    public static final String NINE_DIGIT_CARD_NUMBER = "123456789";
    public static final String EIGHT_DIGIT_CARD_NUMBER = "12345678";
    public static final String FIVE_DIGIT_CARD_NUMBER = "12345";
    public static final Integer NINE_DIGIT_CARD_NUMBER_AS_INTEGER = Integer.valueOf(NINE_DIGIT_CARD_NUMBER);

    @Test
    public void isTwelveDigitNumberShouldReturnTrue() {
        assertTrue(OysterCardNumberUtil.isTwelveDigitNumber(TWELVE_DIGIT_CARD_NUMBER));
    }

    @Test
    public void isTwelveDigitNumberShouldReturnFalse() {
        assertFalse(OysterCardNumberUtil.isTwelveDigitNumber(SHORT_CARD_NUMBER));
    }

    @Test
    public void isElevenDigitNumberShouldReturnTrue() {
        assertTrue(OysterCardNumberUtil.isElevenDigitNumber(ELEVEN_DIGIT_CARD_NUMBER));
    }

    @Test
    public void isElevenDigitNumberShouldReturnFalse() {
        assertFalse(OysterCardNumberUtil.isElevenDigitNumber(SHORT_CARD_NUMBER));
    }

    @Test
    public void isNineDigitNumberShouldReturnTrue() {
        assertTrue(OysterCardNumberUtil.isNineDigitNumber(NINE_DIGIT_CARD_NUMBER));
    }

    @Test
    public void isNineDigitNumberShouldReturnFalse() {
        assertFalse(OysterCardNumberUtil.isNineDigitNumber(SHORT_CARD_NUMBER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNineDigitNumberShouldError() {
        OysterCardNumberUtil.getNineDigitNumber(SHORT_CARD_NUMBER);
    }

    @Test
    public void shouldGetNineDigitNumber() {
        assertEquals(NINE_DIGIT_CARD_NUMBER, OysterCardNumberUtil.getNineDigitNumber(TWELVE_DIGIT_CARD_NUMBER));
    }

    @Test
    public void shouldGetNineDigitNumberAsInteger() {
        assertEquals(NINE_DIGIT_CARD_NUMBER_AS_INTEGER,
                OysterCardNumberUtil.getNineDigitNumberAsInteger(TWELVE_DIGIT_CARD_NUMBER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFullCardNumberShouldError() {
        OysterCardNumberUtil.getFullCardNumber(SHORT_CARD_NUMBER);
    }

    @Test
    public void getFullCardNumberShouldProcessNineDigitNumber() {
        assertTrue(TWELVE_DIGIT_CARD_NUMBER_LENGTH == OysterCardNumberUtil.getFullCardNumber(NINE_DIGIT_CARD_NUMBER).length());
    }

    @Test
    public void getFullCardNumberShouldProcessElevenDigitNumber() {
        assertTrue(
                TWELVE_DIGIT_CARD_NUMBER_LENGTH == OysterCardNumberUtil.getFullCardNumber(ELEVEN_DIGIT_CARD_NUMBER).length());
    }

    @Test
    public void getFullCardNumberShouldProcessTwelveDigitNumber() {
        assertTrue(
                TWELVE_DIGIT_CARD_NUMBER_LENGTH == OysterCardNumberUtil.getFullCardNumber(TWELVE_DIGIT_CARD_NUMBER).length());
    }

    @Test
    public void calculateCheckDigitsShouldCheckNumberWithoutLeadingZero() {
        assertEquals("07", OysterCardNumberUtil.calculateCheckDigits("533574814"));
    }

    @Test
    public void calculateCheckDigitsShouldCheckNumberWithLeadingZero() {
        assertEquals("07", OysterCardNumberUtil.calculateCheckDigits("0533574814"));
    }
    
    @Test
    public void getFullCardNumberShouldProcessEightDigitNumber() {
        assertTrue(TWELVE_DIGIT_CARD_NUMBER_LENGTH == OysterCardNumberUtil.getFullCardNumber(EIGHT_DIGIT_CARD_NUMBER).length());
    }
    
    @Test
    public void getFullCardNumberShouldProcessFiveDigitNumber() {
        assertTrue(TWELVE_DIGIT_CARD_NUMBER_LENGTH == OysterCardNumberUtil.getFullCardNumber(FIVE_DIGIT_CARD_NUMBER).length());
    }
    
    @Test
    public void isFiveDigitNumberShouldReturnFalse() {
        assertFalse(OysterCardNumberUtil.isFiveDigitNumber(EIGHT_DIGIT_CARD_NUMBER));
    }
    
    @Test
    public void isFiveDigitNumberShouldReturnTrue() {
        assertTrue(OysterCardNumberUtil.isFiveDigitNumber(FIVE_DIGIT_CARD_NUMBER));
    }
    
    @Test
    public void isEightDigitNumberShouldReturnFalse() {
        assertFalse(OysterCardNumberUtil.isEightDigitNumber(FIVE_DIGIT_CARD_NUMBER));
    }
    
    @Test
    public void isEightDigitNumberShouldReturnTrue() {
        assertTrue(OysterCardNumberUtil.isEightDigitNumber(EIGHT_DIGIT_CARD_NUMBER));
    }
}
