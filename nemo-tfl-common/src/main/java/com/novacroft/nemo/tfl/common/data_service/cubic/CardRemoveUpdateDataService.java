package com.novacroft.nemo.tfl.common.data_service.cubic;

import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;

public interface CardRemoveUpdateDataService {
    CardUpdateResponseDTO removePendingUpdate(CardRemoveUpdateRequestDTO cardRemoveUpdateRequest);
}
