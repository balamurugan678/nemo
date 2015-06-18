package com.novacroft.nemo.common.constant;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;

/**
 * Date constants
 */
public final class DateConstant {
    public static final String _01_01_2099 = "01/01/2099";
    private static final  String _01_01_2001 = "01/01/2001";
	public static final Integer MILLISECONDS_IN_A_SECOND = 1000;
    public static final Integer SECONDS_IN_A_MINUTE = 60;
    public static final Integer FIVE_MINUTES_IN_SECONDS = 5 * SECONDS_IN_A_MINUTE;
    public static final Integer HOURS_IN_DAY = 24;
    public static final Integer MONTHS_IN_YEAR = 12;
    public static final Integer MINUTES_IN_HOUR = 60;
    public static final Integer DAYS_IN_WEEK = 7;
    public static final Integer BUSINESS_DAY_CUT_OFF_HOURS = 23;
    public static final Integer BUSINESS_DAY_CUT_OFF_MINUTES = 30;
    public static final Integer BEFORE_MIDNIGHT_MINUTES = 59;
    public static final Integer ZERO_MINUTES = 00;
    
    public static final int MONTHS_LIMIT_FOR_ATU_PAYMENT = 2;
    public static final int MONTHS_LIMIT_FOR_REGULAR_PAYMENT = 0;

    public static final String SHORT_DATE_PATTERN = "dd/MM/yyyy";
    public static final String SHORT_DATE_PATTERN_JQUERY = "dd/mm/yy";
    public static final String SHORT_DATE_PATTERN_PARTIAL_YEAR = "dd/mm/yy";
    public static final String AGENTLOG_SEARCH_SHORT_DATE_PATTERN = "MM/dd/yyyy";
    public static final String PRE_PAID_TICKET_SHORT_DATE_PATTERN = "MM/dd/yyyy";
    public static final String DAY_WEEK_DATE_PATTERN = "EEEE, dd MMMM yyyy";
    public static final String DAYINMONTH_SHORTMONTH_YEAR = "dd MMMM yyyy";
    public static final String TIME_TO_MINUTE_PATTERN = "HH:mm";
    public static final String TIME_TO_SECOND_PATTERN = "HH:mm:ss";
    public static final String JOURNEY_HISTORY_DATE_AND_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String ISO_8601_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String CYBER_SOURCE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX";
    public static final String SHORT_DATE_AND_TIME_TO_SECOND_PATTERN = SHORT_DATE_PATTERN + " " + TIME_TO_SECOND_PATTERN;
    public static final String HTTP_HEAD_EXPIRE_DATE_FORMAT = "EEE, dd-MMM-yyyy hh:mm:ss z";

    public static final String UTC_TIME_ZONE_ID = "UTC";
    public static final Integer OFFSETDAY = 1;
    public static final Integer DOUBLE_OFFSETDAY = 2;
    public static final String JAN = "January";
    public static final Date  TEMPORAL_FURURE_END_DATE =  DateUtil.parse(_01_01_2099);
	public static final Date  TEMPORAL_PAST_START_DATE =  DateUtil.parse(_01_01_2001);

    private DateConstant() {
    }
}
