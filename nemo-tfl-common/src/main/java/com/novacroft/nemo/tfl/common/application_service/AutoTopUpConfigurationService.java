package com.novacroft.nemo.tfl.common.application_service;

/**
 * Configure Oyster Card Auto Top Up (Autoload)
 */
public interface AutoTopUpConfigurationService {
    void changeConfiguration(Long cardId, Long orderId, Integer amount, Long pickUpLocation);
}
