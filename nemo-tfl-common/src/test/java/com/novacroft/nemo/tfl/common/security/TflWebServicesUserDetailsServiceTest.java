package com.novacroft.nemo.tfl.common.security;

import static com.novacroft.nemo.test_support.ExternalUserTestUtil.PASSWORD_1;
import static com.novacroft.nemo.test_support.ExternalUserTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.ExternalUserTestUtil.getExternalUserDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.novacroft.nemo.tfl.common.data_service.ExternalUserDataService;

public class TflWebServicesUserDetailsServiceTest {

    private TflWebServicesUserDetailsService service;
    private ExternalUserDataService mockDataService;
    
    @Before
    public void setUp() throws Exception {
        service = new TflWebServicesUserDetailsService();
        mockDataService = mock(ExternalUserDataService.class);
        service.dataService = mockDataService; 
    }

    @Test
    public void loadUserByUsernameShouldReturnUser() {
        when(mockDataService.findByUsername(anyString())).thenReturn(getExternalUserDTO1());
        UserDetails userDetails = service.loadUserByUsername(USERNAME_1);
        assertNotNull(userDetails);
        assertEquals(USERNAME_1, userDetails.getUsername());
        assertEquals(PASSWORD_1, userDetails.getPassword());
        verify(mockDataService).findByUsername(USERNAME_1);
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameShouldError() {
        when(mockDataService.findByUsername(anyString())).thenReturn(null);
        service.loadUserByUsername(USERNAME_1);
        verify(mockDataService).findByUsername(USERNAME_1);
    }

}
