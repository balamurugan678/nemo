package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.constant.Durations.ANNUAL;
import static com.novacroft.nemo.common.constant.Durations.OTHER;
import static com.novacroft.nemo.common.constant.Durations.SEVEN_DAYS;
import static com.novacroft.nemo.common.constant.Durations.SIX_MONTH;
import static com.novacroft.nemo.common.constant.Durations.THREE_MONTH;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getEndDate;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getRenewEndDate;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;
import static com.novacroft.nemo.test_support.DateTestUtil.AUGUST_07;
import static com.novacroft.nemo.test_support.DateTestUtil.OCTOBER_24;
import static com.novacroft.nemo.test_support.DateTestUtil.SEPTEMBER_15;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.novacroft.nemo.common.constant.Durations;

/**
 * DateUtil unit tests
 */
public class TravelCardDurationUtilTest {

    protected static final String START_DATE_MONTH = "01/02/2014";
    protected static final String END_DATE_MONTH = "01/03/2014";
    protected static final String START_DATE_SEVEN_DAYS = "01/01/2014";
    protected static final String END_DATE_SEVEN_DAYS = "07/01/2014";
    protected static final String START_DATE_OTHER = "01/01/2014";
    protected static final String END_DATE_OTHER = "17/01/2014";
    protected static final String END_DATE_OTHER_MONTH = "02/02/2014";
    protected static final String START_DATE_THREE_MONTH = "01/01/2014";
    protected static final String END_DATE_THREE_MONTH = "31/03/2014";
    protected static final String START_DATE_SIX_MONTH = "01/01/2014";
    protected static final String END_DATE_SIX_MONTH = "30/06/2014";
    protected static final String START_DATE_ANNUAL = "01/01/2014";
    protected static final String END_DATE_ANNUAL = "31/12/2014";

    protected static final String START_DATE_MONTH_FOR_LEAPYEAR = "31/01/2016";
    protected static final String END_DATE_MONTH_FOR_LEAPYEAR = "29/02/2016";
    protected static final String MONTHLY_TC_START_DATE_FOR_CALENDERMONTH = "30/06/2014";
    protected static final String END_DATE_MONTH_FOR_ONEDAYBEFORE_OF_NEXTCALENDERMONTH = "30/07/2014";
    protected static final String END_DATE_MONTH_FOR_DAYBEFORE_OF_NEXTCALENDERMONTH = "29/07/2014";
    protected static final String START_DATE_MONTH_FOR_CALENDERMONTH = "31/05/2014";
    protected static final String END_DATE_MONTH_FOR_LAST_DAYOF_NEXT_CALENDERMONTH = "30/06/2014";
    protected static final String FEB_MONTH_END_DATE_FOR_NON_LEAPYEAR = "28/02/2014";

    protected static final String RENEW_END_DATE_MONTH = "02/04/2014";
    protected static final String RENEW_END_DATE_SEVEN_DAYS = "14/01/2014";
    protected static final String RENEW_END_DATE_OTHER = "03/02/2014";
    protected static final String RENEW_END_DATE_OTHER_MONTH = "04/03/2014";
    protected static final String RENEW_END_DATE_THREE_MONTH = "30/06/2014";
    protected static final String RENEW_END_DATE_SIX_MONTH = "31/12/2014";
    protected static final String RENEW_END_DATE_ANNUAL = "31/12/2015";

    @Test
    public void getDurationTypeFromStartAndEndDateShouldReturnDurationTypeMonth() {
        assertEquals(OTHER.getDurationType(), getTravelCardDurationFromStartAndEndDate(START_DATE_MONTH, END_DATE_MONTH));
    }

    @Test
    public void getDurationTypeFromStartAndEndDateShouldReturnDurationTypeSevenDays() {
        assertEquals(SEVEN_DAYS.getDurationType(), getTravelCardDurationFromStartAndEndDate(START_DATE_SEVEN_DAYS, END_DATE_SEVEN_DAYS));
    }

    @Test
    public void getDurationTypeFromStartAndEndDateShouldReturnDurationTypeThreeMonth() {
        assertEquals(THREE_MONTH.getDurationType(), getTravelCardDurationFromStartAndEndDate(START_DATE_THREE_MONTH, END_DATE_THREE_MONTH));
    }

    @Test
    public void getDurationTypeFromStartAndEndDateShouldReturnDurationTypeSixMonth() {
        assertEquals(SIX_MONTH.getDurationType(), getTravelCardDurationFromStartAndEndDate(START_DATE_SIX_MONTH, END_DATE_SIX_MONTH));
    }

    @Test
    public void getDurationTypeFromStartAndEndDateShouldReturnDurationTypeAnnual() {
        assertEquals(ANNUAL.getDurationType(), getTravelCardDurationFromStartAndEndDate(START_DATE_ANNUAL, END_DATE_ANNUAL));
    }

    @Test
    public void getDurationTypeFromStartAndEndDateShouldReturnDurationTypeOtherDays() {
        assertEquals(OTHER.getDurationType(), getTravelCardDurationFromStartAndEndDate(START_DATE_OTHER, END_DATE_OTHER));
    }

    @Test
    public void getEnDateForSevenDayDurationTypeShouldAddSevenDays() {
        assertEquals(END_DATE_SEVEN_DAYS, formatDate(getEndDate(START_DATE_SEVEN_DAYS, Durations.SEVEN_DAYS.getDurationType())));
    }

    @Test
    public void getEnDateForMonthDurationTypeShouldAddOneMonth() {
        assertEquals(FEB_MONTH_END_DATE_FOR_NON_LEAPYEAR, formatDate(getEndDate(START_DATE_MONTH, Durations.MONTH.getDurationType())));
    }

    @Test
    public void getEnDateForThreeMonthDurationTypeShouldAddThreeMonths() {
        assertEquals(END_DATE_THREE_MONTH, formatDate(getEndDate(START_DATE_THREE_MONTH, Durations.THREE_MONTH.getDurationType())));
    }

    @Test
    public void getEnDateForSixMonthDurationTypeShouldAddSixMonths() {
        assertEquals(END_DATE_SIX_MONTH, formatDate(getEndDate(START_DATE_SIX_MONTH, Durations.SIX_MONTH.getDurationType())));
    }

    @Test
    public void getEnDateForAnnualDurationTypeShouldAddOneYear() {
        assertEquals(END_DATE_ANNUAL, formatDate(getEndDate(START_DATE_ANNUAL, Durations.ANNUAL.getDurationType())));
    }

    @Test
    public void getRenewEnDateForSevenDayDurationTypeShouldAddSevenDays() {
        assertEquals(RENEW_END_DATE_SEVEN_DAYS, formatDate(getRenewEndDate(START_DATE_SEVEN_DAYS, END_DATE_SEVEN_DAYS, 1)));
    }

    @Test
    public void getRenewEnDateForMonthDurationTypeShouldAddOneMonth() {
        assertEquals(RENEW_END_DATE_MONTH, formatDate(getRenewEndDate(START_DATE_MONTH, END_DATE_MONTH, 1)));
    }

    @Test
    public void getRenewEnDateForThreeMonthDurationTypeShouldAddThreeMonths() {
        assertEquals(RENEW_END_DATE_THREE_MONTH, formatDate(getRenewEndDate(START_DATE_THREE_MONTH, END_DATE_THREE_MONTH, 1)));
    }

    @Test
    public void getRenewEnDateForSixMonthDurationTypeShouldAddSixMonths() {
        assertEquals(RENEW_END_DATE_SIX_MONTH, formatDate(getRenewEndDate(START_DATE_SIX_MONTH, END_DATE_SIX_MONTH, 1)));
    }

    @Test
    public void getRenewEnDateForAnnualDurationTypeShouldAddOneYear() {
        assertEquals(RENEW_END_DATE_ANNUAL, formatDate(getRenewEndDate(START_DATE_ANNUAL, END_DATE_ANNUAL, 1)));
    }

    @Test
    public void getRenewEnDateForOtherDurationTypeLessThanOneMonthShouldAddOddTicketPeriodDuration() {
        assertEquals(RENEW_END_DATE_OTHER, formatDate(getRenewEndDate(START_DATE_OTHER, END_DATE_OTHER, 1)));
    }

    @Test
    public void getRenewEnDateForOtherDurationTypeGreaterThanOneMonthShouldAddOddTicketPeriodDuration() {
        assertEquals(RENEW_END_DATE_OTHER_MONTH, formatDate(getRenewEndDate(START_DATE_OTHER, END_DATE_OTHER_MONTH, 1)));
    }

    @Test
    public void getEnDateForMonthDurationTypeShouldAddOneMonthForLeapYearFebrauryMonth() {
        assertEquals(END_DATE_MONTH_FOR_LEAPYEAR, formatDate(getEndDate(START_DATE_MONTH_FOR_LEAPYEAR, Durations.MONTH.getDurationType())));
    }

    @Test
    public void getEnDateForMonthDurationTypeShouldAddOneMonthWithLastDayOfNextCalenderMonth() {
        assertEquals(END_DATE_MONTH_FOR_LAST_DAYOF_NEXT_CALENDERMONTH,
                        formatDate(getEndDate(START_DATE_MONTH_FOR_CALENDERMONTH, Durations.MONTH.getDurationType())));
    }

    @Test
    public void getEnDateForMonthDurationTypeShouldAddOneMonthWithOneDayBeforeTheSameDayOfNextCalanderMonth() {
        assertEquals(END_DATE_MONTH_FOR_DAYBEFORE_OF_NEXTCALENDERMONTH,
                        formatDate(getEndDate(MONTHLY_TC_START_DATE_FOR_CALENDERMONTH, Durations.MONTH.getDurationType())));
    }

    @Test
    public void getRenewEnDateForOtherDurationTypeGreaterThanOneMonthShouldAddOddTicketPeriodDurationTest() {
        assertEquals(OCTOBER_24, formatDate(getRenewEndDate(AUGUST_07, SEPTEMBER_15, 1)));
    }

    @Test
    public void getEnDateForMonthDurationTypeShould() {
        assertEquals(END_DATE_ANNUAL, formatDate(getEndDate(START_DATE_MONTH, END_DATE_ANNUAL, Durations.OTHER.getDurationType())));
    }

    @Test
    public void getEnDateForSevenDayDurationType() {
        assertEquals(END_DATE_SEVEN_DAYS, formatDate(getEndDate(START_DATE_SEVEN_DAYS, END_DATE_SEVEN_DAYS, Durations.SEVEN_DAYS.getDurationType())));
    }
    
    @Test
    public void getEndDateShouldReturnNull(){
        assertEquals(null, getEndDate(START_DATE_ANNUAL,  null, Durations.SEVEN_DAYS.getDurationType()));
    }
}
