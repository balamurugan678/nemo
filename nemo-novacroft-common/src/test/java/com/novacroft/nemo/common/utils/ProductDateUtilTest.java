package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.ProductDateUtil.getProductStartDates;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Product Date Util unit tests
 */
public class ProductDateUtilTest {
    protected static final int FIVE_DAYS_ADDED = 5;
    protected static final int DAYS_AFTER = 1;
    protected static final int DAYS_SUBTRACTED = -10;
    protected static final int EXPECTED_HOURS = 23;
    protected static final int EXPECTED_MINUTES = 26;
    protected static final int MAX_HOURS = 23;
    protected static final int MAX_MINUTES = 46;
    protected static final String START_DATE1 = "25/11/2014";
    protected static final String EXPECTED_START_DATE2 = "26/11/2014";
   
    @Test
    public void getProductStartDatesShouldReturnFiveDaysAfterToday() {
        GregorianCalendar cal = new GregorianCalendar(Locale.UK);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        List<Date> datesList = getProductStartDates(DAYS_AFTER, FIVE_DAYS_ADDED);
        assertEquals(5, datesList.size());
        assertEquals(formatDate(cal.getTime()), formatDate(datesList.get(0)));
        
    }
    
    @Test
    public void getProductStartDatesForSpecifiedStartDateShouldReturnFiveDays() {
     
        List<Date> datesList = getProductStartDates(START_DATE1, FIVE_DAYS_ADDED);
        assertEquals(5, datesList.size());
        assertEquals(parse(EXPECTED_START_DATE2), datesList.get(1));
        
    }
    
    @Test
    @Ignore //Test fails depending on what time of day it runs
    public void getProductStartDatesWithHoursAndMinutesShouldReturnFiveDaysAfterToday() {
        GregorianCalendar cal = new GregorianCalendar(Locale.UK);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        List<Date> datesList = getProductStartDates(DAYS_AFTER, FIVE_DAYS_ADDED, EXPECTED_HOURS, EXPECTED_MINUTES);
        assertEquals(5, datesList.size());
        assertEquals(formatDate(cal.getTime()), formatDate(datesList.get(0)));
    }
    
  }
