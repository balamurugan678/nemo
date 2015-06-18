package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.converter.CardUpdatePrePayValueChangeConverter;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;

/**
 * Auto Load Change DTO / service model converter
 */
@Component(value = "cardUpdatePrePayValueChangeConverter")
public class CardUpdatePrePayValueChangeConverterImpl extends CardUpdateChangeConverter implements CardUpdatePrePayValueChangeConverter {

    @Override
    public CardUpdatePrePayValueRequest convertToModel(CardUpdatePrePayValueRequestDTO cardUpdateRequestDTO) {
        CardUpdatePrePayValueRequest cardUpdateRequest = new CardUpdatePrePayValueRequest();
        convert(cardUpdateRequestDTO, cardUpdateRequest);
        return cardUpdateRequest;
    }


}
