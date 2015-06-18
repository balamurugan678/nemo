package com.novacroft.nemo.tfl.common.formatter;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for ZoneUtil
 */
public class PenceFormatterTest {
    
    public static final String CHECK_PRECISION_STRING = "9.70";
    public static final String FOUR_POUNDS_EIGHTY_FIVE_IN_STRING = "4.85";
    public static final Integer FOUR_POUNDS_EIGHTY_FIVE_IN_INTEGER = Integer.valueOf(485);
    public static final String FIVE_POUNDS_IN_STRING = "5.00";
    public static final Integer FIVE_POUNDS_IN_INTEGER = Integer.valueOf(500);
    public static final Locale GB_LOCALE = new Locale("en", "GB");
    public static final String FIVE_POUNDS_FIFTEEN_PENCE_IN_STRING = "5.15";
    public static final Integer FIVE_POUNDS_FIFTEEN_PENCE_IN_INTEGER = Integer.valueOf(515);
    public static final String ZERO_POUNDS_IN_STRING = "0.00";
    public static final Integer ZERO_POUNDS_IN_INTEGER = Integer.valueOf(0);
    public static final String BLANK_STRING = "";
    public static final String TWO_POUNDS_TEN_IN_STRING = "2.10";
    public static final Integer TWO_POUNDS_TEN_IN_INTEGER = Integer.valueOf(210);
    public static final Integer NINE_POUNDS_SEVENTY_INTEGER = Integer.valueOf(970);
    public static final Integer NINE_POUNDS_SIXTYNINE_INTEGER = Integer.valueOf(969);
    public static final Integer THREE_POUNDS_ONE_ZERO_ONE_IN_INTEGER = Integer.valueOf(3101);
    public static final String THREE_POUNDS_ONE_ZERO_ONE_IN_STRING = "3.101";

    PenceFormatter penceFormatter;

    @Before
    public void setUp() {
        penceFormatter = new PenceFormatter();
    }

    @Test
    public void printShouldReturnFivePoundsInString() {
        assertEquals(FIVE_POUNDS_IN_STRING, penceFormatter.print(FIVE_POUNDS_IN_INTEGER, GB_LOCALE));
    }

    @Test
    public void parseShouldReturnFivePoundsInInteger() throws ParseException {
        assertEquals(FIVE_POUNDS_IN_INTEGER, penceFormatter.parse(FIVE_POUNDS_IN_STRING, GB_LOCALE));
    }

    @Test
    public void printShouldReturnFivePoundsFifteenPenceInString() {
        assertEquals(FIVE_POUNDS_FIFTEEN_PENCE_IN_STRING, penceFormatter.print(FIVE_POUNDS_FIFTEEN_PENCE_IN_INTEGER, GB_LOCALE));
    }

    @Test
    public void parseShouldReturnFivePoundsFifteenPenceInInteger() throws ParseException {
        assertEquals(FIVE_POUNDS_FIFTEEN_PENCE_IN_INTEGER, penceFormatter.parse(FIVE_POUNDS_FIFTEEN_PENCE_IN_STRING, GB_LOCALE));
    }

    @Test
    public void printShouldReturnTwoPoundsTenInString() {
        assertEquals(TWO_POUNDS_TEN_IN_STRING, penceFormatter.print(TWO_POUNDS_TEN_IN_INTEGER, GB_LOCALE));
    }

    @Test
    public void parseShouldReturnTwoPoundsTenInInteger() throws ParseException {
        assertEquals(TWO_POUNDS_TEN_IN_INTEGER, penceFormatter.parse(TWO_POUNDS_TEN_IN_STRING, GB_LOCALE));
    }

    @Test
    public void printShouldReturnZeroPoundsInString() {
        assertEquals(ZERO_POUNDS_IN_STRING, penceFormatter.print(ZERO_POUNDS_IN_INTEGER, GB_LOCALE));
    }

    @Test
    public void parseShouldReturnZeroPoundsInInteger() throws ParseException {
        assertEquals(ZERO_POUNDS_IN_INTEGER, penceFormatter.parse(ZERO_POUNDS_IN_STRING, GB_LOCALE));
    }

    @Test
    public void printShouldReturnBlank() {
        assertEquals(BLANK_STRING, penceFormatter.print(null, GB_LOCALE));
    }

    @Test
    public void parseShouldReturnNull() throws ParseException {
        assertEquals(null, penceFormatter.parse(BLANK_STRING, GB_LOCALE));
    }
    
    @Test
    public void parseShouldCheckForPrecision() throws ParseException {
        assertEquals(NINE_POUNDS_SEVENTY_INTEGER, penceFormatter.parse(CHECK_PRECISION_STRING, GB_LOCALE));
    }

    @Test(expected = ParseException.class)
    public void parseShouldReturnThreePoundsOneZeroOneInInteger() throws ParseException {
        assertEquals(THREE_POUNDS_ONE_ZERO_ONE_IN_INTEGER, penceFormatter.parse(THREE_POUNDS_ONE_ZERO_ONE_IN_STRING , GB_LOCALE));
    }

    @Test
    public void parseShouldRoundUpToNearestInteger() throws ParseException {
        assertEquals(FOUR_POUNDS_EIGHTY_FIVE_IN_INTEGER, penceFormatter.parse(FOUR_POUNDS_EIGHTY_FIVE_IN_STRING , GB_LOCALE));
    }
}
