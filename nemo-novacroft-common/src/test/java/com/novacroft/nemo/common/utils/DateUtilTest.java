package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.utils.DateUtil.addDaysToDate;
import static com.novacroft.nemo.common.utils.DateUtil.addMonthsAndDaysToDate;
import static com.novacroft.nemo.common.utils.DateUtil.addMonthsFirstAndThenDaysToDate;
import static com.novacroft.nemo.common.utils.DateUtil.addMonthsToDate;
import static com.novacroft.nemo.common.utils.DateUtil.addMonthsToDateTime;
import static com.novacroft.nemo.common.utils.DateUtil.addYearsToDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatDateTime;
import static com.novacroft.nemo.common.utils.DateUtil.formatStringAsDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatTime;
import static com.novacroft.nemo.common.utils.DateUtil.formatTimeToMinute;
import static com.novacroft.nemo.common.utils.DateUtil.getBusinessDay;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInDaysHoursMinutesAsString;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffIncludingStartDate;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffWithDaylightSavings;
import static com.novacroft.nemo.common.utils.DateUtil.getDayBefore;
import static com.novacroft.nemo.common.utils.DateUtil.getDaysDiffExcludingMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getFiveDaysBefore;
import static com.novacroft.nemo.common.utils.DateUtil.getSevenDaysBefore;
import static com.novacroft.nemo.common.utils.DateUtil.getMidnightDay;
import static com.novacroft.nemo.common.utils.DateUtil.getNextSunday;
import static com.novacroft.nemo.common.utils.DateUtil.getTomorrowDate;
import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.common.utils.DateUtil.isAfterOrEqual;
import static com.novacroft.nemo.common.utils.DateUtil.isBefore;
import static com.novacroft.nemo.common.utils.DateUtil.isBeforeOrEqual;
import static com.novacroft.nemo.common.utils.DateUtil.isBetween;
import static com.novacroft.nemo.common.utils.DateUtil.isCurrentYearALeapYearForCurrentDate;
import static com.novacroft.nemo.common.utils.DateUtil.isEqual;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.DateUtil.parseCalenderToStringDateObj;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_01;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_15;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_15_WITH_TIMESTAMP_FORMAT;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_16;
import static com.novacroft.nemo.test_support.DateTestUtil.AUG_16_WITH_TIMESTAMP_FORMAT;
import static com.novacroft.nemo.test_support.DateTestUtil.DATE_FORMAT_STR;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC_2015;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_BEFORE_A_DAY_OF_MONTH_AUG;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_BEFORE_A_DAY_OF_MONTH_JULY;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_BEFORE_A_DAY_OF_MONTH_MAY;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_BEFORE_A_DAY_OF_MONTH_OCT;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_APRIL;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_AUG;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_DEC;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_JAN;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_JULY;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_JUNE;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_MARCH;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_MAY;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_NOV;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_OCT;
import static com.novacroft.nemo.test_support.DateTestUtil.END_DATE_OF_MONTH_SEP;
import static com.novacroft.nemo.test_support.DateTestUtil.FEB_LEAPYEAR_LASTDAY;
import static com.novacroft.nemo.test_support.DateTestUtil.FEB_NON_LEAPYEAR_LASTDAY;
import static com.novacroft.nemo.test_support.DateTestUtil.JAN_LEAP_31;
import static com.novacroft.nemo.test_support.DateTestUtil.JAN_NON_LEAP_31;
import static com.novacroft.nemo.test_support.DateTestUtil.JUN_29;
import static com.novacroft.nemo.test_support.DateTestUtil.JUN_30;
import static com.novacroft.nemo.test_support.DateTestUtil.MAY_31;
import static com.novacroft.nemo.test_support.DateTestUtil.OCTOBER_24;
import static com.novacroft.nemo.test_support.DateTestUtil.SEPTEMBER_15;
import static com.novacroft.nemo.test_support.DateTestUtil.get31Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.getApr03;
import static com.novacroft.nemo.test_support.DateTestUtil.getApr03At1959;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22At1723;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug23At0429;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug23At1810;
import static com.novacroft.nemo.test_support.DateTestUtil.getDate;
import static com.novacroft.nemo.test_support.DateTestUtil.getMar28;
import static com.novacroft.nemo.test_support.DateTestUtil.getMar28At0905;
import static com.novacroft.nemo.test_support.DateTestUtil.getMar30At1420;
import static com.novacroft.nemo.test_support.DateTestUtil.getMar31At1110;
import static com.novacroft.nemo.test_support.DateTestUtil.getMar31At1520;
import static com.novacroft.nemo.test_support.DateTestUtil.getSun16Feb;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.test_support.DateTestUtil;

/**
 * DateUtil unit tests
 */
public class DateUtilTest {
    protected static final String SHORT_DATE_REGEX = "([0-9]{2})/([0-9]{2})/([0-9]{4})";
    public static final Long HOUR_IN_MILLIS = 3600000L;
    protected static final Long DAY_IN_MILLIS = 86400000L;
    protected static final Date DATE_TO_TEST = new GregorianCalendar(2013, 9, 15).getTime();
    // Note: Second Parameter to GregorianCalendar (i.e. Month value) is 0-based.
    protected static final Date DATE_TO_TEST_BUSINESS_CUT_OFF = new GregorianCalendar(2013, 9, 15, 23, 35, 0).getTime();
    protected static final Date PREVIOUS_DAY = new GregorianCalendar(2013, 9, 14).getTime();
    protected static final Date FUTURE_DATE = new GregorianCalendar(2013, 9, 25).getTime();
    protected static final Date FUTURE_DATE_NEXT_DAY = new GregorianCalendar(2013, 9, 26, 23, 35, 0).getTime();
    protected static final Date PAST_DATE = new GregorianCalendar(2013, 9, 5).getTime();
    protected static final int DAYS_ADDED = 10;
    protected static final int DAYS_SUBTRACTED = -10;
    protected static final String DATE_TO_TEST_IN_STRING = "15/10/2013";
    protected static final String START_DATE1 = "25/10/2014";
    protected static final String END_DATE1 = "30/10/2014";
    protected static final int SECONDS_BETWEEN_DATES = 435600;
    protected static final int DAYLIGHT_TEST_DATE_DIFFERENCE_1 = 1;
    protected static final int DAYLIGHT_TEST_DATE_DIFFERENCE_2 = 16;
    protected static final int DAYLIGHT_TEST_DATE_DIFFERENCE_3 = 6;
    protected static final int DAYLIGHT_TEST_DATE_DIFFERENCE_4 = 0;
    protected static final String DIFFERENCE_AS_STRING = "1d 23h 35m";
    protected static final long DIFFERENCE_AS_LONG = 2855L;
    protected static final DateTime FUTURE_DATE_AS_DATETIME = new DateTime(FUTURE_DATE);
    protected static final DateTime FUTURE_DATE_NEXT_DAY_AS_DATETIME = new DateTime(FUTURE_DATE_NEXT_DAY);

    protected static final int LEAPYEAR = 2016;
    protected static final int NOT_LEAPYEAR = 2014;
    protected static final int DURATION_OF_MONTHS_ADDED = 1;
    protected static final Date CURRENT_NOT_LEAPYEAR_DATE= new GregorianCalendar(NOT_LEAPYEAR, 10, 15).getTime();
    protected static final Date CURRENT_LEAPYEAR_DATE = new GregorianCalendar(LEAPYEAR, 10, 15).getTime();
    protected static final Date START_DATE_OF_MONTH_FOR_ONEDAYBEFORE_OF_NEXTCALENDERMONTH = DateTestUtil.getDate(DATE_FORMAT_STR, MAY_31);
    protected static final Date START_DATE_OF_MONTH_FOR_LASTDAY_OF_NEXTCALENDERMONTH = DateTestUtil.getDate(DATE_FORMAT_STR, MAY_31);
    protected static final Date END_DATE_OF_MONTH_FOR_ONEDAYBEFORE_OF_NEXTCALENDERMONTH = DateTestUtil.getDate(DATE_FORMAT_STR, JUN_30);
    protected static final Date END_DATE_OF_MONTH_FOR_LASTDAY_OF_NEXTCALENDERMONTH = DateTestUtil.getDate(DATE_FORMAT_STR, JUN_29);
    protected static final Date START_DATE_OF_MONTH_FOR_LEAPYEAR_JAN_MONTH = DateTestUtil.getDate(DATE_FORMAT_STR, JAN_LEAP_31);
    protected static final Date START_DATE_OF_MONTH_FOR_NON_LEAPYEAR_JAN_MONTH = DateTestUtil.getDate(DATE_FORMAT_STR, JAN_NON_LEAP_31);
    protected static final Date END_DATE_OF_MONTH_FOR_LEAPYEAR_FEB_MONTH = DateTestUtil.getDate(DATE_FORMAT_STR, FEB_LEAPYEAR_LASTDAY);
    protected static final Date END_DATE_OF_MONTH_FOR_NON_LEAPYEAR_FEB_MONTH= DateTestUtil.getDate(DATE_FORMAT_STR, FEB_NON_LEAPYEAR_LASTDAY);
    
    protected static final Date END_DATE_OF_CALENDERMONTH_MARCH_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_MARCH);
    protected static final Date END_DATE_OF_CALENDERMONTH_APRIL_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_APRIL);
    protected static final Date END_DATE_OF_CALENDERMONTH_MAY_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_MAY);
    protected static final Date END_DATE_BEFPORE_A_DAY_OF_CALENDERMONTH_MAY_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_BEFORE_A_DAY_OF_MONTH_MAY);
    protected static final Date END_DATE_OF_CALENDERMONTH_JUNE_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_JUNE);
    protected static final Date END_DATE_OF_CALENDERMONTH_JULY_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_JULY);
    protected static final Date END_DATE_BEFPORE_A_DAY_OF_CALENDERMONTH_JULY_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_BEFORE_A_DAY_OF_MONTH_JULY);
    protected static final Date END_DATE_OF_CALENDERMONTH_AUGUEST_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_AUG);
    protected static final Date END_DATE_BEFPORE_A_DAY_OF_CALENDERMONTH_AUGUEST_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_BEFORE_A_DAY_OF_MONTH_AUG);
    
    protected static final Date END_DATE_OF_CALENDERMONTH_SEP_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_SEP);
    protected static final Date END_DATE_OF_CALENDERMONTH_OCT_WITH_STR_FORMAT= DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_OCT);
    protected static final Date END_DATE_BEFORE_A_DAY_OF_CALENDERMONTH_OCT_WITH_STR_FORMAT= DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_BEFORE_A_DAY_OF_MONTH_OCT);
    protected static final Date END_DATE_OF_CALENDERMONTH_NOV_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_NOV);
    protected static final Date END_DATE_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_DEC);
    protected static final Date END_DATE_BEFORE_A_DAY_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT = DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC);
    protected static final Date END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT= DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_OF_MONTH_JAN);
    protected static final Date END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC_2015_STR_FORMAT= DateTestUtil.getDate(DATE_FORMAT_STR, END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC_2015);
    protected static final Date EXPECTED_END_DATE_OCTOBER_24 = DateTestUtil.getDate(DATE_FORMAT_STR, OCTOBER_24);
    @Test
    public void getBusinessDayShouldReturnFutureDate() {
        assertEquals(FUTURE_DATE, getBusinessDay(DATE_TO_TEST, DAYS_ADDED));
    }

    @Test
    public void getBusinessDayShouldReturnFutureDateNextDay() {
        assertEquals(FUTURE_DATE_NEXT_DAY, getBusinessDay(DATE_TO_TEST_BUSINESS_CUT_OFF, DAYS_ADDED));
    }

    @Test
    public void addDaysToDateShouldReturnFutureDate() {
        assertEquals(FUTURE_DATE, addDaysToDate(DATE_TO_TEST, DAYS_ADDED));
    }

    @Test
    public void addDaysToDateShouldReturnPastDate() {
        assertEquals(PAST_DATE, addDaysToDate(DATE_TO_TEST, DAYS_SUBTRACTED));
    }

    @Test
    public void addDaysToDateStringMethodShouldReturnFutureDate() {
        assertEquals(FUTURE_DATE, addDaysToDate(DATE_TO_TEST_IN_STRING, DAYS_ADDED));
    }

    @Test
    public void addDaysToDateStringMethodShouldReturnPastDate() {
        assertEquals(PAST_DATE, addDaysToDate(DATE_TO_TEST_IN_STRING, DAYS_SUBTRACTED));
    }

    @Test
    public void getDayBeforeShouldReturnYesterday() {
        assertEquals(PREVIOUS_DAY, getDayBefore(DATE_TO_TEST));
    }

    @Test
    public void getMidnightDayShouldResetTime() {
        Date midNightTime = getMidnightDay(DATE_TO_TEST);
        GregorianCalendar cal = new GregorianCalendar(Locale.UK);
        cal.setTime(midNightTime);
        assertEquals(0, cal.get(GregorianCalendar.HOUR_OF_DAY));
        assertEquals(0, cal.get(GregorianCalendar.MILLISECOND));
        assertEquals(0, cal.get(GregorianCalendar.SECOND));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithoutDaylightSavingsWithZeroDayDifference() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_4, getDateDiffWithDaylightSavings(getAug22At1723(), getAug23At0429()));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithoutDaylightSavingsWithOneDayDifference() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_1, getDateDiffWithDaylightSavings(getAug22At1723(), getAug23At1810()));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithoutDaylightSavingsWithSixteenDayDifference() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_2, getDateDiffWithDaylightSavings(get31Jan(), getSun16Feb()));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithDaylightSavingsWithZeroDayDifference() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_4, getDateDiffWithDaylightSavings(getMar30At1420(), getMar31At1110()));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithDaylightSavingsWithOneDayDifference() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_1, getDateDiffWithDaylightSavings(getMar30At1420(), getMar31At1520()));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithDaylightSavingsWithSixteenDayDifferenceWithoutTime() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_3, getDateDiffWithDaylightSavings(getMar28(), getApr03()));
    }

    @Test
    public void getDateDiffWithDaylightSavingsShouldReturnDateDifferenceWithDaylightSavingsWithSixteenDayDifferenceWithTime() {
        assertEquals(DAYLIGHT_TEST_DATE_DIFFERENCE_3, getDateDiffWithDaylightSavings(getMar28At0905(), getApr03At1959()));
    }

    @Test
    public void checkCurrentYearIsLeapYear() {
    	assertTrue(isCurrentYearALeapYearForCurrentDate(CURRENT_LEAPYEAR_DATE));
    }
    @Test
    public void checkCurrentYearIsNotLeapYear() {
    	assertFalse(isCurrentYearALeapYearForCurrentDate(CURRENT_NOT_LEAPYEAR_DATE));
    }
    @Test
    public void getEndDateForMonthDurationTypeShouldAddOneMonthWithLastDayOfNextCalenderMonth() {
        assertEquals(END_DATE_OF_MONTH_FOR_ONEDAYBEFORE_OF_NEXTCALENDERMONTH, addMonthsToDateTime(START_DATE_OF_MONTH_FOR_LASTDAY_OF_NEXTCALENDERMONTH, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getEndDateForMonthDurationTypeShouldAddOneMonthWithOneDayBeforeTheSameDayOfNextCalanderMonth() {
        assertEquals(END_DATE_OF_MONTH_FOR_ONEDAYBEFORE_OF_NEXTCALENDERMONTH, addMonthsToDateTime(START_DATE_OF_MONTH_FOR_ONEDAYBEFORE_OF_NEXTCALENDERMONTH, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getEndDateOfLeapYearFebMonth() {
        assertEquals(END_DATE_OF_MONTH_FOR_LEAPYEAR_FEB_MONTH, addMonthsToDateTime(START_DATE_OF_MONTH_FOR_LEAPYEAR_JAN_MONTH, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getEndDateOfNonLeapYearFebMonth() {
        assertEquals(END_DATE_OF_MONTH_FOR_NON_LEAPYEAR_FEB_MONTH, addMonthsToDateTime(START_DATE_OF_MONTH_FOR_NON_LEAPYEAR_JAN_MONTH, DURATION_OF_MONTHS_ADDED));
    }

    @Test
    public void getMonthEndDateBetweenMarchAndAprilWithMonthEndDate() {
        assertEquals(END_DATE_OF_CALENDERMONTH_APRIL_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_MARCH_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    } 
    @Test
    public void getMonthEndDateBetweenAprilAndMayWithMonthEndDate() {
        assertEquals(END_DATE_BEFPORE_A_DAY_OF_CALENDERMONTH_MAY_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_APRIL_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getMonthEndDateBetweenMayAndJuneWithMonthEndDate() {
        assertEquals(END_DATE_OF_CALENDERMONTH_JUNE_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_MAY_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    } 
    @Test
    public void getMonthEndDateBetweenJuneAndJulyWithMonthEndDate() {
        assertEquals(END_DATE_BEFPORE_A_DAY_OF_CALENDERMONTH_JULY_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_JUNE_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getMonthEndDateBetweenJulyAndAuguestWithMonthEndDate() {
        assertEquals(END_DATE_BEFPORE_A_DAY_OF_CALENDERMONTH_AUGUEST_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_JULY_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getMonthEndDateBetweenAuguestAndSeptemberWithMonthEndDate() {
        assertEquals(END_DATE_OF_CALENDERMONTH_SEP_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_AUGUEST_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getMonthEndDateBetweenSeptemberAndOctoberWithMonthEndDate() {
        assertEquals(END_DATE_BEFORE_A_DAY_OF_CALENDERMONTH_OCT_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_SEP_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getMonthEndDateBetweenOctoberAndNovemberWithMonthEndDate() {
        assertEquals(END_DATE_OF_CALENDERMONTH_NOV_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_OCT_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    @Test
    public void getMonthEndDateBetweenNovemberAndDecemberWithMonthEndDate() {
        assertEquals(END_DATE_BEFORE_A_DAY_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_NOV_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    } 
    @Test
    public void getMonthEndDateBetweenDecemberAndJanuaryWithMonthEndDate() {
        assertEquals(END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT, addMonthsToDateTime(END_DATE_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }
    
    @Test
    public void getCalenderToStringDateObjTestWithTrue() {
    	assertEquals(AUG_15,parseCalenderToStringDateObj(AUG_15_WITH_TIMESTAMP_FORMAT));
    }
    
    @Test
    public void getCalenderToStringDateObjTestWithFalse() {
    	assertEquals(AUG_16,parseCalenderToStringDateObj(AUG_16_WITH_TIMESTAMP_FORMAT));
    }

    @Test
    @Ignore
    public void isBeforeBusinessDayCutOffHoursOnTheSameDay() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis() - HOUR_IN_MILLIS);

        assertTrue(DateUtil.isBeforeBusinessDayCutOffHoursOnTheSameDay(date));
    }

    @Test
    public void isBeforeBusinessDayCutOffHoursForNextDay() {
        Date date = parse(formatDate(new Date()));
        date.setTime(date.getTime() - 60000);
        assertTrue(DateUtil.isBeforeBusinessDayCutOffHoursForTheNextDay(date));
    }

    @Test
    public void isAfterBusinessDayCutOffHoursOnTheSameDay() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis() - DAY_IN_MILLIS);

        assertFalse(DateUtil.isBeforeBusinessDayCutOffHoursOnTheSameDay(date));
    }

    @Test
    public void getBusinessDayNullResult() {
        assertNull(getBusinessDay(null, DAYS_ADDED));
    }

    @Test
    public void addMonthsToDateSuccess() {
        assertEquals(END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT,
                        addMonthsToDate(END_DATE_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }

    @Test
    public void addMonthsToDateIncrementZero() {
        assertNotEquals(END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT, addMonthsToDate(END_DATE_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT, 0));
    }

    @Test
    public void addMonthsAndDaysToDateSuccess() {
        assertEquals(END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT,
                        addMonthsAndDaysToDate("30/12/2014", DURATION_OF_MONTHS_ADDED, DURATION_OF_MONTHS_ADDED));
    }

    @Test
    public void addMothsToDateSuccess() {
        assertEquals(END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT,
                        addMonthsToDate(formatDate(END_DATE_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT), DURATION_OF_MONTHS_ADDED));
    }

    @Test
    public void isEqualDate1Null() {
        assertFalse(isEqual(null, END_DATE_OF_CALENDERMONTH_JAN_WITH_STR_FORMAT));
    }

    @Test
    public void isEqualsDateStr1Null() {
        assertFalse(isEqual(null, ""));
    }

    @Test
    public void addYearsToDateSuccess() {
        assertEquals(END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC_2015_STR_FORMAT,
                        addYearsToDate(END_DATE_BEFORE_A_DAY_OF_CALENDERMONTH_DEC_WITH_STR_FORMAT, DURATION_OF_MONTHS_ADDED));
    }

    
    @Test
    public void formatStringAsDateTest(){
        assertEquals(getDate(SHORT_DATE_PATTERN,MAY_31),formatStringAsDate(MAY_31));
    }
    
    @Test
    public void formatStringAsDateShouldThrowParseExceptionForWrongFormatTest(){
        assertNull(formatStringAsDate(AUG_01));
    }
    
    @Test
    public void formatStringAsDateShouldReturnNullForEmptyDateTest(){
        assertNull(formatStringAsDate(null));
    }

    @Test
    public void getTomorrowDateTest() {
        Date expectedDate = addDaysToDate(new Date(), 1);
        assertEquals(formatDate(expectedDate), formatDate(getTomorrowDate()));
    }

    @Test
    public void getFiveDaysBeforeTest() {
        Date expectedDate = addDaysToDate(new Date(), -5);
        assertEquals(formatDate(expectedDate), formatDate(getFiveDaysBefore(new Date())));
    }
    
    @Test
    public void getSevenDaysBeforeTest() {
        Date expectedDate = addDaysToDate(new Date(), -7);
        assertEquals(formatDate(expectedDate), formatDate(getSevenDaysBefore(new Date())));
    }

    @Test
    public void formatDateWithNullFormatString() {
        String formattedDate = formatDate(new Date(), null);
        assertTrue(formattedDate.matches(SHORT_DATE_REGEX));
    }

    @Test
    public void formatDateWithNotNullFormatString() {
        String formattedDate = formatDate(new Date(), SHORT_DATE_PATTERN);
        assertTrue(formattedDate.matches(SHORT_DATE_REGEX));
    }

    @Test
    public void formatDateTimeWithTimeZone() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        assertNotNull(formatDateTime(new Date(), null, timeZone));
    }

    @Test
    public void formatDateTest() {
        assertNotEquals("", formatDate(new Date()));
    }

    @Test
    public void isBetweenDatesTest() {
        Date date = new Date();
        Date fromDate = parse("09/09/2014");
        Date endDate = addDaysToDate(new Date(), 2);
        assertTrue(isBetween(date, fromDate, endDate));
    }

    @Test
    public void isNoteBetweenDatesTest() {
        Date fromDate = parse("09/09/2014");
        Date endDate = addDaysToDate(new Date(), 2);
        assertFalse(isBetween(null, fromDate, endDate));
    }

    @Test
    public void isNotBeforeOrEqual() {
        assertFalse(isBeforeOrEqual(null, new Date()));
    }

    @Test
    public void isNotAfterOrEqual() {
        assertFalse(isAfterOrEqual(new Date(), null));
    }

    @Test
    public void isBeforeOrEqualString() {
        assertTrue(isBeforeOrEqual(DateTestUtil.END_DATE_OF_MONTH_MARCH, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isBeforeOrEqualNullString() {
        assertFalse(isBeforeOrEqual(null, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isAfterOrEqualString() {
        assertTrue(isAfterOrEqual(DateTestUtil.END_DATE_OF_MONTH_MARCH, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isAftereOrEqualNullString() {
        assertFalse(isAfterOrEqual(null, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isBeforeString() {
        assertTrue(isBefore(DateTestUtil.END_DATE_OF_MONTH_MARCH, DateTestUtil.END_DATE_OF_MONTH_APRIL));
    }

    @Test
    public void isBeforeStringSameDay() {
        assertFalse(isBefore(DateTestUtil.END_DATE_OF_MONTH_MARCH, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isBeforeNullString() {
        assertFalse(isBefore(null, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isAfterString() {
        assertTrue(isAfter(DateTestUtil.END_DATE_OF_MONTH_APRIL, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void isAftereNullString() {
        assertFalse(isAfter(null, DateTestUtil.END_DATE_OF_MONTH_MARCH));
    }

    @Test
    public void getDateDiffInDaysHoursMinutesAsStringTest() {
        DateTime now = new DateTime();
        assertEquals("0m", getDateDiffInDaysHoursMinutesAsString(now, now));
    }

    @Test
    public void shouldGetDateDiffInMonthsSecondCalendarLargerCalender() {
        assertEquals(1, getDateDiffInMonths(new GregorianCalendar(2013, 9, 15), new GregorianCalendar(2013, 10, 16)));
    }

    @Test
    public void shouldGetDateDiffInMonthsSameDayCalender() {
        assertEquals(0, getDateDiffInMonths(new GregorianCalendar(), new GregorianCalendar()));
    }

    @Test
    public void shouldGetDateDiffInMonthsFirstCalendarLargerCalender() {
        assertEquals(1, getDateDiffInMonths(new GregorianCalendar(2013, 9, 15), new GregorianCalendar(2013, 11, 13)));
    }

    @Test
    public void getDateDiffInMonthsTest() {
        assertEquals(0, getDateDiffInMonths(new Date(), new Date()));
    }

    @Test
    public void getDateDiffInMonthsString() {
        assertEquals(-1, getDateDiffInMonths(DateTestUtil.DATE_10_09_2014, DateTestUtil.DATE_10_08_2014));
    }

    @Test
    public void getNextSundayTest() {
        assertNotNull(getNextSunday(Calendar.getInstance()));
    }

    @Test
    public void getDaysDiffExcludingMonthsTest() {
        assertEquals(1, getDaysDiffExcludingMonths(DateTestUtil.DATE_09_09_2014, DateTestUtil.DATE_09_09_2014));
    }

    @Test
    public void getDateDiffIncludingStartDateStringTest() {
        assertEquals(1, getDateDiffIncludingStartDate(DateTestUtil.DATE_09_09_2014, DateTestUtil.DATE_09_09_2014));
    }

    @Test
    public void getDateDiffIncludingStartDateTest() {
        assertEquals(1, getDateDiffIncludingStartDate(new Date(), new Date()));
    }

    @Test
    public void formatDateTimeReturnsString() {
        assertNotNull(formatDateTime(new Date()));
    }

    @Test
    public void formatTimeTest() {
        assertNotNull(formatTime(new Date()));
    }

    @Test
    public void shouldFormatTime() {
        assertNotNull(formatTime(new Date(), DateConstant.TIME_TO_SECOND_PATTERN));
    }

    @Test
    public void formatTimeIntMinutesTest() {
        assertNotNull(formatTimeToMinute(new Date()));
    }
    
    @Test
    public void testConvertXMLGregorianToString() throws DatatypeConfigurationException{
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(new Date().getTime());
        XMLGregorianCalendar xmlGregarianCalendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
       String result =  DateUtil.convertXMLGregorianToString(xmlGregarianCalendar);
       assertNotNull(result);
    }
    
    @Test
    public void testConvertStringToXMLGregorian() throws DatatypeConfigurationException{
        XMLGregorianCalendar xmlGregarionCal = DateUtil.convertStringToXMLGregorian(DATE_TO_TEST_IN_STRING);
        assertNotNull(xmlGregarionCal);
    }
    
    @Test
    public void testConvertXMLGregorianToDate() throws DatatypeConfigurationException{
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(new Date().getTime());
        XMLGregorianCalendar xmlGregarianCalendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
       Date result =  DateUtil.convertXMLGregorianToDate(xmlGregarianCalendar);
       assertNotNull(result);
    }
    
    @Test
    public void testgetDaysBetween(){
       int result =  DateUtil.getDaysBetween(DateUtil.formatStringAsDate(DateTestUtil.MARCH_10),DateUtil.formatStringAsDate(AUG_15));
       assertNotNull(result);
    }
    
    @Test
    public void testFormatDateAsISO8601() {
       String result =  DateUtil.formatDateAsISO8601(new Date());
       assertNotNull(result);
    }
    
    @Test
    public void testConvertDateToXMLGregorian() {
        XMLGregorianCalendar xmlGregarionCal = DateUtil.convertDateToXMLGregorian(new Date());
        assertNotNull(xmlGregarionCal);
    }
    
    @Test
    public void testtruncateAtDay(){
       Date date =  DateUtil.truncateAtDay(new Date());
       assertNotNull(date);
    }
    
    @Test
    public void testgetDateDiffInSecondsAsLong() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateConstant.SHORT_DATE_PATTERN);
        DateTime startDate = formatter.parseDateTime(START_DATE1);
        DateTime endDate = formatter.parseDateTime(END_DATE1);
        Long result = DateUtil.getDateDiffInSecondsAsLong(startDate, endDate);
        assertEquals(SECONDS_BETWEEN_DATES, result.intValue());
        assertNotNull(result);
    }
    
    @Test
    public void testIsBeforeString(){
       boolean result =  DateUtil.isBefore(DateTestUtil.MARCH_10,AUG_15,DateConstant.SHORT_DATE_PATTERN);
       assertTrue(result);
    }
    
    @Test
    public void isBeforeShouldReturnFalseIfADateIsNull() {
        assertFalse(DateUtil.isBefore(null, DateUtil.formatStringAsDate(AUG_15)));
        assertFalse(DateUtil.isBefore(DateUtil.formatStringAsDate(AUG_15), null));
    }

    @Test
    public void isBeforeStringWithFormatShouldReturnFalseIfADateIsNull() {
        assertFalse(DateUtil.isBefore(null, AUG_15, DATE_FORMAT_STR));
        assertFalse(DateUtil.isBefore(AUG_15, null, DATE_FORMAT_STR));
    }

    @Test
    public void  testIsAfterOrEqualExcludingTimestamp(){
       boolean result =    DateUtil.isAfterOrEqualExcludingTimestamp(DateUtil.formatStringAsDate(AUG_15),DateUtil.formatStringAsDate(DateTestUtil.MARCH_10));
        assertEquals(true, result);
    }
    
    @Test
    public void isAfterOrEqualExcludingTimestampShouldReturnFalseIfADateIsNull() {
        assertFalse(DateUtil.isAfterOrEqualExcludingTimestamp(null, DateUtil.formatStringAsDate(AUG_15)));
        assertFalse(DateUtil.isAfterOrEqualExcludingTimestamp(DateUtil.formatStringAsDate(AUG_15), null));
    }

    @Test
    public void isAfterShouldReturnFalseIfADateIsNull() {
        assertFalse(DateUtil.isAfter(null, DateUtil.formatStringAsDate(AUG_15)));
        assertFalse(DateUtil.isAfter(DateUtil.formatStringAsDate(AUG_15), null));
    }

    @Test
    public void testGetTwoDaysAfter(){
        Date twoDaysAfter = DateUtil.getTwoDaysAfter(new Date());
        assertEquals(formatDate(addDaysToDate(new Date(), 2)), formatDate(twoDaysAfter));
    }
    
    @Test
    public void addMonthsFirstAndThenDaysToDateSuccess() {
        assertEquals(EXPECTED_END_DATE_OCTOBER_24,
                        addMonthsFirstAndThenDaysToDate(SEPTEMBER_15, DURATION_OF_MONTHS_ADDED, DAYS_ADDED));
    }
    
    @Test
    public void isTomorrowShouldBeTrue() {
    	Date date = DateUtil.addDaysToDate(new Date(), 1);
    	assertTrue(DateUtil.isTomorrow(date));
    }
    
    @Test
    public void isTomorrowShouldBeFalse() {
    	Date date = DateUtil.addDaysToDate(new Date(), 2);
    	assertFalse(DateUtil.isTomorrow(date));
    }
    
    @Test
    public void getDiffMonths(){
    	GregorianCalendar endCalendar = new GregorianCalendar(2015, Calendar.FEBRUARY, 23);
    	GregorianCalendar startCalendar = new GregorianCalendar(2014, Calendar.DECEMBER, 22);
    	int diffMonths = DateUtil.getDifferenceInMonths(startCalendar.getTime(), endCalendar.getTime());
    	assertNotNull(diffMonths);
    	assertEquals(2, diffMonths);
    }
    
    @Test
    public void getDaysWithoutMonths(){
    	GregorianCalendar endCalendar = new GregorianCalendar(2015, Calendar.FEBRUARY, 23);
    	GregorianCalendar startCalendar = new GregorianCalendar(2014, Calendar.DECEMBER, 22);
    	int diffDays = DateUtil.getDaysWithoutMonths(startCalendar.getTime(), endCalendar.getTime());
    	assertNotNull(diffDays);
    	assertEquals(2, diffDays);
    }
}
