package com.novacroft.nemo.tfl.common.application_service;

public interface CardUpdateService {
    Integer requestCardUpdatePrePayTicket(String cardNumber, Integer productCode, String startDate, String expiryDate, Integer productPrice, Long pickUpLocation);
    
    Integer requestCardUpdatePrePayValue(String cardNumber, Long pickUpLocation, Integer prePayValue, Integer currency, String cartType);
    
    Integer requestCardAutoLoadChange(Long cardId, Long pickUpLocation, Integer autoLoadState, Long customerId);
    
    Integer requestCardAutoLoadChange(Long cardId, Long pickUpLocation, Integer autoLoadState);
    
    void createLostOrStolenEventForHotlistedCard(String cardNumber, Integer hotlistReasonId);
}
