package com.novacroft.nemo.tfl.services.test_support;

import static com.novacroft.nemo.tfl.services.test_support.OrdersTestUtil.getServiceOrderList;

import com.novacroft.nemo.tfl.services.transfer.CustomerOrder;

public final class CustomerOrdersTestUtil {   
    
    public static CustomerOrder getCustomerOrdersList() {
        CustomerOrder orders = new CustomerOrder();
        orders.setOrders(getServiceOrderList());
        return orders;
    }
    
}
