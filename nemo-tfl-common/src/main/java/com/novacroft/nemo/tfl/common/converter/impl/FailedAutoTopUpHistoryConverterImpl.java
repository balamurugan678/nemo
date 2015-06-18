package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpHistory;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;

@Component("failedAutoTopUpHistoryConverter")
public class FailedAutoTopUpHistoryConverterImpl extends BaseDtoEntityConverterImpl<FailedAutoTopUpHistory, FailedAutoTopUpHistoryDTO> {

    @Override
    protected FailedAutoTopUpHistoryDTO getNewDto() {
        return new FailedAutoTopUpHistoryDTO();
    }

}
