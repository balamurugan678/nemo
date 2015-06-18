package com.novacroft.nemo.tfl.services.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.tfl.services.application_service.CartExternalService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.test_support.CheckoutRequestTestUtil;
import com.novacroft.nemo.tfl.services.test_support.CheckoutResultTestUtil;
import com.novacroft.nemo.tfl.services.test_support.TestSupportUtilities;
import com.novacroft.nemo.tfl.services.test_support.WebServiceResultTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.CheckoutRequest;
import com.novacroft.nemo.tfl.services.transfer.ListResult;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public class CartControllerTest {

	private CartController controller;
	private CartExternalService mockCartService;
    private Cart testCart;
    private Long cartId;
    private Cart testCartWithError;
    
	
	@Before
	public void setUp() throws Exception {
		controller = new CartController();
		mockCartService = mock(CartExternalService.class);
		controller.cartExternalService = mockCartService;
		testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart3();
		testCartWithError = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithError();
		cartId = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.CART_ID_3;
	}

	@Test
	public void shouldCreateCart() {
		when(mockCartService.createCart(CustomerTestUtil.CUSTOMER_ID_1)).thenReturn(TestSupportUtilities.getTestCart1());
		 Cart cart = controller.createCart(CustomerTestUtil.CUSTOMER_ID_1);
		 assertNotNull(cart);
		 verify(mockCartService).createCart(CustomerTestUtil.CUSTOMER_ID_1);
	}
	
    @Test
    public void testRetrieveCart() {
        when(mockCartService.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1)).thenReturn(
                        com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart2());
        Cart cart = controller.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        verify(mockCartService).retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        assertNotNull(cart);
        assertEquals(CartTestUtil.EXTERNAL_CART_ID_1, cart.getId());
        assertNull(cart.getErrors());
    }

    @Test
    public void testGetCartListByCustomerId() {
        when(mockCartService.getCartListByCustomerId(CartTestUtil.CUSTOMER_ID)).thenReturn(
                        com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartListResult());
        ListResult<Cart> response = controller.getCartListByCustomerId(CartTestUtil.CUSTOMER_ID);
        assertNotNull(response);
        assertEquals(response.getResultList().size(), com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartList().size());
    }

    @Test
    public void deleteCartShouldReturnSuccess() {
        when(mockCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.CUSTOMER_ID_2)).thenReturn(
                        WebServiceResultTestUtil.createSuccessfulResult(CartTestUtil.EXTERNAL_CART_ID_1));
        WebServiceResult result = controller.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.CUSTOMER_ID_2);
        verify(mockCartService).deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.CUSTOMER_ID_2);
        assertNotNull(result.getId());
        assertSuccessfulResult(result);
    }

    @Test
    public void deleteCartShouldReturnFailure() {
        when(mockCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.CUSTOMER_ID_2)).thenReturn(
                        WebServiceResultTestUtil.createFailedResult(CartTestUtil.EXTERNAL_CART_ID_1));
        WebServiceResult result = controller.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.CUSTOMER_ID_2);
        verify(mockCartService).deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.CUSTOMER_ID_2);
        assertNotNull(result.getId());
        assertFailureResult(result);
    }

    @Test
    public void beginCheckoutShouldReturnSuccess() {
        when(mockCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1)).thenReturn(
                        CheckoutResultTestUtil.createSuccessfulResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest checkoutRequest = CheckoutRequestTestUtil.getExistingCardCmdForCheckoutStart();

        WebServiceResult result = controller.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, checkoutRequest);
        verify(mockCartService).beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);
        assertNotNull(result.getId());
        assertSuccessfulResult(result);
    }

    @Test
    public void beginCheckoutShouldReturnFailure() {
        when(mockCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1)).thenReturn(
                        CheckoutResultTestUtil.createFailedResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest checkoutRequest = CheckoutRequestTestUtil.getExistingCardCmdForCheckoutStart();

        WebServiceResult result = controller.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, checkoutRequest);
        verify(mockCartService).beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);
        assertNotNull(result.getId());
        assertFailureResult(result);
    }

    @Test
    public void authoriseCheckoutShouldReturnSuccess() {
        when(mockCartService.authoriseCheckoutPayment(any(Long.class), any(Long.class), any(Long.class), anyString())).thenReturn(
                        CheckoutResultTestUtil.createSuccessfulResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest checkoutRequest = CheckoutRequestTestUtil.getExistingCardCmdForCheckoutStart();

        WebServiceResult result = controller.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, checkoutRequest);
        verify(mockCartService).authoriseCheckoutPayment(any(Long.class), any(Long.class), any(Long.class), anyString());
        assertNotNull(result.getId());
        assertSuccessfulResult(result);
    }

    @Test
    public void authoriseCheckoutShouldReturnFailure() {
        when(mockCartService.authoriseCheckoutPayment(any(Long.class), any(Long.class), any(Long.class), anyString())).thenReturn(
                        CheckoutResultTestUtil.createFailedResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest cmd = CheckoutRequestTestUtil.getExistingCardCmdForCheckoutStart();

        WebServiceResult result = controller.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, cmd);
        verify(mockCartService).authoriseCheckoutPayment(any(Long.class),  any(Long.class), any(Long.class), anyString());
        assertNotNull(result.getId());
        assertFailureResult(result);
    }

    @Test
    public void updatePaymentStatusToFailedShouldReturnSuccess() {
        when(mockCartService.updatePaymentCardSettlementStatusToFailed(any(Long.class), any(Long.class), any(Long.class))).thenReturn(
                        CheckoutResultTestUtil.createSuccessfulResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest cmd = CheckoutRequestTestUtil.getNewOrderCmdForCheckoutAuthoriseUpdatePaymentRecordToFailed();
        WebServiceResult result = controller.failCheckoutPayment(cartId, cmd);
        verify(mockCartService).updatePaymentCardSettlementStatusToFailed(any(Long.class), any(Long.class), any(Long.class));
        assertNotNull(result.getId());
        assertSuccessfulResult(result);
    }

    @Test
    public void updatePaymentStatusToFailedShouldReturnFailure() {
        when(mockCartService.updatePaymentCardSettlementStatusToFailed(any(Long.class), any(Long.class), any(Long.class))).thenReturn(
                        CheckoutResultTestUtil.createFailedResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest cmd = CheckoutRequestTestUtil.getNewOrderCmdForCheckoutAuthoriseUpdatePaymentRecordToFailed();
        WebServiceResult result = controller.failCheckoutPayment(cartId, cmd);
        verify(mockCartService).updatePaymentCardSettlementStatusToFailed(any(Long.class), any(Long.class), any(Long.class));
        assertNotNull(result.getId());
        assertFailureResult(result);
    }

    @Test
    public void completeCheckoutShouldReturnSuccess() {
        when(mockCartService.completeCheckout(any(Long.class), any(Long.class), any(Long.class), any(Long.class))).thenReturn(
                        CheckoutResultTestUtil.createSuccessfulResult(CartTestUtil.EXTERNAL_CART_ID_1)); 
        CheckoutRequest cmd = CheckoutRequestTestUtil.getExistingCardCmdForCheckoutStart();

        WebServiceResult result = controller.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1,cmd);
        verify(mockCartService).completeCheckout(any(Long.class), any(Long.class), any(Long.class), any(Long.class));
        assertNotNull(result.getId());
        assertSuccessfulResult(result);
    }

    @Test
    public void completeCheckoutShouldReturnFailure() {
        when(mockCartService.completeCheckout(any(Long.class), any(Long.class), any(Long.class), any(Long.class))).thenReturn(
                        CheckoutResultTestUtil.createFailedResult(CartTestUtil.EXTERNAL_CART_ID_1));
        CheckoutRequest cmd = CheckoutRequestTestUtil.getExistingCardCmdForCheckoutStart();

        WebServiceResult result = controller.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, cmd);
        verify(mockCartService).completeCheckout(any(Long.class), any(Long.class), any(Long.class), any(Long.class));
        assertNotNull(result.getId());
        assertFailureResult(result);
    }
    
    @Test
    public void updateCartShouldReturnSuccess() {
        when(mockCartService.updateCart(testCart,cartId)).thenReturn(
                        testCart);
        Cart result = controller.updateCart(testCart, cartId);
        verify(mockCartService).updateCart(testCart,cartId);
        assertNotNull(result.getId());
        assertNull(result.getErrors());
    }

    @Test
    public void updateCartShouldReturnFailure() {
        when(mockCartService.updateCart(testCart,cartId)).thenReturn(
                        testCartWithError);
        Cart result = controller.updateCart(testCart, cartId);
        verify(mockCartService).updateCart(testCart,cartId);
        assertNotNull(result.getErrors());
    }

    private void assertSuccessfulResult(WebServiceResult result){
        assertEquals(result.getResult(), WebServiceResultAttribute.SUCCESS.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.SUCCESS.contentCode());
    }

    private void assertFailureResult(WebServiceResult result){
        assertEquals(result.getResult(), WebServiceResultAttribute.FAILURE.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.FAILURE.contentCode());
    }
}
