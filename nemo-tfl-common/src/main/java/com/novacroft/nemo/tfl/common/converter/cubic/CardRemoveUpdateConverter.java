package com.novacroft.nemo.tfl.common.converter.cubic;

import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;

public interface CardRemoveUpdateConverter {
    CardRemoveUpdateRequest convertToModel(CardRemoveUpdateRequestDTO cardRemoveUpdateRequestDTO);

    CardUpdateResponseDTO convertToDTO(CardRemoveUpdateResponse cardRemoveUpdateResponse);

    CardUpdateResponseDTO convertToDTO(RequestFailure requestFailuer);
}
