package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmdItemWithDefaultItemsAndOrderDetails;
import static com.novacroft.nemo.test_support.WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTO1;
import static com.novacroft.nemo.test_support.WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTOEmptyList;
import static com.novacroft.nemo.test_support.WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTOList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

/**
 * WebAccountCreditRefundPaymentServiceImpl unit tests
 */
public class WebAccountCreditRefundPaymentServiceImplTest {
    
    private WebAccountCreditRefundPaymentServiceImpl service;
    private WebAccountCreditRefundPaymentServiceImpl mockService;
    private WebCreditService mockWebCreditService;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    private CommonRefundPaymentService mockCommonRefundPaymentService;
    
    @Before
    public void setup() {
        service = new WebAccountCreditRefundPaymentServiceImpl();
        mockWebCreditService = mock(WebCreditService.class);
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        mockCommonRefundPaymentService = mock(CommonRefundPaymentService.class);
        service.webCreditService = mockWebCreditService;
        service.webCreditSettlementDataService = mockWebCreditSettlementDataService;
        service.commonRefundPaymentService = mockCommonRefundPaymentService;
        mockService = mock(WebAccountCreditRefundPaymentServiceImpl.class);
    }

    @Test
    public void shouldMakePaymentWhenWebCreditSettlementListIsNotEmpty() {
        doNothing().when(mockCommonRefundPaymentService).updateToPayAmount(any(CartCmdImpl.class));
        when(mockCommonRefundPaymentService.createOrderFromCart(any(CartCmdImpl.class))).thenReturn(getTestCartCmdItemWithDefaultItemsAndOrderDetails());
        doNothing().when(mockCommonRefundPaymentService).updateOrderStatusToPaid(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateCartItemsWithOrderId(any(CartCmdImpl.class));
        doNothing().when(mockWebCreditService).applyWebCreditToOrder(anyLong(), anyInt());
        when(mockWebCreditSettlementDataService.findByOrderId(anyLong())).thenReturn(getWebCreditSettlementDTOList());
        doNothing().when(mockCommonRefundPaymentService).createEvent(any(CartCmdImpl.class));
        when(mockWebCreditSettlementDataService.createOrUpdate(any(WebCreditSettlementDTO.class))).thenReturn(getWebCreditSettlementDTO1());
        service.makePayment(getTestCartCmdItemWithDefaultItemsAndOrderDetails());        
        assertTrue(mockWebCreditSettlementDataService.findByOrderId(anyLong()).size() > 0);
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateToPayAmount(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).createOrderFromCart(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateOrderStatusToPaid(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateCartItemsWithOrderId(any(CartCmdImpl.class));        
        verify(mockWebCreditService, atLeastOnce()).applyWebCreditToOrder(anyLong(), anyInt());        
        verify(mockWebCreditSettlementDataService, atLeastOnce()).findByOrderId(anyLong());        
        verify(mockWebCreditSettlementDataService, atLeastOnce()).createOrUpdate(any(WebCreditSettlementDTO.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).createEvent(any(CartCmdImpl.class));        
    }
    
    @Test
    public void shouldNotMakePaymentWhenWebCreditSettlementListIsEmpty() {
        doNothing().when(mockCommonRefundPaymentService).updateToPayAmount(any(CartCmdImpl.class));
        when(mockCommonRefundPaymentService.createOrderFromCart(any(CartCmdImpl.class))).thenReturn(getTestCartCmdItemWithDefaultItemsAndOrderDetails());
        doNothing().when(mockCommonRefundPaymentService).updateOrderStatusToPaid(any(CartCmdImpl.class));
        doNothing().when(mockCommonRefundPaymentService).updateCartItemsWithOrderId(any(CartCmdImpl.class));
        doNothing().when(mockWebCreditService).applyWebCreditToOrder(anyLong(), anyInt());
        when(mockWebCreditSettlementDataService.findByOrderId(anyLong())).thenReturn(getWebCreditSettlementDTOEmptyList());
        doNothing().when(mockCommonRefundPaymentService).createEvent(any(CartCmdImpl.class));
        when(mockWebCreditSettlementDataService.createOrUpdate(any(WebCreditSettlementDTO.class))).thenReturn(getWebCreditSettlementDTO1());
        service.makePayment(getTestCartCmdItemWithDefaultItemsAndOrderDetails());        
        assertFalse(mockWebCreditSettlementDataService.findByOrderId(anyLong()).size() > 0);
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateToPayAmount(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).createOrderFromCart(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateOrderStatusToPaid(any(CartCmdImpl.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).updateCartItemsWithOrderId(any(CartCmdImpl.class));        
        verify(mockWebCreditService, atLeastOnce()).applyWebCreditToOrder(anyLong(), anyInt());        
        verify(mockWebCreditSettlementDataService, atLeastOnce()).findByOrderId(anyLong());        
        verify(mockWebCreditSettlementDataService, never()).createOrUpdate(any(WebCreditSettlementDTO.class));        
        verify(mockCommonRefundPaymentService, atLeastOnce()).createEvent(any(CartCmdImpl.class));        
    }    
}
