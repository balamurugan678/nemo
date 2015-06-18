package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertAndReturnModelAttributeOfType;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CollateOrdersService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.SecurityServiceImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageParameterValue;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ViewOrderHistoryControllerTest {

    ViewOrderHistoryController controller;
    OrderService mockOrderService;
    SecurityService securityService;
    CollateOrdersService collateOrdersService;

    @Before
    public void setup() {
        controller = new ViewOrderHistoryController();
        mockOrderService = mock(OrderService.class);
        securityService = mock(SecurityServiceImpl.class);
        collateOrdersService = mock(CollateOrdersService.class);
        controller.orderService = mockOrderService;
        controller.collateOrdersService = collateOrdersService;
        setField(controller, "securityService", securityService);
    }

    @Test
    public void testGetLoadOrderItems() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        OrderDTO order = new OrderDTO();
        ItemDTO item = new ProductItemDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        items.add(item);
        OrderItemsDTO orderItem = new OrderItemsDTO(order, items, null, null, null);
        orderItems.add(orderItem);
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(orderItems);
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234L);
        when(securityService.getLoggedInCustomer()).thenReturn(customer);

        ModelAndView modelAndView = controller.getLoadOrderItems();
        assertViewName(modelAndView, "ViewOrderHistoryView");
        assertAndReturnModelAttributeOfType(modelAndView, PageParameterValue.ORDERDAYS, List.class);
    }

    @Test
    public void testGetLoadOrderItemsNoItemsReturned() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(orderItems);
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234L);
        when(securityService.getLoggedInCustomer()).thenReturn(customer);

        ModelAndView modelAndView = controller.getLoadOrderItems();
        assertViewName(modelAndView, "ViewOrderHistoryView");
        assertAndReturnModelAttributeOfType(modelAndView, PageParameterValue.ORDERDAYS, List.class);
    }

    @Test
    public void testSetWebCreditItemName() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        orderItems = OrderTestUtil.getOrderItemsWebCredit();
        controller.setWebCreditItemName(orderItems);
        assertEquals(CartAttribute.WEB_CREDIT_REFUND, orderItems.get(0).getItems().get(0).getName());
    }

    @Test
    public void testNotWebCreditItemName() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        orderItems = OrderTestUtil.getOrderItems();
        controller.setWebCreditItemName(orderItems);
        assertNotEquals(CartAttribute.WEB_CREDIT_REFUND, orderItems.get(0).getItems().get(0).getName());
    }

    @Test
    public void getLoadOrderItemsShouldInvokeOrderService() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(orderItems);
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234L);
        when(securityService.getLoggedInCustomer()).thenReturn(customer);
        controller.getLoadOrderItems();
        verify(mockOrderService, times(4)).findOrderItemsByCustomerIdAndOrderStatus(any(Long.class), anyString());
    }
}
