package com.novacroft.nemo.tfl.common.application_service.cubic_import;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

public interface AdHocLoadRefundPaymentService {
    void makePayment(CartCmdImpl cmd);
}
