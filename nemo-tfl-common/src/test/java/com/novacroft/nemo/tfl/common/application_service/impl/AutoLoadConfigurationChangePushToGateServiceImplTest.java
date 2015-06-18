package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadConfigurationChangePushToGateDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.*;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * AutoLoadConfigurationChangePushToGateService unit tests
 */
public class AutoLoadConfigurationChangePushToGateServiceImplTest {
    protected final static String DUMMY_PARAMETER_VALUE = "dummy-parameter-value";

    @Test
    public void isErrorResponseShouldReturnTrue() {
        AutoLoadConfigurationChangePushToGateServiceImpl service = new AutoLoadConfigurationChangePushToGateServiceImpl();
        assertTrue(service.isErrorResponse(getTestFailAutoLoadChangeResponseDTO1()));
    }

    @Test
    public void isErrorResponseShouldReturnFalse() {
        AutoLoadConfigurationChangePushToGateServiceImpl service = new AutoLoadConfigurationChangePushToGateServiceImpl();
        assertFalse(service.isErrorResponse(getTestSuccessAutoLoadChangeResponseDTO1()));
    }

    @Test(expected = ApplicationServiceException.class)
    public void requestAutoLoadConfigurationChangeShouldError() {
        SystemParameterService mockSystemParameterService = mock(SystemParameterService.class);
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);

        AutoLoadConfigurationChangePushToGateDataService mockAutoLoadConfigurationChangePushToGateDataService =
                mock(AutoLoadConfigurationChangePushToGateDataService.class);
        when(mockAutoLoadConfigurationChangePushToGateDataService
                .changeAutoLoadConfigurationRequest(any(AutoLoadChangeRequestDTO.class)))
                .thenReturn(getTestFailAutoLoadChangeResponseDTO1());

        AutoLoadConfigurationChangePushToGateServiceImpl service = mock(AutoLoadConfigurationChangePushToGateServiceImpl.class);
        when(service.requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong())).thenCallRealMethod();
        when(service.isErrorResponse(any(AutoLoadChangeResponseDTO.class))).thenReturn(Boolean.TRUE);
        service.systemParameterService = mockSystemParameterService;
        service.autoLoadConfigurationChangePushToGateDataService = mockAutoLoadConfigurationChangePushToGateDataService;

        service.requestAutoLoadConfigurationChange(OYSTER_NUMBER_1, TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1);

        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(mockAutoLoadConfigurationChangePushToGateDataService)
                .changeAutoLoadConfigurationRequest(any(AutoLoadChangeRequestDTO.class));
        verify(service).isErrorResponse(any(AutoLoadChangeResponseDTO.class));
    }

    @Test
    public void shouldRequestAutoLoadConfigurationChange() {
        SystemParameterService mockSystemParameterService = mock(SystemParameterService.class);
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);

        AutoLoadConfigurationChangePushToGateDataService mockAutoLoadConfigurationChangePushToGateDataService =
                mock(AutoLoadConfigurationChangePushToGateDataService.class);
        when(mockAutoLoadConfigurationChangePushToGateDataService
                .changeAutoLoadConfigurationRequest(any(AutoLoadChangeRequestDTO.class)))
                .thenReturn(getTestSuccessAutoLoadChangeResponseDTO1());

        AutoLoadConfigurationChangePushToGateServiceImpl service = mock(AutoLoadConfigurationChangePushToGateServiceImpl.class);
        when(service.requestAutoLoadConfigurationChange(anyString(), anyInt(), anyLong())).thenCallRealMethod();
        when(service.isErrorResponse(any(AutoLoadChangeResponseDTO.class))).thenReturn(Boolean.FALSE);
        service.systemParameterService = mockSystemParameterService;
        service.autoLoadConfigurationChangePushToGateDataService = mockAutoLoadConfigurationChangePushToGateDataService;

        Integer result = service.requestAutoLoadConfigurationChange(OYSTER_NUMBER_1, TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1);

        assertEquals(REQUEST_SEQUENCE_NUMBER, result);

        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(mockAutoLoadConfigurationChangePushToGateDataService)
                .changeAutoLoadConfigurationRequest(any(AutoLoadChangeRequestDTO.class));
        verify(service).isErrorResponse(any(AutoLoadChangeResponseDTO.class));
    }
}
