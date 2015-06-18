package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardHotListReasons;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardHotListReasonsDTO;
/**
* OystercardHotListReasons data service implementation.
*/

public interface OysterCardHotListReasonsDataService extends BaseDataService<OysterCardHotListReasons, OysterCardHotListReasonsDTO> {
  
    List<OysterCardHotListReasonsDTO> findAllBy(String test);
    OysterCardHotListReasonsDTO findByTest(String test);
    OysterCardHotListReasonsDTO findByCardNumber(String cardNumber);
}
