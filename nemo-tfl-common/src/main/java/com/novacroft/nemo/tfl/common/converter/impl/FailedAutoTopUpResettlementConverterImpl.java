package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpResettlement;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpResettlementDTO;

public class FailedAutoTopUpResettlementConverterImpl extends BaseDtoEntityConverterImpl<FailedAutoTopUpResettlement, FailedAutoTopUpResettlementDTO> {
	
    @Override
    protected FailedAutoTopUpResettlementDTO getNewDto() {
        return new FailedAutoTopUpResettlementDTO();
    }
}
