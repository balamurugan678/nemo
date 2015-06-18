package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpCase;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;

@Component("failedAutoTopUpCaseConverterConverter")
public class FailedAutoTopUpCaseConverterImpl extends BaseDtoEntityConverterImpl<FailedAutoTopUpCase, FailedAutoTopUpCaseDTO> {

    @Override
    protected FailedAutoTopUpCaseDTO getNewDto() {
        return new FailedAutoTopUpCaseDTO();
    }

}
