package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpNotificationService;

public class AdHocLoadPickedUpNotificationActionTest {
    @Test
    public void shouldHandle() {

        AdHocLoadPickedUpNotificationService mockAdHocLoadPickedUpNotificationService = mock(AdHocLoadPickedUpNotificationService.class);
        doNothing().when(mockAdHocLoadPickedUpNotificationService).notifyCustomer(anyInt(), anyString());

        AdHocLoadPickedUpNotificationActionImpl action = new AdHocLoadPickedUpNotificationActionImpl();
        action.adHocLoadPickedUpNotificationService = mockAdHocLoadPickedUpNotificationService;

        action.handle(getAdHocDistributionTestRecord1());

        verify(mockAdHocLoadPickedUpNotificationService).notifyCustomer(anyInt(), anyString());
    }
}
