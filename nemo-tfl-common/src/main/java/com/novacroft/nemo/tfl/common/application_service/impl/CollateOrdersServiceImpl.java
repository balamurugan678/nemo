package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.CollateOrdersService;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDayDTO;

@Service("collateOrdersService")
public class CollateOrdersServiceImpl implements CollateOrdersService{

	@Override
    public List<OrderItemsDayDTO> collateByDay(List<OrderItemsDTO> orderItems) {
        List<OrderItemsDayDTO> orderDays = new ArrayList<OrderItemsDayDTO>();
        for (OrderItemsDTO orderItem : orderItems) {
            addOrderItemToDay(orderDays, orderItem);
        }
        return orderDays;
    }

    protected void addOrderItemToDay(List<OrderItemsDayDTO> orderDays,
			OrderItemsDTO orderItem) {
    	Date orderDate = orderItem.getOrder().getOrderDate();
    	OrderItemsDayDTO day = getDay(orderDays, orderDate);
        day.getOrderItems().add(orderItem);
        day.setOrderDate(orderDate);
	}

    protected OrderItemsDayDTO getDay(List<OrderItemsDayDTO> orderDays, Date orderDate) {
        for (OrderItemsDayDTO day : orderDays) {
            if (DateUtils.isSameDay(day.getOrderDate(), orderDate)) {
                return day;
            }
        }
        return initialiseDay(orderDays, orderDate);
    }

    protected OrderItemsDayDTO initialiseDay(List<OrderItemsDayDTO> orderDays, Date orderDate) {
        OrderItemsDayDTO day = new OrderItemsDayDTO(orderDate, new ArrayList<OrderItemsDTO>());
        orderDays.add(day);
        return day;
    }
}
