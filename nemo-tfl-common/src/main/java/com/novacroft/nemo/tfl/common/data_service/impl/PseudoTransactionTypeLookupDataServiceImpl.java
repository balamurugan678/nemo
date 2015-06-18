package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PseudoTransactionTypeLookupConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PttidLookupDAO;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.domain.PseudoTransactionTypeLookup;
import com.novacroft.nemo.tfl.common.transfer.PseudoTransactionTypeLookupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pseudo Transaction Type Lookup Data Service implementation
 */
@Service(value = "pseudoTransactionTypeLookupDataService")
@Transactional(readOnly = true)
public class PseudoTransactionTypeLookupDataServiceImpl
        extends BaseDataServiceImpl<PseudoTransactionTypeLookup, PseudoTransactionTypeLookupDTO>
        implements PseudoTransactionTypeLookupDataService {
    @Autowired
    public void setDao(PttidLookupDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PseudoTransactionTypeLookupConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public PseudoTransactionTypeLookup getNewEntity() {
        return new PseudoTransactionTypeLookup();
    }

}
