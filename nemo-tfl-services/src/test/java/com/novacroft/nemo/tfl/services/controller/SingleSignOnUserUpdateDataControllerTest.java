package com.novacroft.nemo.tfl.services.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.services.application_service.SingleSignOnUpdateService;

public class SingleSignOnUserUpdateDataControllerTest {

    private SingleSignOnUserUpdateDataController controller;
    private SingleSignOnUpdateService mockSsoUpdateService;

    
    
    @Before
    public void init(){
        controller = new SingleSignOnUserUpdateDataController();
        mockSsoUpdateService =  mock(SingleSignOnUpdateService.class);
        controller.ssoUpdateService = mockSsoUpdateService;
    }
    
    @Test
    public void updateUserShouldInvokeUpdateService(){
        when(mockSsoUpdateService.updateUser(anyString())).thenReturn(null);
        
        String testUser = "test user string";
        controller.updateUser(testUser);
        
        verify(mockSsoUpdateService).updateUser(testUser);
    }
    
}
