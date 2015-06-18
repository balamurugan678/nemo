package com.novacroft.nemo.tfl.online.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertAndReturnModelAttributeOfType;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.SecurityServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ViewOrderDetailsControllerTest {

    ViewOrderDetailsController controller;
    SecurityService securityService;
    private OrderService mockOrderService;

    @Before
    public void setup() {
        controller = new ViewOrderDetailsController();
        securityService = mock(SecurityServiceImpl.class);
        mockOrderService = mock(OrderService.class);
        controller.orderService = mockOrderService;
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
        assertViewName(modelAndView, "ViewOrderDetailsView");
        assertAndReturnModelAttributeOfType(modelAndView, "orders", ArrayList.class);
    }

    @Test
    public void testGetLoadOrderItemsNoItemsReturned() {
        List<OrderItemsDTO> orderItems = new ArrayList<OrderItemsDTO>();
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(orderItems);
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234L);
        when(securityService.getLoggedInCustomer()).thenReturn(customer);

        ModelAndView modelAndView = controller.getLoadOrderItems();
        assertViewName(modelAndView, "ViewOrderDetailsView");
        assertAndReturnModelAttributeOfType(modelAndView, "orders", ArrayList.class);
    }

}
