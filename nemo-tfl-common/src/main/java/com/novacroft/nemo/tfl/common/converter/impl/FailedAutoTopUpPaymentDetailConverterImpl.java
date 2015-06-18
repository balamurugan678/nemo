package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpPaymentDetail;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpPaymentDetailDTO;

@Component("failedAutoTopUpPaymentDetailConverter")
public class FailedAutoTopUpPaymentDetailConverterImpl extends
                BaseDtoEntityConverterImpl<FailedAutoTopUpPaymentDetail, FailedAutoTopUpPaymentDetailDTO> {

    @Override
    protected FailedAutoTopUpPaymentDetailDTO getNewDto() {
        return new FailedAutoTopUpPaymentDetailDTO();
    }

}
