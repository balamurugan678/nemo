package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionRecord1;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadReadyForCollectionNotificationService;

public class AdHocLoadReadyForCollectionNotificationActionTest {
    @Test
    public void shouldHandle() {

        AdHocLoadReadyForCollectionNotificationService mockAdHocLoadReadyForCollectionNotificationService = mock(AdHocLoadReadyForCollectionNotificationService.class);
        doNothing().when(mockAdHocLoadReadyForCollectionNotificationService).notifyCustomer(anyInt(), anyString());

        AdHocLoadReadyForCollectionNotificationActionImpl action = new AdHocLoadReadyForCollectionNotificationActionImpl();
        action.adHocLoadReadyForCollectionNotificationService = mockAdHocLoadReadyForCollectionNotificationService;
        CurrentActionRecord currentActionRecord = getTestCurrentActionRecord1();
        action.handle(currentActionRecord);

        verify(mockAdHocLoadReadyForCollectionNotificationService).notifyCustomer(anyInt(), anyString());
    }
}
