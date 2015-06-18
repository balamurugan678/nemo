package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.test_support.CartTestUtil.SESSION_ATTRIBUTE_SHOPPING_CART_DATA;
import static com.novacroft.nemo.test_support.CartTestUtil.getCardRefundableDepositItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewAdminFeeItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewGoodwillPaymentItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewPayAsYouGoItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewProductItemDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getShippingMethodItemDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

public class CartUtilTest {
    private CartDTO cartDTO;
    private HttpSession mockHttpSession;
    
    @Before
    public void setUp() {
        mockHttpSession = mock(HttpSession.class);
        doNothing().when(mockHttpSession).removeAttribute(anyString());
        doNothing().when(mockHttpSession).setAttribute(anyString(), anyString());
    }

    @Test
    public void getAutoTopUpAmountShouldReturnTopUpAmount() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        Integer amount = CartUtil.getAutoTopUpAmount(cartDTO.getCartItems());
        assertEquals(AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount(), amount);
    }

    @Test
    public void isAutoTopUpPresentReturnsTrue() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        boolean result = CartUtil.isAutoTopUpPresent(cartDTO.getCartItems());
        assertTrue(result);
    }

    @Test
    public void isAutoTopUpPresentReturnsFalse() {
        cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        boolean result = CartUtil.isAutoTopUpPresent(cartDTO.getCartItems());
        assertFalse(result);
    }

    @Test
    public void getAutoTopUpAmountShouldReturnZeroAmountWithNoItemFound() {
        cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        Integer amount = CartUtil.getAutoTopUpAmount(cartDTO.getCartItems());
        assertEquals(AutoLoadState.NO_TOP_UP.topUpAmount(), amount);
    }

    @Test
    public void isItemDTOAdministrationFeeItemDTOShouldReturnTrue() {
    	assertTrue(CartUtil.isItemDTOAdministrationFeeItemDTO(getNewAdminFeeItemDTO()));
    }
    
    @Test
    public void isItemDTOAdministrationFeeItemDTOShouldReturnFalse() {
    	assertFalse(CartUtil.isItemDTOAdministrationFeeItemDTO(getNewProductItemDTO()));
    }
    
    @Test
    public void isItemDTOPayAsYouGoItemDTOShouldReturnTrue() {
    	assertTrue(CartUtil.isItemDTOPayAsYouGoItemDTO(getNewPayAsYouGoItemDTO()));
    }
    
    @Test
    public void isItemDTOPayAsYouGoItemDTOShouldReturnFalse() {
    	assertFalse(CartUtil.isItemDTOPayAsYouGoItemDTO(getNewProductItemDTO()));
    }
    
    @Test
    public void isItemDTOProductItemDTOShouldReturnTrue() {
    	assertTrue(CartUtil.isItemDTOProductItemDTO(getNewProductItemDTO()));
    }
    
    @Test
    public void isItemDTOProductItemDTOShouldReturnFalse() {
    	assertFalse(CartUtil.isItemDTOProductItemDTO(getNewAdminFeeItemDTO()));
    }
    
    @Test
    public void isItemDTOGoodwillPaymentItemDTOShouldReturnTrue() {
    	assertTrue(CartUtil.isItemDTOGoodwillPaymentItemDTO(getNewGoodwillPaymentItemDTO()));
    }
    
    @Test
    public void isItemDTOGoodwillPaymentItemDTOShouldReturnFalse() {
    	assertFalse(CartUtil.isItemDTOGoodwillPaymentItemDTO(getNewProductItemDTO()));
    }

    @Test
    public void isItemDTOCardShippingMethodItemDTOShouldReturnTrue() {
        assertTrue(CartUtil.isItemDTOCardShippingMethodItemDTO(getShippingMethodItemDTO()));
    }
    
    @Test
    public void isItemDTOCardShippingMethodItemDTOShouldReturnFalse() {
        assertFalse(CartUtil.isItemDTOCardShippingMethodItemDTO(getNewProductItemDTO()));
    }
    
    @Test
    public void isItemDTOAutoTopUpItemDTOShouldReturnTrue() {
        assertTrue(CartUtil.isItemDTOAutoTopUpItemDTO(new AutoTopUpConfigurationItemDTO()));
    }
    
    @Test
    public void isItemDTOAutoTopUpItemDTOShouldReturnFalse() {
        assertFalse(CartUtil.isItemDTOAutoTopUpItemDTO(getNewGoodwillPaymentItemDTO()));
    }
    
    @Test
    public void getCartTotalShouldReturnValue() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        Integer amount = CartUtil.getCartTotal(cartDTO.getCartItems());
        assertTrue(amount == 5940);
    }

    @Test
    public void isItemDTOCardRefundableDepositItemDTOShouldReturnTrue() {
        Boolean itemDTOisCardRefundableDepositItemDTO = CartUtil.isItemDTOCardRefundableDepositItemDTO(getCardRefundableDepositItemDTO());
        assertTrue(itemDTOisCardRefundableDepositItemDTO);
    }
    
    @Test
    public void isItemDTOCardRefundableDepositItemDTOShouldReturnFalse() {
        Boolean itemDTOisCardRefundableDepositItemDTO = CartUtil.isItemDTOCardRefundableDepositItemDTO(getNewProductItemDTO());
        assertFalse(itemDTOisCardRefundableDepositItemDTO);
    }
    
    
    @Test
    public void getCartRefundTotalShouldReturnCorrectValue() {
        cartDTO = CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem();
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        cartDTO.addCartItem(CartTestUtil.getNewGoodwillPaymentItemDTO());
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTOWithRefund());
        Integer amount = CartUtil.getCartRefundTotal(cartDTO.getCartItems());
        assertTrue(amount == 4440);
    }

    @Test
    public void getRefundableDepotAmountShouldReturnValue() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        Integer amount = CartUtil.getCardRefundableDepositAmount(cartDTO.getCartItems());
        assertEquals(CartTestUtil.TEST_REFUNDABLE_DEPOSIT_PRICE, amount);
    }

    @Test
    public void getRefundableDepositAmountShouldReturnZeroAmountWithNoItemFound() {
        cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        Integer amount = CartUtil.getCardRefundableDepositAmount(cartDTO.getCartItems());
        assertTrue(amount == 0);
    }

    @Test
    public void getRefundableDepositItemShouldReturnItem() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        CardRefundableDepositItemDTO item = CartUtil.getCardRefundableDepositItem(cartDTO.getCartItems());
        assertNotNull(item);
    }

    @Test
    public void getPayAsYouGoItemShouldReturnItem() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        PayAsYouGoItemDTO item = CartUtil.getPayAsYouGoItem(cartDTO.getCartItems());
        assertNotNull(item);
    }

    @Test
    public void getAutoTopupItemShouldReturnItem() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        AutoTopUpConfigurationItemDTO item = CartUtil.getAutoTopUpItem(cartDTO.getCartItems());
        assertNotNull(item);
    }

    @Test
    public void getShippingMethodItemShouldReturnItem() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        ShippingMethodItemDTO item = CartUtil.getShippingMethodItem(cartDTO.getCartItems());
        assertNotNull(item);
    }

    @Test
    public void getShippingMethodAmountShouldShouldReturnValue() {
        cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        Integer amount = CartUtil.getShippingMethodAmount(cartDTO.getCartItems());
        assertEquals(CartTestUtil.TEST_SHIPPING_METHOD_ITEM_PRICE, amount);
    }

    @Test
    public void getShippingMethodAmountShouldReturnZeroAmountWithNoItemFound() {
        cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        Integer amount = CartUtil.getShippingMethodAmount(cartDTO.getCartItems());
        assertTrue(amount == 0);
    }

    @Test
    public void getAdministrationFeeItemShouldReturnItem() {
        cartDTO = CartTestUtil.getNewCartDTOWithItem();
        AdministrationFeeItemDTO item = CartUtil.getAdministrationFeeItem(cartDTO.getCartItems());
        assertNotNull(item);
    }

	@Test
    public void cartContainsBothBusPassAndTravelCard(){
        cartDTO = CartTestUtil.getTestCartDTOWithBusAndTravelCardItems();
        Boolean actualResult = CartUtil.isCartContainsBusPassAndTravelCard(cartDTO.getCartItems());
        assertEquals(Boolean.TRUE, actualResult);
    }
    
    @Test
    public void cartContainsBothTravelCardAndPayAsYouGo(){
        cartDTO = CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems();
        Boolean actualResult = CartUtil.isCartContainsBusPassAndTravelCard(cartDTO.getCartItems());
        assertEquals(Boolean.FALSE, actualResult);
    }
    
    @Test
    public void shouldReturnZeroValueForCartRefundTotalAmountIsNegative() {
        cartDTO = CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem();
        cartDTO.addCartItem(CartTestUtil.getTestAdminFeeItemDTO());
        cartDTO.addCartItem(CartTestUtil.getNewGoodwillPaymentItemDTO());
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTOWithRefund());
        Integer amount = CartUtil.getCartRefundTotal(cartDTO.getCartItems());
        assertTrue(amount == 0);
    }
    
    @Test
    public void shouldGetCartSessionDataDTOFromSession() {
        CartUtil.getCartSessionDataDTOFromSession(mockHttpSession);
        
        verify(mockHttpSession).getAttribute(SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }
    
    @Test
    public void shouldAddCartSessionDataDTOToSession() {
        CartSessionData mockSessionData = mock(CartSessionData.class);
        CartUtil.addCartSessionDataDTOToSession(mockHttpSession, mockSessionData);
        
        verify(mockHttpSession).setAttribute(SESSION_ATTRIBUTE_SHOPPING_CART_DATA, mockSessionData);
    }
    
    @Test
    public void shouldRemoveCartSessionDataDTOFromSession() {
        CartUtil.removeCartSessionDataDTOFromSession(mockHttpSession);
        
        verify(mockHttpSession).removeAttribute(SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }
    
    @Test
    public void getRefundAmountFromItemDTOShouldReturnItemPrice() {
        ItemDTO adminFeeItemDTO = getNewAdminFeeItemDTO();
        assertEquals(adminFeeItemDTO.getPrice().intValue(), CartUtil.getRefundAmountFromItemDTO(adminFeeItemDTO));
    }

    @Test
    public void isDestroyedOrFaildCartTypeReturnTrueForDestroyedType() {
        assertTrue(CartUtil.isDestroyedOrFaildCartType(CartType.DESTROYED_CARD_REFUND.code()));
    }
    
    @Test
    public void isDestroyedOrFaildCartTypeReturnTrueForFailedType() {
        assertTrue(CartUtil.isDestroyedOrFaildCartType(CartType.FAILED_CARD_REFUND.code()));
    }
    
    @Test
    public void isDestroyedOrFaildCartTypeReturnFalse() {
        assertFalse(CartUtil.isDestroyedOrFaildCartType(CartType.LOST_REFUND.code()));
    }
}
