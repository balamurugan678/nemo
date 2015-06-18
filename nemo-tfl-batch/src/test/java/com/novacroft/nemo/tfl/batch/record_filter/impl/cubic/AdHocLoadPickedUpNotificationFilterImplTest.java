package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.PRESTIGE_ID_1;
import static com.novacroft.nemo.test_support.AdHocDistributionRecordTestUtil.getAdHocDistributionTestRecord1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpNotificationService;

public class AdHocLoadPickedUpNotificationFilterImplTest {
    private AdHocLoadPickedUpNotificationFilterImpl filter;
    private AdHocLoadPickedUpNotificationService mockAdHocLoadPickedUpNotificationService;
    private AdHocDistributionRecord mockAdHocDistributionRecord;

    @Before
    public void setUp() {
        this.filter = mock(AdHocLoadPickedUpNotificationFilterImpl.class);

        this.mockAdHocLoadPickedUpNotificationService = mock(AdHocLoadPickedUpNotificationService.class);
        this.filter.adHocLoadPickedUpNotificationService = mockAdHocLoadPickedUpNotificationService;

        this.mockAdHocDistributionRecord = mock(AdHocDistributionRecord.class);
    }

    @Test
    public void matchesShouldReturnTrue() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isActionStatusOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        assertTrue(this.filter.matches(this.mockAdHocDistributionRecord));
    }

    @Test
    public void matchesShouldReturnFalseWithNotOk() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isActionStatusOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.FALSE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.filter.matches(this.mockAdHocDistributionRecord));
    }

    @Test
    public void matchesShouldReturnFalseWithNotRequested() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isActionStatusOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.FALSE);
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        assertFalse(this.filter.matches(this.mockAdHocDistributionRecord));
    }
    
    @Test
    public void matchesShouldReturnFalseWithEmailNotRequired() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isActionStatusOk(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenReturn(Boolean.TRUE);
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenReturn(Boolean.FALSE);
        assertFalse(this.filter.matches(this.mockAdHocDistributionRecord));
    }

    @Test
    public void isActionStatusOkShouldReturnTrue() {
        when(this.filter.isActionStatusOk(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getStatusOfAttemptedAction()).thenReturn(CubicActionStatus.OK.name());
        assertTrue(this.filter.isActionStatusOk(this.mockAdHocDistributionRecord));

    }

    @Test
    public void isActionStatusOkShouldReturnFalse() {
        when(this.filter.isActionStatusOk(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getStatusOfAttemptedAction()).thenReturn(CubicActionStatus.FAILED.name());
        assertFalse(this.filter.isActionStatusOk(this.mockAdHocDistributionRecord));

    }

    @Test
    public void hasRefundBeenRequestedShouldReturnTrue() {
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getRequestSequenceNumber()).thenReturn(REQUEST_SEQUENCE_NUMBER);
        when(this.mockAdHocDistributionRecord.getPrestigeId()).thenReturn(PRESTIGE_ID_1);
        when(this.mockAdHocLoadPickedUpNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.TRUE);

        assertTrue(this.filter.hasRefundBeenRequested(this.mockAdHocDistributionRecord));

        verify(this.mockAdHocDistributionRecord).getRequestSequenceNumber();
        verify(this.mockAdHocDistributionRecord).getPrestigeId();
        verify(this.mockAdHocLoadPickedUpNotificationService).hasAdHocLoadBeenRequested(anyInt(), anyString());
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalse() {
        when(this.filter.hasRefundBeenRequested(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocDistributionRecord.getRequestSequenceNumber()).thenReturn(REQUEST_SEQUENCE_NUMBER);
        when(this.mockAdHocDistributionRecord.getPrestigeId()).thenReturn(PRESTIGE_ID_1);
        when(this.mockAdHocLoadPickedUpNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);

        assertFalse(this.filter.hasRefundBeenRequested(this.mockAdHocDistributionRecord));

        verify(this.mockAdHocDistributionRecord).getRequestSequenceNumber();
        verify(this.mockAdHocDistributionRecord).getPrestigeId();
        verify(this.mockAdHocLoadPickedUpNotificationService).hasAdHocLoadBeenRequested(anyInt(), anyString());
    }
    
    @Test
    public void isNotificationEmailRequiredShouldReturnTrue() {
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocLoadPickedUpNotificationService.isNotificationEmailRequired(anyInt(), anyString()))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.filter.isNotificationEmailRequired(getAdHocDistributionTestRecord1()));
    }

    @Test
    public void isNotificationEmailRequiredShouldReturnFalse() {
        when(this.filter.isNotificationEmailRequired(any(AdHocDistributionRecord.class))).thenCallRealMethod();
        when(this.mockAdHocLoadPickedUpNotificationService.isNotificationEmailRequired(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.filter.isNotificationEmailRequired(getAdHocDistributionTestRecord1()));
    }
}