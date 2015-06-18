package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.OysterCardNumberUtilTest.TWELVE_DIGIT_CARD_NUMBER;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO3;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestPrePayTicketSlot1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestRenewCartCmd3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.INVALID_QUICKBUY_EXPIRYDATE_DAYS_TO_ADD;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PRODUCT_AVAILABLE_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_DATE_3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_DATE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_DATE_3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.USER_PRODUCT_START_AFTER_DAYS;
import static com.novacroft.nemo.test_support.CartItemTestUtil.VALID_QUICKBUY_EXPIRYDATE_DAYS_TO_ADD;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCardList1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewProductItemDTOWithEndDateBeforeDateOfRefund;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.UserCartValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Unit tests for renew travelcard service
 */
public class RenewTravelCardServiceTest {

    private RenewTravelCardServiceImpl service;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private SystemParameterService mockSystemParameterService;
    private TopUpTicketService mockTopUpTicketService;
    private UserCartValidator mockUserCartValidator;
    private ZoneService mockZoneService;
    private CartDTO mockCartDTO;
    private CartService mockCartService;

    private CartCmdImpl mockCubicCmd;
    private List<CartItemCmdImpl> mockCubicItemList;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        service = new RenewTravelCardServiceImpl();

        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockTopUpTicketService = mock(TopUpTicketService.class);
        mockUserCartValidator = mock(UserCartValidator.class);
        mockZoneService = mock(ZoneService.class);
        mockCartDTO = mock(CartDTO.class);
        mockCartService = mock(CartService.class);

        service.cardDataSerivce = mockCardDataService;
        service.getCardService = mockGetCardService;
        service.systemParameterService = mockSystemParameterService;
        service.topUpTicketService = mockTopUpTicketService;
        service.userCartValidator = mockUserCartValidator;
        service.zoneService = mockZoneService;
        service.cartService = mockCartService;

        mockCubicCmd = mock(CartCmdImpl.class);
        mockCubicItemList = mock(List.class);
        mockCartDTO.setPpvRenewItemAddFlag(false);
        when(mockTopUpTicketService.updateCartItemCmdWithProductsFromCubic(any(CartCmdImpl.class))).thenReturn(mockCubicCmd);
        when(mockCubicCmd.getCartItemList()).thenReturn(mockCubicItemList);
    }

    @Test
    public void getCartCmdFromCubicShouldPopulateRenewTravelCardDetails() {
        CardDTO cardDTO = getTestCardDTO1();
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(cardDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO3());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(
                        QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        when(mockCubicCmd.getCartItemList()).thenReturn(getTestTravelCardList1());
        when(mockZoneService.isZonesOverlapWithTravelCardZones(any(CartItemCmdImpl.class), any(Date.class), any(Date.class), any(Integer.class), any(Integer.class))).thenReturn(false);
        CartCmdImpl cartCmd = service.getCartItemsFromCubic(mockCartDTO, CARD_ID_1);

        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertTrue(cartCmd.getCartItemList().size() == 3);
    }

    @Test
    public void getCartCmdFromCubicShouldReturnNullWithNullCartItems() {
        CardDTO cardDTO = getTestCardDTO1();
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(cardDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO3());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(0);
        when(mockCubicCmd.getCartItemList()).thenReturn(getTestTravelCardList1());
        when(mockZoneService.isZonesOverlapWithTravelCardZones(any(CartItemCmdImpl.class), any(Date.class), any(Date.class), any(Integer.class), any(Integer.class))).thenReturn(false);

        CartCmdImpl cartCmd = service.getCartItemsFromCubic(mockCartDTO, CARD_ID_1);

        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertTrue(cartCmd.getCartItemList().isEmpty());
    }

    @Test
    public void getCartItemsFromCubicShouldCallEmptyCart() {
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO3());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(
                        USER_PRODUCT_START_AFTER_DAYS);
        when(mockCubicCmd.getCartItemList()).thenReturn(getTestTravelCardList1());
        when(mockZoneService.isZonesOverlapWithTravelCardZones(any(CartItemCmdImpl.class), any(Date.class), any(Date.class), any(Integer.class), any(Integer.class))).thenReturn(false);
        mockCartDTO.setPpvRenewItemAddFlag(false);
        service.getCartItemsFromCubic(mockCartDTO, CARD_ID_1);
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        verify(mockCartService, atLeastOnce()).emptyCart(any(CartDTO.class));
     }

    @Test
    public void getCartItemsFromCubicShouldReturnThreeCartItems() {
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO3());
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        when(mockCubicCmd.getCartItemList()).thenReturn(getTestTravelCardList1());
        when(mockZoneService.isZonesOverlapWithTravelCardZones(any(CartItemCmdImpl.class), any(Date.class), any(Date.class), any(Integer.class), any(Integer.class))).thenReturn(false);
        CartCmdImpl cartCmd = service.getCartItemsFromCubic(mockCartDTO, CARD_ID_1);
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        verify(mockCartService, atLeastOnce()).emptyCart(any(CartDTO.class));
        assertEquals(3, cartCmd.getCartItemList().size());
    }

    @Test
    public void getCardProductDTOFromCubicShouldReturnCardInfoResponse() {
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO3());

        CardInfoResponseV2DTO cardInfoResponseV2DTO = service.getCardProductDTOFromCubic(TWELVE_DIGIT_CARD_NUMBER);

        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        assertNotNull(cardInfoResponseV2DTO);
    }

    @Test
    public void getCardProductDTOFromCubicShouldReturnNullWithNullCardInfoResponse() {
        when(mockGetCardService.getCard(anyString())).thenReturn(null);

        CardInfoResponseV2DTO cardInfoResponseV2DTO = service.getCardProductDTOFromCubic(TWELVE_DIGIT_CARD_NUMBER);

        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        assertNull(cardInfoResponseV2DTO);
    }

    @Test
    public void getCardNumberFromCardIdShouldReturnCardNumber() {
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(getTestCardDTO1());

        String cartNumber = service.getCardNumberFromCardId(CARD_ID_1);

        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        assertEquals(OYSTER_NUMBER_1, cartNumber);
    }

    @Test
    public void persistTravelCardFromPrePayTicketSlotShouldAddCartItem() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        when(mockCubicCmd.getCartItemList()).thenReturn(getTestTravelCardList1());
        when(mockZoneService.isZonesOverlapWithTravelCardZones(any(CartItemCmdImpl.class), any(Date.class), any(Date.class), any(Integer.class), any(Integer.class))).thenReturn(false);
        service.persistTravelCardFromPrePayTicketSlotAlongWithCart(getTestPrePayTicketSlot1(), CARD_ID_1, mockCartDTO);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), (CartItemCmdImpl) any(), any(Class.class));
    }

    @Test
    public void shouldReturnStartDate() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.USER_PRODUCT_START_AFTER_DAYS)).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
        String startDate = service.getStartDate(TRAVEL_END_DATE_3);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.USER_PRODUCT_START_AFTER_DAYS);
        assertEquals(DateUtil.formatDate(DateUtil.addDaysToDate(TRAVEL_END_DATE_3, 1)), startDate);
    }

    @Test
    public void shouldReturnEndDate() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.USER_PRODUCT_START_AFTER_DAYS)).thenReturn(USER_PRODUCT_START_AFTER_DAYS);
        String endDate = service.getEndDate(TRAVEL_START_DATE_3, TRAVEL_END_DATE_3);
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.USER_PRODUCT_START_AFTER_DAYS);
        assertEquals("01/12/2013", endDate);
    }

    @Test
    public void getProductStartDateListShouldReturnStartDates() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.PRODUCT_AVAILABLE_DAYS)).thenReturn(PRODUCT_AVAILABLE_DAYS);

        SelectListDTO startDateList = service.getRenewProductStartDateList(TRAVEL_START_DATE_3);

        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(CartAttribute.PRODUCT_AVAILABLE_DAYS);
        assertEquals(PRODUCT_AVAILABLE_DAYS.intValue(), startDateList.getOptions().size());
    }

    @Test
    public void renewProductsShouldRenew() {
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        ProductItemDTO product = (ProductItemDTO) getNewProductItemDTOWithEndDateBeforeDateOfRefund();
        product.setStartDate(new Date());
        items.add(product);
        items.add(product);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartItems(items);
        CartCmdImpl cartCmdImpl = getTestRenewCartCmd3();
        service.renewProducts(cartCmdImpl, cartDTO);
        assertEquals(cartDTO.getCartItems().get(0).getId(),cartCmdImpl.getCartItemList().get(0).getId());
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), (CartItemCmdImpl) any(), any(Class.class));
    }

    @Test
    public void renewProductsShouldNotRenewWithEmptyCartCmd() {
        service.renewProducts(new CartCmdImpl(), mockCartDTO);
        verify(mockCartService, never()).addUpdateItem(any(CartDTO.class), (CartItemCmdImpl) any(), any(Class.class));
    }

    @Test(expected = NullPointerException.class)
    public void renewProductsShouldNotRenewWithNullCartCmd() {
        service.renewProducts(null, mockCartDTO);
        verify(mockCartService, never()).addUpdateItem(any(CartDTO.class), (CartItemCmdImpl) any(), any(Class.class));
    }

    @Test
    public void shouldReturnRenewEndDate() {
        String endDate = service.getRenewEndDate(TRAVEL_START_DATE_2, TRAVEL_START_DATE_3, TRAVEL_END_DATE_3);
        assertEquals("27/11/2013", endDate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRecogniseOverlap() {
        RenewTravelCardServiceImpl mockService = mock(RenewTravelCardServiceImpl.class);
        mockService.topUpTicketService = mockTopUpTicketService;

        BindException mockErrors = mock(BindException.class);

        when(mockService.validateOverlappingProducts(any(CartItemCmdImpl.class), any(List.class), any(BindException.class))).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(true, false);

        doCallRealMethod().when(mockService).isOverlappingWithCubicProducts(any(CartItemCmdImpl.class), anyLong());

        CartItemCmdImpl mockCartItem = mock(CartItemCmdImpl.class);
        Long cardId = null;

        assertEquals(true, mockService.isOverlappingWithCubicProducts(mockCartItem, cardId));
        assertEquals(false, mockService.isOverlappingWithCubicProducts(mockCartItem, cardId));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallValidationForOverlappingProducts() {
        RenewTravelCardServiceImpl mockService = mock(RenewTravelCardServiceImpl.class);
        mockService.userCartValidator = mockUserCartValidator;

        doCallRealMethod().when(mockService).validateOverlappingProducts(any(CartItemCmdImpl.class), any(List.class), any(BindException.class));

        CartItemCmdImpl cartItem = new CartItemCmdImpl();
        cartItem.setStartDate(TRAVEL_START_DATE_3);
        cartItem.setEndDate(TRAVEL_END_DATE_3);
        BindException errors = null;
        mockService.validateOverlappingProducts(cartItem, mockCubicItemList, errors);
    }
    
    @Test
    public void renewTravelCardsShouldNotBeUnderQuickBuyProductBasedOnPPTExpiryDate() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        String expiryDate = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), VALID_QUICKBUY_EXPIRYDATE_DAYS_TO_ADD));
        assertEquals(false, service.isQuickBuyProduct(expiryDate));
    }
    
    @Test
    public void renewTravelCardsShouldBeUnderQuickBuyProductBasedOnPPTExpiryDate() {
        when(mockSystemParameterService.getIntegerParameterValue(CartAttribute.QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY)).thenReturn(QUICK_BUY_WITHIN_NUMBER_OF_DAYS_OF_EXPIRY);
        String expiryDate = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), INVALID_QUICKBUY_EXPIRYDATE_DAYS_TO_ADD));
        assertTrue(service.isQuickBuyProduct(expiryDate));
    }
    
}
