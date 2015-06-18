package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Specification for new travel card service
 */
public interface TravelCardService extends CartItemAddService {

    boolean isTicketLongerThanTenMonthsTwelveDays(int diffMonths, int diffDaysExcludingMonths, long diffDays);

    boolean addPrePayTicketToCubic(CartCmdImpl cmd);

    boolean isTravelCard(ItemDTO itemDTO);
    
    CartDTO addUpdateItems(Long cartId, CartItemCmdImpl cartItemCmd);
        
}
