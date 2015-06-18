package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorStatusService;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AdHocLoadErrorActionImplTest {
    @Test
    public void shouldHandle() {

        AdHocLoadErrorStatusService mockAdHocLoadErrorStatusService = mock(AdHocLoadErrorStatusService.class);
        doNothing().when(mockAdHocLoadErrorStatusService).updateStatusToFailed(anyInt(), anyString());

        AdHocLoadErrorActionImpl action = new AdHocLoadErrorActionImpl();
        action.adHocLoadErrorStatusService = mockAdHocLoadErrorStatusService;

        action.handle(getAdHocDistributionTestRecord1());

        verify(mockAdHocLoadErrorStatusService).updateStatusToFailed(anyInt(), anyString());
    }
}
