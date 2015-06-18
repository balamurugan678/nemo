package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.getTestBACSSettlementDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestAddress;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndOrderDetails;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

public class BacsRefundPaymentServiceImplTest {

    private BacsRefundPaymentServiceImpl mockService;
    private CommonRefundPaymentService mockCommonRefundPaymentService;
    private BACSSettlementDataService mockBACSSettlementDataService;
    private AddressDataService mockAddressDataService;
    private CustomerDataService mockCustomerDataService;
    private CartCmdImpl mockCartCmdImpl;
    private CartDTO mockCartDTO;
    private OrderDTO mockOrderDTO;
    
    @Before
    public void setUp() {
        mockService = mock(BacsRefundPaymentServiceImpl.class);
        mockCommonRefundPaymentService = mock(CommonRefundPaymentService.class);
        mockBACSSettlementDataService = mock(BACSSettlementDataService.class);
        mockAddressDataService = mock(AddressDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockService.commonRefundPaymentService = mockCommonRefundPaymentService;
        mockService.bacsSettlementDataService = mockBACSSettlementDataService;
        mockService.addressDataService = mockAddressDataService;
        mockService.customerDataService = mockCustomerDataService;
        mockCartCmdImpl = mock(CartCmdImpl.class);
        mockCartDTO = mock(CartDTO.class);
        mockOrderDTO = mock(OrderDTO.class);
    }

    @Test
    public void shouldMakePayment() {
        doCallRealMethod().when(mockService).makePayment(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateToPayAmount(any(CartCmdImpl.class));
        when(mockCommonRefundPaymentService.createOrderFromCart(any(CartCmdImpl.class))).thenReturn(getTestCartCmd1());
        doNothing().when(mockCommonRefundPaymentService).updateOrderStatusToPaid(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateCartItemsWithOrderId(any(CartCmdImpl.class));
        doNothing().when(mockService).makePaymentByBACS(any(CartCmdImpl.class));
        mockService.makePayment(getTestCartCmd1());
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateToPayAmount(any(CartCmdImpl.class));
        verify(mockCommonRefundPaymentService, atLeastOnce()).createOrderFromCart(any(CartCmdImpl.class));
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateOrderStatusToPaid(any(CartCmdImpl.class));
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateCartItemsWithOrderId(any(CartCmdImpl.class));
    }

    @Test
    public void shouldMakePaymentByBACS() {
        doCallRealMethod().when(mockService).makePaymentByBACS(any(CartCmdImpl.class));
        when(mockService.createBACSSettlementDTOFromCartCmdImpl(any(CartCmdImpl.class))).thenReturn(getTestBACSSettlementDTO1());
        when(mockBACSSettlementDataService.createOrUpdate(any(BACSSettlementDTO.class))).thenReturn(getTestBACSSettlementDTO1());
        mockService.makePaymentByBACS(getTestCartCmd1());
        verify(mockService, atLeastOnce()).createBACSSettlementDTOFromCartCmdImpl(any(CartCmdImpl.class));
        verify(mockBACSSettlementDataService, atLeastOnce()).createOrUpdate(any(BACSSettlementDTO.class));        
    }

    @Test
    public void shouldCreateBACSSettlementDTOFromCartCmdImpl() {
        doCallRealMethod().when(mockService).createBACSSettlementDTOFromCartCmdImpl(any(CartCmdImpl.class));
        when(mockCommonRefundPaymentService.overwriteOrCreateNewAddress(any(CartCmdImpl.class))).thenReturn(getTestAddress());
        mockService.createBACSSettlementDTOFromCartCmdImpl(getTestCartCmdItemWithDefaultItemsAndOrderDetails());
        verify(mockCommonRefundPaymentService, atLeastOnce()).overwriteOrCreateNewAddress(any(CartCmdImpl.class));
    }
}
