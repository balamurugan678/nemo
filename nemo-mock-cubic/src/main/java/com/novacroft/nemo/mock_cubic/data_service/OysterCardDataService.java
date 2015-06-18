package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCard;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
/**
* Oystercard transfer implementation.
*/

public interface OysterCardDataService extends BaseDataService<OysterCard, OysterCardDTO> {
  
    List<OysterCard>  findAllBy(String test);
    OysterCard  findByTest(String test);
    OysterCardDTO findByCardNumber(String cardNumber);
}
