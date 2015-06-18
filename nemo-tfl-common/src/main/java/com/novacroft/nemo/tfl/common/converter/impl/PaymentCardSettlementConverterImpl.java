package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import org.springframework.stereotype.Component;

/**
 * Settlement entity/DTO translator
 */
@Component(value = "paymentCardSettlementConverter")
public class PaymentCardSettlementConverterImpl
        extends BaseDtoEntityConverterImpl<PaymentCardSettlement, PaymentCardSettlementDTO> {
    @Override
    protected PaymentCardSettlementDTO getNewDto() {
        return new PaymentCardSettlementDTO();
    }
}
