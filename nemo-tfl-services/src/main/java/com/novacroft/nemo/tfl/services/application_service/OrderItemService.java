package com.novacroft.nemo.tfl.services.application_service;

import com.novacroft.nemo.tfl.services.transfer.CustomerOrder;
import com.novacroft.nemo.tfl.services.transfer.Order;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

/**
 * Application service for orders.
 * 
 */
public interface OrderItemService {

    CustomerOrder getOrderItemsByCustomerIdAndDates(Long customerId, String dateFrom, String dateTo);

    Order getOrderByExternalOrderId(Long externalOrderId);

    WebServiceResult cancelOrder(Long externalCustomerId, Long externalOrderId);

    WebServiceResult completeCancelOrder(Long externalCustomerId, Long externalOrderId);
}
