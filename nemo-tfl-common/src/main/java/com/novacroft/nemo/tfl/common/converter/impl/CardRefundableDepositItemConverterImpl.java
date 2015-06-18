package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDepositItem;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;

/**
 * Transform between card refundable deposit item entity and item DTO.
 */
@Component(value = "cardRefundableDepositItemConverter")
public class CardRefundableDepositItemConverterImpl extends BaseDtoEntityConverterImpl<CardRefundableDepositItem, CardRefundableDepositItemDTO> {
    @Override
    protected CardRefundableDepositItemDTO getNewDto() {
        return new CardRefundableDepositItemDTO();
    }
}
