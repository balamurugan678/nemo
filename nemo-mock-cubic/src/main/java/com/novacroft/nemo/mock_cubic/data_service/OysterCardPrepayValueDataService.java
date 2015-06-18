package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayValue;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;
/**
* OysterCardPrepayValueDataService transfer implementation.
*/

public interface OysterCardPrepayValueDataService extends BaseDataService<OysterCardPrepayValue, OysterCardPrepayValueDTO> {
    List<OysterCardPrepayValueDTO>  findAllBy(String test);
    OysterCardPrepayValueDTO  findByTest(String test);
    OysterCardPrepayValueDTO findByCardNumber(String cardNumber);
}
