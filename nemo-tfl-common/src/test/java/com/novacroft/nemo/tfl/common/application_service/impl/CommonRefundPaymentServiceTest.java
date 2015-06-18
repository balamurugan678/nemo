package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonRefundPaymentServiceUtil.buildCartCmdImp;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

public class CommonRefundPaymentServiceTest {
	private Integer expected_ToPayAmount = new Integer(500);
	private Integer unexpected_ToPayAmount = new Integer(15500);
	private CommonRefundPaymentServiceImpl mockCommonRefundPaymentServiceImpl;
	private CartCmdImpl cartCmdImpl;
	private OrderDTO orderDTO;
	private OrderDataService mockOrderDataService;
	private ApplicationEventService mockApplicationEventService;
	private AddressDataService mockAddressDataService;
	private CardService mockCardService;
	
	@Before
    public void setUp() {
		cartCmdImpl = buildCartCmdImp();
		mockCommonRefundPaymentServiceImpl = mock(CommonRefundPaymentServiceImpl.class);
		mockOrderDataService = mock(OrderDataService.class);
		mockApplicationEventService = mock(ApplicationEventService.class);
		mockAddressDataService = mock(AddressDataService.class);
		mockCardService = mock(CardService.class);
		orderDTO = getTestOrderDTO1();
		
		mockCommonRefundPaymentServiceImpl.orderDataService = mockOrderDataService;
		mockCommonRefundPaymentServiceImpl.applicationEventService = mockApplicationEventService;
		mockCommonRefundPaymentServiceImpl.addressDataService = mockAddressDataService;
		mockCommonRefundPaymentServiceImpl.cardService = mockCardService;
	}
	
	@Test
	public void updateToPayAmountTestShouldPopulateExpectedToPayAmount() {
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).updateToPayAmount(any(CartCmdImpl.class));
		mockCommonRefundPaymentServiceImpl.updateToPayAmount(cartCmdImpl);
		assertEquals(expected_ToPayAmount, cartCmdImpl.getToPayAmount());
    }
	
	@Test
	public void updateToPayAmountTestShouldNotPopulateUnexpectedToPayAmount() {
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).updateToPayAmount(any(CartCmdImpl.class));
		mockCommonRefundPaymentServiceImpl.updateToPayAmount(cartCmdImpl);
		assertNotEquals(unexpected_ToPayAmount, cartCmdImpl.getToPayAmount());
    }
	
	@Test
	public void createOrderFromCartBasedOnCustomerShouldCreateOrder() {
		cartCmdImpl.getCartDTO().setCardId(null);
		when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(orderDTO);
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).createOrderFromCart(any(CartCmdImpl.class));
		
		mockCommonRefundPaymentServiceImpl.createOrderFromCart(cartCmdImpl);
		verify(mockOrderDataService).create(any(OrderDTO.class));
	}
	
	@Test
	public void createOrderFromCartBasedOnCardShouldCreateOrder() {
		cartCmdImpl.getCartDTO().setCustomerId(null);
		cartCmdImpl.getCartDTO().setCardId(CARD_ID_1);
		when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(orderDTO);
		when(mockCardService.getCardDTOById(anyLong())).thenReturn(getTestCardDTO1());
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).createOrderFromCart(any(CartCmdImpl.class));
		
		mockCommonRefundPaymentServiceImpl.createOrderFromCart(cartCmdImpl);
		verify(mockOrderDataService).create(any(OrderDTO.class));
		verify(mockCardService).getCardDTOById(CARD_ID_1);
	}
	
	@Test
	public void createOrderFromCartForAnnonymsShouldCreateOrder() {
		cartCmdImpl.getCartDTO().setCardId(null);
		cartCmdImpl.getCartDTO().setCustomerId(null);
		when(mockOrderDataService.create(any(OrderDTO.class))).thenReturn(orderDTO);
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).createOrderFromCart(any(CartCmdImpl.class));
		
		mockCommonRefundPaymentServiceImpl.createOrderFromCart(cartCmdImpl);
		verify(mockOrderDataService).create(any(OrderDTO.class));
	}
	
	@Test
	public void updateOrderStatusToPaidShouldReturnOrderStatusPaid() {
		when(mockOrderDataService.findById(any(Long.class))).thenReturn(orderDTO);
		when(mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(orderDTO);
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).updateOrderStatusToPaid(any(CartCmdImpl.class));
		
		mockCommonRefundPaymentServiceImpl.updateOrderStatusToPaid(cartCmdImpl);
		
        assertEquals(OrderStatus.PAID.code(), cartCmdImpl.getCartDTO().getOrder().getStatus());
		verify(mockOrderDataService).findById(any(Long.class));
		verify(mockOrderDataService).createOrUpdate(any(OrderDTO.class));
	}
	
	@Test
	public void updateCartItemsWithOrderIdShouldInvokeDAO() {
        cartCmdImpl.getCartDTO().getOrder().setStatus(OrderStatus.PAID.code());
		when(mockOrderDataService.createOrUpdate(any(OrderDTO.class))).thenReturn(orderDTO);
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).updateCartItemsWithOrderId(any(CartCmdImpl.class));
		mockCommonRefundPaymentServiceImpl.updateCartItemsWithOrderId(cartCmdImpl);
		
		verify(mockOrderDataService).createOrUpdate(any(OrderDTO.class));
	}
	
	@Test
	public void createEventShouldInvokeApplicationEventService() {
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).createEvent(any(CartCmdImpl.class));
		mockCommonRefundPaymentServiceImpl.createEvent(cartCmdImpl);
		
		verify(mockApplicationEventService).create(any(Long.class), any(EventName.class), anyString());
    }
	
	@Test
	public void overwriteOrCreateNewAddressShouldCreateAddressWithOverwriteAddressFalse() {
		cartCmdImpl.setOverwriteAddress(false);
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).overwriteOrCreateNewAddress(any(CartCmdImpl.class));
		mockCommonRefundPaymentServiceImpl.overwriteOrCreateNewAddress(cartCmdImpl);
		
		verify(mockAddressDataService).createOrUpdate(any(AddressDTO.class));
	}
	
	@Test
	public void overwriteOrCreateNewAddressShouldCreateAddressWithOverwriteAddressTrue() {
		cartCmdImpl.setOverwriteAddress(true);
		doCallRealMethod().when(mockCommonRefundPaymentServiceImpl).overwriteOrCreateNewAddress(any(CartCmdImpl.class));
		mockCommonRefundPaymentServiceImpl.overwriteOrCreateNewAddress(cartCmdImpl);
		
		verify(mockAddressDataService).createOrUpdate(any(AddressDTO.class));
	}

	
}
