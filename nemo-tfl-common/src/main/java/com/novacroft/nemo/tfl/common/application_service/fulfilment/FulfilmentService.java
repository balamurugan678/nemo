package com.novacroft.nemo.tfl.common.application_service.fulfilment;

import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;

/**
 * Retrieves information relating to fulfilments
 */
public interface FulfilmentService {
    void updateOrderStatus(OrderDTO order, String orderStatus);
    
    void associateFulfiledOysterCardNumberWithTheOrder(OrderDTO order, String fulfilledOysterNumber);
    
    FulfilmentCmd loadOrderDetails(FulfilmentCmd fulfilmentCmd);
    
    OrderDTO getOrderFromOrderNumber(Long orderNumber);
}
