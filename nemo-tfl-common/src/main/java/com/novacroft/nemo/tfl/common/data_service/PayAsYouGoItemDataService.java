package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

/**
 * Pay as you go item data service specification
 */
public interface PayAsYouGoItemDataService extends BaseDataService<PayAsYouGoItem, PayAsYouGoItemDTO> {

	PayAsYouGoItemDTO findByCartIdAndCardId(Long cartId, Long cardId);

	List<PayAsYouGoItemDTO> findByCustomerOrderId(Long customerOrderId);
}
