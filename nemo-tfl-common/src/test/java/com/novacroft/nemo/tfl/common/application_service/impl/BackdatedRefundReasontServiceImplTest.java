package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.data_service.impl.BackdatedRefundReasonDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.BackdatedRefundReasonDTO;

import static org.mockito.Mockito.*;

public class BackdatedRefundReasontServiceImplTest {

    private static final String TEST_STRING = "test";
    private static final long ONE_LONG = 1L;
    
    private BackdatedRefundReasonServiceImpl backdatedRefundReasonService;
    private BackdatedRefundReasonDataServiceImpl mockBackdatedRefundReasonDataService;


    @Before
    public void setup() {
        backdatedRefundReasonService = new BackdatedRefundReasonServiceImpl();
        mockBackdatedRefundReasonDataService = mock(BackdatedRefundReasonDataServiceImpl.class);
        
        backdatedRefundReasonService.backdatedRefundReasonDataService = mockBackdatedRefundReasonDataService;
        
    }

    @Test
    public void testGetBackDatedRefundTypes() {
        
        List<BackdatedRefundReasonDTO> results = new ArrayList<BackdatedRefundReasonDTO>();
        results.add(new BackdatedRefundReasonDTO(ONE_LONG,TEST_STRING));
        when(mockBackdatedRefundReasonDataService.findAll()).thenReturn(results);
        SelectListDTO backDatedRefundTypes = backdatedRefundReasonService.getBackdatedRefundTypes();
        assert(backDatedRefundTypes != null);
    }


}
