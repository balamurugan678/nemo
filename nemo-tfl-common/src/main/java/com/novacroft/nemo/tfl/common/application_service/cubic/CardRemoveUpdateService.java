package com.novacroft.nemo.tfl.common.application_service.cubic;

import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;

public interface CardRemoveUpdateService {
    StringBuffer removePendingUpdate(String xmlRequest);

    CardUpdateResponseDTO removePendingUpdate(String cardNumber, Long originatingSequenceNumber);
}
