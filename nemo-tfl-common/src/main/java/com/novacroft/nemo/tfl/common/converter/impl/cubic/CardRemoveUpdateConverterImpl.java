package com.novacroft.nemo.tfl.common.converter.impl.cubic;

import static com.novacroft.nemo.common.utils.Converter.convert;
import com.novacroft.nemo.tfl.common.converter.cubic.CardRemoveUpdateConverter;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;

import org.springframework.stereotype.Service;

@Service("cardRemoveUpdateConverter")
public class CardRemoveUpdateConverterImpl implements CardRemoveUpdateConverter {

    @Override
    public CardUpdateResponseDTO convertToDTO(CardRemoveUpdateResponse cardRemoveUpdateResponse) {
        CardUpdateResponseDTO cardRemoveUpdateResponseDTO = new CardUpdateResponseDTO();
        convert(cardRemoveUpdateResponse, cardRemoveUpdateResponseDTO);
        return cardRemoveUpdateResponseDTO;
    }

    @Override
    public CardUpdateResponseDTO convertToDTO(RequestFailure requestFailure) {
        CardUpdateResponseDTO cardRemoveUpdateResponseDTO = new CardUpdateResponseDTO();
        convert(requestFailure, cardRemoveUpdateResponseDTO);
        return cardRemoveUpdateResponseDTO;
    }

    @Override
    public CardRemoveUpdateRequest convertToModel(CardRemoveUpdateRequestDTO cardRemoveUpdateRequestDTO) {
        CardRemoveUpdateRequest cardRemoveUpdateRequest = new CardRemoveUpdateRequest();
        convert(cardRemoveUpdateRequestDTO, cardRemoveUpdateRequest);
        return cardRemoveUpdateRequest;
    }

}
