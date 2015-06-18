package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.ADMINISTRATION_FEE_CART_TOTAL_1;
import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.ADMINISTRATION_FEE_CART_TOTAL_2;
import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.ADMINISTRATION_FEE_CART_TOTAL_3;
import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.ADMINISTRATION_FEE_DEFAULT;
import static com.novacroft.nemo.test_support.AdministrationFeeTestUtil.getTestAdministrationFeeDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_2;
import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithCardRefundableDepositItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithExpiredProductItems;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithShippingItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithThreeProductItems;
import static com.novacroft.nemo.test_support.CartTestUtil.getShippingMethodItemDTO;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PRODUCT_AVAILABLE_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PRODUCT_START_AFTER_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AdministrationFeeService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

/**
 * Unit tests for New Cart Administration Service
 */
public class CartAdministrationServiceTest {

    private CartAdministrationServiceImpl service;
    private CartDTO cartDTO;
    private CartItemCmdImpl cartItemCmd;
    private CartService mockCartService;
    private CartDataService mockCartDataService;
    private SystemParameterService mockSystemParameterService;
    private ShippingMethodDataService mockShippingMethodDataService;
    private AdministrationFeeService mockAdministrationFeeService;
    @Before
    public void setUp() {
        service = new CartAdministrationServiceImpl();
        cartItemCmd = new CartItemCmdImpl();
        cartDTO = new CartDTO();

        mockCartService = mock(CartService.class);
        mockCartDataService = mock(CartDataService.class);
        mockShippingMethodDataService = mock(ShippingMethodDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockAdministrationFeeService = mock(AdministrationFeeService.class);

        service.cartService = mockCartService;
        service.cartDataService = mockCartDataService;
        service.systemParameterService = mockSystemParameterService;
        service.shippingMethodDataService = mockShippingMethodDataService;
        service.administrationFeeService = mockAdministrationFeeService;
    }

    @Test
    public void applyRefundableDepositShouldCallCartServiceReturnCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(CardRefundableDepositItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartDTO.setCardId(CARD_ID_1);
        CartDTO returnCart = service.applyRefundableDeposit(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(CardRefundableDepositItemDTO.class));
        assertEquals(returnCart.getCartItems().size(), 3);
    }

    @Test
    public void applyRefundableDepositShouldNotCallCartServiceReturnCart() {
        cartDTO = getNewCartDTOWithCardRefundableDepositItemDTO();
        cartDTO.setCardId(CARD_ID_1);
        CartDTO returnCart = service.applyRefundableDeposit(cartDTO, cartItemCmd);
        verify(mockCartService, never()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(CardRefundableDepositItemDTO.class));
        assertEquals(CARD_ID_1, returnCart.getCardId());
    }

    @Test
    public void applyShippingCostShouldCallCartServiceReturnCart() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ShippingMethodItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartDTO.setCardId(CARD_ID_1);
        CartDTO returnCart = service.applyShippingCost(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ShippingMethodItemDTO.class));
        assertEquals(returnCart.getCartItems().size(), 3);
    }

    @Test
    public void removeRefundableDepositAndShippingCostShouldCallCartServiceDeleteMethodReturnCart() {
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithThreeProductItems());
        cartDTO = getNewCartDTOWithCardRefundableDepositItemDTO();
        CartDTO returnCart = service.removeRefundableDepositAndShippingCost(cartDTO);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(returnCart.getCartItems().size(), 3);
    }

    @Test
    public void removeRefundableDepositAndShippingCostShouldNotCallCartServiceDeleteMethodReturnCart() {
        cartDTO = getNewCartDTOWithThreeProductItems();
        cartDTO.setCardId(CARD_ID_1);
        CartDTO returnCart = service.removeRefundableDepositAndShippingCost(cartDTO);
        verify(mockCartService, never()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(CARD_ID_1, returnCart.getCardId());
    }

    @Test
    public void getProductStartDateListShouldReturnListOfFiveDatesAfterTommorow() {
        when(mockSystemParameterService.getIntegerParameterValue(PRODUCT_START_AFTER_DAYS)).thenReturn(1);
        when(mockSystemParameterService.getIntegerParameterValue(PRODUCT_AVAILABLE_DAYS)).thenReturn(5);
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START)).thenReturn(15);
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START)).thenReturn(15);
        GregorianCalendar cal = new GregorianCalendar(Locale.UK);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        SelectListDTO selectList = service.getProductStartDateList();
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertNotEquals(null, selectList.getOptions());
    }

    @Test
    public void getUserProductStartDateListShouldReturnListOfFiveDatesAfterTommorow() {
        when(mockSystemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)).thenReturn(1);
        when(mockSystemParameterService.getIntegerParameterValue(PRODUCT_AVAILABLE_DAYS)).thenReturn(5);
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START)).thenReturn(15);
        when(mockSystemParameterService.getIntegerParameterValue(MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START)).thenReturn(15);
       
        GregorianCalendar cal = new GregorianCalendar(Locale.UK);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        SelectListDTO selectList = service.getUserProductStartDateList();
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(anyString());
        assertNotEquals(null, selectList.getOptions());
    }

    @Test
    public void addApprovalIdShouldCallCartServiceReturnApprovalId() {
        when(mockCartDataService.addApprovalId(any(CartDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        Long approvalId = service.addApprovalId(cartDTO);
        verify(mockCartDataService, atLeastOnce()).addApprovalId(any(CartDTO.class));
        assertEquals(null, approvalId);
    }
    
    @Test
	public void shouldAddAdministrationFeeToCart() {
		service.addOrRemoveAdministrationFeeToCart(CartTestUtil.getNewCartDTOWithProductItemWithRefund(), CardTestUtil.CARD_ID, CartType.LOST_REFUND.code());
		verify(mockCartService).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), (Class<? extends ItemDTO>) anyObject());
	}
    
    @Test
	public void shouldRemoveAdministrationFeeFromCart() {
		service.addOrRemoveAdministrationFeeToCart(CartTestUtil.getNewCartDTOWithItem(), CardTestUtil.CARD_ID, CartType.LOST_REFUND.code());
		verify(mockCartService).deleteItem(any(CartDTO.class), anyLong());
	}
                 
    @Test
    public void getDefaultAdministrationFeeValueShouldInvokeService(){
        service.getDefaultAdministrationFeeValue(CartType.FAILED_CARD_REFUND.code());
        verify(mockAdministrationFeeService).getAdministrationFeeDTO(anyString());
    }
    
    @Test
    public void getDefaultAdministrationFeeValueShouldReturnAdminFeeTest(){
        when(mockAdministrationFeeService.getAdministrationFeeDTO(anyString())).thenReturn(getTestAdministrationFeeDTO1());
        assertEquals(ADMINISTRATION_FEE_PRICE_1, service.getDefaultAdministrationFeeValue(CartType.FAILED_CARD_REFUND.code()));
        verify(mockAdministrationFeeService).getAdministrationFeeDTO(anyString());
    }
    
    @Test
    public void updateAdministrationFeeAmountShouldUpdateAdministrationPriceToDefaultAmountTest(){
        cartDTO = getNewCartDTOWithItem();
        service.updateAdministrationFeeAmount(cartDTO, ADMINISTRATION_FEE_CART_TOTAL_1, ADMINISTRATION_FEE_PRICE_1, CardTestUtil.CARD_ID, CartType.FAILED_CARD_REFUND.code());
        assertEquals(ADMINISTRATION_FEE_PRICE_1, getAdministrationFeePrice(cartDTO));
    }
    
    @Test
    public void updateAdministrationFeeAmountShouldUpdateAdministrationPriceToCartTotal2Test(){
        cartDTO = getNewCartDTOWithItem();
        service.updateAdministrationFeeAmount(cartDTO, ADMINISTRATION_FEE_CART_TOTAL_2, ADMINISTRATION_FEE_PRICE_2, CardTestUtil.CARD_ID, CartType.FAILED_CARD_REFUND.code());
        assertEquals(ADMINISTRATION_FEE_CART_TOTAL_2, getAdministrationFeePrice(cartDTO));
    }
    
    @Test
    public void updateAdministrationFeeAmountShouldUpdateAdministrationPriceToCartTotal3Test(){
        cartDTO = getNewCartDTOWithItem();
        service.updateAdministrationFeeAmount(cartDTO, ADMINISTRATION_FEE_CART_TOTAL_3, ADMINISTRATION_FEE_DEFAULT , CardTestUtil.CARD_ID, CartType.FAILED_CARD_REFUND.code());
        assertEquals(ADMINISTRATION_FEE_CART_TOTAL_3, getAdministrationFeePrice(cartDTO));
    }
    
    @Test
    public void updateAdministrationFeeAmountShouldNotDoAnythingTest(){
        cartDTO = getNewCartDTOWithExpiredProductItems();
        service.updateAdministrationFeeAmount(cartDTO, ADMINISTRATION_FEE_CART_TOTAL_3, ADMINISTRATION_FEE_DEFAULT , CardTestUtil.CARD_ID, CartType.FAILED_CARD_REFUND.code());
        assertEquals(new Integer(0), getAdministrationFeePrice(cartDTO));
    }
    
    protected Integer getAdministrationFeePrice(CartDTO cart){
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof AdministrationFeeItemDTO){
                return itemDTO.getPrice();
            }
        }
        return 0;
    }

    @Test
    public void applyShippingCostShouldCallCartServiceReturnCartTest() {
        when(mockCartService.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ShippingMethodItemDTO.class))).thenReturn(getNewCartDTOWithThreeProductItems());
        cartDTO = getNewCartDTOWithShippingItem();
        cartDTO.setCardId(CARD_ID_1);
        CartDTO returnCart = service.applyShippingCost(cartDTO, cartItemCmd);
        verify(mockCartService, atLeastOnce()).addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(ShippingMethodItemDTO.class));
        assertEquals(returnCart.getCartItems().size(), 3);
    }
    
    @Test
    public void removeRefundableDepositAndShippingCostShouldCallCartServiceDeleteMethodTest() {
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithShippingItem());
        cartDTO = getNewCartDTOWithCardRefundableDepositItemDTO();
        CartDTO returnCart = service.removeRefundableDepositAndShippingCost(cartDTO);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(returnCart.getCartItems().size(), 1);
    }
    
    @Test
    public void isCartContainUserAddedItemsShouldReturnFalseTest(){
        cartDTO = getNewCartDTOWithShippingItem();
        assertFalse(service.isCartContainUserAddedItems(cartDTO));
    }
    
    @Test
    public void isCartContainUserAddedItemsShouldReturnTrueTest(){
        cartDTO = getNewCartDTOWithExpiredProductItems();
        assertTrue(service.isCartContainUserAddedItems(cartDTO));
    }
    
    @Test
    public void isShippingCostPresentInCartShouldReturnTrue() {
        cartDTO = getNewCartDTOWithShippingItem();
        assertTrue(service.isShippingCostPresentInCart(cartDTO));
    }
    @Test
    public void isShippingCostPresentInCartShouldReturnFalse() {
        cartDTO = getNewCartDTOWithItem();
        assertFalse(service.isShippingCostPresentInCart(cartDTO));
    }
    
    @Test
    public void getRefundAmountInItemDTOShouldReturnItemDTOPriceTest() {
        assertEquals(0, service.getRefundAmountInItemDTO(getShippingMethodItemDTO()));
    }
}
