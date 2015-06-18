package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayTicket;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;
/**
* OysterCardPrepayTicket transfer implementation.
*/

public interface OysterCardPrepayTicketDataService extends BaseDataService<OysterCardPrepayTicket, OysterCardPrepayTicketDTO> {
    List<OysterCardPrepayTicketDTO>  findAllBy(String test);
    OysterCardPrepayTicketDTO  findByTest(String test);
    OysterCardPrepayTicketDTO findByCardNumber(String cardNumber);
}
