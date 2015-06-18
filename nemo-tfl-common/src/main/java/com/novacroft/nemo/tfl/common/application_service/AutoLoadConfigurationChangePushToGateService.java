package com.novacroft.nemo.tfl.common.application_service;

/**
 * Push auto load configuration change to the gate (CUBIC)
 */
public interface AutoLoadConfigurationChangePushToGateService {
    /**
     * @return request sequence number
     */
    Integer requestAutoLoadConfigurationChange(String cardNumber, Integer autoLoadState, Long pickUpLocation);
}
