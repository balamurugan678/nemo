package com.novacroft.nemo.tfl.common.application_service.impl;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

public class WebCreditServiceImplTest {
    private static Integer INTEGER_ZERO = 0;
    private static Integer INTEGER_TEN = 10;
    private static Integer INTEGER_MINUS_TEN = -10;
    private static Long TEST_ORDER_ID = 1l;
    
    private WebCreditServiceImpl service;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    
    @Before
    public void setUp() {
        service = new WebCreditServiceImpl();
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        service.webAccountCreditSettlementDataService = mockWebCreditSettlementDataService;
    }
    
    @Test
    public void checkAvailableBalanceIfBalanceIsZero() {
        when(mockWebCreditSettlementDataService.getBalance(anyLong())).thenReturn(INTEGER_ZERO);
        assertEquals(INTEGER_ZERO, service.getAvailableBalance(CUSTOMER_ID_1));
    }
    
    @Test
    public void checkAvailableBalanceIfBalanceIsPositive() {
        when(mockWebCreditSettlementDataService.getBalance(anyLong())).thenReturn(INTEGER_TEN);
        assertEquals(INTEGER_ZERO, service.getAvailableBalance(CUSTOMER_ID_1));
    }
    
    @Test
    public void checkAvailableBalanceIfBalanceIsMinus() {
        when(mockWebCreditSettlementDataService.getBalance(anyLong())).thenReturn(INTEGER_MINUS_TEN);
        assertEquals(INTEGER_TEN, service.getAvailableBalance(CUSTOMER_ID_1));
    }
    
    @Test
    public void shouldApplyWebCreditToOrder() {
        when(mockWebCreditSettlementDataService.createOrUpdate(any(WebCreditSettlementDTO.class)))
            .thenReturn(null);
        service.applyWebCreditToOrder(TEST_ORDER_ID, INTEGER_TEN);
        verify(mockWebCreditSettlementDataService).createOrUpdate(any(WebCreditSettlementDTO.class));
    }
}
