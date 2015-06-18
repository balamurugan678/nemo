package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.JOB_CENTRE_PLUS_DISCOUNT_RATE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.MAGNETIC_TICKET_NUMBER;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_DATE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_ZONE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestJobCentrePlusTravelCardCmd1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestProductItemDTO1;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.getTestJobCentrePlusDiscountDTO4;
import static com.novacroft.nemo.test_support.ProductTestUtil.TICKET_PRICE_1;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProductDTO1;
import static com.novacroft.nemo.test_support.RefundTestUtil.REFUND_ID_1;
import static com.novacroft.nemo.tfl.common.constant.CartType.CANCEL_SURRENDER_REFUND;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.ORDINARY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.SystemParameterServiceImpl;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.TradedTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class NonOddPeriodTravelCardServiceImplTest {
    private ProductService mockProductService;
	private CartItemCmdImpl cartItemCmdImpl;
    private SystemParameterService systemParameterService;
    private NonOddPeriodTravelCardServiceImpl nonOddPeriodTravelCardServiceImpl;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private JobCentrePlusTravelCardServiceImpl mockJobCentrePlusTravelCardService;
    private NonOddPeriodTravelCardServiceImpl mocknonoddPeriodTravelCardServiceImpl;
	private RefundCalculationBasisService mockrefundCalculationBasisService;
	private AddUnlistedProductService mockAddunlistedProductService;
	private TradedTravelCardService mockTradedTravelCardServiceImpl;
	
	@Before
    public void setUp() {
        systemParameterService = mock(SystemParameterServiceImpl.class);
        mockJobCentrePlusTravelCardService=mock(JobCentrePlusTravelCardServiceImpl.class);
        mockProductService=mock(ProductService.class);
        nonOddPeriodTravelCardServiceImpl =new NonOddPeriodTravelCardServiceImpl();
        mocknonoddPeriodTravelCardServiceImpl=mock(NonOddPeriodTravelCardServiceImpl.class);
        nonOddPeriodTravelCardServiceImpl.systemParameterService = systemParameterService;
        mockCardDataService = mock(CardDataService.class);
        mockrefundCalculationBasisService=mock(RefundCalculationBasisService.class);
        nonOddPeriodTravelCardServiceImpl.refundCalculationBasisService=mockrefundCalculationBasisService;
        mockGetCardService = mock(GetCardService.class);
        nonOddPeriodTravelCardServiceImpl.productService=mockProductService;
        nonOddPeriodTravelCardServiceImpl.jobCentrePlusTravelCardService=mockJobCentrePlusTravelCardService;
        mockAddunlistedProductService = mock(AddUnlistedProductService.class);
        nonOddPeriodTravelCardServiceImpl.addUnlistedProductService = mockAddunlistedProductService;
        mockTradedTravelCardServiceImpl = mock(TradedTravelCardServiceImpl.class);
        nonOddPeriodTravelCardServiceImpl.tradedTravelCardService = mockTradedTravelCardServiceImpl;
        
        when(mockProductService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class))).thenReturn(
                        getTestProductDTO1());
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyString())).thenReturn(getTestJobCentrePlusDiscountDTO4());
        when(systemParameterService.getFloatParameterValue(CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_RATE)).thenReturn(JOB_CENTRE_PLUS_DISCOUNT_RATE);
        when(systemParameterService.getIntegerParameterValue(CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS)).thenReturn(JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS);
        when(systemParameterService.getIntegerParameterValue(CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS)).thenReturn(JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS);
        when(mockJobCentrePlusTravelCardService.checkJobCentrePlusDiscountAndUpdateTicketPrice(getTestJobCentrePlusTravelCardCmd1(),TICKET_PRICE_1)).thenReturn(TICKET_PRICE_1);
        when(mocknonoddPeriodTravelCardServiceImpl.getNewProductItem(cartItemCmdImpl, TICKET_PRICE_1, getTestProductDTO1())).thenReturn(getTestProductItemDTO1()) ;
        when(mockrefundCalculationBasisService.getRefundCalculationBasis(anyString(),anyString(), anyLong(), anyString(), anyString(),any(Integer.class),any(Integer.class),anyBoolean(),anyBoolean()))
            .thenReturn(RefundCalculationBasis.ORDINARY.code());
        
        doNothing().when(mockAddunlistedProductService).setTravelcardTypeByFormTravelCardType(any(CartItemCmdImpl.class));
    }

    @Test
    public void getOddPeriodOtherTravelCardTicketPrice_TenMonthsTwelvedays() {
        cartItemCmdImpl = CartItemTestUtil.getTestTravelCard1();
        cartItemCmdImpl .setEndZone(TRAVEL_END_ZONE_1);
        cartItemCmdImpl.setTravelCardType(Durations.SEVEN_DAYS.getDurationType() +"");
        cartItemCmdImpl.setId(CARD_ID_1);
        cartItemCmdImpl.setCardId(CARD_ID_1);
        cartItemCmdImpl.setMagneticTicketNumber(MAGNETIC_TICKET_NUMBER);
        cartItemCmdImpl.setTicketUnused(true);
        cartItemCmdImpl.setBackdatedRefundReasonId(REFUND_ID_1);
        cartItemCmdImpl.setDateOfRefund(new Date());
        cartItemCmdImpl.setRefundType(ORDINARY+"");
        cartItemCmdImpl.setBackdated(false);
        cartItemCmdImpl.setEndDate(TRAVEL_END_DATE_1);
        nonOddPeriodTravelCardServiceImpl.convertCartItemCmdImplToProductItemDTO(cartItemCmdImpl);
        nonOddPeriodTravelCardServiceImpl.getNewProductItem(cartItemCmdImpl,TICKET_PRICE_1,getTestProductDTO1());
        verify(mockProductService, atLeastOnce()).getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(any(CartItemCmdImpl.class));
        verify(mockrefundCalculationBasisService,atLeastOnce()).getRefundCalculationBasis(anyString(),anyString(), anyString(),anyLong(), anyString(), anyString(),any(Integer.class),any(Integer.class),anyBoolean());
    }
    
    @Test
    public void getTravelCardItemForTradedTicket() {
        CartItemCmdImpl tradedCartItem = CartItemTestUtil.getTestTopUpTicketTravelCardWithExchangeCmd1();
        tradedCartItem.setRefundType(CANCEL_SURRENDER_REFUND.code());
        tradedCartItem.setExchangedDate(new DateTime());
         nonOddPeriodTravelCardServiceImpl.convertCartItemCmdImplToProductItemDTO(tradedCartItem);
        verify(mockTradedTravelCardServiceImpl,atLeastOnce()).getTravelCardItemForTradedTicket(any(CartItemCmdImpl.class), any(ProductItemDTO.class));
    }
    
    @Test
    public void getTravelCardItem() {
        CartItemCmdImpl tradedCartItem = CartItemTestUtil.getTestTopUpTicketTravelCardWithExchangeCmd1();
        tradedCartItem.setTradedTicket(null);
        tradedCartItem.setRefundType(CANCEL_SURRENDER_REFUND.code());
        tradedCartItem.setExchangedDate(new DateTime());
        ProductItemDTO productItemDTO = nonOddPeriodTravelCardServiceImpl.convertCartItemCmdImplToProductItemDTO(tradedCartItem);
        assertEquals(CANCEL_SURRENDER_REFUND.code(), productItemDTO.getRefundType());
    }
    
}
