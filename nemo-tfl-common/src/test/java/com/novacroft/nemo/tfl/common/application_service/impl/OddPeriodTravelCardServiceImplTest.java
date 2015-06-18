package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_ANNUAL_MULTIPLAY_RATE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MONTHLY_MULTIPLAY_RATE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD_DURATION_IN_MONTHS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestZoneIdDescriptionDTO1;
import static com.novacroft.nemo.test_support.DateTestUtil.*;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.JOB_CENTRE_PLUS_DISCOUNT_PRICE_2;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ODD_PERIOD_OTHER_TRAVEL_CARD_RESET_ANNUAL;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.SystemParameterServiceImpl;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.ProductTestUtil;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.data_service.ZoneIdDescriptionDataService;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class OddPeriodTravelCardServiceImplTest {
    private static final String TEST_SUBSTITUTION_TRAVELCARD_TYPE_VALUE = "Substitution Card";
    private static final String TEST_SUBSTITUTION_ANNUAL_TYPE_VALUE = "Annual Card";

    private static final Integer EXPECTED_MONTHLY_SUBSTITUTION_PRICE = 4349960;

    private OddPeriodTravelCardServiceImpl service;
    private SystemParameterService mockSystemParameterService;
    private ProductService mockProductService;
    private RefundCalculationBasisService mockRefundCalculationService;
    private ZoneIdDescriptionDataService mockZoneIdDescriptionService;
    private JobCentrePlusTravelCardServiceImpl mockJobCentrePlusTravelCardService;
    private TradedTravelCardServiceImpl mockTradedTravelCardServiceImpl;

    @Before
    public void setUp() {
        service = new OddPeriodTravelCardServiceImpl();

        mockSystemParameterService = mock(SystemParameterServiceImpl.class);
        mockProductService = mock(ProductService.class);
        mockRefundCalculationService = mock(RefundCalculationBasisService.class);
        mockZoneIdDescriptionService = mock(ZoneIdDescriptionDataService.class);
        mockJobCentrePlusTravelCardService = mock(JobCentrePlusTravelCardServiceImpl.class);
        mockTradedTravelCardServiceImpl = mock(TradedTravelCardServiceImpl.class);

        service.productService = mockProductService;
        service.systemParameterService = mockSystemParameterService;
        service.refundCalculationBasisService = mockRefundCalculationService;
        service.tradedTravelCardService = mockTradedTravelCardServiceImpl;

        when(mockSystemParameterService.getFloatParameterValue(CartAttribute.OTHER_TRAVELCARD_MONTHLY_MULTIPLAY_RATE)).thenReturn(
                        OTHER_TRAVELCARD_MONTHLY_MULTIPLAY_RATE);
        when(mockSystemParameterService.getParameterValue(CartAttribute.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE)).thenReturn(
                        TEST_SUBSTITUTION_TRAVELCARD_TYPE_VALUE);
        when(mockSystemParameterService.getParameterValue(CartAttribute.OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD)).thenReturn(
                        TEST_SUBSTITUTION_ANNUAL_TYPE_VALUE);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS)).thenReturn(
                        OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS)).thenReturn(
                        OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(
                        OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD_DURATION_IN_MONTHS))
                        .thenReturn(OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD_DURATION_IN_MONTHS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_MULTIPLAY_RATE)).thenReturn(
                        OTHER_TRAVELCARD_ANNUAL_MULTIPLAY_RATE);
        when(
                        mockRefundCalculationService.getRefundCalculationBasis(anyString(), anyString(), anyString(), anyLong(), anyString(),
                                        anyString(), anyInt(), anyInt(), anyBoolean())).thenReturn(RefundCalculationBasis.ORDINARY.code());
        when(mockZoneIdDescriptionService.findById(anyLong())).thenReturn(getTestZoneIdDescriptionDTO1());
        when(mockJobCentrePlusTravelCardService.checkJobCentrePlusDiscountAndUpdateTicketPrice(any(CartItemCmdImpl.class), anyInt())).thenReturn(
                        JOB_CENTRE_PLUS_DISCOUNT_PRICE_2);
    }

    @Test
    public void getOddPeriodOtherTravelCardTicketPriceIfLongerThanTenMonthsTwelveDays() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        testCartItemCmd.setStartDate(START_DATE_OF_MONTH_JAN);
        testCartItemCmd.setEndDate(NOV_13);
        when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(testCartItemCmd)).thenReturn(
                        ProductTestUtil.getTestProductDTO3());
        when(mockProductService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class), anyString())).thenReturn(
                ProductTestUtil.getTestProductDTO3());
        assertEquals(ProductTestUtil.getTestProductDTO3().getTicketPrice(), service.getOddPeriodTicketPrice(testCartItemCmd));
    }

    @Test
    public void getOddPeriodOtherTravelCardTicketPriceIfEightMonths() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        testCartItemCmd.setStartDate(START_DATE_OF_MONTH_JAN);
        testCartItemCmd.setEndDate(END_DATE_OF_MONTH_AUG);
        when(mockProductService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class), anyString()))
                        .thenReturn(ProductTestUtil.getTestProductDTO2());
        assertEquals(EXPECTED_MONTHLY_SUBSTITUTION_PRICE, service.getOddPeriodTicketPrice(testCartItemCmd));
    }

    @Test
    public void testConvertToProductItemDTOIfLessThanTenMonthsTwelveDays() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        ProductItemDTO actualProductItemDTO = service.convertCartItemCmdImplToProductItemDTO(testCartItemCmd);
        assertNotNull(actualProductItemDTO);
    }

    @Test
    public void testConvertToProductItemDTOIfLongerThanTenMonthsTwelveDays() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        testCartItemCmd.setStartDate(START_DATE_OF_MONTH_JAN);
        testCartItemCmd.setEndDate(NOV_15_2014);
        when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(testCartItemCmd)).thenReturn(
                        ProductTestUtil.getTestProductDTO3());
        ProductItemDTO actualProductItemDTO = service.convertCartItemCmdImplToProductItemDTO(testCartItemCmd);
        assertNotNull(actualProductItemDTO);
        assertEquals(END_DATE_OF_MONTH_DEC, testCartItemCmd.getEndDate());
        assertEquals(ODD_PERIOD_OTHER_TRAVEL_CARD_RESET_ANNUAL.textCode(), testCartItemCmd.getStatusMessage());
    }

    @Test
    public void testInvokeTradedTravelCardServiceForTradedTicket() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        testCartItemCmd.setTradedTicket(CartItemTestUtil.getOtherTradedTicket());
        service.convertCartItemCmdImplToProductItemDTO(testCartItemCmd);
        verify(mockTradedTravelCardServiceImpl, atLeastOnce())
                        .getTravelCardItemForTradedTicket(any(CartItemCmdImpl.class), any(ProductItemDTO.class));
    }

    @Test
    public void getOddPeriodTicketPriceUpgradeToConcessionEndDate() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class))).thenReturn(
                        ProductTestUtil.getTestProductDTO2());
        testCartItemCmd.setStartDate(START_DATE_OF_MONTH_JAN);
        testCartItemCmd.setEndDate(END_DATE_OF_MONTH_NOV);
        testCartItemCmd.setConcessionEndDate(END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC);
        Integer ticketPrice = service.getOddPeriodTicketPrice(testCartItemCmd);
        assertEquals(ProductTestUtil.TICKET_PRICE_2, ticketPrice);
    }
   

    @Test
    public void getOddPeriodTicketPriceConcessionEndDateAfterAnnual() {
        CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
        when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class))).thenReturn(
                        ProductTestUtil.getTestProductDTO2());
        testCartItemCmd.setStartDate(START_DATE_OF_MONTH_JAN);
        testCartItemCmd.setEndDate(END_DATE_OF_MONTH_NOV);
        testCartItemCmd.setConcessionEndDate(END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC_2015);
        Integer ticketPrice = service.getOddPeriodTicketPrice(testCartItemCmd);
        assertNotNull(ticketPrice);
        assertEquals(ProductTestUtil.getTestProductDTO2().getTicketPrice(), ticketPrice);
    }
    
    @Test
    public void isOddPeriodWithAnnualPriceIfStartDateIsBeforeConcessionEndDate() {
    	CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
    	assertFalse(service.isOddPeriodWithAnnualPrice(testCartItemCmd, DateUtil.parse(END_DATE_A_BEFORE_A_DAY_OF_MONTH_DEC)));
    }
    
    @Test
    public void calculateOddPeriodTicketPriceShouldReturnNullIfNoSubstituteProductIsFound() {
    	CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
    	when(mockProductService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(testCartItemCmd, "")).thenReturn(null);
    	when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(testCartItemCmd)).thenReturn(
                null);
    	Integer price = service.calculateOddPeriodTicketPrice(testCartItemCmd, 10, 1);
    	assertNull(price);
    }
    
    @Test
    public void calculateOddPeriodTicketPriceShouldReturnNullIfNoSubstitutePriceIsFound() {
    	CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
    	ProductDTO testProductDTO = getTestProductDTO1();
    	testProductDTO.setTicketPrice(null);
    	when(mockProductService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(testCartItemCmd, "")).thenReturn(testProductDTO);
    	when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(testCartItemCmd)).thenReturn(
    			testProductDTO);
    	Integer price = service.calculateOddPeriodTicketPrice(testCartItemCmd, 10, 1);
    	assertNull(price);
    }

    @Test
    public void getOddPeriodProductItemDTO(){
    	CartItemCmdImpl testCartItemCmd = CartItemTestUtil.getTestOddPeriodOtherTravelCardCmd1();
    	testCartItemCmd.setPrice(1000);
    	when(mockProductService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class))).thenReturn(getTestProductDTO1());
    	ProductItemDTO productItemDTO = service.getOddPeriodProductItemDTO(testCartItemCmd);
    	assertNotNull(productItemDTO);
    	assertNotNull(productItemDTO.getProductId());
    	assertEquals(testCartItemCmd.getPrice(), productItemDTO.getPrice());
    	
    }
}
