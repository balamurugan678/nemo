package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;

/**
 * Auto top-up item data service specification
 */
public interface AutoTopUpItemDataService extends BaseDataService<AutoTopUpConfigurationItem, AutoTopUpConfigurationItemDTO> {

    AutoTopUpConfigurationItemDTO findByCartIdAndCardId(Long cartId, Long cardId);
    
    List<AutoTopUpConfigurationItemDTO> findByCustomerOrderId(Long customerOrderId);

}
