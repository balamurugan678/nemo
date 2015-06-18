package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;


public interface RefundPaymentService{

    void completeRefund(CartCmdImpl cmd);

    SelectListDTO createCardsSelectListForAdHocLoad(String cardNumber);
    

}
