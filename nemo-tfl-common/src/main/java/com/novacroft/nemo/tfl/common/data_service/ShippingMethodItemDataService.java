package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

import java.util.List;

/**
 * shipping method item data service specification
 */
public interface ShippingMethodItemDataService extends BaseDataService<ShippingMethodItem, ShippingMethodItemDTO> {

    ShippingMethodItemDTO findByCartIdAndCardId(Long cartId, Long cardId);

    List<ShippingMethodItemDTO> findByOrderNumber(Long customerOrderId);

}
