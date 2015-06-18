package com.novacroft.nemo.mock_cubic.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;

/**
 * OysterCardPendingDTO transfer implementation.
 */

public interface OysterCardPendingDataService extends BaseDataService<OysterCardPending, OysterCardPendingDTO> {

    List<OysterCardPendingDTO> findAllBy(String test);

    OysterCardPendingDTO findByTest(String test);

    List<OysterCardPendingDTO> findByCardNumber(String cardNumber);

    OysterCardPendingDTO findByRequestSequenceNumber(Long requestSequenceNumber);

    OysterCardPendingDTO findByRequestSequenceNumber(Long originalRequestSequenceNumber, String prestigeId);

    Boolean isLimitExceededForPendingItems(Long prestigeId);

    Boolean isBalanceTotalExceededForPendingItems(Long prestigeId, Long additionalPPVValue);
}