package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;


public interface WebAccountCreditRefundPaymentService{
    void makePayment(CartCmdImpl cmd);
}
