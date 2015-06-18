package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;

import java.util.List;

/**
 * Settlement (for Auto Load Changes) data service specification
 */
public interface AutoLoadChangeSettlementDataService
        extends BaseDataService<AutoLoadChangeSettlement, AutoLoadChangeSettlementDTO> {
    AutoLoadChangeSettlementDTO findByRequestSequenceNumberAndCardNumber(Integer requestSequenceNumber, String cardNumber);

    AutoLoadChangeSettlementDTO findLatestByCardId(Long cardId);

    List<AutoLoadChangeSettlementDTO> findByOrderId(Long orderId);
}
