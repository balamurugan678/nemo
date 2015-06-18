package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpPerformedSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpPerformedSettlementDTO;

/**
this interface makes use of its parent createOrUpdate method to insert a record for an auto top-up performed 
 * (ie. batch process triggered) event into the settlement table for use in the auto top-up history table in 
 * viewAutoTopUpHistory.jsp in nemo-tfl-online. 
 */

public interface AutoTopUpPerformedSettlementDataService extends BaseDataService<AutoTopUpPerformedSettlement, AutoTopUpPerformedSettlementDTO> {
    List<AutoTopUpPerformedSettlementDTO> findByOrderId(Long orderId);
    
}
