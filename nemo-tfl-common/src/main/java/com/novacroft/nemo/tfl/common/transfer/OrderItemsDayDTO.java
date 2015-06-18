package com.novacroft.nemo.tfl.common.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderItemsDayDTO {

	private Date orderDate;
	private List<OrderItemsDTO> orderItems = new ArrayList<>();
	
	public OrderItemsDayDTO() {
	}
	
	public OrderItemsDayDTO(Date orderDate, List<OrderItemsDTO> orderItems) {
		super();
		this.orderDate = orderDate;
		this.orderItems = orderItems;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public List<OrderItemsDTO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemsDTO> orderItems) {
		this.orderItems = orderItems;
	}
	
}
