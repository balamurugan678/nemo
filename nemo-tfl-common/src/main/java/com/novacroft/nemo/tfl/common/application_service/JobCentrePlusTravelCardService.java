package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;



/**
 * Specification for new travel card service
 */
public interface JobCentrePlusTravelCardService {
    
    Integer checkJobCentrePlusDiscountAndUpdateTicketPrice(CartItemCmdImpl cmd, Integer ticketPrice);
    
}
