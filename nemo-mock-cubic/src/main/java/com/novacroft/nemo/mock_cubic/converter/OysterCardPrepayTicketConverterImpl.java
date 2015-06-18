package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayTicket;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;

/**
* Oystercardprepayticket  entity/DTO converter.
*/

@Component(value = "oysterCardPrepayTicketConverter")
public class OysterCardPrepayTicketConverterImpl extends BaseDtoEntityConverterImpl<OysterCardPrepayTicket, OysterCardPrepayTicketDTO> {
  @Override
  public OysterCardPrepayTicketDTO getNewDto() {
    return new  OysterCardPrepayTicketDTO();
  }
}
