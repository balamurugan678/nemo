package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;

/**
* Oystercardpending  entity/DTO converter.
*/

@Component(value = "oysterCardPendingConverter")
public class OysterCardPendingConverterImpl extends BaseDtoEntityConverterImpl<OysterCardPending, OysterCardPendingDTO> {
  @Override
  public OysterCardPendingDTO getNewDto() {
    return new  OysterCardPendingDTO();
  }
}