package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.PseudoTransactionTypeLookupConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PttidLookupDAO;

public class PseudoTransactionTypeLookupDataServiceImplTest {
    private PseudoTransactionTypeLookupDataServiceImpl service;
    private PttidLookupDAO mockDAO;
    private PseudoTransactionTypeLookupConverterImpl mockConverter;
    
    @Before
    public void setUp() {
        service = new PseudoTransactionTypeLookupDataServiceImpl();
        mockDAO = mock(PttidLookupDAO.class);
        mockConverter = mock(PseudoTransactionTypeLookupConverterImpl.class);
        service.setDao(mockDAO);
        service.setConverter(mockConverter);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
}
