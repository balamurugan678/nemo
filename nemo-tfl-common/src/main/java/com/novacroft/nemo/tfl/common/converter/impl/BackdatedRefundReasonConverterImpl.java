package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.BackdatedRefundReason;
import com.novacroft.nemo.tfl.common.transfer.BackdatedRefundReasonDTO;

/**
 * Transform between pay as you go entity and DTO.
 */
@Component(value = "backdatedrefundReasonConverter")
public class BackdatedRefundReasonConverterImpl extends BaseDtoEntityConverterImpl<BackdatedRefundReason, BackdatedRefundReasonDTO> {
    @Override
    protected BackdatedRefundReasonDTO getNewDto() {
        return new BackdatedRefundReasonDTO();
    }
}
