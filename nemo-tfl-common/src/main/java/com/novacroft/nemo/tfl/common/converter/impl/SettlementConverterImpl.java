package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Settlement;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

import org.springframework.stereotype.Component;

/**
 * Settlement / SettlementDTO translator
 */
@Component(value = "settlementConverter")
public class SettlementConverterImpl extends BaseDtoEntityConverterImpl<Settlement, SettlementDTO> {
	
    @Override
    protected SettlementDTO getNewDto() {
        return new SettlementDTO();
    }

}
