package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDepositItem;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;

/**
 * Card refundable deposit item data service specification
 */
@Deprecated
public interface CardRefundableDepositItemDataService extends BaseDataService<CardRefundableDepositItem, CardRefundableDepositItemDTO> {
    CardRefundableDepositItemDTO findByCartIdAndCardId(Long cartId, Long cardId);

    CardRefundableDepositItemDTO findByOrderNumber(Long customerOrderId);
}
