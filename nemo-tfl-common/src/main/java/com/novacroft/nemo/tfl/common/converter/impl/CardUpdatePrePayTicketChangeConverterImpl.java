package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.converter.CardUpdatePrePayTicketChangeConverter;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;

/**
 * Auto Load Change DTO / service model converter
 */
@Component(value = "cardUpdatePrePayTicketChangeConverter")
public class CardUpdatePrePayTicketChangeConverterImpl extends CardUpdateChangeConverter implements CardUpdatePrePayTicketChangeConverter {

    @Override
    public CardUpdatePrePayTicketRequest convertToModel(CardUpdatePrePayTicketRequestDTO cardUpdateRequestDTO) {
        CardUpdatePrePayTicketRequest cardUpdateRequest = new CardUpdatePrePayTicketRequest();
        convert(cardUpdateRequestDTO, cardUpdateRequest);
        return cardUpdateRequest;
    }


}
