package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

/**
 * Settlement (by Ad-Hoc Load) data service specification
 */
public interface AdHocLoadSettlementDataService extends BaseDataService<AdHocLoadSettlement, AdHocLoadSettlementDTO> {
    AdHocLoadSettlementDTO findByRequestSequenceNumberAndCardNumber(Integer requestSequenceNumber, String cardNumber);

    List<AdHocLoadSettlementDTO> findByOrderId(Long orderId);
}
