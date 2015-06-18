package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * Interface that all Services that can add items to the shopping cart should implement
 */
public interface CartItemAddService {

    CartDTO addCartItemForExistingCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd);

    CartDTO addCartItemForNewCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd);
}
