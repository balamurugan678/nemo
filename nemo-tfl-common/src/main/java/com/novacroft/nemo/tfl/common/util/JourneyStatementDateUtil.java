package com.novacroft.nemo.tfl.common.util;

import java.util.Calendar;
import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;

/**
 * Journey history statement date utilities
 */
public final class JourneyStatementDateUtil {
    public static final int DAYS_IN_A_WEEK = 7;
    private static final int ELEVEN_PM = 23;

    /**
     * Week starts on Monday and ends on Sunday.  If <code>asOn</code> is Sunday then go back to week ending previous Sunday.
     */
    public static Date getStartOfLastWeek(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getEndOfLastWeek(asOn));
        while (isNotMonday(calendar)) {
            goBackOneDay(calendar);
        }
        setTimeToStartOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * Week starts on Monday and ends on Sunday.  
     */
    public static Date getStartOfWeek(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(asOn);
        while (isNotMonday(calendar)) {
            goBackOneDay(calendar);
        }
        setTimeToStartOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * Week starts on Monday and ends on Sunday. 
     * @param weeksFromNow - current week=0, previous week=-1, next week=1, etc...   
     * @return date Start Of Week Date, with time portion set to minimum ceiling for day.
     */
    public static Date getStartOfWeek(int weeksFromNow) {
    	Date startOfWeek = getStartOfWeek(new Date());
    	startOfWeek = DateUtil.addDaysToDate(startOfWeek,  weeksFromNow * DAYS_IN_A_WEEK);
        return startOfWeek;
    }

    /**
     * Week starts on Monday and ends on Sunday. 
     * @param weeksFromNow - current week=0, previous week=-1, next week=1, etc...   
     * @return date End Of Week Date, with time portion set to maximum ceiling for day.
     */
    public static Date getEndOfWeek(int weeksFromNow) {
    	Date endOfWeek = getEndOfWeek(new Date());
    	endOfWeek = DateUtil.addDaysToDate(endOfWeek,  weeksFromNow * DAYS_IN_A_WEEK);
        return endOfWeek;
    }

    /**
     * Week starts on Monday and ends on Sunday.  If <code>asOn</code> is Sunday then go back to week ending previous Sunday.
     */
    public static Date getEndOfLastWeek(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(asOn);
        if (isSunday(calendar)) {
            goBackOneDay(calendar);
        }
        while (isNotSunday(calendar)) {
            goBackOneDay(calendar);
        }
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * Week starts on Monday and ends on Sunday. 
     */
    public static Date getEndOfWeek(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(asOn);
        while (isNotSunday(calendar)) {
        	goForwardOneDay(calendar);
        }
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getStartOfLastMonth(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(asOn);
        goBackOneMonth(calendar);
        setDayToStartOfMonth(calendar);
        setTimeToStartOfDay(calendar);
        return calendar.getTime();
    }
    
    public static Boolean isMonday(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(asOn);
        return isMonday(calendar);
    }

    public static Date getEndOfLastMonth(Date asOn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(asOn);
        goBackOneMonth(calendar);
        setDayToEndOfMonth(calendar);
        setTimeToEndOfDay(calendar);
        return calendar.getTime();
    }

    protected static boolean isSunday(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    protected static boolean isNotSunday(Calendar calendar) {
        return !isSunday(calendar);
    }

    protected static boolean isMonday(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    protected static boolean isNotMonday(Calendar calendar) {
        return !isMonday(calendar);
    }

    protected static void goBackOneDay(Calendar calendar) {
        calendar.add(Calendar.DAY_OF_WEEK, -1);
    }

    protected static void goForwardOneDay(Calendar calendar) {
        calendar.add(Calendar.DAY_OF_WEEK, 1);
    }

    protected static void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, ELEVEN_PM);
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
    }

    protected static void setTimeToStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
    }

    protected static void goBackOneMonth(Calendar calendar) {
        calendar.add(Calendar.MONTH, -1);
    }

    protected static void setDayToStartOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    }

    protected static void setDayToEndOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    private JourneyStatementDateUtil() {
    }
}
