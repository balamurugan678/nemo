package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicFailureReasonCode;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorStatusService;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdHocLoadErrorFilterImplTest {
    private AdHocLoadErrorStatusService mockAdHocLoadErrorStatusService;

    @Before
    public void setUp() {
        this.mockAdHocLoadErrorStatusService = mock(AdHocLoadErrorStatusService.class);
    }

    @Test
    public void hasFailedShouldReturnTrue() {
        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setStatusOfAttemptedAction(CubicActionStatus.FAILED.name());
        assertTrue(filter.hasFailed(record));
    }

    @Test
    public void hasFailedShouldReturnFalse() {
        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setStatusOfAttemptedAction(CubicActionStatus.OK.name());
        assertFalse(filter.hasFailed(record));
    }

    @Test
    public void hasErrorShouldReturnTrue() {
        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(1);
        assertTrue(filter.hasError(record));
    }

    @Test
    public void hasErrorShouldReturnFalseNullReasonCode() {
        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(null);
        assertFalse(filter.hasError(record));
    }

    @Test
    public void hasErrorShouldReturnFalseExpiredReasonCode() {
        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        AdHocDistributionRecord record = getAdHocDistributionTestRecord1();
        record.setFailureReasonCode(CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code());
        assertFalse(filter.hasError(record));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnTrue() {
        when(this.mockAdHocLoadErrorStatusService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.TRUE);

        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        filter.adHocLoadErrorStatusService = mockAdHocLoadErrorStatusService;

        assertTrue(filter.hasRefundBeenRequested(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalse() {
        when(this.mockAdHocLoadErrorStatusService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.FALSE);

        AdHocLoadErrorFilterImpl filter = new AdHocLoadErrorFilterImpl();
        filter.adHocLoadErrorStatusService = mockAdHocLoadErrorStatusService;

        assertFalse(filter.hasRefundBeenRequested(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void shouldMatch() {
        AdHocLoadErrorFilterImpl filter = mock(AdHocLoadErrorFilterImpl.class);
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(filter.hasFailed(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasError(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(true);
        assertTrue(filter.matches(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void shouldNotMatch() {
        AdHocLoadErrorFilterImpl filter = mock(AdHocLoadErrorFilterImpl.class);
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(filter.hasFailed(any(AdHocDistributionRecord.class))).thenReturn(false);
        when(filter.hasError(any(AdHocDistributionRecord.class))).thenReturn(true);
        when(filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(true);
        assertFalse(filter.matches(getAdHocDistributionTestRecord1()));
    }
}
