package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPptPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;

/**
 * OysterCardPptPendingDTO transfer implementation.
 */

public interface OysterCardPptPendingDataService extends BaseDataService<OysterCardPptPending, OysterCardPptPendingDTO> {

    List<OysterCardPptPendingDTO> findAllBy(String test);

    OysterCardPptPendingDTO findByTest(String test);

    OysterCardPptPendingDTO findByCardNumber(String cardNumber);

    OysterCardPptPendingDTO findByRequestSequenceNumber(Long requestSequenceNumber);

    OysterCardPptPendingDTO findByRequestSequenceNumber(Long originalRequestSequenceNumber, String prestigeId);
}
