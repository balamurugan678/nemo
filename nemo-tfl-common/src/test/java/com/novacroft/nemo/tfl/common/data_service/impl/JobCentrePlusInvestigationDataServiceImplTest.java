package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.JobCentrePlusInvestigationConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.JobCentrePlusInvestigationDAO;

public class JobCentrePlusInvestigationDataServiceImplTest {
    private JobCentrePlusInvestigationDataServiceImpl service;
    private JobCentrePlusInvestigationDAO mockDAO;
    private JobCentrePlusInvestigationConverterImpl mockConverter;
    
    @Before
    public void setUp() {
        service = new JobCentrePlusInvestigationDataServiceImpl();
        mockDAO = mock(JobCentrePlusInvestigationDAO.class);
        mockConverter = mock(JobCentrePlusInvestigationConverterImpl.class);
        service.setDao(mockDAO);
        service.setConverter(mockConverter);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
}
