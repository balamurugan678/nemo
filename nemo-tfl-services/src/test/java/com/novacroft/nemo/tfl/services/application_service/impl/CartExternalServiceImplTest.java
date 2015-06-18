package com.novacroft.nemo.tfl.services.application_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.novacroft.nemo.common.exception.ApplicationServiceStaleDataException;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.services.application_service.ItemExternalService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.CartConverter;
import com.novacroft.nemo.tfl.services.converter.impl.CartConverterImpl;
import com.novacroft.nemo.tfl.services.test_support.ProductDataTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListResult;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public class CartExternalServiceImplTest {

    private CartExternalServiceImpl externalCartService;
	private CartService mockCartService;
    private CustomerDataService mockCustomerDataService;
    private CartDataService mockCartDataService;
    private CartConverter mockCartConverter;
    private PaymentService mockPaymentService;
    private CardService mockCardService;
    private Cart testCart;
    private List<Cart> testCartList;
    private List<Long> testCartIdList;
    private ApplicationContext mockApplicationContext;
    private PickUpLocationValidator mockPickUpLocationValidator;
    private PaymentCardSettlementDataService mockPaymentCardSettlementDataService;
	private OrderDataService mockOrderDataService;
    private ItemExternalService mockItemExternalService;
    private Long testCartId3;
    private Cart testCart3;
    private Errors mockErrors;
    private CardDataService mockCardDataService;
	
	@Before
	public void setUp() throws Exception {
        externalCartService = new CartExternalServiceImpl();
		mockCartService = mock(CartService.class);
		mockCartDataService = mock(CartDataService.class);
		mockCustomerDataService = mock(CustomerDataService.class);
		mockCartConverter = mock(CartConverterImpl.class);
        mockPaymentService = mock(PaymentService.class);
        mockApplicationContext = mock(ApplicationContext.class);
        mockPickUpLocationValidator = mock(PickUpLocationValidator.class);
        mockCardService = mock(CardService.class);
        mockPaymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        mockOrderDataService = mock(OrderDataService.class);
        mockItemExternalService = mock(ItemExternalService.class);
        mockCardDataService = mock(CardDataService.class);
		externalCartService.cartService = mockCartService;
		externalCartService.cartDataService = mockCartDataService;
		externalCartService.cartConverter = mockCartConverter;
		externalCartService.customerDataService = mockCustomerDataService;
		externalCartService.paymentService = mockPaymentService;
        externalCartService.pickUpLocationValidator = mockPickUpLocationValidator;
        externalCartService.cardService = mockCardService;
        externalCartService.paymentCardSettlementDataService = mockPaymentCardSettlementDataService;
        externalCartService.orderDataService = mockOrderDataService;
        externalCartService.itemExternalService = mockItemExternalService;
        externalCartService.cardDataService = mockCardDataService;
		ReflectionTestUtils.setField(externalCartService, "applicationContext", mockApplicationContext);
		mockErrors = mock(Errors.class);
        
		testCartIdList = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartIdList();
		testCartList = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartList();
		testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart1();
		testCart3 = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart3();
		testCartId3 = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.CART_ID_3;
		setUpStaticContent();
	}

	private void setUpStaticContent(){
	    when(mockApplicationContext.getMessage(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
        when(mockApplicationContext.getMessage(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode());
        when(mockApplicationContext.getMessage(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
        when(mockApplicationContext.getMessage(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
        when(mockApplicationContext.getMessage(ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode(), null, null)).thenReturn(ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode());
        when(mockApplicationContext.getMessage(WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.contentCode());
        }
	
	@Test
	public void testCreateCartSuccessScenario() {
        CartDTO testCartDTO = CartTestUtil.getTestCartDTO1();
	    when(mockCardDataService.findByExternalId(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartService.createCartFromCardId(CardTestUtil.CARD_ID_1)).thenReturn(testCartDTO);
        Cart cart = externalCartService.createCart(CardTestUtil.CARD_ID_1);
        assertNotNull(cart);
        assertNotNull(cart.getId());
        assertNull(cart.getErrors());
        verify(mockCartService, never()).removeExpiredCartItems(any(CartDTO.class));
	}

    @Test
    public void testCreateCartReturnsExistingSuccessScenario() {
        CartDTO testCartDTO = CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems();
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartService.createCartFromCardId(CardTestUtil.CARD_ID_1)).thenReturn(testCartDTO);
        when(mockCartService.removeExpiredCartItems(any(CartDTO.class))).thenReturn(testCartDTO);
        Cart cart = externalCartService.createCart(CardTestUtil.CARD_ID_1);
        assertNotNull(cart);
        assertNotNull(cart.getId());
        assertNull(cart.getErrors());
        verify(mockCartService).removeExpiredCartItems(any(CartDTO.class));
    }
	
	@Test
	public void testCreateCartExceptionScenario() {
		when(mockCardDataService.findByExternalId(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartService.createCartFromCardId(CardTestUtil.CARD_ID_1)).thenThrow(new RuntimeException("create cart exception"));
        Cart cart = externalCartService.createCart(CardTestUtil.CARD_ID_1);
        assertNotNull(cart);
        assertNull(cart.getId());
        assertNotNull(cart.getErrors());
	}

	@Test
    public void testCreateCartStaleDataExceptionScenario() {
        CartDTO testCartDTO = CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems();
		when(mockCardDataService.findByExternalId(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartService.createCartFromCardId(CardTestUtil.CARD_ID_1)).thenReturn(testCartDTO);
        when(mockCartService.removeExpiredCartItems(any(CartDTO.class))).thenThrow(new ApplicationServiceStaleDataException("Stale Data"));
        Cart cart = externalCartService.createCart(CardTestUtil.CARD_ID_1);
        assertNotNull(cart.getErrors());
    }
    	
	@Test
    public void createCartShouldErrorDueToInvalidCardId() {
        when(mockCardDataService.findByExternalId(anyLong())).thenThrow(new RuntimeException("Invalid Card Id"));
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        Cart cart = externalCartService.createCart(CardTestUtil.CARD_ID_1);
        assertNotNull(cart);
        assertNull(cart.getId());
        assertNotNull(cart.getErrors());
    }
	
	@Test
    public void testCreateCartShouldErrorDueToCustomerNotFound() {
	    when(mockCustomerDataService.findByExternalId(anyLong())).thenReturn(null);
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartService.createCartFromCustomerId(CustomerTestUtil.CUSTOMER_ID_1)).thenReturn(CartTestUtil.getTestCartDTO1());
        Cart cart = externalCartService.createCart(CustomerTestUtil.CUSTOMER_ID_1);
        assertNotNull(cart);
        assertNull(cart.getId());
        assertNotNull(cart.getErrors());
    }
	
	@SuppressWarnings("unchecked")
    @Test
    public void testGetCartListShouldReturnList() {
	    when(mockCustomerDataService.findByExternalId(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockCartDataService.findCartListByCustomerId(CustomerTestUtil.CUSTOMER_ID_1)).thenReturn(testCartIdList);
        when(mockCartConverter.convert(anyList())).thenReturn(testCartList);
        ListResult<Cart> response = externalCartService.getCartListByCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        assertNotNull(response);
        assertNotNull(response.getResultList());
        assertEquals(testCartIdList.size(), response.getResultList().size());
    }
	
	@SuppressWarnings("unchecked")
    @Test
    public void testGetCartListShouldReturnEmptyList() {
	    when(mockCustomerDataService.findByExternalId(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
	    when(mockCartDataService.findCartListByCustomerId(CustomerTestUtil.CUSTOMER_ID_1)).thenThrow(new RuntimeException("Cart Lookup Exception"));
	    when(mockCartConverter.convert(anyList())).thenReturn(new ArrayList<Cart>());
        ListResult<Cart> response = externalCartService.getCartListByCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        assertNotNull(response);
        assertNotNull(response.getResultList());
        assertEquals(0, response.getResultList().size());
    }
	
	@Test
    public void testGetCartListShouldReturnEmptyListDueToCustomerNotFound() {
	    when(mockCustomerDataService.findByExternalId(anyLong())).thenThrow(new RuntimeException("Customer Lookup Exception"));
        ListResult<Cart> response = externalCartService.getCartListByCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        assertNotNull(response);
        assertNotNull(response.getResultList());
        assertEquals(0, response.getResultList().size());
    }

    @Test
    public void retrieveCartShouldErrorWithCartLookupException() {
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartDataService.findByExternalId(anyLong())).thenThrow(new RuntimeException("Cart Lookup Exception"));
        Cart cart = externalCartService.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        assertNotNull(cart);
        assertNull(cart.getId());
        assertNotNull(cart.getErrors());
    }

    @Test
    public void retrieveCartShouldErrorWithStaleDataException() {
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems());
        when(mockCartService.removeExpiredCartItems(any(CartDTO.class))).thenThrow(new ApplicationServiceStaleDataException("Stale Data"));
        Cart cart = externalCartService.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        assertNotNull(cart.getErrors());
    }
    
    @Test
    public void retrieveCartShouldErrorWithCartConvertException() {
        when(mockCartConverter.convert(any(CartDTO.class))).thenThrow(new RuntimeException("Cart Lookup Exception"));
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        Cart cart = externalCartService.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        assertNotNull(cart);
        assertNull(cart.getId());
        assertNotNull(cart.getErrors());
    }

    @Test
    public void retrieveCartShouldReturnCartSuccessfully() {
        CartDTO testCartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart2());
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(testCartDTO);
        when(mockCartService.removeExpiredCartItems(any(CartDTO.class))).thenReturn(testCartDTO);
        Cart cart = externalCartService.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        assertNotNull(cart);
        assertEquals(CartTestUtil.EXTERNAL_CART_ID_1, cart.getId());
        assertNull(cart.getErrors());
        verify(mockCartService).removeExpiredCartItems(any(CartDTO.class));
    }

    @Test
    public void retrieveCartShouldReturnEmptyCartSuccessfully() {
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart2());
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        Cart cart = externalCartService.retrieveCart(CartTestUtil.EXTERNAL_CART_ID_1);
        assertNotNull(cart);
        assertEquals(CartTestUtil.EXTERNAL_CART_ID_1, cart.getId());
        assertNull(cart.getErrors());
        verify(mockCartService, never()).removeExpiredCartItems(any(CartDTO.class));
    }

    @Test
    public void deleteCartShouldErrorDueToNullCartId() {
        WebServiceResult result = externalCartService.deleteCart(null, CustomerTestUtil.EXTERNAL_USER_ID);

        verify(mockCartService, never()).deleteCart(any(Long.class));
        assertNull(result.getId());
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void deleteCartShouldErrorDueToNullCustomerId() {
        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, null);

        verify(mockCartService, never()).deleteCart(any(Long.class));
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void deleteCartShouldErrorDueToCartNotFound() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(null);

        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        verify(mockCartService, never()).deleteCart(any(Long.class));
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void deleteCartShouldErrorDueToCartCustomerNotFound() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(mockCustomerDataService.findById(any(Long.class))).thenThrow(new RuntimeException("Cart Delete Exception"));

        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void deleteCartShouldErrorDueToCartAndCustomerNotMatching() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem();
        cartDTO.setCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(cartDTO);
        CustomerDTO mockCustomerDTO = CustomerTestUtil.getTestCustomerDTO1();
        mockCustomerDTO.setExternalId(CustomerTestUtil.CUSTOMER_ADDRESS_ID_4);
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(mockCustomerDTO);
        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_NOT_FOUND.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode());
    }

    @Test
    public void deleteCartShouldErrorWithCartLookupException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenThrow(new RuntimeException("Cart Lookup Exception"));

        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void deleteCartShouldErrorWithCartDeleteException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        CustomerDTO mockCustomerDTO = CustomerTestUtil.getTestCustomerDTO1();
        mockCustomerDTO.setExternalId(CustomerTestUtil.EXTERNAL_USER_ID);
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(mockCustomerDTO);
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        doThrow(new RuntimeException("Cart Delete Exception")).when(mockCartService).deleteCart(any(Long.class));

        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        verify(mockCartService, times(1)).deleteCart(any(Long.class));
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void deleteCartShouldErrorWitStaleDataException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        CustomerDTO mockCustomerDTO = CustomerTestUtil.getTestCustomerDTO1();
        mockCustomerDTO.setExternalId(CustomerTestUtil.EXTERNAL_USER_ID);
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(mockCustomerDTO);
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        doThrow(new ApplicationServiceStaleDataException("Stale Data")).when(mockCartService).deleteCart(any(Long.class));

        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        verify(mockCartService, times(1)).deleteCart(any(Long.class));
        assertEquals(result.getResult(), WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.contentCode());
    }

    @Test
    public void deleteCartShouldCompleteSuccessfully() {
        CustomerDTO mockCustomerDTO = CustomerTestUtil.getTestCustomerDTO1();
        mockCustomerDTO.setExternalId(CustomerTestUtil.EXTERNAL_USER_ID);
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(mockCustomerDTO);
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        doNothing().when(mockCartService).deleteCart(any(Long.class));

        WebServiceResult result = externalCartService.deleteCart(CartTestUtil.EXTERNAL_CART_ID_1, CustomerTestUtil.EXTERNAL_USER_ID);

        verify(mockCartService, times(1)).deleteCart(any(Long.class));
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.SUCCESS.name());
        assertNull(result.getMessage());
    }

    @Test
    public void beginCheckoutShouldErrorDueToNullCartId() {
        CheckoutResult result = externalCartService.beginCheckout(null, LocationTestUtil.LOCATION_ID_1);

        assertEquals(result.getId(), null);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void beginCheckoutShouldErrorDueToNullStationId() {
        CheckoutResult result = externalCartService.beginCheckout(CartTestUtil.CART_ID_1, null);
        assertEquals(result.getId(), CartTestUtil.CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void beginCheckoutShouldErrorDueToInvalidStationId() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                throw new RuntimeException("Invalid station Id");
            }
        }).when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        
        CheckoutResult result = externalCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);
        
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void beginCheckoutShouldErrorDueToStationValidation() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Errors errors = (Errors) args[1];
                errors.rejectValue(PageCommandAttribute.FIELD_STATION_ID, ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode());
                return null;
            }
        }).when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        
        CheckoutResult result = externalCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);
        
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.name());
        assertEquals(result.getMessage(), ContentCode.SELECTED_STATION_DIFFERENT_FROM_PENDINGITEM_STATION.errorCode());
    }

    @Test
    public void beginCheckoutShouldErrorWithCartLookupException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenThrow(new RuntimeException("Cart Lookup Exception"));
        doNothing().when(mockPickUpLocationValidator).validate(any(CartCmdImpl.class), any(Errors.class));

        CheckoutResult result = externalCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);

        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void beginCheckoutShouldErrorWithCreateOrderSettlementException() {
        CartDTO cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        cartDTO.setCustomerId(CustomerTestUtil.CS_CUSTOMERID_1);

        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(cartDTO);
        doNothing().when(mockPickUpLocationValidator).validate(any(CartCmdImpl.class), any(Errors.class));
        doThrow(new RuntimeException("Cart Delete Exception")).when(mockPaymentService).createOrderAndSettlementsFromCart(any(CartDTO.class),
                        any(CartCmdImpl.class));

        CheckoutResult result = externalCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void beginCheckoutShouldCompleteSuccessfullyWithStationId() {
        CartDTO cartDTO = CartTestUtil.getCartDTOWithAllItemsForOrderNewCard();
        cartDTO.setCustomerId(CustomerTestUtil.CS_CUSTOMERID_1);

        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(cartDTO);
        doNothing().when(mockPickUpLocationValidator).validate(any(CartCmdImpl.class), any(Errors.class));
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                CartDTO processedCartDTO = (CartDTO) args[0];
                processedCartDTO.setCustomerId(CustomerTestUtil.CS_CUSTOMERID_1);
                processedCartDTO.setOrder(OrderTestUtil.getTestOrderDTO1());
                processedCartDTO.setPaymentCardSettlement(SettlementTestUtil.getTestPaymentCardSettlementDTO1());
                return null;
            }
        });
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());

        CheckoutResult result = externalCartService.beginCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1);
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.SUCCESS.name());
        assertNull(result.getMessage());
        assertNotNull(result.getOrderId());
        assertNotNull(result.getPaymentSettlementId());
    }

    @Test
    public void authoriseCheckoutPaymentShouldErrorWithNullCartId() {
        CheckoutResult result = externalCartService.authoriseCheckoutPayment(null, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");
        assertNull(result.getId());
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void authoriseCheckoutPaymentShouldErrorWithNullOrderId() {
        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, null, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }
    
    @Test
    public void authoriseCheckoutPaymentShouldErrorWithNullSettlementId() {
        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, null, "457346746464");
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }
    
    @Test
    public void authoriseCheckoutPaymentShouldErrorWithNullPaymentReference() {
        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "");
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void authoriseCheckoutPaymentShouldErrorWithCartNotFound() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenThrow(new RuntimeException("Record Not Found"));
        
        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }
   
    @Test
    public void authoriseCheckoutPaymentShouldErrorWithPaymentIdNotFound() {
        when(mockPaymentCardSettlementDataService.findByOrderId(any(Long.class))).thenThrow(new RuntimeException("Record Not Found"));
        
        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }

    
    @Test
    public void authoriseCheckoutPaymentShouldErrorWithUpdateError() {
        setUpMatchedPaymentCardSettlementMock();
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenThrow(new RuntimeException("Update Error"));

        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");
        
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
    }
    
    @Test
    public void authoriseCheckoutPaymentShouldErrorWithPaymentNotForOrder() {
        setUpNotMatchedPaymentCardSettlementMock();
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenThrow(new RuntimeException("Update Error"));

        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");

        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_NOT_FOUND.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode());
    }

    @Test
    public void authoriseCheckoutPaymentShouldCompleteSuccessfully() {
        PaymentCardSettlementDTO mockPaymentSettlementDTO = setUpMatchedPaymentCardSettlementMock();
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(mockPaymentSettlementDTO);

        CheckoutResult result = externalCartService.authoriseCheckoutPayment(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1, "457346746464");
        verify(mockPaymentSettlementDTO, times(1)).setAuthorisationTransactionReferenceNumber("457346746464");
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.SUCCESS.name());
        assertNull(result.getMessage());
    }


    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithNullCartId() {
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(null, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
        assertNull(result.getId());
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithNullOrderId() {
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, null, SettlementTestUtil.SETTLEMENT_ID_1);
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithNullSettlementId() {
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, null);
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithCartNotFound() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenThrow(new RuntimeException("Record Not Found"));
        
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithPaymentIdNotFound() {
        when(mockOrderDataService.getInternalIdFromExternalId(any(Long.class))).thenReturn(null);
        when(mockPaymentCardSettlementDataService.findByOrderId(any(Long.class))).thenThrow(new RuntimeException("Record Not Found"));
        
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }
    
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithUpdateError() {
        setUpMatchedPaymentCardSettlementMock();
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenThrow(new RuntimeException("Update Error"));
    
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
        
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
    }
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldErrorWithPaymentNotForOrder() {
        setUpNotMatchedPaymentCardSettlementMock();
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenThrow(new RuntimeException("Update Error"));
    
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
    
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_NOT_FOUND.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode());
    }

    private void setUpNotMatchedPaymentCardSettlementMock() {
        PaymentCardSettlementDTO mockPaymentSettlementDTO1 = mock(PaymentCardSettlementDTO.class);
        PaymentCardSettlementDTO mockPaymentSettlementDTO2 = mock(PaymentCardSettlementDTO.class);
        when(mockPaymentSettlementDTO1.getExternalId()).thenReturn(null);
        when(mockPaymentSettlementDTO2.getExternalId()).thenReturn(null);
        when(mockOrderDataService.getInternalIdFromExternalId(any(Long.class))).thenReturn(null);
        when(mockPaymentCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(Arrays.asList(new PaymentCardSettlementDTO[]{mockPaymentSettlementDTO1, mockPaymentSettlementDTO2}));
    }
    
    @Test
    public void updatePaymentCardSettlementStatusToFailedShouldCompleteSuccessfully() {
        PaymentCardSettlementDTO mockPaymentSettlementDTO2 = setUpMatchedPaymentCardSettlementMock();
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(mockPaymentSettlementDTO2);
    
        CheckoutResult result = externalCartService.updatePaymentCardSettlementStatusToFailed(CartTestUtil.EXTERNAL_CART_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
        verify(mockPaymentSettlementDTO2, times(1)).setStatus(SettlementStatus.FAILED.code());
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.SUCCESS.name());
        assertNull(result.getMessage());
    }
    
    private PaymentCardSettlementDTO setUpMatchedPaymentCardSettlementMock() {
        PaymentCardSettlementDTO mockPaymentSettlementDTO1 = mock(PaymentCardSettlementDTO.class);
        PaymentCardSettlementDTO mockPaymentSettlementDTO2 = mock(PaymentCardSettlementDTO.class);
        when(mockPaymentSettlementDTO1.getExternalId()).thenReturn(12029L);
        when(mockPaymentSettlementDTO2.getExternalId()).thenReturn(SettlementTestUtil.SETTLEMENT_ID_1);
        when(mockOrderDataService.getInternalIdFromExternalId(any(Long.class))).thenReturn(null);
        when(mockPaymentCardSettlementDataService.findByOrderId(any(Long.class))).thenReturn(Arrays.asList(new PaymentCardSettlementDTO[]{mockPaymentSettlementDTO1, mockPaymentSettlementDTO2}));
        return mockPaymentSettlementDTO2;
    }

    
    
    @Test
    public void getPaymentCardSettlementDTODoesNotFindMatchDueToNullInput(){
        PaymentCardSettlementDTO matchedSettlementRecord = externalCartService.getPaymentCardSettlementDTO(null, null);
        assertNull(matchedSettlementRecord);
    }

    @Test
    public void getPaymentCardSettlementDTODoesNotFindMatchDueToEmptyPaymentSettlement(){
        PaymentCardSettlementDTO matchedSettlementRecord = externalCartService.getPaymentCardSettlementDTO(new ArrayList<PaymentCardSettlementDTO>(), SettlementTestUtil.SETTLEMENT_ID_1);
        assertNull(matchedSettlementRecord);
    }

    @Test
    public void getPaymentCardSettlementDTODoesNotFindMatchInList(){
        PaymentCardSettlementDTO mockPaymentSettlementDTO1 = mock(PaymentCardSettlementDTO.class);
        PaymentCardSettlementDTO mockPaymentSettlementDTO2 = mock(PaymentCardSettlementDTO.class);
        when(mockPaymentSettlementDTO1.getExternalId()).thenReturn(null);
        when(mockPaymentSettlementDTO2.getExternalId()).thenReturn(null);
        PaymentCardSettlementDTO matchedSettlementRecord = externalCartService.getPaymentCardSettlementDTO(Arrays.asList(new PaymentCardSettlementDTO[]{mockPaymentSettlementDTO1, mockPaymentSettlementDTO2}), SettlementTestUtil.SETTLEMENT_ID_1);
        assertNull(matchedSettlementRecord);
    }

    @Test
    public void getPaymentCardSettlementDTOFindsMatch(){
        PaymentCardSettlementDTO mockPaymentSettlementDTO1 = mock(PaymentCardSettlementDTO.class);
        PaymentCardSettlementDTO mockPaymentSettlementDTO2 = mock(PaymentCardSettlementDTO.class);
        when(mockPaymentSettlementDTO1.getExternalId()).thenReturn(null);
        when(mockPaymentSettlementDTO2.getExternalId()).thenReturn(SettlementTestUtil.SETTLEMENT_ID_1);
        PaymentCardSettlementDTO matchedSettlementRecord = externalCartService.getPaymentCardSettlementDTO(Arrays.asList(new PaymentCardSettlementDTO[]{mockPaymentSettlementDTO1, mockPaymentSettlementDTO2}), SettlementTestUtil.SETTLEMENT_ID_1);
        assertNotNull(matchedSettlementRecord);
    }


    @Test
    public void completeCheckoutPaymentShouldErrorWithNullCartId() {
        CheckoutResult result = externalCartService.completeCheckout(null, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertNull(result.getId());
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void completeCheckoutPaymentShouldErrorWithNullStationId() {
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, null, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void completeCheckoutPaymentShouldErrorWithNullOrderId() {
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, null, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }

    @Test
    public void completeCheckoutPaymentShouldErrorWithNullPaymentSettlementId() {
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, null);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode());
    }
    
    @Test
    public void completeCheckoutPaymentShouldErrorWithCartNotFound() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenThrow(new RuntimeException("Record Not Found"));
        
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }
    
    @Test
    public void completeCheckoutPaymentShouldErrorProcessPaymentResponse() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        setUpNotMatchedPaymentCardSettlementMock();
        when(mockPaymentService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenThrow(new RuntimeException("Cannot process checkout"));
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(null);
        
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_NOT_FOUND.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode());
    }
    
    @Test
    public void completeCheckoutPaymentShouldErrorOrderNotFound() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(mockOrderDataService.getInternalIdFromExternalId(any(Long.class))).thenThrow(new RuntimeException("Record Not Found"));
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(null);
        
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
    }
   
   
    @Test
    public void completeCheckoutPaymentShouldErrorPaymentServiceException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        setUpMatchedPaymentCardSettlementMock();
        when(mockPaymentService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenThrow(new RuntimeException("Cannot process checkout"));
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(null);
        
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void completeCheckoutPaymentShouldErrorPaymentSettlementUpdateException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        setUpMatchedPaymentCardSettlementMock();
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockPaymentService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenReturn(null);
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenThrow(new RuntimeException("Record Not Found"));
        
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name());
        assertEquals(result.getMessage(), WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode());
    }

    @Test
    public void completeCheckoutPaymentShouldCompleteSuccessfully() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        PaymentCardSettlementDTO mockPaymentSettlementDTO = setUpMatchedPaymentCardSettlementMock();
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockPaymentService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenReturn(null);
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(mockPaymentSettlementDTO);
        
        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);       
        assertEquals(result.getId(), CartTestUtil.EXTERNAL_CART_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.SUCCESS.name());
        assertNull(result.getMessage());
    }

    @Test
    public void completeCheckoutShouldErrorWithStaleDataException() {
        when(mockCartDataService.findByExternalId(any(Long.class))).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        PaymentCardSettlementDTO mockPaymentSettlementDTO = setUpMatchedPaymentCardSettlementMock();
        when(mockCardService.getCardDTOById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockPaymentService.processPaymentGatewayReply(any(CartCmdImpl.class))).thenThrow(new ApplicationServiceStaleDataException("Stale Data"));
        when(mockPaymentCardSettlementDataService.createOrUpdate(any(PaymentCardSettlementDTO.class))).thenReturn(mockPaymentSettlementDTO);

        CheckoutResult result = externalCartService.completeCheckout(CartTestUtil.EXTERNAL_CART_ID_1, LocationTestUtil.LOCATION_ID_1, OrderTestUtil.ORDER_ID, SettlementTestUtil.SETTLEMENT_ID_1);
        assertEquals(result.getResult(), WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name());
    }
    
    @Test
    public void updateCartShouldErrorWithCartLookupException() {
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(testCart);
        when(mockCartDataService.findByExternalId(anyLong())).thenThrow(new RuntimeException("Cart Lookup Exception"));
        Cart cart = externalCartService.updateCart(testCart3,testCartId3);
        assertNotNull(cart);
        assertNotNull(cart.getErrors());
    }

    @Test
    public void updateCartShouldReturnCartSuccessfully() {
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addTravelCardItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockItemExternalService.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenReturn(mockErrors);
        when(mockItemExternalService.addBusPassItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(false);
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems());
        when(mockCartConverter.convert(any(CartDTO.class))).thenReturn(com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCart3());
        Cart cart = externalCartService.updateCart(testCart3,testCartId3);
        assertNotNull(cart);
        assertEquals(testCartId3, cart.getId());
        assertNull(cart.getErrors());
    }
    
    @Test
    public void updateCartShouldErrorWithAddTravelcardError() {
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithThreeProductItems());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addTravelCardItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockItemExternalService.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenReturn(mockErrors);
        when(mockItemExternalService.addBusPassItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(true);
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError(ProductDataTestUtil.ERROR_DURATION_FIELD, ProductDataTestUtil.ERROR_DURATION_MESSAGE));
        when(mockErrors.getAllErrors()).thenReturn(objectErrors);
        Cart cart = externalCartService.updateCart(testCart3,testCartId3);
        assertNotNull(cart);
        assertEquals(testCartId3, cart.getId());
        assertNotNull(cart.getErrors());
    }
    
    @Test
    public void updateCartShouldErrorWithBusPassError() {
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithBusPassItem();
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addBusPassItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(true);
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError(ProductDataTestUtil.ERROR_DURATION_FIELD, ProductDataTestUtil.ERROR_DURATION_MESSAGE));
        when(mockErrors.getAllErrors()).thenReturn(objectErrors);
        Cart cart = externalCartService.updateCart(testCart,testCart.getId());
        assertNotNull(cart);
        assertEquals(testCart.getId(), cart.getId());
        assertNotNull(cart.getErrors());
    }
    
    @Test
    public void updateCartShouldErrorWithPayAsYouGoError() {
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithPayAsYouGoItem();
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(true);
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError(ProductDataTestUtil.ERROR_DURATION_FIELD, ProductDataTestUtil.ERROR_DURATION_MESSAGE));
        when(mockErrors.getAllErrors()).thenReturn(objectErrors);
        Cart cart = externalCartService.updateCart(testCart,testCart.getId());
        assertNotNull(cart);
        assertEquals(testCart.getId(), cart.getId());
        assertNotNull(cart.getErrors());
    }
    
    @Test
    public void updateCartWithErrorShouldReplacePreviousCart() {
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithPayAsYouGoItem();
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(true);
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(new ObjectError(ProductDataTestUtil.ERROR_DURATION_FIELD, ProductDataTestUtil.ERROR_DURATION_MESSAGE));
        when(mockErrors.getAllErrors()).thenReturn(objectErrors);
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems());
        Cart cart = externalCartService.updateCart(testCart, testCart.getId());
        assertNotNull(cart);
        assertNotNull(cart.getErrors());
    }

    @Test
    public void updateCartShouldErrorWithCartConvertException() {
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addTravelCardItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockItemExternalService.addPayAsYouGoItemToCart(any(CartDTO.class), any(Item.class), anyInt())).thenReturn(mockErrors);
        when(mockItemExternalService.addBusPassItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenReturn(mockErrors);
        when(mockErrors.hasErrors()).thenReturn(false);
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getTestCartDTOWithTravelCardAndPayAsYouGoItems());
        when(mockCartConverter.convert(any(CartDTO.class))).thenThrow(new RuntimeException("Cart Convert Exception"));
        Cart cart = externalCartService.updateCart(testCart3,testCartId3);
        assertNotNull(cart);
        assertNotNull(cart.getErrors());
    }
    
    @Test
    public void updateCartShouldErrorWithStaleDataException() {
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockItemExternalService.addTravelCardItemToCart(any(CartDTO.class), any(Item.class), any(Cart.class), anyInt())).thenThrow(new ApplicationServiceStaleDataException("Stale Data"));
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItem());
        Cart cart = externalCartService.updateCart(testCart3, testCartId3);
        assertNotNull(cart.getErrors());
    }

    @Test
    public void shouldReturnFalseForNullCartDTO() {
        assertFalse(externalCartService.cartItemsExist(null));
    }

    @Test
    public void shouldReturnFalseForNullCartItems() {
        CartDTO testCartDTO = CartTestUtil.getTestCartDTO1();
        testCartDTO.setCartItems(null);
        assertFalse(externalCartService.cartItemsExist(testCartDTO));
    }

    @Test
    public void shouldReturnFalseForEmptyCartItems() {
        assertFalse(externalCartService.cartItemsExist(CartTestUtil.getTestCartDTO1()));
    }

    @Test
    public void shouldReturnTrueForNonEmptyCartItems() {
        assertTrue(externalCartService.cartItemsExist(CartTestUtil.getNewCartDTOWithAnnualBusPassProductItem()));
    }
 
    @Test
    public void updateCartShouldErrorWithCartDataExpired() {
        CartDTO dbCart = CartTestUtil.getNewCartDTOWithProductItem();
        dbCart.setVersion(new Long(25));
        testCart.setVersion(new Long(12));
        when(mockCartDataService.findByExternalId(anyLong())).thenReturn(dbCart);
        Cart cart = externalCartService.updateCart(testCart, testCartId3);
        assertNotNull(cart.getErrors());
    }

    @Test
    public void hasCartDataExpiredShouldReturnTrueForLessThanVersion() {
        CartDTO dbCart = CartTestUtil.getNewCartDTOWithProductItem();
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithPayAsYouGoItem();
        dbCart.setVersion(new Long(11));
        testCart.setVersion(new Long(13));
        assertTrue(externalCartService.hasCartDataExpired(testCart, dbCart));
    }

    @Test
    public void hasCartDataExpiredShouldReturnTrueForGreaterThanVersion() {
        CartDTO dbCart = CartTestUtil.getNewCartDTOWithProductItem();
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithPayAsYouGoItem();
        dbCart.setVersion(new Long(16));
        testCart.setVersion(new Long(13));
        assertTrue(externalCartService.hasCartDataExpired(testCart, dbCart));
    }

    @Test
    public void hasCartDataExpiredShouldReturnFalseForMatchedVerion() {
        CartDTO dbCart = CartTestUtil.getNewCartDTOWithProductItem();
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithPayAsYouGoItem();
        dbCart.setVersion(new Long(25));
        testCart.setVersion(new Long(25));
        assertFalse(externalCartService.hasCartDataExpired(testCart, dbCart));

    }

    @Test
    public void hasCartDataExpiredShouldReturnFalseForNotSpecifiedVersion() {
        CartDTO dbCart = CartTestUtil.getNewCartDTOWithProductItem();
        testCart = com.novacroft.nemo.tfl.services.test_support.CartTestUtil.getTestCartWithPayAsYouGoItem();
        dbCart.setVersion(new Long(25));
        testCart.setVersion(null);
        assertFalse(externalCartService.hasCartDataExpired(testCart, dbCart));

    }
    
}
