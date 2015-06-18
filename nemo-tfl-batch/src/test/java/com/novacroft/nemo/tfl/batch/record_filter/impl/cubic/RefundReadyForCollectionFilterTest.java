package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadReadyForCollectionNotificationService;
import com.novacroft.nemo.tfl.common.application_service.impl.cubic_import.AdHocLoadReadyForCollectionNotificationServiceImpl;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionRecord1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for RefundReadyForCollectionFilter
 */
public class RefundReadyForCollectionFilterTest {

    @Test
    public void shouldMatch() {
        AdHocLoadReadyForCollectionNotificationService mockAdHocLoadReadyForCollectionNotificationService =
                mock(AdHocLoadReadyForCollectionNotificationServiceImpl.class);
        when(mockAdHocLoadReadyForCollectionNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.TRUE);

        RefundReadyForCollectionFilterImpl filter = new RefundReadyForCollectionFilterImpl();
        filter.adHocLoadReadyForCollectionNotificationService = mockAdHocLoadReadyForCollectionNotificationService;

        assertTrue(filter.matches(getTestCurrentActionRecord1()));
    }

    @Test
    public void shouldNotMatch() {
        AdHocLoadReadyForCollectionNotificationService mockAdHocLoadReadyForCollectionNotificationService =
                mock(AdHocLoadReadyForCollectionNotificationServiceImpl.class);
        when(mockAdHocLoadReadyForCollectionNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);

        RefundReadyForCollectionFilterImpl filter = new RefundReadyForCollectionFilterImpl();
        filter.adHocLoadReadyForCollectionNotificationService = mockAdHocLoadReadyForCollectionNotificationService;

        assertFalse(filter.matches(getTestCurrentActionRecord1()));
    }
}
