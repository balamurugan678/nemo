package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between payment card entity and DTO.
 */
@Component(value = "paymentCardConverter")
public class PaymentCardConverterImpl extends BaseDtoEntityConverterImpl<PaymentCard, PaymentCardDTO> {
    @Override
    protected PaymentCardDTO getNewDto() {
        return new PaymentCardDTO();
    }
}
