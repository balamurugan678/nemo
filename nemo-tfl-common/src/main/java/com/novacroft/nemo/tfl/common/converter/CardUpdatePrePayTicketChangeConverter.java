package com.novacroft.nemo.tfl.common.converter;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayTicketRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public interface CardUpdatePrePayTicketChangeConverter {
    
    CardUpdatePrePayTicketRequest convertToModel(CardUpdatePrePayTicketRequestDTO cardUpdateRequestDTO);

    CardUpdateResponseDTO convertToDto(CardUpdateResponse cardUpdateResponse);

    CardUpdateResponseDTO convertToDto(RequestFailure requestFailure);

}
