package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCard;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;

/**
* OysterCard  entity/DTO converter.
*/

@Component(value = "oysterCardConverter")
public class OysterCardConverterImpl extends BaseDtoEntityConverterImpl <OysterCard, OysterCardDTO>{
  @Override
  public OysterCardDTO getNewDto() {
    return new OysterCardDTO();
  }
}
