package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPpvPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;

/**
* Oystercardppvpending  entity/DTO converter.
*/

@Component(value = "oysterCardPpvPendingConverter")
public class OysterCardPpvPendingConverterImpl extends BaseDtoEntityConverterImpl<OysterCardPpvPending, OysterCardPpvPendingDTO> {
  @Override
  public OysterCardPpvPendingDTO getNewDto() {
    return new  OysterCardPpvPendingDTO();
  }
}
