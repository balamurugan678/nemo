package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

/**
 * Cart data service specification
 */
public interface CardDataService extends BaseDataService<Card, CardDTO> {

    List<CardDTO> findByCustomerId(Long customerId);
    


    List<CardDTO> findByUsername(String username);


    CardDTO findByCardNumber(String cardNumber);

    CardDTO findByCustomerIdAndCardNumber(Long customerId, String cardNumber);

    CardDTO findByCustomerIdAndExternalId(Long customerId, Long externalId);
    
    List<CardDTO> findHotlistedCards();

    List<CardDTO> findHotlistedCardsWithReason();

    List<CardDTO> findByPaymentCardId(Long paymentCardId);
    
    List<CardDTO> getAllCardsFromUserExceptCurrent(String cardNumber);
}
