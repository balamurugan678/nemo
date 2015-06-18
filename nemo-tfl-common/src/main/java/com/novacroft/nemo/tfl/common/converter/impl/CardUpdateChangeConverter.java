package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.utils.Converter.convert;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public class CardUpdateChangeConverter {

    public CardUpdateResponseDTO convertToDto(CardUpdateResponse cardUpdateResponse) {
        CardUpdateResponseDTO cardUpdateResponseDTO = new CardUpdateResponseDTO();
        convert(cardUpdateResponse, cardUpdateResponseDTO );
        return cardUpdateResponseDTO;
    }

    public CardUpdateResponseDTO convertToDto(RequestFailure requestFailure) {
        CardUpdateResponseDTO cardUpdateResponseDTO = new CardUpdateResponseDTO();
        convert(requestFailure, cardUpdateResponseDTO );
        return cardUpdateResponseDTO;
    }
    
}
