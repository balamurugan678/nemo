package com.novacroft.nemo.tfl.services.application_service;

import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public interface OysterCardService {

    Card getCard(String cardNumber);
    WebServiceResult createOysterCard(Long externalCustomerId);

    WebServiceResult createOysterCard(Long externalCustomerId, String cardNumber);

    Card getCard(Long externalCardId);
    
    Card updateCard(Long externalCardId, Card card);
}
