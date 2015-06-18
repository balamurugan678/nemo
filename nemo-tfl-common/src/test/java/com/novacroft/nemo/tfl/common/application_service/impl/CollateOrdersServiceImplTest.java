package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDayDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

/**
 * CollateOrdersService unit tests
 */
public class CollateOrdersServiceImplTest {

    @Test
    public void shouldInitialiseDay() {
        CollateOrdersServiceImpl service = new CollateOrdersServiceImpl();
        List<OrderItemsDayDTO> orderDays = new ArrayList<OrderItemsDayDTO>();
        OrderItemsDayDTO result = service.initialiseDay(orderDays, getAug19());
        assertEquals(getAug19(), result.getOrderDate());
        assertTrue(orderDays.contains(result));
    }

    @Test
    public void shouldGetDayNotWithExistingDay() {
        CollateOrdersServiceImpl service = new CollateOrdersServiceImpl();
        List<OrderItemsDayDTO> orderDays = new ArrayList<OrderItemsDayDTO>();
        OrderItemsDayDTO result = service.getDay(orderDays, getAug19());
        assertEquals(getAug19(), result.getOrderDate());
    }

    @Test
    public void shouldGetDayWithExistingDay() {
        CollateOrdersServiceImpl service = new CollateOrdersServiceImpl();
        List<OrderItemsDayDTO> orderDays = new ArrayList<OrderItemsDayDTO>();
        orderDays.add(new OrderItemsDayDTO(getAug19(), new ArrayList<OrderItemsDTO>()));
        orderDays.add(new OrderItemsDayDTO(getAug20(), new ArrayList<OrderItemsDTO>()));
        OrderItemsDayDTO result = service.getDay(orderDays, getAug19());
        assertEquals(getAug19(), result.getOrderDate());
    }

    @Test
    public void shouldAddOrderToDay() {

        List<OrderItemsDayDTO> orderDays = new ArrayList<OrderItemsDayDTO>();
        OrderItemsDTO order = new OrderItemsDTO(new OrderDTO(), new ArrayList<ItemDTO>(), new WebCreditSettlementDTO(), new PaymentCardSettlementDTO(), new AdHocLoadSettlementDTO());
        order.getOrder().setOrderDate(getAug19());
        CollateOrdersServiceImpl service = new CollateOrdersServiceImpl();
        service.addOrderItemToDay(orderDays, order);
        assertEquals(1, orderDays.get(0).getOrderItems().size());
        assertTrue(getAug19().equals(orderDays.get(0).getOrderDate()));
        
    }

    @Test
    public void shouldCollateByDay() {
        CollateOrdersServiceImpl service = new CollateOrdersServiceImpl();
        List<OrderItemsDTO> orderItems = new ArrayList<>();
        OrderItemsDTO order1 = new OrderItemsDTO(new OrderDTO(), new ArrayList<ItemDTO>(), new WebCreditSettlementDTO(), new PaymentCardSettlementDTO(), new AdHocLoadSettlementDTO());
        OrderItemsDTO order2 = new OrderItemsDTO(new OrderDTO(), new ArrayList<ItemDTO>(), new WebCreditSettlementDTO(), new PaymentCardSettlementDTO(), new AdHocLoadSettlementDTO());
        OrderItemsDTO order3 = new OrderItemsDTO(new OrderDTO(), new ArrayList<ItemDTO>(), new WebCreditSettlementDTO(), new PaymentCardSettlementDTO(), new AdHocLoadSettlementDTO());
        OrderItemsDTO order4 = new OrderItemsDTO(new OrderDTO(), new ArrayList<ItemDTO>(), new WebCreditSettlementDTO(), new PaymentCardSettlementDTO(), new AdHocLoadSettlementDTO());
        OrderItemsDTO order5 = new OrderItemsDTO(new OrderDTO(), new ArrayList<ItemDTO>(), new WebCreditSettlementDTO(), new PaymentCardSettlementDTO(), new AdHocLoadSettlementDTO());
        order1.getOrder().setOrderDate(getAug19());
        order2.getOrder().setOrderDate(getAug19());
        order3.getOrder().setOrderDate(getAug20());
        order4.getOrder().setOrderDate(getAug20());
        order5.getOrder().setOrderDate(getAug20());
        orderItems.add(order1);
        orderItems.add(order2);
        orderItems.add(order3);
        orderItems.add(order4);
        orderItems.add(order5);
        List<OrderItemsDayDTO> orderDays = service.collateByDay(orderItems);
        assertTrue(Integer.compare(orderDays.size(),2)==0);
    }

}


