package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestPayAsYouGo1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestPayAsYouGoWithBackdatedRefundTrue;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestPayAsYouGoWithDeceasedCustomerRefundTrue;
import static com.novacroft.nemo.test_support.CartTestUtil.CART_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithThreeProductItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.PrePaidTicketDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Unit tests for New Travel Card Service
 */
public class TravelCardServiceTest {

    private TravelCardServiceImpl service;
    private TravelCardServiceImpl mockService;
    private CartDTO cartDTO;
    private CartItemCmdImpl cartItemCmd;
    private CartService mockCartService;
    private CartAdministrationService mockNewCartAdministrationService;
    private SystemParameterService mockSystemParameterService;
    private CardDataService mockCardDataService;
    private ProductDataService mockProductDataService;
    private PrePaidTicketDataService mockPrePaidTicketDataService;
    private CardUpdateService mockCardUpdateService;
    private AdHocLoadSettlementDataService mockAdHocLoadSettlementDataService;


    @Before
    public void setUp() {
        service = new TravelCardServiceImpl();
        cartItemCmd = new CartItemCmdImpl();
        cartDTO = new CartDTO();

        mockCartService = mock(CartService.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockCardDataService = mock(CardDataService.class);
        mockProductDataService = mock(ProductDataService.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockAdHocLoadSettlementDataService = mock(AdHocLoadSettlementDataService.class);
        mockPrePaidTicketDataService = mock(PrePaidTicketDataServiceImpl.class);

        service.cartAdminService = mockNewCartAdministrationService;
        service.cartService = mockCartService;
        service.systemParameterService = mockSystemParameterService;
        service.cardDataService = mockCardDataService;
        service.productDataService = mockProductDataService;
        service.cardUpdateService = mockCardUpdateService;
        service.adHocLoadSettlementDataService = mockAdHocLoadSettlementDataService;
        service.prePaidTicketDataService = mockPrePaidTicketDataService;
        
        mockService = mock(TravelCardServiceImpl.class);
        mockService.cartService = mockCartService;
        
    }

    @Test
    public void addCartItemForExistingCardShouldAddToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForExistingCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void addCartItemForExistingCardWithNullCartItemCmdShouldShowAssertionException() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(null);
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForExistingCard(cartDTO, null);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
    }

    @Test
    public void addCartItemForNewCardShouldAddToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForNewCard(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
        assertEquals(cartDTO.getCartItems().size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void addCartItemForNewCardWithNullCartItemCmdShouldNotAddToCart() {
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(null);
        when(mockNewCartAdministrationService.applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        when(mockNewCartAdministrationService.applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartDTO = service.addCartItemForNewCard(cartDTO, null);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ProductItemDTO.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyRefundableDeposit(any(CartDTO.class), any(CartItemCmdImpl.class));
        verify(mockNewCartAdministrationService, atLeastOnce()).applyShippingCost(any(CartDTO.class), any(CartItemCmdImpl.class));
    }
    
    @Test
    public void isTicketLongerThanTenMonthsTwelveDaysShouldReturnTrue() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS)).thenReturn(OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS);
        
        boolean result = service.isTicketLongerThanTenMonthsTwelveDays(10, 17, 320);

        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS);
        assertTrue(result);
    }
    
    @Test
    public void isTicketLongerThanTenMonthsTwelveDaysShouldReturnFalse() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS)).thenReturn(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS)).thenReturn(OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS);
        
        boolean result = service.isTicketLongerThanTenMonthsTwelveDays(9, 17, 290);

        verify(mockSystemParameterService, never()).getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS);
        assertFalse(result);
    } 

    @Test
    public void shouldAddPrePayTicketToCubic() {
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTO());
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO1WithId());
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO1WithId());
        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockPrePaidTicketDataService.findById(any(Long.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        when(mockCardUpdateService.requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(), any(Long.class)))
                        .thenReturn(new Integer(1));
        CartCmdImpl cartCmd =  CartCmdTestUtil.getPrePayTicketCartCmdImpl();
        cartCmd.getCartDTO().getOrder().getOrderItems().add(ProductItemTestUtil.getTestTravelCardProductDTO1());              
        service.addPrePayTicketToCubic(cartCmd);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(),
                        any(Long.class));
    }

    @Test
    public void shouldNotAddPrePayTicketSettlement() {
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO1WithId());
        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockPrePaidTicketDataService.findById(any(Long.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        when(mockCardUpdateService
                .requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(), any(Long.class)))
                        .thenReturn(new Integer(1));
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayTicketCartCmdImpl();
        cartCmd.getCartDTO().getOrder().getOrderItems().add(ProductItemTestUtil.getTestTravelCardProductDTO1()); 
        service.addPrePayTicketToCubic(cartCmd);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(),
                        any(Long.class));
    }

    @Test
    public void shouldAddPrePayTicketSettlementUsingTodaysDate() {
        when(mockAdHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(anyInt(), anyString())).thenReturn(null);
        when(mockAdHocLoadSettlementDataService.createOrUpdate(any(AdHocLoadSettlementDTO.class))).thenReturn(
                        SettlementTestUtil.getTestAdHocLoadSettlementDTO1WithId());
        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockPrePaidTicketDataService.findById(any(Long.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        when(mockCardUpdateService
                .requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(), any(Long.class)))
                        .thenReturn(new Integer(1));
        CartCmdImpl cartCmd = CartCmdTestUtil.getPrePayValueCartCmdWithNoPaymentCardSettlementImpl();
        cartCmd.getCartDTO().getOrder().getOrderItems().add(ProductItemTestUtil.getTestTravelCardProductDTO1()); 
        service.addPrePayTicketToCubic(cartCmd);
        verify(mockCardUpdateService, times(1)).requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(),
                        any(Long.class));
    }

    @Test
    public void shouldAddUpdateItems() {
        when(mockService.addUpdateItems(CART_ID_1, getTestPayAsYouGo1())).thenCallRealMethod();
        when(mockCartService.findById(any(Long.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        doNothing().when(mockService).updateCartItemForBackdatedRefund(any(CartItemCmdImpl.class), any(ProductItemDTO.class));
        doNothing().when(mockService).updateCartItemForDeceasedCustomerRefund(any(CartItemCmdImpl.class), any(ProductItemDTO.class));
        CartDTO testCartDto = getNewCartDTOWithThreeProductItems();
        when(mockCartService.updateCart(any(CartDTO.class))).thenReturn(testCartDto);
        when(mockCartService.postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class))).thenReturn(testCartDto);
        
        CartDTO actualResult = mockService.addUpdateItems(CART_ID_1, getTestPayAsYouGo1());
        
        assertEquals(testCartDto, actualResult);
        verify(mockCartService, atLeastOnce()).findById(any(Long.class));
        verify(mockService, atLeastOnce()).updateCartItemForBackdatedRefund(any(CartItemCmdImpl.class), any(ProductItemDTO.class));
        verify(mockService, atLeastOnce()).updateCartItemForDeceasedCustomerRefund(any(CartItemCmdImpl.class), any(ProductItemDTO.class));
        verify(mockCartService, atLeastOnce()).updateCart(any(CartDTO.class));
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(testCartDto);
    }
    
    @Test
    public void shouldUpdateCartItemForBackdatedRefund() {
        ProductItemDTO mockProductItemDTO = mock(ProductItemDTO.class);
        doCallRealMethod().when(mockService).updateCartItemForBackdatedRefund(getTestPayAsYouGoWithBackdatedRefundTrue(), mockProductItemDTO);
        doNothing().when(mockProductItemDTO).setTicketBackdated(any(Boolean.class));
        doNothing().when(mockProductItemDTO).setBackdatedRefundReasonId(any(Long.class));
        mockService.updateCartItemForBackdatedRefund(getTestPayAsYouGoWithBackdatedRefundTrue(), mockProductItemDTO);
        verify(mockProductItemDTO, atLeastOnce()).setTicketBackdated(any(Boolean.class));
        verify(mockProductItemDTO, atLeastOnce()).setBackdatedRefundReasonId(any(Long.class));
    }

    @Test
    public void updateCartItemForDeceasedCustomerRefund() {
        ProductItemDTO mockProductItemDTO = mock(ProductItemDTO.class);
        doCallRealMethod().when(mockService).updateCartItemForDeceasedCustomerRefund(getTestPayAsYouGoWithDeceasedCustomerRefundTrue(), mockProductItemDTO);
        doNothing().when(mockProductItemDTO).setDeceasedCustomer(any(Boolean.class));
        mockService.updateCartItemForDeceasedCustomerRefund(getTestPayAsYouGoWithDeceasedCustomerRefundTrue(), mockProductItemDTO);
        verify(mockProductItemDTO, atLeastOnce()).setDeceasedCustomer(any(Boolean.class));
    }

}
