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
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredNotificationService;

public class AdHocLoadPickUpWindowExpiredNotificationFilterImplTest {

    private AdHocLoadPickUpWindowExpiredNotificationFilterImpl filter;
    private AdHocLoadPickUpWindowExpiredNotificationService mockAdHocLoadPickUpWindowExpiredNotificationService;

    @Before
    public void setUp() {
        this.filter = mock(AdHocLoadPickUpWindowExpiredNotificationFilterImpl.class);
        this.mockAdHocLoadPickUpWindowExpiredNotificationService = mock(AdHocLoadPickUpWindowExpiredNotificationService.class);
        this.filter.adHocLoadPickUpWindowExpiredNotificationService = this.mockAdHocLoadPickUpWindowExpiredNotificationService;
    }

    @Test
    public void hasFailedShouldReturnTrue() {
        when(this.filter.hasFailed(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setStatusOfAttemptedAction(CubicActionStatus.FAILED.name());
        assertTrue(this.filter.hasFailed(record));
    }

    @Test
    public void hasFailedShouldReturnFalse() {
        when(this.filter.hasFailed(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setStatusOfAttemptedAction(CubicActionStatus.OK.name());
        assertFalse(this.filter.hasFailed(record));
    }

    @Test
    public void hasPickUpWindowExpiredShouldReturnTrue() {
        when(this.filter.hasPickUpWindowExpired(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code());
        assertTrue(this.filter.hasPickUpWindowExpired(record));
    }

    @Test
    public void hasPickUpWindowExpiredShouldReturnFalse() {
        when(this.filter.hasPickUpWindowExpired(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(null);
        assertFalse(this.filter.hasPickUpWindowExpired(record));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnTrue() {
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadPickUpWindowExpiredNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.filter.hasRefundBeenRequested(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalse() {
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadPickUpWindowExpiredNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.filter.hasRefundBeenRequested(getAdHocDistributionTestRecord1()));
    }
    
    @Test
    public void hasNotificationEmailRequiredShouldReturnTrue() {
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadPickUpWindowExpiredNotificationService.isNotificationEmailRequired(anyInt(), anyString()))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.filter.isNotificationEmailRequired(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void hasNotificationEmailRequiredShouldReturnFalse() {
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadPickUpWindowExpiredNotificationService.isNotificationEmailRequired(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.filter.isNotificationEmailRequired(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void shouldMatch() {
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(filter.hasFailed(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasPickUpWindowExpired(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(true);
        assertTrue(filter.matches(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void shouldNotMatch() {
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(filter.hasFailed(any(AdHocDistributionRecord.class))).thenReturn(false);
        when(filter.hasPickUpWindowExpired(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(true);
        assertFalse(filter.matches(getAdHocDistributionTestRecord1()));
    }
}
