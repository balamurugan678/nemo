package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpStatusService;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.PRESTIGE_ID_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AdHocLoadPickedUpFilterImplTest {
    private AdHocLoadPickedUpFilterImpl filter;
    private AdHocLoadPickedUpStatusService mockAdHocLoadPickedUpStatusService;
    private AdHocDistributionRecord mockAdHocDistributionRecord;

    @Before
    public void setUp() {
        this.filter = mock(AdHocLoadPickedUpFilterImpl.class);

        this.mockAdHocLoadPickedUpStatusService = mock(AdHocLoadPickedUpStatusService.class);
        this.filter.adHocLoadPickedUpStatusService = mockAdHocLoadPickedUpStatusService;

        this.mockAdHocDistributionRecord = mock(AdHocDistributionRecord.class);
    }

    @Test
    public void matchesShouldReturnTrue() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        assertTrue(this.filter.matches(this.mockAdHocDistributionRecord));
    }

    @Test
    public void matchesShouldReturnFalseWithNotOk() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.FALSE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.filter.matches(this.mockAdHocDistributionRecord));
    }

    @Test
    public void matchesShouldReturnFalseWithNotRequested() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.FALSE);
        assertFalse(this.filter.matches(this.mockAdHocDistributionRecord));
    }

    @Test
    public void isOkShouldReturnTrue() {
        when(this.filter.isOk(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getStatusOfAttemptedAction()).thenReturn(CubicActionStatus.OK.name());
        assertTrue(this.filter.isOk(this.mockAdHocDistributionRecord));

    }

    @Test
    public void isOkShouldReturnFalse() {
        when(this.filter.isOk(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getStatusOfAttemptedAction()).thenReturn(CubicActionStatus.FAILED.name());
        assertFalse(this.filter.isOk(this.mockAdHocDistributionRecord));

    }

    @Test
    public void hasRefundBeenRequestedShouldReturnTrue() {
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getRequestSequenceNumber()).thenReturn(REQUEST_SEQUENCE_NUMBER);
        when(this.mockAdHocDistributionRecord.getPrestigeId()).thenReturn(PRESTIGE_ID_1);
        when(this.mockAdHocLoadPickedUpStatusService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.TRUE);

        assertTrue(this.filter.hasRefundBeenRequested(this.mockAdHocDistributionRecord));

        verify(this.mockAdHocDistributionRecord).getRequestSequenceNumber();
        verify(this.mockAdHocDistributionRecord).getPrestigeId();
        verify(this.mockAdHocLoadPickedUpStatusService).hasAdHocLoadBeenRequested(anyInt(), anyString());
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalse() {
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getRequestSequenceNumber()).thenReturn(REQUEST_SEQUENCE_NUMBER);
        when(this.mockAdHocDistributionRecord.getPrestigeId()).thenReturn(PRESTIGE_ID_1);
        when(this.mockAdHocLoadPickedUpStatusService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);

        assertFalse(this.filter.hasRefundBeenRequested(this.mockAdHocDistributionRecord));

        verify(this.mockAdHocDistributionRecord).getRequestSequenceNumber();
        verify(this.mockAdHocDistributionRecord).getPrestigeId();
        verify(this.mockAdHocLoadPickedUpStatusService).hasAdHocLoadBeenRequested(anyInt(), anyString());
    }
}