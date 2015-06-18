package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;


public interface CommonRefundPaymentService{
    
    void updateToPayAmount(CartCmdImpl cmd);
    
    CartCmdImpl createOrderFromCart(CartCmdImpl cmd);
    
    void updateOrderStatusToPaid(CartCmdImpl cmd);
    
    void updateCartItemsWithOrderId(CartCmdImpl cmd);
    
    void createEvent(CartCmdImpl cmd);
    
    AddressDTO overwriteOrCreateNewAddress(CartCmdImpl cmd);
}
