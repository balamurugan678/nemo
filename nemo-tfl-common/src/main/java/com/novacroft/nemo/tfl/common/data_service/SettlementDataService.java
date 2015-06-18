package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Settlement;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

/**
 * Order data service specification
 */
public interface SettlementDataService extends BaseDataService<Settlement, SettlementDTO> {
	SettlementDTO create(SettlementDTO settlementDTO);

	SettlementDTO findBySettlementId(Long settlementId);
	
	List<SettlementDTO> findByOrderId(Long orderId);
	
	List<SettlementDTO> findPolymorphicChildTypeSettlementByOrderId(Long orderId);

    List<SettlementDTO> findAllRefundSettlementsInPast12Months( Long customerId);
}
