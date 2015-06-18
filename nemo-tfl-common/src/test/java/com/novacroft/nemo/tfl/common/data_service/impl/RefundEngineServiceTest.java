package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.DateTestUtil.DATE_FORMAT_STR;
import static com.novacroft.nemo.test_support.DateTestUtil.JUN_29;
import static com.novacroft.nemo.test_support.DateTestUtil.JUN_30;
import static com.novacroft.nemo.test_support.DateTestUtil.MAY_31;
import static com.novacroft.nemo.test_support.DateTestUtil.REFUND_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_29_NOV_2014;
import static com.novacroft.nemo.test_support.DateTestUtil.TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_30_11_2014;
import static com.novacroft.nemo.test_support.DateTestUtil.TICKET_START_DATE_AFTER_DATE_OF_REFUND_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.TICKET_START_DATE_BEFORE_DATE_OF_REFUND_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.TICKET_START_DATE_SAME_DATE_OF_REFUND_DATE;
import static com.novacroft.nemo.test_support.RefundTestUtil.PRODUCTID_TRADEDTICKET;
import static com.novacroft.nemo.test_support.RefundTestUtil.PRODUCT_ID;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTO;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOAnnualZone1toZone3;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOAnnualZone1toZone3_2008;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOAnnualZone1toZone4;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOAnnualZone1toZone5;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOAnnualZone1toZone6;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOAnnualZone2toZone5;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOForSixMonthsZone1to3;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOForTradedTicket;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOMonthlyBusPassAllLondon;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOMonthlyZone3toZone5;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOOddPeriodZone1toZone2;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOOddPeriodZone1toZone3_2;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOOddPeriodZone2toZone5;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOOddPeriodZone2toZone5_2;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOSixMonthZone1toZone2;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOThreeMonthZone1toZone2;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOWeeklyZone1toZone2;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOWeeklyZone2toZone3;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOWeeklyZone2toZone5;
import static com.novacroft.nemo.test_support.RefundTestUtil.getRefundCardProductDTOWeeklyZone3toZone5;
import static com.novacroft.nemo.test_support.RefundTestUtil.weeklyProductID;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.ORDINARY;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.PRO_RATA;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.TRADE_DOWN;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.TRADE_UP;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ANNUAL;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.SEVENDAY;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.SIXMONTH;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.THREEMONTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.constant.RefundConstants.REFUND_REASON_KEY;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

public class RefundEngineServiceTest {

    private RefundEngineServiceImpl refundEngine;
    private Long expectedResponse;
    private DateTime ticketStartDate;
    private DateTime ticketEndDate;
    private DateTime tradeUpDate;
    private Long productId;

    private DateTime refundDate;
    private DateTimeFormatter formatter;
    private ProductDataService mockProductDataService;
    private DateTime calculateMonthUsage;

    protected final String LESS_THAN_A_MONTH = "Less than a month";
    protected final String PRO_RATA_REFUND = "Pro rata refund";
    protected final String ORDINARY_BASIS_REFUND = "Ordinary basis refund";
    protected final String ODD_PERIOD_REFUND = "Odd period refund";
    protected final String GREATER_THAN_A_MONTH = "Greater than a month";

    protected static final Date START_DATE_OF_MONTH_FOR_MONTHLY_CARD_TYPE = DateTestUtil.getDate(DATE_FORMAT_STR, MAY_31);
    protected static final Date END_DATE_OF_MONTH_FOR_MONTHLY_CARD_TYPE = DateTestUtil.getDate(DATE_FORMAT_STR, JUN_30);
    protected static final Date START_DATE_OF_MONTH_FOR_NON_MONTHLY_CARD_TYPE = DateTestUtil.getDate(DATE_FORMAT_STR, MAY_31);
    protected static final Date END_DATE_OF_MONTH_FOR_NON_MONTHLY_CARD_TYPE = DateTestUtil.getDate(DATE_FORMAT_STR, JUN_29);
    protected static final String MONTHLY_TRAVEL_CARD = "1Month";
    protected static final String OTHER_TRAVEL_CARD = "Other";

    protected final String EXPECTED_RESULT_1 = "3 Weeks and 3 Days";
    protected final String EXPECTED_RESULT_2 = "1 Month and 24 Days";
    protected final String EXPECTED_RESULT_3 = "2 Months and 24 Days";
    protected final String EXPECTED_RESULT_4 = "3 Months and 12 Days";
    protected final String EXPECTED_RESULT_5 = "2 Weeks and 4 Days";
    protected final String EXPECTED_RESULT_6 = "3 Days";
    protected final String EXPECTED_RESULT_7 = "1 Day";
    
    @Before
    public void setUp() {
        refundEngine = new RefundEngineServiceImpl();
        formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        mockProductDataService = mock(ProductDataService.class);
        refundEngine.productDataService = mockProductDataService;
    }

    @Test
    public void testOrdinaryRefundWhenLessThanAMonthUsageAsPerExample1() {
        // Customer ticket = Monthly Travelcard Zones 3 to 5 (£96.80)
        // From 25th Oct 2013 to 24th Nov 2013
        // Refund requested on 12th Nov 2013

        expectedResponse = 2120L;
        ticketStartDate = formatter.parseDateTime("25/10/2013");
        ticketEndDate = formatter.parseDateTime("24/11/2013");
        productId = 55L;
        refundDate = formatter.parseDateTime("12/11/2013");

        when(mockProductDataService.findById(productId, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOMonthlyZone3toZone5());
        when(mockProductDataService.findById(weeklyProductID, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOWeeklyZone3toZone5());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone3toZone5());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, ORDINARY);

        assertEquals(Integer.valueOf(0), refund.getRefundableDays());
        assertEquals(Integer.valueOf(3), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());

    }

    @Test
    public void testOrdinaryRefundWhenLessThanAMonthUsageAsPerExample2() {
        // Customer ticket = Monthly Travelcard Zones 3 to 5 (£96.80) (productId #55)
        // From 25thOct 2013 to 24th Nov 2013
        // Refund requested on 9th Nov 2013
        expectedResponse = 3630L;
        ticketStartDate = formatter.parseDateTime("25/10/2013");
        ticketEndDate = formatter.parseDateTime("24/11/2013");
        productId = 55L;
        refundDate = formatter.parseDateTime("09/11/2013");

        when(mockProductDataService.findById(productId, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOMonthlyZone3toZone5());
        when(mockProductDataService.findById(weeklyProductID, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOWeeklyZone3toZone5());
        when(
                        mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                                        any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone3toZone5());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, ORDINARY);

        assertEquals(Integer.valueOf(2), refund.getRefundableDays());
        assertEquals(Integer.valueOf(2), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testOrdinaryRefundWhenMoreThanAMonthUsageAsPerExample3() {
        // Customer ticket = Annual Travelcard Zones 2 to 5 (£1208.00)
        // From 29th May 2013 to 28th May 2014.
        // Refund requested on 12th Nov 2013..

        expectedResponse = 57010L;
        ticketStartDate = formatter.parseDateTime("29/05/2013");
        ticketEndDate = formatter.parseDateTime("28/05/2014");
        productId = 134L;
        refundDate = formatter.parseDateTime("12/11/2013");

        when(mockProductDataService.findById(productId, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        when(mockProductDataService.findById(weeklyProductID, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOWeeklyZone2toZone5());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone2toZone5());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, ORDINARY);

        assertEquals(Integer.valueOf(15), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(5), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testOrdinaryRefundWhenMoreThanAMonthUsageAsPerExample4() {
        // Customer ticket = Annual Travelcard Zones 2 to 5 (£1208.00)
        // From 29th May 2013 to 28th May 2014.
        // Refund requested on 12th Nov 2013..

        expectedResponse = 108040L;
        ticketStartDate = formatter.parseDateTime("29/05/2013");
        ticketEndDate = formatter.parseDateTime("28/05/2014");
        productId = 134L;
        refundDate = formatter.parseDateTime("01/07/2013");

        when(mockProductDataService.findById(productId, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        when(mockProductDataService.findById(weeklyProductID, ticketStartDate.toDate())).thenReturn(getRefundCardProductDTOWeeklyZone2toZone5());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone2toZone5());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, ORDINARY);

        assertEquals(Integer.valueOf(3), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(1), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testOddPeriodRefundWhenMoreThanAMonthUsageAsPerExample5() {

        expectedResponse = 91860L;
        ticketStartDate = formatter.parseDateTime("07/11/2013");
        ticketEndDate = formatter.parseDateTime("18/09/2014");
        refundDate = formatter.parseDateTime("18/09/2014");
        Integer startZone = 2;
        Integer endZone = 3;

        ProductDTO product = getRefundCardProductDTOWeeklyZone2toZone3();
        product.setType(ProductItemType.TRAVEL_CARD.databaseCode());
        when(
                        mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                                        any(Date.class), anyString(), anyString(), anyString())).thenReturn(product);

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, startZone, endZone, refundDate, ORDINARY, ProductItemType.TRAVEL_CARD.databaseCode());

        assertEquals(Integer.valueOf(12), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(10), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
        assertNotNull(refund.getRefundReasonings().get(REFUND_REASON_KEY.ORIGINAL_TICKET_PRICE.toString()));
    }

    @Test
    public void testOddPeriodRefundWheAMonthUsageAsPerExample6() {

        expectedResponse = 1770L;
        ticketStartDate = formatter.parseDateTime("07/11/2013");
        ticketEndDate = formatter.parseDateTime("07/12/2013");
        refundDate = formatter.parseDateTime("12/11/2013");
        Integer startZone = 2;
        Integer endZone = 3;

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone2toZone3());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, startZone, endZone, refundDate, ORDINARY, ProductItemType.TRAVEL_CARD.databaseCode());

        assertEquals(Integer.valueOf(0), refund.getRefundableDays());
        assertEquals(Integer.valueOf(1), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenLessThanAMonthUsageAsPerExample7() {

        expectedResponse = 2170L;
        ticketStartDate = formatter.parseDateTime("12/11/2013");
        ticketEndDate = formatter.parseDateTime("18/11/2013");
        refundDate = formatter.parseDateTime("13/11/2013");
        productId = 55L;

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOWeeklyZone1toZone2());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone1toZone2());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(2), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenMoreThanAMonthUsageAsPerExample8() {

        expectedResponse = 2000L;
        ticketStartDate = formatter.parseDateTime("23/10/2013");
        ticketEndDate = formatter.parseDateTime("22/11/2013");
        refundDate = formatter.parseDateTime("13/11/2013");
        productId = 55L;

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOMonthlyBusPassAllLondon());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOMonthlyBusPassAllLondon());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(22), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenMoreThanAMonthUsageOddPeriodsCheckPrecisionFirstInput() {

        expectedResponse = 59740L;
        ticketStartDate = formatter.parseDateTime("14/04/2014");
        ticketEndDate = formatter.parseDateTime("31/08/2014");
        refundDate = formatter.parseDateTime("20/04/2014");
        productId = 92L;

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOOddPeriodZone1toZone3_2());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOOddPeriodZone1toZone3_2());
        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(18), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(4), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenMoreThanAMonthUsageOddPeriodsCheckPrecisionSecondInput() {

        expectedResponse = 52550L;
        ticketStartDate = formatter.parseDateTime("14/04/2014");
        ticketEndDate = formatter.parseDateTime("31/08/2014");
        refundDate = formatter.parseDateTime("06/05/2014");
        productId = 92L;

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOOddPeriodZone1toZone3_2());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOOddPeriodZone1toZone3_2());
        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(18), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(4), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenMoreThanAMonthUsageAsPerExample9() {

        expectedResponse = 65190L;
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        refundDate = formatter.parseDateTime("13/11/2013");
        productId = 55L;

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(168), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenMoreThanAMonthUsageAsPerExample10() {

        expectedResponse = 8500L;
        ticketStartDate = formatter.parseDateTime("07/11/2013");
        ticketEndDate = formatter.parseDateTime("07/12/2013");
        refundDate = formatter.parseDateTime("15/11/2013");

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOOddPeriodZone2toZone5());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOOddPeriodZone2toZone5());
        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(1), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(1), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundForOneyearDuration() {

        expectedResponse = 1670L;
        ticketStartDate = formatter.parseDateTime("01/01/2014");
        ticketEndDate = formatter.parseDateTime("31/12/2014");
        refundDate = formatter.parseDateTime("13/06/2014");

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOOddPeriodZone1toZone2());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOOddPeriodZone1toZone2());
        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(164), refund.getRefundableDays());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testproRataBasisRefundWhenMoreThanAMonthUsageAsPerExample11() {

        expectedResponse = 47310L;
        ticketStartDate = formatter.parseDateTime("14/05/2013");
        ticketEndDate = formatter.parseDateTime("19/03/2014");
        refundDate = formatter.parseDateTime("15/11/2013");
        productId = 55L;

        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOOddPeriodZone2toZone5_2());
        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOOddPeriodZone2toZone5());
        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, productId, refundDate, PRO_RATA);

        assertEquals(Integer.valueOf(6), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(10), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testTradeUpRefundWhenMoreThanAMonthUsageAsPerExample12() {

        expectedResponse = 46640L;// TODO check as example says 46640 but this doesn't match rest of refund engine?
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        tradeUpDate = formatter.parseDateTime("13/11/2013");
        refundDate = formatter.parseDateTime("13/11/2013");
        Long newproductId = 2L;

        when(mockProductDataService.findById(newproductId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone1toZone5());
        when(mockProductDataService.findById(productId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        Refund refund = refundEngine.calculateRefundTradeUpOrDown(ticketStartDate, ticketEndDate, newproductId, productId, tradeUpDate, refundDate,
                        TRADE_UP);

        assertEquals(Integer.valueOf(168), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testOverLoadedTradeUpRefundWhenMoreThanAMonthUsageAsPerExample12() {

        expectedResponse = 46640L;// TODO check as example says 46640 but this doesn't match rest of refund engine?
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        tradeUpDate = formatter.parseDateTime("13/11/2013");
        refundDate = formatter.parseDateTime("13/11/2013");

        ProductDTO existingProductDTO = getRefundCardProductDTOAnnualZone1toZone5();
        ProductDTO tradedProductDTO = getRefundCardProductDTOAnnualZone2toZone5();
        Refund refund = refundEngine.calculateRefundTradeUpOrDown(ticketStartDate, ticketEndDate, existingProductDTO, tradedProductDTO, tradeUpDate,
                        refundDate, TRADE_UP);

        assertEquals(Integer.valueOf(168), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testTradeDownRefundWhenMoreThanAMonthUsageAsPerExample13() {

        expectedResponse = 45210L;
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        tradeUpDate = formatter.parseDateTime("19/11/2013");
        refundDate = formatter.parseDateTime("19/11/2013");
        Long newproductId = 2L;

        when(mockProductDataService.findById(productId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone1toZone5());
        when(mockProductDataService.findById(newproductId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        Refund refund = refundEngine.calculateRefundTradeUpOrDown(ticketStartDate, ticketEndDate, newproductId, productId, tradeUpDate, refundDate,
                        TRADE_DOWN);

        assertEquals(Integer.valueOf(174), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testOverloadedTradeDownRefundWhenMoreThanAMonthUsageAsPerExample13() {

        expectedResponse = 45210L;
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        tradeUpDate = formatter.parseDateTime("19/11/2013");
        refundDate = formatter.parseDateTime("19/11/2013");
        ProductDTO tradedProductDTO = getRefundCardProductDTOAnnualZone1toZone5();
        ProductDTO existingproductDTO = getRefundCardProductDTOAnnualZone2toZone5();
        Refund refund = refundEngine.calculateRefundTradeUpOrDown(ticketStartDate, ticketEndDate, existingproductDTO, tradedProductDTO, tradeUpDate,
                        refundDate, TRADE_DOWN);

        assertEquals(Integer.valueOf(174), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testOrdinaryRefundAfterTradeUpAsPerExample14() {

        RefundEngineServiceImpl mockEngine = mock(RefundEngineServiceImpl.class);
        mockEngine.productDataService = mockProductDataService;
        Refund refund = new Refund();
        expectedResponse = 60450L;
        ticketStartDate = formatter.parseDateTime("10/12/2008");
        ticketEndDate = formatter.parseDateTime("09/12/2009");
        tradeUpDate = formatter.parseDateTime("14/02/2009");
        refundDate = formatter.parseDateTime("07/06/2009");
        productId = 1L;
        Long newproductId = 2L;
        Long historicProductId = 3L;
        ProductDTO prod1 = new ProductDTO();
        ProductDTO prod2 = new ProductDTO();
        ProductDTO prod3 = new ProductDTO();
        prod2.setTicketPrice(3020);
        prod1.setTicketPrice(3680);
        prod3.setTicketPrice(2840);

        ProductDTO refundCardProductDTOAnnualZone1toZone4 = getRefundCardProductDTOAnnualZone1toZone4();
        ProductDTO refundCardProductDTOAnnualZone1toZone3_2008 = getRefundCardProductDTOAnnualZone1toZone3_2008();
        ProductDTO refundCardProductDTOAnnualZone1toZone3 = getRefundCardProductDTOAnnualZone1toZone3();
        refundCardProductDTOAnnualZone1toZone3.setTicketPrice(120800);

        when(mockProductDataService.findById(productId, tradeUpDate.toDate())).thenReturn(refundCardProductDTOAnnualZone1toZone3);
        when(mockProductDataService.findById(newproductId, tradeUpDate.toDate())).thenReturn(refundCardProductDTOAnnualZone1toZone4);
        when(mockProductDataService.findById(historicProductId, ticketStartDate.toDate())).thenReturn(refundCardProductDTOAnnualZone1toZone3_2008);
        when(mockEngine.findSimilarProductWithDifferentPeriod(refundCardProductDTOAnnualZone1toZone3, "7Day", tradeUpDate)).thenReturn(prod1);
        when(mockEngine.findSimilarProductWithDifferentPeriod(refundCardProductDTOAnnualZone1toZone4, "7Day", tradeUpDate)).thenReturn(prod2);
        when(mockEngine.findSimilarProductWithDifferentPeriod(refundCardProductDTOAnnualZone1toZone3_2008, "7Day", ticketStartDate)).thenReturn(prod3);
        when(
                        mockEngine.calculateOrdinaryBasisRefundAfterTradeUp(refund, ticketStartDate, ticketEndDate,
                                        refundCardProductDTOAnnualZone1toZone3_2008, refundCardProductDTOAnnualZone1toZone3,
                                        refundCardProductDTOAnnualZone1toZone4, tradeUpDate, refundDate)).thenCallRealMethod();
        when(mockEngine.calculateNumberOfUsedDays(any(DateTime.class), any(DateTime.class), anyBoolean())).thenCallRealMethod();
        when(mockEngine.calculateFullMonthsBetweenDates(any(DateTime.class), any(DateTime.class))).thenCallRealMethod();
        when(mockEngine.calculateTicketFactor(anyString(), any(DateTime.class), any(DateTime.class))).thenCallRealMethod();
        when(mockEngine.calculateMonthUsage(any(DateTime.class), any(DateTime.class))).thenCallRealMethod();
        Mockito.doCallRealMethod().when(mockEngine)
                        .calculateRefundableMonthsWeeksAndDays(any(Refund.class), any(DateTime.class), any(DateTime.class));
        Mockito.doCallRealMethod().when(mockEngine).roundBasedOnType(any(Refund.class), any(Double.class), any(String.class));

        refund = mockEngine.calculateOrdinaryBasisRefundAfterTradeUp(refund, ticketStartDate, ticketEndDate,
                        refundCardProductDTOAnnualZone1toZone3_2008, refundCardProductDTOAnnualZone1toZone3, refundCardProductDTOAnnualZone1toZone4,
                        tradeUpDate, refundDate);

        assertEquals(Integer.valueOf(29), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(5), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
        verify(mockEngine, atLeast(3)).findSimilarProductWithDifferentPeriod(any(ProductDTO.class), anyString(), any(DateTime.class));
    }

    @Test
    public void testCalculateMonth1() {

        ticketStartDate = formatter.parseDateTime("02/01/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("01/02/2013"));
    }

    @Test
    public void testCalculateMonth2() {
        ticketStartDate = formatter.parseDateTime("23/01/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("22/02/2013"));
    }

    @Test
    public void testCalculateMonth3() {
        ticketStartDate = formatter.parseDateTime("01/02/2012");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("29/02/2012"));
    }

    @Test
    public void testCalculateMonth4() {
        ticketStartDate = formatter.parseDateTime("01/03/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(formatter.parseDateTime("31/03/2013"), calculateMonthUsage);
    }

    @Test
    public void testCalculateMonth5() {
        ticketStartDate = formatter.parseDateTime("31/01/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("28/02/2013"));
    }

    @Test
    public void testCalculateMonth6() {
        ticketStartDate = formatter.parseDateTime("31/03/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("30/04/2013"));
    }

    @Test
    public void testCalculateMonth7() {
        ticketStartDate = formatter.parseDateTime("01/04/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("30/04/2013"));
    }

    @Test
    public void testCalculateMonth8() {
        ticketStartDate = formatter.parseDateTime("01/05/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("31/05/2013"));
    }

    @Test
    public void testCalculateSpannedMonth1() {
        ticketStartDate = formatter.parseDateTime("29/05/2013");
        refundDate = formatter.parseDateTime("01/07/2013");
        calculateMonthUsage = refundEngine.calculateMonthUsage(ticketStartDate, refundDate);
        assertEquals(calculateMonthUsage, formatter.parseDateTime("28/06/2013"));
    }

    @Test
    public void testCalculateNumberOfDaysUsed1() {
        ticketStartDate = formatter.parseDateTime("25/10/2013");
        refundDate = formatter.parseDateTime("12/11/2013");
        Integer daysUsed = refundEngine.calculateNumberOfUsedDays(ticketStartDate, refundDate, false);

        assertEquals(Integer.valueOf(19), daysUsed);
    }

    @Test
    public void testCalculateNumberOfDaysUsed2() {
        ticketStartDate = formatter.parseDateTime("25/10/2013");
        refundDate = formatter.parseDateTime("9/11/2013");
        Integer daysUsed = refundEngine.calculateNumberOfUsedDays(ticketStartDate, refundDate, false);

        assertEquals(Integer.valueOf(16), daysUsed);
    }

    @Test
    public void testCalculateNumberOfDaysUsedWhenNegative() {
        ticketStartDate = formatter.parseDateTime(DateTestUtil.REFUND_DATE_1);
        refundDate = formatter.parseDateTime(DateTestUtil.REFUND_DATE_2);
        Integer daysUsed = refundEngine.calculateNumberOfUsedDays(ticketStartDate, refundDate, false);

        assertTrue(daysUsed >= 0);
    }

    @Test
    public void testCalculateFullWeeksInNumberOfDays() {
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(1), Integer.valueOf(0));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(6), Integer.valueOf(0));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(7), Integer.valueOf(1));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(8), Integer.valueOf(1));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(13), Integer.valueOf(1));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(14), Integer.valueOf(2));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(15), Integer.valueOf(2));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(20), Integer.valueOf(2));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(21), Integer.valueOf(3));
        assertEquals(refundEngine.calculateFullWeeksInNumberOfDays(22), Integer.valueOf(3));
    }

    @Test
    public void testCalculateMonthsWeeksAndDaysfor19Days() {
        ticketStartDate = formatter.parseDateTime("25/10/2013");
        ticketEndDate = formatter.parseDateTime("12/11/2013");
        Refund refundParams = new Refund();

        refundEngine.calculateRefundableMonthsWeeksAndDays(refundParams, ticketStartDate, ticketEndDate);
        assertEquals(Integer.valueOf(3), refundParams.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refundParams.getRefundableDays());
    }

    @Test
    public void testCalculateMonthsWeeksAndDaysfor16Days() {
        ticketStartDate = formatter.parseDateTime("25/10/2013");
        ticketEndDate = formatter.parseDateTime("9/11/2013");
        Refund refundParams = new Refund();

        refundEngine.calculateRefundableMonthsWeeksAndDays(refundParams, ticketStartDate, ticketEndDate);
        assertEquals(Integer.valueOf(2), refundParams.getRefundableWeeks());
        assertEquals(Integer.valueOf(2), refundParams.getRefundableDays());
    }

    @Test
    public void calculateFullMonthsInPeriod() {

        ticketStartDate = formatter.parseDateTime("29/05/2013");
        ticketEndDate = formatter.parseDateTime("12/11/2013");

        Integer calculateFullMonthsRemainderInNumberOfDays = refundEngine.calculateFullMonthsBetweenDates(ticketStartDate, ticketEndDate);

        assert (5 == calculateFullMonthsRemainderInNumberOfDays);
    }

    @Test
    public void testCalculateTicketFactorSevenDay() {
        ticketStartDate = formatter.parseDateTime("23/10/2013");
        ticketEndDate = formatter.parseDateTime("22/11/2013");
        refundDate = formatter.parseDateTime("13/11/2013");
        Integer result = 0;
        result = refundEngine.calculateTicketFactor(getRefundCardProductDTOWeeklyZone1toZone2().getDuration(), ticketStartDate, ticketEndDate);
        assert (result == 7);
    }

    @Test
    public void testCalculateTicketFactorOneMonth() {
        ticketStartDate = formatter.parseDateTime("01/01/2013");
        ticketEndDate = formatter.parseDateTime("30/01/2013");
        Integer result = 0;
        result = refundEngine.calculateTicketFactor(getRefundCardProductDTOMonthlyZone3toZone5().getDuration(), ticketStartDate, ticketEndDate);
        assert (result == 30);
    }

    @Test
    public void testCalculateTicketFactorAnnual() {
        ticketStartDate = formatter.parseDateTime("01/01/2013");
        ticketEndDate = formatter.parseDateTime("31/12/2013");
        Integer result = 0;
        result = refundEngine.calculateTicketFactor(getRefundCardProductDTOAnnualZone2toZone5().getDuration(), ticketStartDate, ticketEndDate);
        assert (result == 365);
    }

    @Test
    public void testCalculateTicketFactorThreeMonth() {
        ticketStartDate = formatter.parseDateTime("01/01/2013");
        ticketEndDate = formatter.parseDateTime("28/03/2013");
        Integer result = 0;
        result = refundEngine.calculateTicketFactor(getRefundCardProductDTOThreeMonthZone1toZone2().getDuration(), ticketStartDate, ticketEndDate);
        assert (result == 87);
    }

    @Test
    public void testCalculateTicketFactorSixMonth() {
        ticketStartDate = formatter.parseDateTime("01/01/2013");
        ticketEndDate = formatter.parseDateTime("28/06/2013");
        Integer result = 0;
        result = refundEngine.calculateTicketFactor(getRefundCardProductDTOSixMonthZone1toZone2().getDuration(), ticketStartDate, ticketEndDate);
        assert (result == 179);
    }

    @Test
    public void testIsOddPeriodTicket7dayPeriod() {

        ticketStartDate = formatter.parseDateTime("01/01/2013");
        refundDate = formatter.parseDateTime("07/01/2013");
        Boolean isOddPeriodTicket = refundEngine.isNotOddPeriodTicket(ticketStartDate, refundDate);
        assert (isOddPeriodTicket);
    }

    @Test
    public void testIsOddPeriodTicket8dayPeriod() {

        ticketStartDate = formatter.parseDateTime("01/01/2013");
        refundDate = formatter.parseDateTime("08/01/2013");
        Boolean isOddPeriodTicket = refundEngine.isNotOddPeriodTicket(ticketStartDate, refundDate);
        assert (!isOddPeriodTicket);
    }

    @Test
    public void testIsOddPeriodTicketOneMonthMinus1DayPeriod() {

        ticketStartDate = formatter.parseDateTime("01/01/2013");
        refundDate = formatter.parseDateTime("30/01/2013");
        Boolean isOddPeriodTicket = refundEngine.isNotOddPeriodTicket(ticketStartDate, refundDate);
        assert (!isOddPeriodTicket);
    }

    @Test
    public void testIsOddPeriodTicketOneMonthPeriod() {

        ticketStartDate = formatter.parseDateTime("01/01/2013");
        refundDate = formatter.parseDateTime("31/01/2013");
        Boolean isOddPeriodTicket = refundEngine.isNotOddPeriodTicket(ticketStartDate, refundDate);
        assert (isOddPeriodTicket);
    }

    @Test
    public void testRoundingBasedOnTypeForExample1() {
        Refund refund = new Refund();

        refundEngine.roundBasedOnType(refund, 2120D, ORDINARY_BASIS_REFUND.toString());
        assertEquals(new Long(2120), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample2() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 3632D, ORDINARY_BASIS_REFUND.toString());
        assertEquals(new Long(3630), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample3() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 57017.6D, ORDINARY_BASIS_REFUND.toString());
        assertEquals(new Long(57010), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample4() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 108044D, ORDINARY_BASIS_REFUND.toString());
        assertEquals(new Long(108040), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample5() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 91852.8D, ODD_PERIOD_REFUND.toString());
        assertEquals(new Long(91860), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample6() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 9126.4D, ODD_PERIOD_REFUND.toString());
        assertEquals(new Long(9130), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample7() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 2171.4285714285716D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(2170), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample8() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 2008D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(2000), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample9() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 65198.9D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(65190), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample10() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 8509.032D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(8500), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample11() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 8509.032D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(8500), refund.getRefundAmount());
    }

    @Test
    public void testRoundingBasedOnTypeForExample12() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 46632.329D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(46630), refund.getRefundAmount());// TODO check as example says 46640 but this doesn't match rest of refund engine?
    }

    @Test
    public void testRoundingBasedOnTypeForExample13() {
        Refund refund = new Refund();
        refundEngine.roundBasedOnType(refund, 45212.055D, PRO_RATA_REFUND.toString());
        assertEquals(new Long(45210), refund.getRefundAmount());
    }

    @Test
    public void testMonthylyTravelCardTypeByDurationOfStartAndEndDateReturnsMonth() {
        DateTime monthly_Ticket_Start_Date = new DateTime(START_DATE_OF_MONTH_FOR_MONTHLY_CARD_TYPE);
        DateTime monthly_Ticket_End_Date = new DateTime(END_DATE_OF_MONTH_FOR_MONTHLY_CARD_TYPE);
        assertEquals(Durations.MONTH.getDurationType(), refundEngine.findTravelCardTypeByDuration(monthly_Ticket_Start_Date, monthly_Ticket_End_Date));
    }

    @Test
    public void testMonthylyTravelCardTypeByDurationOfStartAndEndDateReturnsOddPediodTicket() {
        DateTime monthly_Ticket_Start_Date = new DateTime(START_DATE_OF_MONTH_FOR_NON_MONTHLY_CARD_TYPE);
        DateTime monthly_Ticket_End_Date = new DateTime(END_DATE_OF_MONTH_FOR_NON_MONTHLY_CARD_TYPE);
        assertEquals(Durations.OTHER.getDurationType(), refundEngine.findTravelCardTypeByDuration(monthly_Ticket_Start_Date, monthly_Ticket_End_Date));
    }

    @Test
    public void testRefundAmountNotNegativeForTradedTicket() {
        DateTime tradeUpDate = formatter.parseDateTime(DateTestUtil.NOV_13);
        when(mockProductDataService.findById(PRODUCT_ID, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTO());
        when(mockProductDataService.findById(PRODUCTID_TRADEDTICKET, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOForTradedTicket());

        Refund resultRefund = refundEngine.calculateRefundTradeUpOrDown(formatter.parseDateTime(DateTestUtil.MAY_30),
                        formatter.parseDateTime(DateTestUtil.NOV_21), PRODUCT_ID, PRODUCTID_TRADEDTICKET,
 tradeUpDate, new DateTime(new Date()),
                        RefundCalculationBasis.TRADE_UP);
        assertEquals(new Long(0), resultRefund.getRefundAmount());

    }

    @Test
    public void testRefundAmountForSixMonthsZones1to3() {

        ticketStartDate = formatter.parseDateTime(DateTestUtil.MARCH_10);
        ticketEndDate = formatter.parseDateTime(DateTestUtil.SEPTEMBER_09);
        refundDate = formatter.parseDateTime(DateTestUtil.APRIL_02);
        when(mockProductDataService.findById(anyLong(), any(Date.class))).thenReturn(getRefundCardProductDTOForSixMonthsZone1to3());
        Refund actualRefund = refundEngine.calculateProRataRefund(ticketStartDate, ticketEndDate, 93L, 1, 3, refundDate);
        assertEquals(new Long(71330), actualRefund.getRefundAmount());

    }

    @Test
    public void testCalculateRefundTradeUp() {

        expectedResponse = 46640L;
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        tradeUpDate = formatter.parseDateTime("13/11/2013");
        refundDate = formatter.parseDateTime("13/11/2013");
        Long newproductId = 2L;
        when(mockProductDataService.findById(newproductId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone1toZone5());
        when(mockProductDataService.findById(productId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        Refund refund = refundEngine.calculateRefundTradeUpOrDown(ticketStartDate, ticketEndDate, getRefundCardProductDTOAnnualZone1toZone5(),
                        getRefundCardProductDTOAnnualZone2toZone5(), tradeUpDate, refundDate, TRADE_UP);

        assertEquals(Integer.valueOf(168), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testCalculateRefundTradeDown() {

        expectedResponse = 45210L;
        ticketStartDate = formatter.parseDateTime("30/05/2013");
        ticketEndDate = formatter.parseDateTime("29/05/2014");
        tradeUpDate = formatter.parseDateTime("19/11/2013");
        refundDate = formatter.parseDateTime("19/11/2013");
        Long newproductId = 2L;

        when(mockProductDataService.findById(productId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone1toZone5());
        when(mockProductDataService.findById(newproductId, tradeUpDate.toDate())).thenReturn(getRefundCardProductDTOAnnualZone2toZone5());
        Refund refund = refundEngine.calculateRefundTradeUpOrDown(ticketStartDate, ticketEndDate, getRefundCardProductDTOAnnualZone2toZone5(),
                        getRefundCardProductDTOAnnualZone1toZone5(), tradeUpDate, refundDate, TRADE_DOWN);

        assertEquals(Integer.valueOf(174), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(0), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
    }

    @Test
    public void testCalculateRefundForThreeMonthsZoneOnetoZoneTwo() {

        expectedResponse = 5840L;
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("31/01/2014");
        refundDate = formatter.parseDateTime("15/01/2014");
        Integer startZone = 1;
        Integer endZone = 2;

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOThreeMonthZone1toZone2(), getRefundCardProductDTOWeeklyZone1toZone2());

        Refund refund = refundEngine.calculateRefund(ticketStartDate, ticketEndDate, startZone, endZone, refundDate, ORDINARY,ProductItemType.TRAVEL_CARD.databaseCode());

        assertEquals(Integer.valueOf(15), refund.getRefundableDays());
        assertEquals(Integer.valueOf(0), refund.getRefundableWeeks());
        assertEquals(Integer.valueOf(2), refund.getRefundableMonths());
        assertEquals(expectedResponse, refund.getRefundAmount());
        assertNotNull(refund.getRefundReasonings().get(REFUND_REASON_KEY.ORIGINAL_TICKET_PRICE.toString()));
    }

    @Test
    public void testGetProductIdForNotOddPeriodTicketThreeMonths() {

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOThreeMonthZone1toZone2());
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("31/01/2014");
        Integer startZone = 1;
        Integer endZone = 2;
        assertEquals(RefundConstants.THREEMONTH, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));
        assertEquals(new Long(63L), refundEngine.getProductIdForNotOddPeriodTicket(ticketStartDate, ticketEndDate, startZone, endZone, ProductItemType.TRAVEL_CARD.databaseCode()));

    }

    @Test
    public void testGetProductIdForNotOddPeriodTicketSixMonths() {

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString()))
                        .thenReturn(getRefundCardProductDTOSixMonthZone1toZone2());
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("30/04/2014");
        Integer startZone = 1;
        Integer endZone = 2;
        assertEquals(RefundConstants.SIXMONTH, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));
        assertEquals(new Long(92L), refundEngine.getProductIdForNotOddPeriodTicket(ticketStartDate, ticketEndDate, startZone, endZone,ProductItemType.TRAVEL_CARD.databaseCode()));

    }

    @Test
    public void testGetProductIdForNotOddPeriodTicketAnnualYear() {

        when(mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOAnnualZone1toZone6());
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("31/10/2014");
        Integer startZone = 1;
        Integer endZone = 6;
        assertEquals(RefundConstants.ANNUAL, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));
        assertEquals(new Long(99L), refundEngine.getProductIdForNotOddPeriodTicket(ticketStartDate, ticketEndDate, startZone, endZone,ProductItemType.TRAVEL_CARD.databaseCode()));

    }

    @Test
    public void testGetProductIdForNotOddPeriodTicketOther() {

        when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        getRefundCardProductDTOOddPeriodZone2toZone5_2());
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("12/10/2014");
        Integer startZone = 2;
        Integer endZone = 5;
        refundEngine.getProductIdForNotOddPeriodTicket(ticketStartDate, ticketEndDate, startZone, endZone,ProductItemType.TRAVEL_CARD.databaseCode());
        verify(mockProductDataService, atLeast(1)).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(),
                        anyInt(), any(Date.class), anyString(), anyString(), anyString());

    }

    @Test
    public void testResetRefundAmountToZero() {
        Refund refund = new Refund();
        refund.setRefundAmount(-3204L);
        refundEngine.resetRefundAmountToZeroWhenNegative(refund);
        assertEquals(new Long(0), refund.getRefundAmount());
    }

    @Test
    public void testFindSimilarProductWithDifferentPeriod() {
        when(
                        mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                                        any(Date.class), anyString(), anyString(), anyString())).thenReturn(getRefundCardProductDTOWeeklyZone3toZone5());
        refundEngine.findSimilarProductWithDifferentPeriod(getRefundCardProductDTOWeeklyZone3toZone5(), Durations.SEVEN_DAYS.getDurationType(),
                        new DateTime());
        verify(mockProductDataService, atLeast(1)).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                        any(Date.class), anyString(), anyString(), anyString());

    }

    @Test
    public void testFindTravelCardTypeByDurationSevenDays() {

        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("07/11/2013");
        assertEquals(SEVENDAY, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));

    }

    @Test
    public void testFindTravelCardTypeByDurationThreeMonths() {
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("31/01/2014");
        assertEquals(THREEMONTH, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));

    }

    @Test
    public void testFindTravelCardTypeByDurationSixMonths() {
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("30/04/2014");
        assertEquals(SIXMONTH, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));
    }

    @Test
    public void testFindTravelCardTypeByDurationAnnualYear() {
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("31/10/2014");
        assertEquals(ANNUAL, refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));
    }

    @Test
    public void testFindTravelCardTypeByDurationOther() {
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("05/05/2014");
        assertEquals(Durations.OTHER.getDurationType(), refundEngine.findTravelCardTypeByDuration(ticketStartDate, ticketEndDate));
    }

    @Test
    public void testIsNotOddPeriodTicketForSixMonths() {
        ticketStartDate = formatter.parseDateTime("01/11/2013");
        ticketEndDate = formatter.parseDateTime("30/04/2014");
        assertTrue(refundEngine.isNotOddPeriodTicket(ticketStartDate, ticketEndDate));

    }

    @Test
    public void testUsagePeriodLessThanAMonth() {
        ticketStartDate = formatter.parseDateTime("25/08/2014");
        ticketEndDate = formatter.parseDateTime("17/09/2014");
        assertEquals(EXPECTED_RESULT_1, refundEngine.calculateUsagePeriod(ticketStartDate, ticketEndDate));
    }

    @Test
    public void testUsagePeriodOverAMonth() {
        ticketStartDate = formatter.parseDateTime("25/07/2014");
        ticketEndDate = formatter.parseDateTime("17/09/2014");
        assertEquals(EXPECTED_RESULT_2, refundEngine.calculateUsagePeriod(ticketStartDate, ticketEndDate));
    }

    @Test
    public void testUsagePeriodOverTwoMonth() {
        ticketStartDate = formatter.parseDateTime("25/06/2014");
        ticketEndDate = formatter.parseDateTime("17/09/2014");
        assertEquals(EXPECTED_RESULT_3, refundEngine.calculateUsagePeriod(ticketStartDate, ticketEndDate));
    }

    @Test
    public void testGetUsageDurationOverAMonthScenario() {
        assertEquals(EXPECTED_RESULT_4, refundEngine.formatUsageDuration(3, 0, 12));
    }

    @Test
    public void testGetUsageDurationUnderAMonthScenario() {
        assertEquals(EXPECTED_RESULT_5, refundEngine.formatUsageDuration(0, 2, 4));
    }

    @Test
    public void testGetUsageDurationLessThanAWeekScenario() {
        assertEquals(EXPECTED_RESULT_6, refundEngine.formatUsageDuration(0, 0, 3));
    }

    @Test
    public void testGetUsageDurationForOneDayScenario() {
        assertEquals(EXPECTED_RESULT_7, refundEngine.formatUsageDuration(0, 0, 1));
    }
    
    @Test
    public void testCalculateNumberOfDaysUsedWhenDateOfRefundIsAfterTheStartDate() {
        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_AFTER_DATE_OF_REFUND_DATE);
        refundDate = formatter.parseDateTime(REFUND_DATE);
        Integer daysUsed = refundEngine.calculateNumberOfUsedDays(ticketStartDate, refundDate, false);
        assertTrue(daysUsed == 0);
    }

    @Test
    public void testCalculateNumberOfDaysUsedWhenDateOfRefundIsSameDayOfStartDate() {
        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_SAME_DATE_OF_REFUND_DATE);
        refundDate = formatter.parseDateTime(REFUND_DATE);
        Integer daysUsed = refundEngine.calculateNumberOfUsedDays(ticketStartDate, refundDate, false);
        assertTrue(daysUsed > 0);
    }
    
    @Test
    public void testCalculateNumberOfDaysUsedWhenDateOfRefundIsBeforeTheStartDate() {
        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_BEFORE_DATE_OF_REFUND_DATE);
        refundDate = formatter.parseDateTime(REFUND_DATE);
        Integer daysUsed = refundEngine.calculateNumberOfUsedDays(ticketStartDate, refundDate, false);
        assertTrue(daysUsed > 0);
    }
    
    @Test
    public void testIsOddPeriodTicket1MonthPeriodWithStartDate31() {

        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_AFTER_DATE_OF_REFUND_DATE);
        refundDate = formatter.parseDateTime(TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_30_11_2014);
        Boolean isOddPeriodTicket = refundEngine.isNotOddPeriodTicket(ticketStartDate, refundDate);
        assertTrue(isOddPeriodTicket);
    }
    
    @Test
    public void testIsMonthlyTicketForEndOfMonthStartDateShouldReturnTrue(){
        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_AFTER_DATE_OF_REFUND_DATE);
        ticketEndDate = formatter.parseDateTime(TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_30_11_2014);
        assertTrue(refundEngine.isMonthlyTicket(ticketStartDate, ticketEndDate));
    }
    
    @Test
    public void testIsMonthlyTicketForEndOfMonthStartDateShouldReturnFalse(){
        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_SAME_DATE_OF_REFUND_DATE);
        ticketEndDate = formatter.parseDateTime(TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_30_11_2014);
        assertFalse(refundEngine.isMonthlyTicket(ticketStartDate, ticketEndDate));
    }
    
    @Test
    public void testIsMonthlyTicketForOtherStartDatesShouldReturnTrue(){
        ticketStartDate = formatter.parseDateTime(TICKET_START_DATE_SAME_DATE_OF_REFUND_DATE);
        ticketEndDate = formatter.parseDateTime(TICKET_END_DATE_ONE_MONTH_TRAVEL_CARD_29_NOV_2014);
        assertTrue(refundEngine.isMonthlyTicket(ticketStartDate, ticketEndDate));
    }
}
