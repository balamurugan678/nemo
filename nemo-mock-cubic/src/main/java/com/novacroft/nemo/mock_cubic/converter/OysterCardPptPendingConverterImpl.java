package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPptPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;

/**
* OysterCardPptPending  entity/DTO converter.
*/

@Component(value = "oysterCardPptPendingConverter")
public class OysterCardPptPendingConverterImpl extends BaseDtoEntityConverterImpl <OysterCardPptPending, OysterCardPptPendingDTO> {
  @Override
  public OysterCardPptPendingDTO getNewDto() {
    return new  OysterCardPptPendingDTO();
  }
}
