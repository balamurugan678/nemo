package com.novacroft.nemo.test_support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.LocalDate;

/**
 * Utilities for using dates in tests
 */
public final class DateTestUtil {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String AUG_14_AT_10_45 = "2013-08-14 10:45:00 BST 2014";
    public static String AUG_14_2014_AT_10_45 = "2014-08-14 10:45:00 BST 2014";
    public static String AUG_19_AT_10_45 = "2013-08-19 10:45:00 BST 2014";
    public static String AUG_19_AT_00_00 = "2013-08-19 00:00:00 BST 2014";
    public static String AUG_16_AT_00_00 = "2014-08-16 00:00:00 BST 2014";
    public static String AUG_02_AT_00_00 = "2014-08-02 00:00:00";
    public static String AUG_19 = "2013-08-19 12:00:00";
    public static String AUG_20 = "2013-08-20 12:00:00";
    public static String AUG_21 = "2013-08-21 12:00:00";
    public static String AUG_22 = "2013-08-22 12:00:00";
    public static String AUG_22_AT_0000 = "2013-08-22 00:00:00";
    public static String AUG_22_AT_1723 = "2013-08-22 17:23:00";
    public static String AUG_22_AT_1804 = "2013-08-22 18:04:00";
    public static String AUG_23_AT_0000 = "2013-08-23 00:00:00";
    public static String AUG_23_AT_0429 = "2013-08-23 04:29:00";
    public static String AUG_23_AT_0430 = "2013-08-23 04:30:00";
    public static String AUG_23_AT_0431 = "2013-08-23 04:31:00";
    public static String AUG_23_AT_1810 = "2013-08-23 18:10:00";
    public static final String AUG_09_AT_0000 = "2014-08-09 00:00:00.0";
    public static final String AUG_12_AT_0000 = "2014-08-12 00:00:00.0";
    public static final String AUG_14_AT_0000 = "2014-08-14 00:00:00.0";

    public static String JAN_1 = "2014-01-01 00:00:00.0";
    public static String JAN_31 = "2014-01-31 23:59:59.0";
    public static String MON_10_FEB = "2014-02-10 00:00:00.0";
    public static String SUN_16_FEB = "2014-02-16 23:59:59.0";
    public static String MON_17_FEB_0000 = "2014-02-17 00:00:00.0";
    public static String TUE_18_FEB = "2014-02-18 12:00:00";
    public static String SUN_23_FEB = "2014-02-23 12:00:00";
    public static String SUN_23_FEB_2359 = "2014-02-23 23:59:59.0";

    public static String MAR_28 = "2014-03-28 00:00:00";
    public static String APR_03 = "2014-04-03 00:00:00";

    public static String MAR_28_AT_0905 = "2014-03-28 09:05:00";
    public static String MAR_30_AT_1420 = "2014-03-30 14:20:00";
    public static String MAR_31_AT_1110 = "2014-03-31 11:10:00";
    public static String MAR_31_AT_1520 = "2014-03-31 15:20:00";
    public static String APR_03_AT_1959 = "2014-04-03 19:59:00";

    public static String WEB_SERVICE_REQUEST_START_DATE = "20140315";
    public static String WEB_SERVICE_REQUEST_END_DATE = "20140801";
    public static String WEB_SERVICE_REQUEST_INVALID_LENGTH_DATE = "2014080";

    public static int MILLISECONDS_IN_SECOND = 1000;
    public static int SECONDS_IN_MINUTE = 60;
    public static int FIFTEEN_MINUTES_IN_MILLISECONDS = 15 * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND;
    public static int FIVE_DAYS = 5;

    public static String INVALID_DATE_1 = "30/02/2014";
    public static String INVALID_DATE_2 = "30/13/2014";

    public static String REFUND_DATE_1 = "10/05/2014";
    public static String REFUND_DATE_2 = "08/05/2014";

    public static String JUN_10 = "2014-06-10 12:00:00";
    public static String AUG_10 = "2014-08-10 12:00:00";

    public static final String DATE_TODAY = "24/03/2014";
    public static final String EXPIRY_DATE = "30/03/2014";
    public static final String START_DATE = "29/03/2014";
    public static final String END_DATE = "28/03/2014";
    public static final String ANNUAL_START_DATE = "29/03/2013";
    public static final String ANNUAL_END_DATE = "28/03/2014";

    public static final String DATE_31_07_2014 = "31/07/2014";
    public static final String DATE_28_07_2014 = "28/07/2014";
    public static final String DATE_20_07_2014 = "20/07/2014";
    public static final String DATE_30_07_2014 = "30/07/2014";
    public static final String DATE_21_12_2013 = "21/12/2013";
    public static final String DATE_21_12_2014 = "21/12/2014";
    public static final String DATE_20_03_2014 = "20/03/2014";
    public static final String DATE_09_09_2014 = "09/09/2014";
    public static final String DATE_10_08_2014 = "10/08/2014";
    public static final String DATE_10_09_2014 = "10/09/2014";

    public static String MAY_30 = "30/05/2014";
    public static String MAY_31 = "31/05/2014";
    public static String JUN_29 = "29/06/2014";
    public static String JUN_30 = "30/06/2014";
    public static String NOV_13 = "13/11/2014";
    public static String NOV_15_2014 = "15/11/2014";
    public static String AUGUST_1ST = "2014-08-01 00:00:00";

    public static String JAN_LEAP_31 = "31/01/2004";
    public static String JAN_NON_LEAP_31 = "31/01/2014";
    public static String FEB_LEAPYEAR_LASTDAY = "29/02/2004";
    public static String FEB_NON_LEAPYEAR_LASTDAY = "28/02/2014";

    public static String AUG_01 = "Fri Aug 01 00:00:00 BST 2014";
    public static String AUG_05 = "Tue Aug 05 00:00:00 BST 2014";
    // public static final String AUG_02 = "02/08/2014";
    public static final String AUG_08 = "08/08/2014";
    public static final String FUTURE_DATE_10_10_9999 = "10/10/9999";
    public static final String EXPIRED_DATE_10_10_2014 = "10/10/2014";

    public static final String NOV_21 = "21/11/2014";

    public static String DATE_FORMAT_STR = "dd/MM/yyyy";

    public static final String AUG_15 = "15/08/2014";
    public static final String AUG_16 = "16/08/2014";
    public static final String MARCH_10 = "10/03/2014";
    public static final String SEPTEMBER_09 = "09/09/2014";
    public static final String APRIL_02 = "02/04/2014";
    public static final String SEPTEMBER_15 = "15/09/2014";
    public static final String OCTOBER_24 = "24/10/2014";
    public static final String AUGUST_07 = "07/08/2014";
    public static String AUG_15_WITH_TIMESTAMP_FORMAT = "Fri Aug 15 00:00:00 BST 2014";
    public static String AUG_16_WITH_TIMESTAMP_FORMAT = "Sat Aug 16 00:00:00 BST 2014";
    public static String END_DATE_OF_MONTH_MARCH = "31/03/2014";
    public static String END_DATE_OF_MONTH_APRIL = "30/04/2014";
    public static String END_DATE_OF_MONTH_MAY = "31/05/2014";
    public static String END_DATE_BEFORE_A_DAY_OF_MONTH_MAY = "29/05/2014";
    public static String END_DATE_OF_MONTH_JUNE = "30/06/2014";
    public static String END_DATE_OF_MONTH_JULY = "31/07/2014";
    public static String END_DATE_BEFORE_A_DAY_OF_MONTH_JULY = "29/07/2014";
    public static String END_DATE_OF_MONTH_AUG = "31/08/2014";
    public static String END_DATE_BEFORE_A_DAY_OF_MONTH_AUG = "30/08/2014";
    public static String END_DATE_OF_MONTH_SEP = "30/09/2014";
    public static String END_DATE_OF_MONTH_OCT = "31/10/2014";
    public static String END_DATE_BEFORE_A_DAY_OF_MONTH_OCT = "29/10/2014";
    public static String END_DATE_OF_MONTH_NOV = "30/11/2014";
    public static String END_DATE_OF_MONTH_DEC = "31/12/2014";
    public static String END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC = "29/12/2014";
    public static String END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC_2015 = "28/12/2015";
    public static String END_DATE_OF_MONTH_JAN = "30/01/2015";

    public static String START_DATE_OF_MONTH_JAN = "01/01/2014";

    public static String REFUND_DATE = "30/10/2014";
    public static String TICKET_START_DATE_SAME_DATE_OF_REFUND_DATE = "30/10/2014";
    public static String TICKET_START_DATE_AFTER_DATE_OF_REFUND_DATE = "31/10/2014";
    public static String TICKET_START_DATE_BEFORE_DATE_OF_REFUND_DATE = "29/10/2014";
    public static String TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_30_11_2014 = "30/11/2014";
    public static String TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_29_NOV_2014 = "29/11/2014";

    public static String FAILED_AUTOTOPUP_RESETTLEMENT_END_DATE = "29/11/2014";

    public static Date getAug19() {
        return getDate(DATE_FORMAT, AUG_19);
    }

    public static Date getDate21122014() {
        return getDate(DATE_FORMAT_STR, DATE_21_12_2014);
    }

    public static Date getDate21122013() {
        return getDate(DATE_FORMAT_STR, DATE_21_12_2013);
    }

    public static Date getDate20032014() {
        return getDate(DATE_FORMAT_STR, DATE_20_03_2014);
    }

    public static Date getAug01() {
        return getDate(DATE_FORMAT, AUG_01);
    }

    public static Date getAugust_1st() {
        return getDate(DATE_FORMAT, AUGUST_1ST);
    }

    public static Object getAug02() {
        return getDate(DATE_FORMAT, AUG_02_AT_00_00);
    }

    public static Date getAUG_14_AT_10_45() {
        return getDate(DATE_FORMAT, AUG_14_AT_10_45);
    }

    public static Date getAUG_14_2014_AT_10_45() {
        return getDate(DATE_FORMAT, AUG_14_2014_AT_10_45);
    }

    public static Date getAug09_AT_0000() {
        return getDate(DATE_FORMAT, AUG_09_AT_0000);
    }

    public static Date getAug16_AT_0000() {
        return getDate(DATE_FORMAT, AUG_16_AT_00_00);
    }

    public static Date getAug19At0000() {
        return getDate(DATE_FORMAT, AUG_19_AT_00_00);
    }

    public static Date getAug20() {
        return getDate(DATE_FORMAT, AUG_20);
    }

    public static Date getZuluAug20() {
        return getZuluDate(DATE_FORMAT, AUG_20);
    }

    public static Date getAug21() {
        return getDate(DATE_FORMAT, AUG_21);
    }

    public static Date getAug22() {
        return getDate(DATE_FORMAT, AUG_22);
    }

    public static Date getAug22At0000() {
        return getDate(DATE_FORMAT, AUG_22_AT_0000);
    }

    public static Date getAug22At1723() {
        return getDate(DATE_FORMAT, AUG_22_AT_1723);
    }

    public static Date getAug22At1804() {
        return getDate(DATE_FORMAT, AUG_22_AT_1804);
    }

    public static Date getAug23At0000() {
        return getDate(DATE_FORMAT, AUG_23_AT_0000);
    }

    public static Date getAug23At0429() {
        return getDate(DATE_FORMAT, AUG_23_AT_0429);
    }

    public static Date getAug23At1810() {
        return getDate(DATE_FORMAT, AUG_23_AT_1810);
    }

    public static Date getAug23At0430() {
        return getDate(DATE_FORMAT, AUG_23_AT_0430);
    }

    public static Date getAug23At0431() {
        return getDate(DATE_FORMAT, AUG_23_AT_0431);
    }

    public static Date get1Jan() {
        return getDate(DATE_FORMAT, JAN_1);
    }

    public static Date get31Jan() {
        return getDate(DATE_FORMAT, JAN_31);
    }

    public static Date getMon10Feb() {
        return getDate(DATE_FORMAT, MON_10_FEB);
    }

    public static Date getSun16Feb() {
        return getDate(DATE_FORMAT, SUN_16_FEB);
    }

    public static Date getMon17FebAt0000() {
        return getDate(DATE_FORMAT, MON_17_FEB_0000);
    }

    public static Date getTue18Feb() {
        return getDate(DATE_FORMAT, TUE_18_FEB);
    }

    public static Date getSun23Feb() {
        return getDate(DATE_FORMAT, SUN_23_FEB);
    }

    public static Date getSun23FebAt2359() {
        return getDate(DATE_FORMAT, SUN_23_FEB_2359);
    }

    public static Date getMar28() {
        return getDate(DATE_FORMAT, MAR_28);
    }

    public static Date getApr03() {
        return getDate(DATE_FORMAT, APR_03);
    }

    public static Date getMar28At0905() {
        return getDate(DATE_FORMAT, MAR_28_AT_0905);
    }

    public static Date getMar30At1420() {
        return getDate(DATE_FORMAT, MAR_30_AT_1420);
    }

    public static Date getMar31At1110() {
        return getDate(DATE_FORMAT, MAR_31_AT_1110);
    }

    public static Date getMar31At1520() {
        return getDate(DATE_FORMAT, MAR_31_AT_1520);
    }

    public static Date getApr03At1959() {
        return getDate(DATE_FORMAT, APR_03_AT_1959);
    }

    public static Date getJun10() {
        return getDate(DATE_FORMAT, JUN_10);
    }

    public static Date getAug10() {
        return getDate(DATE_FORMAT, AUG_10);
    }

    public static Date getFailedAutoTopUpResettlementEndDate() {
        return getDate(DATE_FORMAT_STR, FAILED_AUTOTOPUP_RESETTLEMENT_END_DATE);
    }

    public static Date getDate(String format, String dateAsString) {
        try {
            return new SimpleDateFormat(format).parse(dateAsString);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("Error: date parse failed; date [%s]; format [%s].", dateAsString, format), e);
        }
    }

    public static Date getZuluDate(String format, String dateAsString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("Error: date parse failed; date [%s]; format [%s].", dateAsString, format), e);
        }
    }

    public static Date getFifteenMinutesInFuture() {
        return new Date(System.currentTimeMillis() + FIFTEEN_MINUTES_IN_MILLISECONDS);
    }

    public static Date getFifteenMinutesInPast() {
        return new Date(System.currentTimeMillis() - FIFTEEN_MINUTES_IN_MILLISECONDS);
    }

    public static String getDateNMonthsFromNow(int months) {
        LocalDate monthsFromNow = new LocalDate().plusMonths(months);
        return String.format("%02d-%04d", monthsFromNow.getMonthOfYear(), monthsFromNow.getYear());
    }

    public static String getDate(String format, Date date) {

        return new SimpleDateFormat(format).format(date);

    }

    public static Date getInvalidDateFeb30() {
        return getDate(DATE_FORMAT_STR, INVALID_DATE_1);
    }
}
