package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.HotlistReasonConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.HotlistReasonDAO;

public class HotlistReasonDataServiceImplTest {
    private HotlistReasonDataServiceImpl service;
    private HotlistReasonDAO mockDAO;
    private HotlistReasonConverterImpl mockConverter;
    
    @Before
    public void setUp() {
        service = new HotlistReasonDataServiceImpl();
        mockDAO = mock(HotlistReasonDAO.class);
        mockConverter = mock(HotlistReasonConverterImpl.class);
        service.setDao(mockDAO);
        service.setConverter(mockConverter);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
}
