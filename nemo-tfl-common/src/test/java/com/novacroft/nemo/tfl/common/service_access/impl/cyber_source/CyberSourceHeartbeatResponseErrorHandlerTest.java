package com.novacroft.nemo.tfl.common.service_access.impl.cyber_source;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CyberSourceHeartbeatResponseErrorHandler unit tests
 */
public class CyberSourceHeartbeatResponseErrorHandlerTest {
    private CyberSourceHeartbeatResponseErrorHandler handler;
    private ClientHttpResponse mockClientHttpResponse;

    @Before
    public void setUp() {
        this.handler = new CyberSourceHeartbeatResponseErrorHandler();
        this.mockClientHttpResponse = mock(ClientHttpResponse.class);
    }

    @Test
    public void hasErrorShouldReturnFalseWithHttpOk() throws IOException {
        when(this.mockClientHttpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
        assertFalse(this.handler.hasError(this.mockClientHttpResponse));
    }

    @Test
    public void hasErrorShouldReturnTrueWithNotHttpOk() throws IOException {
        when(this.mockClientHttpResponse.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        assertTrue(this.handler.hasError(this.mockClientHttpResponse));
    }

    @Test(expected = ServiceNotAvailableException.class)
    public void shouldHandleError() throws IOException {
        when(this.mockClientHttpResponse.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
        this.handler.handleError(this.mockClientHttpResponse);
    }
}
