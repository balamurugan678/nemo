package com.novacroft.nemo.tfl.common.application_service.impl.fulfilment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.OrderDataServiceImpl;

public class FulfilmentRetrievalServiceImplTest {

    private FulfilmentRetrievalServiceImpl service;
	private OrderDataService mockOrderDataService;
	@Before
    public void setUp() {
        service = new FulfilmentRetrievalServiceImpl();
		mockOrderDataService = mock(OrderDataServiceImpl.class);
		
		service.orderDataService = mockOrderDataService;
	}
	
	@Test
    public void getNumberOfOrdersInQueueShouldCallOrderDataServiceWithCorrectCodeAndReturnLong() {
        when(mockOrderDataService.getNumberOfOrdersInQueue(any(OrderStatus.class))).thenReturn(1L);
        assertEquals(Long.class, service.getNumberOfOrdersInQueue(OrderStatus.FULFILMENT_PENDING).getClass());
        verify(mockOrderDataService).getNumberOfOrdersInQueue(OrderStatus.FULFILMENT_PENDING);
	}
	
	@Test
    public void getgetFirstOrderPendingFulfilmentFromQueueAndReturnOrder() {
        when(mockOrderDataService.findByFulfilmentQueue(anyString())).thenReturn(Arrays.asList(OrderTestUtil.getTestOrderDTO1()));
        assertEquals(OrderTestUtil.getTestOrderDTO1(), service.getFirstOrderPendingFulfilmentFromQueue(OrderStatus.FULFILMENT_PENDING));
        verify(mockOrderDataService).findByFulfilmentQueue(OrderStatus.FULFILMENT_PENDING.code());
    }
}

