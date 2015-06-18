package com.novacroft.nemo.tfl.common.application_service.fulfilment;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 *  Populates order statuses according to order items contained in an order 
 */
public interface FulfilmentQueuePopulationService {
	Boolean isFirstIssue(CartDTO cartDTO);
	Boolean isPayAsYouGoTheOnlyItemPresentInOrder(List<ItemDTO> orderItems);
	Boolean isAutoTopUpPresentInOrder(List<ItemDTO> orderItems);
	Boolean isAnnualTravelCardPresentInOrder(List<ItemDTO> orderItems);
	void determineFulfilmentQueueAndUpdateOrderStatus(boolean firstIssueOrder, boolean replacementOrder, OrderDTO order);
	
}
