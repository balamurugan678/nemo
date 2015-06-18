package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

import org.springframework.stereotype.Component;

/**
 * Web Account Credit Settlement entity/DTO translator
 */
@Component(value = "webAccountCreditSettlementConverter")
public class WebCreditSettlementConverterImpl
        extends BaseDtoEntityConverterImpl<WebAccountCreditSettlement, WebCreditSettlementDTO> {
    @Override
    protected WebCreditSettlementDTO getNewDto() {
        return new WebCreditSettlementDTO();
    }
}
