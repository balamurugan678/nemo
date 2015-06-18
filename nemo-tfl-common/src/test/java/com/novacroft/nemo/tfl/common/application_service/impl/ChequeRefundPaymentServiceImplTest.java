package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndOrderDetails;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.getTestChequeSettlementDTO1;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public class ChequeRefundPaymentServiceImplTest {

    private ChequeRefundPaymentServiceImpl chequeRefundService;
    private ChequeRefundPaymentServiceImpl mockChequeRefundService;
    private CommonRefundPaymentService mockCommonRefundPaymentService;
    private ChequeSettlementDataService mockChequeSettlementDataService;
    
    @Before
    public void setUp() throws Exception {
        chequeRefundService = new ChequeRefundPaymentServiceImpl();
        mockCommonRefundPaymentService = mock(CommonRefundPaymentService.class);
        mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        chequeRefundService.commonRefundPaymentService = mockCommonRefundPaymentService;
        chequeRefundService.chequeSettlementDataService = mockChequeSettlementDataService;
        mockChequeRefundService = mock(ChequeRefundPaymentServiceImpl.class);
    }

    @Test
    public void shouldMakePayment() {
        doNothing().when(mockCommonRefundPaymentService).updateToPayAmount(any(CartCmdImpl.class));
        when(mockCommonRefundPaymentService.createOrderFromCart(any(CartCmdImpl.class))).thenReturn(getTestCartCmdItemWithDefaultItemsAndOrderDetails());
        doNothing().when(mockCommonRefundPaymentService).updateOrderStatusToPaid(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateCartItemsWithOrderId(any(CartCmdImpl.class));
        doNothing().when(mockChequeRefundService).makePaymentByCheque(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).createEvent(any(CartCmdImpl.class));
        when(mockChequeSettlementDataService.createOrUpdate(any(ChequeSettlementDTO.class))).thenReturn(getTestChequeSettlementDTO1());
        chequeRefundService.makePayment(getTestCartCmdItemWithDefaultItemsAndOrderDetails());        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateToPayAmount(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).createOrderFromCart(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateOrderStatusToPaid(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateCartItemsWithOrderId(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).createEvent(any(CartCmdImpl.class));        
        verify(mockChequeSettlementDataService, atLeastOnce()).createOrUpdate(any(ChequeSettlementDTO.class));        
    }

}
