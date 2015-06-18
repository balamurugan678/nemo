package com.novacroft.nemo.tfl.common.application_service.cubic_import;

/**
 * Take payment for an auto load performed by CUBIC
 */
public interface PayForAutoLoadPerformedService {
    Boolean isExistingCard(String cardNumber);

    void payForAutoLoadPerformed(String cardNumber, Integer pickUpLocation, Integer topUpAmount, String currency);
}
