package com.novacroft.nemo.mock_cubic.converter;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayValue;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;

/**
* Oystercardprepayvalue  entity/DTO converter.
*/

@Component(value = "oystercardprepayvalueConverter")
public class OysterCardPrepayValueConverterImpl extends BaseDtoEntityConverterImpl<OysterCardPrepayValue, OysterCardPrepayValueDTO> {
    @Override
    public OysterCardPrepayValueDTO getNewDto() {
        return new  OysterCardPrepayValueDTO();
    }
}
