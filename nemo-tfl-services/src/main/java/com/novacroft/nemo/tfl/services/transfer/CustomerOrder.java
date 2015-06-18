package com.novacroft.nemo.tfl.services.transfer;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrder extends AbstractBase {

	private List<Order> orders = new ArrayList<>();

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
