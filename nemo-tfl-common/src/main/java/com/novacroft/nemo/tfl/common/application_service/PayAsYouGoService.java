package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public interface PayAsYouGoService extends CartItemAddService{
    
    CartDTO addAutoTopUpCartItem(CartDTO cartDTO, CartItemCmdImpl cartItemCmd);

    CartDTO removeNonApplicableAutoTopUpCartItem(CartDTO cartDTO);

    boolean updatePrePayValueToCubic(CartCmdImpl cmd);

    void updateSettledAutoTopUpCartItem(CartDTO cartDTO, Long orderId, Long stationId, AutoTopUpConfigurationItemDTO existingItemDTO);

}
