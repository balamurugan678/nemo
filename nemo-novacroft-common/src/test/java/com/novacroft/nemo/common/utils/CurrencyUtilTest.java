package com.novacroft.nemo.common.utils;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * CurrencyUtil unit tests
 */
public class CurrencyUtilTest {
    protected static final Integer PENCE_VALUE_1 = 357;
    protected static final Integer PENCE_VALUE_1_NEGATIVE = -357;
    protected static final Float POUNDS_AND_PENCE_VALUE_1 = 3.57F;
    protected static final String FORMATTED_POUNDS_AND_PENCE_VALUE_1 = "3.57";
    protected static final Float POUNDS_AND_PENCE_VALUE_1_NEGATIVE = -3.57F;
    protected static final Integer PENCE_VALUE_2 = 3579;
    protected static final Integer PENCE_VALUE_3 = 183579;
    protected static final Long PENCE_VALUE_2_LONG = 3579L;
    protected static final Long PENCE_VALUE_3_LONG = 183579L;
    protected static final Float PENCE_VALUE_4 = 1835.79F;
    protected static final String FORMATTED_POUNDS_AND_PENCE_VALUE_2 = "35.79";
    protected static final String FORMATTED_POUNDS_AND_PENCE_VALUE_3 = "1,835.79";
    protected static final String FORMATTED_POUNDS_AND_PENCE_VALUE_3A = "&pound;1,835.79";
    protected static final String FORMATTED_POUNDS_AND_PENCE_VALUE_3B = "&pound;1835.79";
    protected static final String FORMATTED_POUNDS_AND_PENCE_VALUE_4 = "1835.79";

    @Test
    public void shouldConvertPenceToPounds() {
        assertEquals(POUNDS_AND_PENCE_VALUE_1, CurrencyUtil.convertPenceToPounds(PENCE_VALUE_1));
    }

    @Test
    public void convertPenceToPoundsShouldReturnNullWithInteger() {
        assertEquals(null, CurrencyUtil.convertPenceToPounds((Integer) null));
    }

    @Test
    public void convertPenceToPoundsShouldReturnNullWithFloat() {
        assertEquals(null, CurrencyUtil.convertPenceToPounds((Float) null));
    }

    @Test
    public void shouldConvertPoundsToPence() {
        assertEquals(PENCE_VALUE_1, CurrencyUtil.convertPoundsToPence(POUNDS_AND_PENCE_VALUE_1));
    }

    @Test
    public void convertPoundsToPenceShouldReturnNull() {
        assertEquals(null, CurrencyUtil.convertPoundsToPence(null));
    }

    @Test
    public void shouldFormatPoundsAndPenceWithoutCurrencySymbol() {
        assertEquals(FORMATTED_POUNDS_AND_PENCE_VALUE_1,
                CurrencyUtil.formatPoundsAndPenceWithoutCurrencySymbol(POUNDS_AND_PENCE_VALUE_1));
    }

    @Test
    public void formatPoundsAndPenceWithoutCurrencySymbolShouldReturnNull() {
        assertEquals("", CurrencyUtil.formatPoundsAndPenceWithoutCurrencySymbol(null));
    }

    @Test
    public void shouldFormatPenceWithoutCurrencySymbol() {
        assertEquals(FORMATTED_POUNDS_AND_PENCE_VALUE_1, CurrencyUtil.formatPenceWithoutCurrencySymbol(PENCE_VALUE_1));
    }

    @Test
    public void shouldConvertPoundsAndPenceAsStringToPenceAsInteger() {
        assertEquals((Integer) PENCE_VALUE_2,
                CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsInteger(FORMATTED_POUNDS_AND_PENCE_VALUE_2));
    }

    @Test
    public void convertPoundsAndPenceAsStringToPenceAsIntegerShouldConvertAmountOverOneThousandPounds() {
        assertEquals((Integer) PENCE_VALUE_3,
                CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsInteger(FORMATTED_POUNDS_AND_PENCE_VALUE_3));
    }

    @Test
    public void shouldFormatPoundsAndPenceWithoutCurrencySymbolOrCommas() {
        assertEquals(FORMATTED_POUNDS_AND_PENCE_VALUE_4,
                CurrencyUtil.formatPoundsAndPenceWithoutCurrencySymbolOrCommas(PENCE_VALUE_4));
    }

    @Test
    public void shouldFormatPenceWithHtmlCurrencySymbol() {
        assertEquals(FORMATTED_POUNDS_AND_PENCE_VALUE_3B, CurrencyUtil.formatPenceWithHtmlCurrencySymbol(PENCE_VALUE_3));
    }

    @Test(expected = ApplicationServiceException.class)
    public void convertPoundsAndPenceAsStringToPenceAsIntegerShouldError() {
        CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsInteger("&*(%$");
    }

    @Test
    public void shouldFormatPenceWithoutCurrencySymbolOrCommas() {
        assertEquals(FORMATTED_POUNDS_AND_PENCE_VALUE_4, CurrencyUtil.formatPenceWithoutCurrencySymbolOrCommas(PENCE_VALUE_3));
    }

    @Test
    public void shouldConvertPoundsAndPenceAsStringToPenceAsLong() {
        assertEquals(PENCE_VALUE_2_LONG,
                CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsLong(FORMATTED_POUNDS_AND_PENCE_VALUE_2));
    }

    @Test
    public void convertPoundsAndPenceAsStringToPenceAsLongShouldConvertAmountOverOneThousandPounds() {
        assertEquals(PENCE_VALUE_3_LONG,
                CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsLong(FORMATTED_POUNDS_AND_PENCE_VALUE_3));
    }

    @Test(expected = ApplicationServiceException.class)
    public void convertPoundsAndPenceAsStringToPenceAsLongShouldError() {
        CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsLong("&*(%$");
    }

    @Test
    public void shouldReverseSignForPositiveFloatValue() {
        assertEquals(POUNDS_AND_PENCE_VALUE_1_NEGATIVE, CurrencyUtil.reverseSign(POUNDS_AND_PENCE_VALUE_1));
    }

    @Test
    public void shouldReverseSignForNegativeFloatValue() {
        assertEquals(POUNDS_AND_PENCE_VALUE_1, CurrencyUtil.reverseSign(POUNDS_AND_PENCE_VALUE_1_NEGATIVE));
    }

    @Test
    public void shouldReverseSignForPositiveIntegerValue() {
        assertEquals(PENCE_VALUE_1_NEGATIVE, CurrencyUtil.reverseSign(PENCE_VALUE_1));
    }

    @Test
    public void shouldReverseSignForNegativeIntegerValue() {
        assertEquals(PENCE_VALUE_1, CurrencyUtil.reverseSign(PENCE_VALUE_1_NEGATIVE));
    }
    
    @Test
    public void convertPoundsAndPenceToPenceAsIntegerIfPatternMatchesPoundsAndDoubleDecimal(){
        assertEquals(1230, CurrencyUtil.convertPoundsAndPenceToPenceAsInteger("12.30").intValue());
    }
    
    @Test
    public void convertPoundsAndPenceToPenceAsIntegerIfPatternMatchesPoundsAndNoDecimals(){
        assertEquals(1200, CurrencyUtil.convertPoundsAndPenceToPenceAsInteger("12").intValue());
    }
    
    @Test
    public void convertPoundsAndPenceToPenceAsIntegerIfPatternMatchesPoundsAndOneDecimal(){
        assertEquals(1230, CurrencyUtil.convertPoundsAndPenceToPenceAsInteger("12.3").intValue());
    }
    
    @Test
    public void convertPoundsAndPenceToPenceAsIntegerIfPatternDoesNotMatch(){
        assertEquals(1230, CurrencyUtil.convertPoundsAndPenceToPenceAsInteger("12.3023").intValue());
    }
}
