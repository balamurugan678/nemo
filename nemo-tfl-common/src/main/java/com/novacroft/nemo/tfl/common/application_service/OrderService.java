package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;

public interface OrderService {

    List<OrderDTO> findOrdersByCustomerId(Long customerId);
    
    List<OrderItemsDTO> findOrderItemsByCustomerIdAndOrderStatus(Long customerId, String orderStatus);

    List<OrderDTO> findOrdersByCustomerIdAndDates(Long customerId, String startDate, String endDate);

    List<OrderItemsDTO> findOrderItemsByCustomerId(Long customerId); 

    OrderDTO findOrderByNumber(Long orderNumber);

    OrderDTO updateOrderStatus(Long orderId);
}
