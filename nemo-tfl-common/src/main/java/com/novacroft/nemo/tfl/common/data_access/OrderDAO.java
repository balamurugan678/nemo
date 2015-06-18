package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Order;

/**
 * Order data access class
 */
@Repository("orderDAO")
public class OrderDAO extends BaseDAOImpl<Order> {
    public Long getNextOrderNumber() {
    	return getNextSequenceNumber("order_number_seq");
    }
}
