package com.novacroft.nemo.tfl.services.controller;

import static com.novacroft.nemo.test_support.AgentAuthenticationProviderServiceImplTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_END_DATE;
import static com.novacroft.nemo.test_support.DateTestUtil.WEB_SERVICE_REQUEST_START_DATE;
import static com.novacroft.nemo.tfl.services.test_support.CustomerOrdersTestUtil.getCustomerOrdersList;
import static com.novacroft.nemo.tfl.services.test_support.StationDataTestUtil.getTestStation;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.services.application_service.OrderItemService;
import com.novacroft.nemo.tfl.services.application_service.StationService;
import com.novacroft.nemo.tfl.services.test_support.OrdersTestUtil;
import com.novacroft.nemo.tfl.services.test_support.WebServiceResultTestUtil;
import com.novacroft.nemo.tfl.services.transfer.CustomerOrder;
import com.novacroft.nemo.tfl.services.transfer.Station;

/**
 * Order Controller unit tests.
 */
public class OrderControllerTest {

    private OrderController controller;
    private OrderItemService mockOrderItemService;
    private StationService mockStationService;

    @Before
    public void setUp() {

        controller = new OrderController();
        mockOrderItemService = mock(OrderItemService.class);
        mockStationService = mock(StationService.class);
        controller.orderItemService = mockOrderItemService;
        controller.stationService = mockStationService;
    }

    @Test
    public void canceOrder() {
        when(mockOrderItemService.cancelOrder(anyLong(), anyLong())).thenReturn(WebServiceResultTestUtil.getCancelOrder1());
        assertNotNull(controller.cancelOrder(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, OrderTestUtil.EXTERNAL_ID));
    }

    @Test
    public void completeCancelOrder() {
        when(mockOrderItemService.completeCancelOrder(anyLong(), anyLong())).thenReturn(WebServiceResultTestUtil.getCompletedCancelOrder());
        assertNotNull(controller.completeCancelOrder(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, OrderTestUtil.EXTERNAL_ID));

    }

    @Test
    public void getOrder() {
        when(mockOrderItemService.getOrderByExternalOrderId(anyLong())).thenReturn(OrdersTestUtil.getTestOrder());
        assertNotNull(controller.getOrderFromExternalOrderId(OrderTestUtil.ORDER_NUMBER));
    }

    @Test
    public void shouldGetCustomerOrdersAndItemsByDates() {
        when(mockOrderItemService.getOrderItemsByCustomerIdAndDates(anyLong(), anyString(), anyString())).thenReturn(getCustomerOrdersList());
        CustomerOrder orderList = controller.getCustomerOrdersAndItemsByDates(CUSTOMER_ID_1, WEB_SERVICE_REQUEST_START_DATE,
                        WEB_SERVICE_REQUEST_END_DATE);
        assertNotNull(orderList);
        assertNotEquals(0, orderList.getOrders().size());
        verify(mockOrderItemService).getOrderItemsByCustomerIdAndDates(anyLong(), anyString(), anyString());
    }

    @Test
    public void shouldGetOutstandingOrderStation() {
        when(mockStationService.findStationForOutstandingOrder(anyLong(), anyLong())).thenReturn(getTestStation());
        Station station = controller.getOutstandingOrderStation(CUSTOMER_ID_1, CARD_ID);
        assertNotNull(station);
        verify(mockStationService).findStationForOutstandingOrder(anyLong(), anyLong());
    }
}
