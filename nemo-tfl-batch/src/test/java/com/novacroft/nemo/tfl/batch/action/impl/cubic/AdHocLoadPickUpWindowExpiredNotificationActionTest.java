package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredNotificationService;

public class AdHocLoadPickUpWindowExpiredNotificationActionTest {
    @Test
    public void shouldHandle() {

        AdHocLoadPickUpWindowExpiredNotificationService mockAdHocLoadPickUpWindowExpiredNotificationService = mock(AdHocLoadPickUpWindowExpiredNotificationService.class);
        doNothing().when(mockAdHocLoadPickUpWindowExpiredNotificationService).notifyCustomer(anyInt(), anyString());

        AdHocLoadPickUpWindowExpiredNotificationActionImpl action = new AdHocLoadPickUpWindowExpiredNotificationActionImpl();
        action.adHocLoadPickUpWindowExpiredNotificationService = mockAdHocLoadPickUpWindowExpiredNotificationService;

        action.handle(getAdHocDistributionTestRecord1());

        verify(mockAdHocLoadPickUpWindowExpiredNotificationService).notifyCustomer(anyInt(), anyString());
    }
}
