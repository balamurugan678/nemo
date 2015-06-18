package com.novacroft.nemo.tfl.common.data_service.impl.cyber_source;

import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceHeartbeatServiceAccess;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * CyberSourceHeartbeatDataService unit tests
 */
public class CyberSourceHeartbeatDataServiceImplTest {
    private CyberSourceHeartbeatDataServiceImpl service;
    private CyberSourceHeartbeatServiceAccess mockCyberSourceHeartbeatServiceAccess;

    @Before
    public void setUp() {
        this.service = mock(CyberSourceHeartbeatDataServiceImpl.class);
        this.mockCyberSourceHeartbeatServiceAccess = mock(CyberSourceHeartbeatServiceAccess.class);
        this.service.cyberSourceHeartbeatServiceAccess = this.mockCyberSourceHeartbeatServiceAccess;
    }

    @Test
    public void shouldCheckHeartbeat() {
        doCallRealMethod().when(this.service).checkHeartbeat(anyLong());
        doNothing().when(this.mockCyberSourceHeartbeatServiceAccess).checkHeartbeat(anyLong());

        this.service.checkHeartbeat(CustomerTestUtil.CUSTOMER_ID_1);

        verify(this.mockCyberSourceHeartbeatServiceAccess).checkHeartbeat(anyLong());
    }
}
