package com.novacroft.nemo.tfl.common.application_service.fulfilment;

import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Retrieves information relating to fulfilments
 */
public interface FulfilmentRetrievalService {
    Long getNumberOfOrdersInQueue(OrderStatus orderStatus);

    OrderDTO getFirstOrderPendingFulfilmentFromQueue(OrderStatus orderStatus);
}
