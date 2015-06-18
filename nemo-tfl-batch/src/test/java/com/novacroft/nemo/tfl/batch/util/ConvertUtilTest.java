package com.novacroft.nemo.tfl.batch.util;

import com.novacroft.nemo.common.exception.BatchJobException;
import com.novacroft.nemo.tfl.batch.util.cubic.CubicConvertUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static org.junit.Assert.*;

/**
 * ConvertUtil unit tests
 */
public class ConvertUtilTest {
    @Test
    public void convertStringToDateShouldConvertValidDate() {
        String dateAsString = "25-DEC-2001";
        Date date = CubicConvertUtil.convertStringToDate(dateAsString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertTrue(2001 == calendar.get(Calendar.YEAR));
        assertTrue(Calendar.DECEMBER == calendar.get(Calendar.MONTH));
        assertTrue(25 == calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void convertStringToDateShouldNotConvertEmptyDate() {
        String dateAsString = "";
        assertNull(CubicConvertUtil.convertStringToDate(dateAsString));
    }

    @Test(
            expected = BatchJobException.class)
    public void convertStringToDateShouldNotConvertInValidDate() {
        String dateAsString = "25/12/2001";
        CubicConvertUtil.convertStringToDate(dateAsString);
    }

    @Test
    public void convertStringToDateAndTimeShouldConvertValidDateAndTime() {
        String dateAndTimeAsString = "06-FEB-1840 14:30:42";
        Date date = CubicConvertUtil.convertStringToDateAndTime(dateAndTimeAsString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertTrue(1840 == calendar.get(Calendar.YEAR));
        assertTrue(Calendar.FEBRUARY == calendar.get(Calendar.MONTH));
        assertTrue(6 == calendar.get(Calendar.DAY_OF_MONTH));
        assertTrue(14 == calendar.get(Calendar.HOUR_OF_DAY));
        assertTrue(30 == calendar.get(Calendar.MINUTE));
        assertTrue(42 == calendar.get(Calendar.SECOND));
    }

    @Test
    public void convertStringToDateAndTimeShouldNotConvertEmptyDateAndTime() {
        String dateAndTimeAsString = "";
        assertNull(CubicConvertUtil.convertStringToDateAndTime(dateAndTimeAsString));
    }

    @Test(
            expected = BatchJobException.class)
    public void convertStringToDateAndTimeShouldNotConvertInValidDateAndTime() {
        String dateAndTimeAsString = "6/2/1840 2-30 pm";
        CubicConvertUtil.convertStringToDate(dateAndTimeAsString);
    }

    @Test
    public void convertStringToIntegerShouldConvertValidInteger() {
        String integerAsString = "42";
        Integer i = ConvertUtil.convertStringToInteger(integerAsString);
        assertTrue(42 == i);
    }

    @Test
    public void convertStringToIntegerShouldNotConvertEmptyInteger() {
        String integerAsString = "";
        assertNull(ConvertUtil.convertStringToInteger(integerAsString));
    }

    @Test(
            expected = BatchJobException.class)
    public void convertStringToIntegerShouldNotConvertFloat() {
        String integerAsString = "42.42";
        ConvertUtil.convertStringToInteger(integerAsString);
    }

    @Test(
            expected = BatchJobException.class)
    public void convertStringToIntegerShouldNotConvertAlpha() {
        String integerAsString = "ABC";
        ConvertUtil.convertStringToInteger(integerAsString);
    }

    @Test
    public void convertIntegerToStringShouldConvertInteger() {
        assertEquals("42", ConvertUtil.convertIntegerToString(42));
    }

    @Test
    public void convertIntegerToStringShouldNotConvertNull() {
        assertEquals("", ConvertUtil.convertIntegerToString(null));
    }

    @Test
    public void convertDateToIsoFormatStringShouldConvertDate() {
        assertEquals("2013-08-20T12:00:00.000+0100", ConvertUtil.convertDateToIsoFormatString(getAug20()));
    }

    @Test
    public void convertDateToIsoFormatStringShouldNotConvertNull() {
        assertEquals("", ConvertUtil.convertDateToIsoFormatString(null));
    }

    @Test
    public void convertDateToStringShouldConvertDate() {
        assertEquals("20-Aug-2013", CubicConvertUtil.convertDateToString(getAug20()));
    }

    @Test
    public void convertDateToStringShouldNotConvertNull() {
        assertEquals("", CubicConvertUtil.convertDateToString(null));
    }

    @Test
    public void convertDateAndTimeToStringShouldConvertDate() {
        assertEquals("20-Aug-2013 12:00:00", CubicConvertUtil.convertDateAndTimeToString(getAug20()));
    }

    @Test
    public void convertDateAndTimeToStringShouldNotConvertNull() {
        assertEquals("", CubicConvertUtil.convertDateAndTimeToString(null));
    }

    @Test
    public void shouldConvertStringToLong() {
        Long expectedResult = 1234L;
        assertEquals(expectedResult, ConvertUtil.convertStringToLong(String.valueOf(expectedResult)));
    }

    @Test(expected = BatchJobException.class)
    public void convertStringToLongShouldError() {
        ConvertUtil.convertStringToLong(String.valueOf("!@$%*"));
    }

    @Test
    public void shouldConvertStringToFloat() {
        Float expectedResult = 1234.56F;
        assertEquals(expectedResult, ConvertUtil.convertStringToFloat(String.valueOf(expectedResult)));
    }

    @Test(expected = BatchJobException.class)
    public void convertStringToFloatShouldError() {
        ConvertUtil.convertStringToFloat(String.valueOf("!@$%*"));
    }
}
