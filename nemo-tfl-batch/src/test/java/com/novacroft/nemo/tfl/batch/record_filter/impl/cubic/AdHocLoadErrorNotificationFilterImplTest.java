package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicFailureReasonCode;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorNotificationService;

public class AdHocLoadErrorNotificationFilterImplTest {
    private AdHocLoadErrorNotificationService mockAdHocLoadErrorNotificationService;

    @Before
    public void setUp() {
        this.mockAdHocLoadErrorNotificationService = mock(AdHocLoadErrorNotificationService.class);
    }

    @Test
    public void hasFailedShouldReturnTrue() {
        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setStatusOfAttemptedAction(CubicActionStatus.FAILED.name());
        assertTrue(filter.hasFailed(record));
    }

    @Test
    public void hasFailedShouldReturnFalse() {
        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setStatusOfAttemptedAction(CubicActionStatus.OK.name());
        assertFalse(filter.hasFailed(record));
    }

    @Test
    public void hasErrorShouldReturnTrue() {
        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(1);
        assertTrue(filter.hasError(record));
    }

    @Test
    public void hasErrorShouldReturnFalseNullReasonCode() {
        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(null);
        assertFalse(filter.hasError(record));
    }

    @Test
    public void hasErrorShouldReturnFalseExpiredReasonCode() {
        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code());
        assertFalse(filter.hasError(record));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnTrue() {
        when(this.mockAdHocLoadErrorNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.TRUE);

        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        filter.adHocLoadErrorNotificationService = mockAdHocLoadErrorNotificationService;

        assertTrue(filter.hasRefundBeenRequested(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalse() {
        when(this.mockAdHocLoadErrorNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.FALSE);

        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        filter.adHocLoadErrorNotificationService = mockAdHocLoadErrorNotificationService;

        assertFalse(filter.hasRefundBeenRequested(getAdHocDistributionTestRecord1()));
    }
    
    @Test
    public void hasNotificationEmailRequiredShouldReturnTrue() {
        when(this.mockAdHocLoadErrorNotificationService.isNotificationEmailRequired(anyInt(), anyString())).thenReturn(Boolean.TRUE);

        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        filter.adHocLoadErrorNotificationService = mockAdHocLoadErrorNotificationService;

        assertTrue(filter.isNotificationEmailRequired(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void hasNotificationEmailRequiredShouldReturnFalse() {
        when(this.mockAdHocLoadErrorNotificationService.isNotificationEmailRequired(anyInt(), anyString())).thenReturn(Boolean.FALSE);

        AdHocLoadErrorNotificationFilterImpl filter = new AdHocLoadErrorNotificationFilterImpl();
        filter.adHocLoadErrorNotificationService = mockAdHocLoadErrorNotificationService;

        assertFalse(filter.isNotificationEmailRequired(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void shouldMatch() {
        AdHocLoadErrorNotificationFilterImpl filter = mock(AdHocLoadErrorNotificationFilterImpl.class);
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(filter.hasFailed(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasError(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(true);
        assertTrue(filter.matches(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void shouldNotMatch() {
        AdHocLoadErrorNotificationFilterImpl filter = mock(AdHocLoadErrorNotificationFilterImpl.class);
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(filter.hasFailed(any(AdHocDistributionRecord.class))).thenReturn(false);
        when(filter.hasError(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(true);
        assertFalse(filter.matches(getAdHocDistributionTestRecord1()));
    }
}
