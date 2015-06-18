package com.novacroft.nemo.tfl.common.service_access.impl.cyber_source;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.application_service.impl.ServiceCallLogServiceImpl;
import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.support.NemoUserContextImpl;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

/**
 * CyberSourceHeartbeatServiceAccessImpl unit tests
 */
public class CyberSourceHeartbeatServiceAccessImplTest {

    private CyberSourceHeartbeatServiceAccessImpl service;
    private ServiceCallLogService mockServiceCallLogService;
    private NemoUserContext mockNemoUserContext;
    private RestTemplate mockCyberSourceHeartbeatRestTemplate;
    private ResponseErrorHandler mockCyberSourceHeartbeatResponseErrorHandler;

    private ServiceCallLogDTO mockServiceCallLogDTO;

    @Before
    public void setUp() {
        this.service = mock(CyberSourceHeartbeatServiceAccessImpl.class, CALLS_REAL_METHODS);
        this.mockServiceCallLogService = mock(ServiceCallLogServiceImpl.class);
        this.service.serviceCallLogService = mockServiceCallLogService;
        this.mockNemoUserContext = mock(NemoUserContextImpl.class);
        this.service.nemoUserContext = mockNemoUserContext;
        this.mockCyberSourceHeartbeatRestTemplate = mock(RestTemplate.class);
        this.service.cyberSourceHeartbeatRestTemplate = this.mockCyberSourceHeartbeatRestTemplate;
        this.mockCyberSourceHeartbeatResponseErrorHandler = mock(CyberSourceHeartbeatResponseErrorHandler.class);
        this.service.cyberSourceHeartbeatResponseErrorHandler = this.mockCyberSourceHeartbeatResponseErrorHandler;

        this.mockServiceCallLogDTO = mock(ServiceCallLogDTO.class);
    }

    @Test
    public void shouldCheckHeartbeat() {
        when(this.mockServiceCallLogService.initialiseCallLog(anyString(), anyString(), anyLong()))
                .thenReturn(this.mockServiceCallLogDTO);
        doNothing().when(this.mockCyberSourceHeartbeatRestTemplate).setErrorHandler(any(ResponseErrorHandler.class));
        when(this.mockCyberSourceHeartbeatRestTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(null);
        doNothing().when(this.mockServiceCallLogService)
                .finaliseCallLog(any(ServiceCallLogDTO.class), anyString(), anyString());

        this.service.checkHeartbeat(CustomerTestUtil.CUSTOMER_ID_1);

        verify(this.mockServiceCallLogService).initialiseCallLog(anyString(), anyString(), anyLong());
        verify(this.mockCyberSourceHeartbeatRestTemplate).setErrorHandler(any(ResponseErrorHandler.class));
        verify(this.mockCyberSourceHeartbeatRestTemplate).getForEntity(anyString(), any(Class.class));
        verify(this.mockServiceCallLogService).finaliseCallLog(any(ServiceCallLogDTO.class), anyString(), anyString());

    }

    @Test(expected = ServiceNotAvailableException.class)
    public void checkHeartbeatShouldError() {
        when(this.mockServiceCallLogService.initialiseCallLog(anyString(), anyString(), anyLong()))
                .thenReturn(this.mockServiceCallLogDTO);
        doNothing().when(this.mockCyberSourceHeartbeatRestTemplate).setErrorHandler(any(ResponseErrorHandler.class));
        when(this.mockCyberSourceHeartbeatRestTemplate.getForEntity(anyString(), any(Class.class)))
                .thenThrow(new RestClientException("test-error"));
        doNothing().when(this.mockServiceCallLogService)
                .finaliseCallLog(any(ServiceCallLogDTO.class), anyString(), anyString());

        this.service.checkHeartbeat(CustomerTestUtil.CUSTOMER_ID_1);

        verify(this.mockServiceCallLogService).initialiseCallLog(anyString(), anyString(), anyLong());
        verify(this.mockCyberSourceHeartbeatRestTemplate).setErrorHandler(any(ResponseErrorHandler.class));
        verify(this.mockCyberSourceHeartbeatRestTemplate).getForEntity(anyString(), any(Class.class));
        verify(this.mockServiceCallLogService).finaliseCallLog(any(ServiceCallLogDTO.class), anyString(), anyString());
    }
}
