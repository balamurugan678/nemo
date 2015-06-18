package com.novacroft.nemo.tfl.common.comparator;

import java.util.Comparator;

import com.novacroft.nemo.tfl.common.transfer.OrderItemsDayDTO;

public class OrderItemsDayComparator implements Comparator<OrderItemsDayDTO> {

	@Override
	public int compare(OrderItemsDayDTO orderDay1, OrderItemsDayDTO orderDay2) {
		
		return orderDay2.getOrderDate().compareTo(orderDay1.getOrderDate());
	}

}
