package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDeposit;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;

/**
 * Transform between card refundable deposit entity and DTO.
 */
@Component(value = "cardRefundableDepositConverter")
public class CardRefundableDepositConverterImpl extends BaseDtoEntityConverterImpl<CardRefundableDeposit, CardRefundableDepositDTO> {
    @Override
    protected CardRefundableDepositDTO getNewDto() {
        return new CardRefundableDepositDTO();
    }
}
