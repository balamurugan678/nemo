package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.utils.DateUtil.parse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public final class ProductDateUtil {
    
    private ProductDateUtil() {
    }

    /**
     * This method return list of online sales calendar
     *
     * @param startAfter   number of days after today
     * @param numberOfDays to display
     */
    public static List<Date> getProductStartDates(int startAfter, int numberOfDays) {
        if (startAfter != -1 && numberOfDays > 0) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            List<Date> startDates = new ArrayList<Date>();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, startAfter);
            for (int i = startAfter; i <= numberOfDays; i++) {
                startDates.add(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            return startDates;
        }
        return Collections.<Date>emptyList();
    }
    
    
    /**
     * This method return list of online sales calendar
     *
     * @param startAfter   number of days after today
     * @param numberOfDays to display
     * @param maxHourOfDayBeforeDenyingNextDayTravelStart : Hour Of the Day after user will not be able select start date of travel card as tomorrow
     * @param maxMinutesBeforeDenyingNextDayTravelStart : Minutes of the hour after user will not be able select start date of travel card as tomorrow  to display
     */
    public static List<Date> getProductStartDates(int startAfter, int numberOfDays, int maxHourOfDayBeforeDenyingNextDayTravelStart, int maxMinutesBeforeDenyingNextDayTravelStart) {
        if (startAfter != -1 && numberOfDays > 0) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            List<Date> startDates = new ArrayList<Date>();
            cal.setTime(new Date());
            if(cal.get(Calendar.HOUR_OF_DAY) > maxHourOfDayBeforeDenyingNextDayTravelStart){
                startAfter++; 
            } else if(cal.get(Calendar.HOUR_OF_DAY) == maxHourOfDayBeforeDenyingNextDayTravelStart &&  cal.get(Calendar.MINUTE)>= maxMinutesBeforeDenyingNextDayTravelStart){
                startAfter++; 
            }
            cal.add(Calendar.DAY_OF_YEAR, startAfter);
            for (int i = startAfter; i <= numberOfDays; i++) {
                startDates.add(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            return startDates;
        }
        return Collections.<Date>emptyList();
    }
    
    /**
     * This method return list of online sales calendar
     *
     * @param startDate of the product
     * @param numberOfDays to display
     */
    public static List<Date> getProductStartDates(String startDate, int numberOfDays) {
        if (startDate != null && numberOfDays > 0) {
            GregorianCalendar cal = new GregorianCalendar(Locale.UK);
            List<Date> startDates = new ArrayList<Date>();
            cal.setTime(parse(startDate));
            for (int i = 1; i <= numberOfDays; i++) {
                startDates.add(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            return startDates;
        }
        return Collections.<Date>emptyList();
    }
    
}
