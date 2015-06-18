package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorNotificationService;

public class AdHocLoadErrorNotificationActionTest {
    @Test
    public void shouldHandle() {

        AdHocLoadErrorNotificationService mockAdHocLoadErrorNotificationService = mock(AdHocLoadErrorNotificationService.class);
        doNothing().when(mockAdHocLoadErrorNotificationService).notifyCustomer(anyInt(), anyString());

        AdHocLoadErrorNotificationActionImpl action = new AdHocLoadErrorNotificationActionImpl();
        action.adHocLoadErrorNotificationService = mockAdHocLoadErrorNotificationService;

        action.handle(getAdHocDistributionTestRecord1());

        verify(mockAdHocLoadErrorNotificationService).notifyCustomer(anyInt(), anyString());
    }
}
