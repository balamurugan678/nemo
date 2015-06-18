package com.novacroft.nemo.tfl.common.service_access.impl.cubic;

import com.novacroft.nemo.tfl.common.application_service.cubic.CubicConnectorService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.mockito.Mockito.*;

public class CubicServiceAccessImplTest {

    private CubicServiceAccessImpl service;
    private CubicConnectorService mockCubicConnectorService;
    private HttpURLConnection mockHttpURLConnection;

    @Before
    public void setUp() {
        this.service = mock(CubicServiceAccessImpl.class);

        this.mockCubicConnectorService = mock(CubicConnectorService.class);
        this.service.cubicConnectorService = mockCubicConnectorService;

        this.mockHttpURLConnection = mock(HttpURLConnection.class);
    }

    @Test
    public void shouldCallCubic() {
        when(this.service.callCubic(anyString())).thenCallRealMethod();
        when(this.service.getResponseFromRequest(any(HttpURLConnection.class), anyString())).thenReturn(new StringBuffer());
        when(this.mockCubicConnectorService.getCubicConnection()).thenReturn(this.mockHttpURLConnection);

        this.service.callCubic(StringUtils.EMPTY);

        verify(this.service).getResponseFromRequest(any(HttpURLConnection.class), anyString());
        verify(this.mockCubicConnectorService).getCubicConnection();
    }
}