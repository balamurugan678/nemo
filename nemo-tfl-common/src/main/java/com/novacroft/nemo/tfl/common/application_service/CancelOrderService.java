package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;

public interface CancelOrderService {
    CancelOrderResultDTO cancelOrderWithExternalOrderIdAndCustomerId(Long externalCustomerId, Long externalOrderId);

    CancelOrderResultDTO cancelOrderWithOrderIdAndCustomerId(Long customerId, Long orderId);

    CancelOrderResultDTO completeCancelOrderWithExternalOrderIdAndCustomerId(Long externalCustomerId, Long externalOrderId);

    CancelOrderResultDTO completeCancelOrderWithOrderIdAndCustomerId(Long customerId, Long orderId);
}
