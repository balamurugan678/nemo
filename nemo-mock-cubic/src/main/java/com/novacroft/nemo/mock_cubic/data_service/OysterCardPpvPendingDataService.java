package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPpvPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;

/**
 * OysterCardPpvPendingDataService transfer implementation.
 */

public interface OysterCardPpvPendingDataService extends BaseDataService<OysterCardPpvPending, OysterCardPpvPendingDTO> {

    List<OysterCardPpvPendingDTO> findAllBy(String test);

    OysterCardPpvPendingDTO findByTest(String test);

    OysterCardPpvPendingDTO findByCardNumber(String cardNumber);

    OysterCardPpvPendingDTO findByRequestSequenceNumber(Long originalRequestSequenceNumber);

    OysterCardPpvPendingDTO findByCardNumberAndRequestSequenceNumber(String prestigeId, Long originalRequestSequenceNumber);
}
