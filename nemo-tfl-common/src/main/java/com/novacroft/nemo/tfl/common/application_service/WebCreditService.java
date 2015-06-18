package com.novacroft.nemo.tfl.common.application_service;

/**
 * Application service to get web credit balance and make payments/refunds using web credit
 */
public interface WebCreditService {
    Integer getAvailableBalance(Long customerId);

    void applyWebCreditToOrder(Long orderId, Integer webCreditPenceAmount);
}
