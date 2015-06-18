package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Specification for traded travel card service
 */
public interface TradedTravelCardService {

    ProductItemDTO getTravelCardItemForTradedTicket(CartItemCmdImpl cmd, ProductItemDTO productItemDTO);

}
