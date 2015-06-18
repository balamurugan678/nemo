package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.BackdatedRefundReasonConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.BackdatedRefundReasonDAO;


public class BackdatedRefundReasonDataServiceImplTest {

    protected BackdatedRefundReasonDataServiceImpl dataService;
    protected BackdatedRefundReasonDAO mockDao;
    protected BackdatedRefundReasonConverterImpl mockConverter;

    @Before
    public void setUp() {
        dataService = new BackdatedRefundReasonDataServiceImpl();
        mockDao = mock(BackdatedRefundReasonDAO.class);
        mockConverter = mock(BackdatedRefundReasonConverterImpl.class);

        dataService.setConverter(mockConverter);
        dataService.setDao(mockDao);
    }

    @Test 
    public void getNewEntityTest(){
        assertNotNull(dataService.getNewEntity());
    }
   
}
