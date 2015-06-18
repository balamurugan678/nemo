package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.constant.DateConstant.BEFORE_MIDNIGHT_MINUTES;
import static com.novacroft.nemo.common.constant.DateConstant.BUSINESS_DAY_CUT_OFF_HOURS;
import static com.novacroft.nemo.common.constant.DateConstant.BUSINESS_DAY_CUT_OFF_MINUTES;
import static com.novacroft.nemo.common.constant.DateConstant.DAYINMONTH_SHORTMONTH_YEAR;
import static com.novacroft.nemo.common.constant.DateConstant.DAYS_IN_WEEK;
import static com.novacroft.nemo.common.constant.DateConstant.DOUBLE_OFFSETDAY;
import static com.novacroft.nemo.common.constant.DateConstant.HOURS_IN_DAY;
import static com.novacroft.nemo.common.constant.DateConstant.ISO_8601_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.MILLISECONDS_IN_A_SECOND;
import static com.novacroft.nemo.common.constant.DateConstant.MINUTES_IN_HOUR;
import static com.novacroft.nemo.common.constant.DateConstant.MONTHS_IN_YEAR;
import static com.novacroft.nemo.common.constant.DateConstant.OFFSETDAY;
import static com.novacroft.nemo.common.constant.DateConstant.SECONDS_IN_A_MINUTE;
import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_AND_TIME_TO_SECOND_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.TIME_TO_MINUTE_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.TIME_TO_SECOND_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.ZERO_MINUTES;
import static com.novacroft.nemo.common.constant.MonthEnum.AUGUST;
import static com.novacroft.nemo.common.constant.MonthEnum.JANUARY;
import static com.novacroft.nemo.common.constant.MonthEnum.MARCH;
import static com.novacroft.nemo.common.constant.MonthEnum.MAY;
import static com.novacroft.nemo.common.constant.MonthEnum.OCTOBER;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.constant.MonthEnum;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

/**
 * Utility to help with date manipulation.
 */
public final class DateUtil {
    public static final String EMPTY_DATE = "";
    public static final int ONE_DAY = 1;
    public static final int TWO_DAYS = 2;
    public static final int FIVE_DAYS = 5;
    public static final int SEVEN_DAYS = 7;
    
    /**
     * This method add the number of days to the date and check the business day cut-off, if the time is more than cut-off time then add one more day
     * to date.
     */
    public static Date getBusinessDay(Date date, int incrementDays) {
        if (date != null) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            cal.setTime(date);
            if (isTimeGreaterThanBusinessDayCutOff(cal)) {
                cal.add(Calendar.DAY_OF_YEAR, (incrementDays + 1));
            } else {
                cal.add(Calendar.DAY_OF_YEAR, incrementDays);
            }
            return cal.getTime();
        }
        return null;
    }

    protected static boolean isTimeGreaterThanBusinessDayCutOff(GregorianCalendar cal) {
        return cal.get(Calendar.HOUR_OF_DAY) == BUSINESS_DAY_CUT_OFF_HOURS && cal.get(Calendar.MINUTE) >= BUSINESS_DAY_CUT_OFF_MINUTES;
    }

    public static boolean isBeforeBusinessDayCutOffHoursOnTheSameDay(Date date) {
        Calendar cal = Calendar.getInstance();
        return isEqual(formatDate(cal.getTime()), formatDate(date))
                        && (cal.get(Calendar.HOUR_OF_DAY) < BUSINESS_DAY_CUT_OFF_HOURS || (cal.get(Calendar.HOUR_OF_DAY) == BUSINESS_DAY_CUT_OFF_HOURS && cal
                                        .get(Calendar.MINUTE) == ZERO_MINUTES));

    }

    public static boolean isBeforeBusinessDayCutOffHoursForTheNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        return isEqual(formatDate(addDaysToDate(cal.getTime(), -ONE_DAY)), formatDate(date))
                        && cal2.get(Calendar.HOUR_OF_DAY) >= BUSINESS_DAY_CUT_OFF_HOURS
                        && cal2.get(Calendar.MINUTE) <= BEFORE_MIDNIGHT_MINUTES;
    }
    
    public static boolean isTomorrow(Date date) {
    	DateTime tomorrow = new DateTime(getTomorrowDate());
    	DateTime dateToBeCompared = new DateTime(date);
    	return (dateToBeCompared.withTimeAtStartOfDay().isEqual(tomorrow.withTimeAtStartOfDay()));
    }


    /**
     * This method add the number of days to the date.
     */
    public static Date addDaysToDate(Date date, int incrementDays) {
        if (date != null) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, incrementDays);
            return cal.getTime();
        }
        return null;
    }

    public static Date getDayBefore(Date value) {
        return addDaysToDate(value, -1);
    }

    
    /**
     * This method gives the tomorrow's date
     */
    public static Date getTomorrowDate() {
    	GregorianCalendar calendar = new GregorianCalendar(Locale.UK);
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, ONE_DAY);
        return calendar.getTime();
    }
    
    public static Date getFiveDaysBefore(Date value) {
        return addDaysToDate(value, -FIVE_DAYS);
    }
    
    public static Date getSevenDaysBefore(Date value) {
        return addDaysToDate(value, -SEVEN_DAYS);
    }
    
    public static Date getTwoDaysAfter(Date value) {
        return addDaysToDate(value, +TWO_DAYS);
    }
    
    /**
     * This method add the number of days to the date.
     */
    public static Date addDaysToDate(String dateStr, int incrementDays) {
        if (dateStr != null) {
            return addDaysToDate(parse(dateStr), incrementDays);
        }
        return null;
    }

    /**
     * Set the time to midnight.
     * 
     * @return Date
     */
    public static Date getMidnightDay(Date date) {
        GregorianCalendar cal = new GregorianCalendar(Locale.UK);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * This method add the number of months to the date.
     */
    public static Date addMonthsToDate(Date date, int incrementMonths) {
        if (date != null && incrementMonths > 0) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            cal.setTime(date);
            cal.add(Calendar.MONTH, incrementMonths);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            return cal.getTime();
        }
        return date;
    }

    /**
     * This method add the number of months to the date.
     */
    public static Date addMonthsToDate(String dateStr, int incrementMonths) {
        if (dateStr != null) {
            return addMonthsToDate(parse(dateStr), incrementMonths);
        }
        return null;
    }

    /**
     * This method add the number of months and days to the date.
     */
    public static Date addMonthsAndDaysToDate(String dateStr, int incrementMonths, int incrementDays) {
        if (dateStr != null) {
            Date date = addDaysToDate(parse(dateStr), incrementDays);
            return addMonthsToDate(date, incrementMonths);
        }
        return null;
    }
    
    public static Date addMonthsFirstAndThenDaysToDate(String dateStr, int incrementMonths, int incrementDays) {
        if (dateStr != null) {
            Date date = addMonthsToDate(parse(dateStr), incrementMonths);
            return addDaysToDate(date, incrementDays);
        }
        return null;
    }

    /**
     * This method add the number of years to the date.
     */
    public static Date addYearsToDate(Date date, int incrementMonths) {
        if (date != null && incrementMonths > 0) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            cal.add(Calendar.YEAR, incrementMonths);
            return cal.getTime();
        }
        return null;
    }

    public static String formatDateWithShortMonth(Date date) {
        return formatDateTime(date, DAYINMONTH_SHORTMONTH_YEAR);
    }
    
    /**
     * Format a date.
     * 
     * @param date
     *            the <code>Date</code> to be formatted.
     * @return a <code>String</code> containing the formatted date as DD/MM/YYYY.
     */
    public static String formatDate(Date date) {
        return formatDateTime(date, SHORT_DATE_PATTERN);
    }

    /**
     * Format a time.
     * 
     * @param time
     *            the <code>Date</code> to be formatted.
     * @return a <code>String</code> containing the formatted time as HH:MM:SS.
     */
    public static String formatTime(Date time) {
        return formatDateTime(time, TIME_TO_SECOND_PATTERN);
    }

    public static String formatTimeToMinute(Date time) {
        return formatDateTime(time, TIME_TO_MINUTE_PATTERN);
    }

    /**
     * Format a time.
     * 
     * @param time
     *            the <code>Date</code> to be formatted.
     * @param format
     *            the format <code>String</code> to use.
     * @return a <code>String</code> containing the formatted date.
     */
    public static String formatTime(Date time, String format) {
        return formatDateTime(time, format);
    }

    /**
     * Format a date.
     * 
     * @param date
     *            the <code>Date</code> to be formatted.
     * @param format
     *            the format <code>String</code> to use.
     * @return a <code>String</code> containing the formatted date.
     */
    public static String formatDate(Date date, String format) {
        if (format == null || format.length() == 0) {
            return formatDateTime(date, SHORT_DATE_PATTERN);
        } else {
            return formatDateTime(date, format);
        }
    }

    public static String formatDateTime(Date value) {
        return (value != null) ? formatDateTime(value, SHORT_DATE_AND_TIME_TO_SECOND_PATTERN, Locale.UK) : "";
    }

    public static String formatDateTime(Date value, String format) {
        return (value != null) ? formatDateTime(value, (isNotBlank(format) ? format : SHORT_DATE_AND_TIME_TO_SECOND_PATTERN), Locale.UK) : "";
    }

    public static String formatDateTime(Date value, String format, Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat((isNotBlank(format) ? format : SHORT_DATE_AND_TIME_TO_SECOND_PATTERN), (locale != null ? locale
                        : Locale.UK));
        return (value != null) ? formatDateTime(value, dateFormat) : "";
    }

    public static String formatDateTime(Date value, String format, TimeZone timeZone) {
        DateFormat dateFormat = new SimpleDateFormat((isNotBlank(format) ? format : SHORT_DATE_AND_TIME_TO_SECOND_PATTERN), Locale.UK);
        if (timeZone != null) {
            dateFormat.setTimeZone(timeZone);
        }
        return (value != null) ? formatDateTime(value, dateFormat) : "";
    }

    public static String formatDateTime(Date value, DateFormat dateFormat) {
        assert (value != null && dateFormat != null);
        return dateFormat.format(value);
    }

    public static Date parse(String value, String format) {
        return isNotBlank(value) ? parse(value, format, null) : null;
    }

    public static Date parse(String value, String format, TimeZone timeZone) {
        DateFormat dateFormat = new SimpleDateFormat((isNotBlank(format) ? format : SHORT_DATE_PATTERN));
        dateFormat.setLenient(false);
        if (timeZone != null) {
            dateFormat.setTimeZone(timeZone);
        }
        return isNotBlank(value) ? parse(value, dateFormat) : null;
    }

    public static Date parse(String value, DateFormat dateFormat) {
        assert (dateFormat != null);
        try {
            return isNotBlank(value) ? dateFormat.parse(value) : null;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse(String value) {
        return parse(value, SHORT_DATE_PATTERN);
    }

    /**
     * Checks the given date between from date and to date
     */

    public static boolean isBetween(Date date, Date fromDate, Date endDate) {
        if (date == null || fromDate == null || endDate == null) {
            return false;
        }
        return isAfterOrEqual(date, fromDate) && isBeforeOrEqual(date, endDate);
    }

    /**
     * Check the date1 is before or equal to the date2
     */
    public static boolean isBeforeOrEqual(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.compareTo(date2) <= 0;
    }

     /**
     * Check the date1 is after or equal to the date2
     */
    public static boolean isAfterOrEqual(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.compareTo(date2) >= 0;
    }
    
    /**
     * Check the date1 is after or equal to the date2
     */
    public static boolean isAfterOrEqualExcludingTimestamp(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return (DateUtils.isSameDay(date1, date2) || date1.compareTo(date2) > 0);
    }

    /**
     * Check the dateStr1 is before or equal to the dateStr2
     */
    public static boolean isBeforeOrEqual(String dateStr1, String dateStr2) {
        if (dateStr1 == null || dateStr2 == null) {
            return false;
        }
        return isBeforeOrEqual(parse(dateStr1), parse(dateStr2));
    }

    /**
     * Check the dateStr1 is after or equal to the dateStr2
     */
    public static boolean isAfterOrEqual(String dateStr1, String dateStr2) {
        if (dateStr1 == null || dateStr2 == null) {
            return false;
        }
        return isAfterOrEqual(parse(dateStr1), parse(dateStr2));
    }

    /**
     * Check the date1 is before date2
     */
    public static boolean isBefore(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.compareTo(date2) < 0;
    }

    /**
     * Check the date1 is after date2
     */
    public static boolean isAfter(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.compareTo(date2) > 0;
    }

    /**
     * Check the dateStr1 is before dateStr2
     */
    public static boolean isBefore(String dateStr1, String dateStr2) {
        if (dateStr1 == null || dateStr2 == null) {
            return false;
        }
        return isBefore(parse(dateStr1), parse(dateStr2));
    }

    /**
     * Check the dateStr1 is before dateStr2 using specific date format
     */
    public static boolean isBefore(String dateStr1, String dateStr2, String dateFormat) {
        if (dateStr1 == null || dateStr2 == null) {
            return false;
        }
        return isBefore(parse(dateStr1, dateFormat), parse(dateStr2, dateFormat));
    }

    /**
     * Check the dateStr1 is after dateStr2
     */
    public static boolean isAfter(String dateStr1, String dateStr2) {
        if (dateStr1 == null || dateStr2 == null) {
            return false;
        }
        return isAfter(parse(dateStr1), parse(dateStr2));
    }

    public static boolean isEqual(String dateStr1, String dateStr2) {
        if (dateStr1 == null || dateStr2 == null) {
            return false;
        }
        return isEqual(parse(dateStr1), parse(dateStr2));
    }

    public static boolean isEqual(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.compareTo(date2) == 0;
    }

    /**
     * Return the difference between two calendars in days
     */
    public static long getDateDiff(Calendar startCalendar, Calendar endCalendar) {
        return (endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis())
                        / (HOURS_IN_DAY * SECONDS_IN_A_MINUTE * MINUTES_IN_HOUR * MILLISECONDS_IN_A_SECOND);
    }

    /**
     * Return the difference between two dates in days
     */
    public static long getDateDiff(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (HOURS_IN_DAY * SECONDS_IN_A_MINUTE * MINUTES_IN_HOUR * MILLISECONDS_IN_A_SECOND);
    }

    public static long getDateDiffWithDaylightSavings(final Date startDate, final Date endDate) {
        DateTime startDateTime = new DateTime(startDate);
        DateTime endDateTime = new DateTime(endDate);
        return Days.daysBetween(startDateTime, endDateTime).getDays();
    }

    public static String getDateDiffInDaysHoursMinutesAsString(DateTime start, DateTime end) {
        Period period = new Period(start, end);
        PeriodFormatter formatter = new PeriodFormatterBuilder().appendDays().appendSuffix("d ").appendHours().appendSuffix("h ").appendMinutes()
                        .appendSuffix("m").toFormatter();
        return formatter.print(period);
    }

    public static long getDateDiffInSecondsAsLong(DateTime start, DateTime end) {
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * Return the difference between two date strings in days
     */
    public static long getDateDiff(String startDateStr, String endDateStr) {
        return getDateDiff(parse(startDateStr), parse(endDateStr));
    }

    /**
     * Return the difference between two date strings including start date in days
     */
    public static long getDateDiffIncludingStartDate(String startDateStr, String endDateStr) {
        return getDateDiff(startDateStr, endDateStr) + 1;
    }

    /**
     * Return the difference between two date strings including start date in days
     */
    public static long getDateDiffIncludingStartDate(Date startDate, Date endDate) {
        return getDateDiff(startDate, endDate) + 1;
    }

    /**
     * Return the difference between two calendars in months
     */
    public static int getDateDiffInMonths(Calendar startCalendar, Calendar endCalendar) {
        int diffMonth = 0;
        Calendar dayOfMonthEndCalendar = endCalendar;
        dayOfMonthEndCalendar.add(Calendar.DAY_OF_YEAR, 1);
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

        if (endCalendar.get(Calendar.DAY_OF_MONTH) >= startCalendar.get(Calendar.DAY_OF_MONTH)) {
            diffMonth = (diffYear * MONTHS_IN_YEAR) + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        } else if (dayOfMonthEndCalendar.get(Calendar.DAY_OF_MONTH) == startCalendar.get(Calendar.DAY_OF_MONTH)) {
            diffMonth = (diffYear * MONTHS_IN_YEAR) + dayOfMonthEndCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        } else {
            diffMonth = (diffYear * MONTHS_IN_YEAR) + (endCalendar.get(Calendar.MONTH) - 1) - startCalendar.get(Calendar.MONTH);
        }
        return diffMonth;
    }

    /**
     * Return the difference between two dates in months
     */
    public static int getDateDiffInMonths(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        return getDateDiffInMonths(startCalendar, endCalendar);
    }

    /**
     * Return the difference between two date strings in months
     */
    public static int getDateDiffInMonths(String startDateStr, String endDateStr) {
        return getDateDiffInMonths(parse(startDateStr), parse(endDateStr));
    }

    /**
     * Returns next coming sunday
     */
    public static Calendar getNextSunday(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int diff = Calendar.SUNDAY - dayOfWeek;
        if (diff < 0) {
            diff += DAYS_IN_WEEK;
        }
        cal.add(Calendar.DAY_OF_YEAR, diff);
        return cal;
    }

    public static int getDaysDiffExcludingMonths(Calendar startCalendar, Calendar endCalendar) {
        startCalendar.add(Calendar.MONTH, getDateDiffInMonths(startCalendar, endCalendar));
        return (int) getDateDiff(startCalendar, endCalendar);
    }

    public static int getDaysDiffExcludingMonths(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        return getDaysDiffExcludingMonths(startCalendar, endCalendar);
    }

    public static int getDaysDiffExcludingMonths(String startDateStr, String endDateStr) {
        return getDaysDiffExcludingMonths(parse(startDateStr), parse(endDateStr));
    }

    public static String convertXMLGregorianToString(XMLGregorianCalendar xmlDate) {
        GregorianCalendar gc = xmlDate.toGregorianCalendar();
        return new SimpleDateFormat(DateConstant.JOURNEY_HISTORY_DATE_AND_TIME_PATTERN).format(gc.getTime());
    }

    public static XMLGregorianCalendar convertStringToXMLGregorian(String stringDate) throws DatatypeConfigurationException {
        Date sampleDate = parse(stringDate);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(sampleDate);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
    }

    public static XMLGregorianCalendar convertDateToXMLGregorian(Date sourceDate) {
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(sourceDate);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    public static Date convertXMLGregorianToDate(XMLGregorianCalendar xmlDate) {
        return xmlDate != null ? xmlDate.toGregorianCalendar().getTime() : null;
    }

    /**
     * Calculate the difference between two dates in days.
     */
    public static int getDaysBetween(final Date startDate, final Date endDate) {
        return Math.abs((int) ((startDate.getTime() - endDate.getTime()) / (MILLISECONDS_IN_A_SECOND * MINUTES_IN_HOUR * SECONDS_IN_A_MINUTE * HOURS_IN_DAY)));
    }

    public static String formatDateAsISO8601(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_8601_DATE_PATTERN);
        return format.format(date);
    }

    public static Date truncateAtDay(Date value) {
        return (value != null) ? DateUtils.truncate(value, Calendar.DATE) : null;
    }
    
    public static boolean isCurrentYearALeapYearForCurrentDate(Date givenDate) {
    	Calendar currentCalender = Calendar.getInstance();
    	currentCalender.setTime(givenDate);
    	return new DateTime(givenDate).withYear(currentCalender.get(Calendar.YEAR)).year().isLeap();
    }
    
    public static Date addMonthsToDateTime(Date startDate, int duration){
    	if(isStartAndEndMonthCheckForAvoidingOffsetDayAddition(startDate)) {
            return new DateTime(startDate).plus(Period.months(duration)).toDate();
	    }else{
	    	return new DateTime(startDate).plus(Period.months(duration)).minusDays(OFFSETDAY).toDate();
	   	}
    }    
    
    public static boolean isStartAndEndMonthCheckForAvoidingOffsetDayAddition(Date startDate) {
    	DateTime dateTime = new DateTime(startDate);
    	int currentDayOfMonthSelected = dateTime.getDayOfMonth();
    	boolean isStartAndEndMonthCheckForAvoidingOffsetDayAdditionCheck = false;
    	String currentMonth = dateTime.monthOfYear().getAsText().toUpperCase();
    	Calendar currentCalender = Calendar.getInstance();
    	currentCalender.setTime(startDate);
    	int lastDayOfCurrentMonth = currentCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
    	if(MonthEnum.determineMonthWithIndex(JANUARY.getIndex()).toString().equalsIgnoreCase(currentMonth)){
    		return isMonthContainsSpecifiedMonthEndDays(startDate) ? true : false;  
    	} else { 
    		List<String> evenMonthsToOmitOffsetDaysAdd  = new ArrayList<String>();
    		evenMonthsToOmitOffsetDaysAdd.add(MonthEnum.determineMonthWithIndex(MARCH.getIndex()).toString());
    		evenMonthsToOmitOffsetDaysAdd.add(MonthEnum.determineMonthWithIndex(MAY.getIndex()).toString());
    		evenMonthsToOmitOffsetDaysAdd.add(MonthEnum.determineMonthWithIndex(AUGUST.getIndex()).toString());
    		evenMonthsToOmitOffsetDaysAdd.add(MonthEnum.determineMonthWithIndex(OCTOBER.getIndex()).toString());
    		boolean evenMonthsToOmitOffsetDaysAddCheck= evenMonthsToOmitOffsetDaysAdd.contains(currentMonth) ? true : false;
    		if(evenMonthsToOmitOffsetDaysAddCheck && (lastDayOfCurrentMonth==currentDayOfMonthSelected)){
    			isStartAndEndMonthCheckForAvoidingOffsetDayAdditionCheck = true;
    		}
    		return isStartAndEndMonthCheckForAvoidingOffsetDayAdditionCheck;
    	}
	}
    
    public static String parseCalenderToStringDateObj(String dateToBeconverted) {
    	try {
    		DateFormat timestampDtFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    		Date date = timestampDtFormatter.parse(dateToBeconverted);
    		DateFormat stringDtFromatter  = new SimpleDateFormat("dd/MM/yyyy");
    		dateToBeconverted = stringDtFromatter.format(date);
    		return dateToBeconverted;
		} catch (ParseException e) {
			return null;
		}
    }
    
    public static boolean isMonthContainsSpecifiedMonthEndDays(Date startDate){
    	DateTime dateTime = new DateTime(startDate);
    	int currentDayOfMonthSelected = dateTime.getDayOfMonth();
    	Calendar currentCalender = Calendar.getInstance();
    	currentCalender.setTime(startDate);
    	int lastDayOfCurrentMonth = currentCalender.getActualMaximum(Calendar.DAY_OF_MONTH);

    	if(isCurrentYearALeapYearForCurrentDate(startDate)){
	    	List<Integer> dayToBeOmittedForOffetDayAdditionForLeapYear = new ArrayList<Integer>();
	    	dayToBeOmittedForOffetDayAdditionForLeapYear.add(lastDayOfCurrentMonth);
	    	dayToBeOmittedForOffetDayAdditionForLeapYear.add(lastDayOfCurrentMonth-OFFSETDAY);
    		return dayToBeOmittedForOffetDayAdditionForLeapYear.contains(currentDayOfMonthSelected) ? true : false;   
    	} else {
    		List<Integer> dayToBeOmittedForOffetDayAdditionForNonLeapYear = new ArrayList<Integer>();
    		dayToBeOmittedForOffetDayAdditionForNonLeapYear.add(lastDayOfCurrentMonth);
    		dayToBeOmittedForOffetDayAdditionForNonLeapYear.add(lastDayOfCurrentMonth-OFFSETDAY);
    		dayToBeOmittedForOffetDayAdditionForNonLeapYear.add(lastDayOfCurrentMonth-DOUBLE_OFFSETDAY);
    		return dayToBeOmittedForOffetDayAdditionForNonLeapYear.contains(currentDayOfMonthSelected) ? true : false;   
    	}
    }
    
    public static Date formatStringAsDate(String date){
        try{
            return StringUtil.isNotEmpty(date) ? createShortDateFormatter().parse(date) : null;
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static SimpleDateFormat createShortDateFormatter() {
        return new SimpleDateFormat(SHORT_DATE_PATTERN);
    }
    
    public static int getDifferenceInMonths(Date startDate, Date endDate) {
    	Calendar calendarStartDate = Calendar.getInstance();
    	Calendar calendarEndDate = Calendar.getInstance();
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
    	return getDifferenceInMonths(calendarStartDate, calendarEndDate);
    }
    
    public static int getDifferenceInMonths(Calendar startDate, Calendar endDate){
    	endDate.add(Calendar.DATE, ONE_DAY);
    	Period period = new Period(startDate.getTimeInMillis(), endDate.getTimeInMillis(), PeriodType.yearMonthDay());
    		return period.getMonths();
    }
    
    public static int getDaysWithoutMonths(Date startDate, Date endDate) {
    	Calendar calendarStartDate = Calendar.getInstance();
    	Calendar calendarEndDate = Calendar.getInstance();
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		
		return getDaysWithoutMonths(calendarStartDate, calendarEndDate);
    }
    
    public static int getDaysWithoutMonths(Calendar startDate, Calendar endDate) {
    	endDate.add(Calendar.DATE, ONE_DAY);
    	Period period = new Period(startDate.getTimeInMillis(), endDate.getTimeInMillis(), PeriodType.yearMonthDay());
		return period.getDays();
    }

    private DateUtil() {
    }
}
