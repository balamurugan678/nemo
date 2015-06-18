package com.novacroft.nemo.tfl.common.application_service.impl.fulfilment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentRetrievalService;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

@Service(value = "fulfilmentRetrievalService")
public class FulfilmentRetrievalServiceImpl implements FulfilmentRetrievalService {

	@Autowired
    protected OrderDataService orderDataService;

    @Override
    public Long getNumberOfOrdersInQueue(OrderStatus orderStatus) {
        return orderDataService.getNumberOfOrdersInQueue(orderStatus);
    }

    @Override
    public OrderDTO getFirstOrderPendingFulfilmentFromQueue(OrderStatus queue) {
        return orderDataService.findByFulfilmentQueue(queue.code()).get(0);
    }
}
