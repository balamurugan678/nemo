package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpStatusService;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AdHocLoadPickedUpActionImplTest {
    @Test
    public void shouldHandle() {
        AdHocLoadPickedUpStatusService mockAdHocLoadPickedUpStatusService = mock(AdHocLoadPickedUpStatusService.class);
        doNothing().when(mockAdHocLoadPickedUpStatusService).updateStatusToPickedUp(anyInt(), anyString());

        AdHocLoadPickedUpActionImpl action = new AdHocLoadPickedUpActionImpl();
        action.adHocLoadPickedUpStatusService = mockAdHocLoadPickedUpStatusService;

        action.handle(getAdHocDistributionTestRecord1());

        verify(mockAdHocLoadPickedUpStatusService).updateStatusToPickedUp(anyInt(), anyString());
    }
}