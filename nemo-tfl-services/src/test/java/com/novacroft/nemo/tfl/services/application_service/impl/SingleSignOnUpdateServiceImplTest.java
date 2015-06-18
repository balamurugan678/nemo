package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.createMockResponseDTO;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.common.data_service.SingleSignOnDataService;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

public class SingleSignOnUpdateServiceImplTest {
    private SingleSignOnUpdateServiceImpl updateService;
    private SingleSignOnDataService mockDataService;
    private SingleSignOnResponseDTO testResponse;
    
    @Before
    public void setUp() {
        updateService = new SingleSignOnUpdateServiceImpl();
        testResponse = createMockResponseDTO();
        
        mockDataService = mock(SingleSignOnDataService.class);
        updateService.singleSignOnDataService = mockDataService;
    }
    
    @Test
    public void shouldNotUpdateUserIfResponseInvalid() {
        testResponse.setIsValid(Boolean.FALSE);
        String invalidResponse = createTestJsonString(testResponse);
        
        updateService.updateUser(invalidResponse);
        
        verify(mockDataService, never()).checkAndUpdateLocalData(any(SingleSignOnResponseDTO.class));
    }
    
    @Test
    public void shouldUpdateUserIfResponseValid() {
        testResponse.setIsValid(Boolean.TRUE);
        String validResponse = createTestJsonString(testResponse);
        
        when(mockDataService.checkAndUpdateLocalData(any(SingleSignOnResponseDTO.class))).thenReturn(true);
        
        updateService.updateUser(validResponse);
        
        verify(mockDataService).checkAndUpdateLocalData(any(SingleSignOnResponseDTO.class));
    }
    
    private String createTestJsonString(SingleSignOnResponseDTO dto) {
        return new Gson().toJson(dto);
    }
}
