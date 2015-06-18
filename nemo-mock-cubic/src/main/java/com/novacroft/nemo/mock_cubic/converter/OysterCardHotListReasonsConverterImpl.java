package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardHotListReasons;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardHotListReasonsDTO;

/**
* OysterCard  entity/DTO converter.
*/

@Component(value = "oysterCardHotListReasonsConverter")
public class OysterCardHotListReasonsConverterImpl extends BaseDtoEntityConverterImpl<OysterCardHotListReasons, OysterCardHotListReasonsDTO> {
  @Override
  public OysterCardHotListReasonsDTO getNewDto() {
    return new OysterCardHotListReasonsDTO();
  }
}
