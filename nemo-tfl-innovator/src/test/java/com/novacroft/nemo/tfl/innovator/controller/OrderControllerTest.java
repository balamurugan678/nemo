package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getOrders;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_ORDER;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

public class OrderControllerTest {
    private OrderController controller;

    private CustomerDataService mockCustomerDataService;
    private OrderService mockOrderService;

    private CustomerDTO mockCustomerDTO;

    @Before
    public void setUp() {
        this.controller = mock(OrderController.class);

        this.mockOrderService = mock(OrderService.class);
        this.mockCustomerDataService = mock(CustomerDataService.class);
        setField(this.controller, "customerDataService", this.mockCustomerDataService);
        setField(this.controller, "orderService", this.mockOrderService);        

        this.mockCustomerDTO = mock(CustomerDTO.class);
    }

    @Test
    public void shouldLoadOrderByCustomerId() {
        when(this.controller.loadOrderByCustomerId(anyLong())).thenCallRealMethod();
        when(this.mockCustomerDTO.getId()).thenReturn(CUSTOMER_ID_1);
        when(this.mockCustomerDataService.findById(eq(CUSTOMER_ID_1))).thenReturn(this.mockCustomerDTO);
        when(this.mockOrderService.findOrdersByCustomerId(anyLong())).thenReturn(getOrders());

        ModelAndView result = this.controller.loadOrderByCustomerId(CUSTOMER_ID_1);
        assertViewName(result, INV_ORDER);
        assertModelAttributeAvailable(result, "orders");
        assertModelAttributeAvailable(result, "customerId");

        verify(this.mockCustomerDataService).findById(eq(CUSTOMER_ID_1));
        verify(this.mockOrderService).findOrdersByCustomerId(anyLong());
    }

    @Test
    public void shouldCheckSearch() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("orderNumber")).thenReturn(OrderTestUtil.ORDER_NUMBER.toString());
              
        when(this.controller.checkSearch(any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.mockOrderService.findOrderByNumber(anyLong())).thenReturn(getTestOrderDTO1());

        this.controller.checkSearch(request);
        verify(this.mockOrderService).findOrderByNumber(OrderTestUtil.ORDER_NUMBER);
        verify(request, times(2)).getParameter("orderNumber");

    }
}