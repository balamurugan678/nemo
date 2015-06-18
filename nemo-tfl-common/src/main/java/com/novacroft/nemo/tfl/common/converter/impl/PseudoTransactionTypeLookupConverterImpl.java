package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.PseudoTransactionTypeLookup;
import com.novacroft.nemo.tfl.common.transfer.PseudoTransactionTypeLookupDTO;
import org.springframework.stereotype.Component;

/*
 * Pseudo Transaction Type converter
 */
@Component(value = "PseudoTransactionTypeLookupConverter")
public class PseudoTransactionTypeLookupConverterImpl
        extends BaseDtoEntityConverterImpl<PseudoTransactionTypeLookup, PseudoTransactionTypeLookupDTO> {
    @Override
    protected PseudoTransactionTypeLookupDTO getNewDto() {
        return new PseudoTransactionTypeLookupDTO();
    }
}
