package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.TEST_CARD_NUMBER;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;

public class FailedAutoTopUpCaseServiceImplTest {

    private static FailedAutoTopUpCaseDataService mockFailedAutoTopUpCaseDataService;
    private static FailedAutoTopUpCaseCmdImpl mockFATUCmdImpl; 
    private static FailedAutoTopUpCaseServiceImpl failedAutoTopUpCaseService;

    @Before
    public void setup() {
    	failedAutoTopUpCaseService = new FailedAutoTopUpCaseServiceImpl();  
    	mockFailedAutoTopUpCaseDataService = mock(FailedAutoTopUpCaseDataService.class);
    	mockFATUCmdImpl = mock(FailedAutoTopUpCaseCmdImpl.class);

    	failedAutoTopUpCaseService.failedAutoTopUpCaseDataService =  mockFailedAutoTopUpCaseDataService;
    }

    @Test
    public void shouldValidateIfOysterCardWithFailedAutoTopUpCase() {
        when(mockFailedAutoTopUpCaseDataService.isOysterCardWithFailedAutoTopUpCase(anyString())).thenReturn(Boolean.TRUE);
        assertTrue(failedAutoTopUpCaseService.isOysterCardWithFailedAutoTopUpCase(TEST_CARD_NUMBER));
        verify(mockFailedAutoTopUpCaseDataService).isOysterCardWithFailedAutoTopUpCase(anyString());
    }
    
    @Test
    public void shouldValidateIfOysterCardWithOutFailedAutoTopUpCase() {
        when(mockFailedAutoTopUpCaseDataService.isOysterCardWithFailedAutoTopUpCase(anyString())).thenReturn(Boolean.FALSE);
        assertFalse(failedAutoTopUpCaseService.isOysterCardWithFailedAutoTopUpCase(TEST_CARD_NUMBER));
        verify(mockFailedAutoTopUpCaseDataService).isOysterCardWithFailedAutoTopUpCase(anyString());
    }
}
