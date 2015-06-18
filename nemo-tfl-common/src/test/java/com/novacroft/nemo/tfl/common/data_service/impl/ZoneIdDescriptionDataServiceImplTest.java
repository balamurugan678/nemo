package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.ZoneIdDescriptionConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ZoneIdDescriptionDAO;

public class ZoneIdDescriptionDataServiceImplTest {
    private ZoneIdDescriptionDataServiceImpl service;
    private ZoneIdDescriptionDAO mockDAO;
    
    @Before
    public void setUp() {
        service = new ZoneIdDescriptionDataServiceImpl();
        mockDAO = mock(ZoneIdDescriptionDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new ZoneIdDescriptionConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
}
