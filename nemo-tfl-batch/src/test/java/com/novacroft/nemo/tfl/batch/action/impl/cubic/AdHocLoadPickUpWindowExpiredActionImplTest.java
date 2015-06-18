package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredStatusService;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AdHocLoadPickUpWindowExpiredActionImplTest {

    @Test
    public void shouldHandle() {
        AdHocLoadPickUpWindowExpiredStatusService mockAdHocLoadPickUpWindowExpiredStatusService =
                mock(AdHocLoadPickUpWindowExpiredStatusService.class);
        doNothing().when(mockAdHocLoadPickUpWindowExpiredStatusService).updateStatusToPickUpExpired(anyInt(), anyString());

        AdHocLoadPickUpWindowExpiredActionImpl action = new AdHocLoadPickUpWindowExpiredActionImpl();
        action.adHocLoadPickUpWindowExpiredStatusService = mockAdHocLoadPickUpWindowExpiredStatusService;

        action.handle(getAdHocDistributionTestRecord1());

        verify(mockAdHocLoadPickUpWindowExpiredStatusService).updateStatusToPickUpExpired(anyInt(), anyString());
    }
}
