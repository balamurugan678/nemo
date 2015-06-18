package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between card entity and DTO.
 */
@Component(value = "cardConverter")
public class CardConverterImpl extends BaseDtoEntityConverterImpl<Card, CardDTO> {
    @Override
    protected CardDTO getNewDto() {
        return new CardDTO();
    }
}
