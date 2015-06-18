package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDayDTO;

public interface CollateOrdersService {

	List<OrderItemsDayDTO> collateByDay(List<OrderItemsDTO> journeys);
}
