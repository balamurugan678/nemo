package com.novacroft.nemo.tfl.common.converter;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdatePrePayValueRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public interface CardUpdatePrePayValueChangeConverter {
    
    CardUpdatePrePayValueRequest convertToModel(CardUpdatePrePayValueRequestDTO cardUpdateRequestDTO);

    CardUpdateResponseDTO convertToDto(CardUpdateResponse cardUpdateResponse);

    CardUpdateResponseDTO convertToDto(RequestFailure requestFailure);

}
