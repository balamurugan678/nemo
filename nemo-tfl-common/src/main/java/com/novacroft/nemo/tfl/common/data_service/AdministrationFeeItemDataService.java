package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;

/**
 * Administration fee item data service specification
 */
public interface AdministrationFeeItemDataService extends BaseDataService<AdministrationFeeItem, AdministrationFeeItemDTO> {

    AdministrationFeeItemDTO findByCartIdAndCardId(Long cartId, Long cardId);

    List<AdministrationFeeItemDTO> findByCustomerOrderId(Long customerOrderId);
	
    AdministrationFeeItemDTO findByCardId(Long cardId);
}
