package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import static com.novacroft.nemo.test_support.CurrentActionRecordTestUtil.getTestCurrentActionRecord1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadReadyForCollectionNotificationService;

public class AdHocLoadReadyForCollectionNotificationFilterImplTest {

    private AdHocLoadReadyForCollectionNotificationFilterImpl filter;
    private AdHocLoadReadyForCollectionNotificationService mockAdHocLoadReadyForCollectionNotificationService;

    @Before
    public void setUp() {
        this.filter = mock(AdHocLoadReadyForCollectionNotificationFilterImpl.class);
        this.mockAdHocLoadReadyForCollectionNotificationService = mock(AdHocLoadReadyForCollectionNotificationService.class);
        this.filter.adHocLoadReadyForCollectionNotificationService = this.mockAdHocLoadReadyForCollectionNotificationService;
    }
    
    @Test
    public void hasRefundBeenRequestedShouldReturnTrue() {
        when(this.filter.hasRefundBeenRequested(any(CurrentActionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadReadyForCollectionNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.filter.hasRefundBeenRequested(getTestCurrentActionRecord1()));
    }

    @Test
    public void hasRefundBeenRequestedShouldReturnFalse() {
        when(this.filter.hasRefundBeenRequested(any(CurrentActionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadReadyForCollectionNotificationService.hasAdHocLoadBeenRequested(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.filter.hasRefundBeenRequested(getTestCurrentActionRecord1()));
    }
    
    @Test
    public void hasNotificationEmailRequiredShouldReturnTrue() {
        when(this.filter.isNotificationEmailRequired(any(CurrentActionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadReadyForCollectionNotificationService.isNotificationEmailRequired(anyInt(), anyString()))
                .thenReturn(Boolean.TRUE);
        assertTrue(this.filter.isNotificationEmailRequired(getTestCurrentActionRecord1()));
    }

    @Test
    public void hasNotificationEmailRequiredShouldReturnFalse() {
        when(this.filter.isNotificationEmailRequired(any(CurrentActionRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadReadyForCollectionNotificationService.isNotificationEmailRequired(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);
        assertFalse(this.filter.isNotificationEmailRequired(getTestCurrentActionRecord1()));
    }

    @Test
    public void shouldMatch() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isNotificationEmailRequired(any(CurrentActionRecord.class))).thenReturn(true);
        when(this.filter.hasRefundBeenRequested(any(CurrentActionRecord.class))).thenReturn(true);
        assertTrue(this.filter.matches(getTestCurrentActionRecord1()));
    }

    @Test
    public void shouldNotMatch() {
        when(this.filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(this.filter.isNotificationEmailRequired(any(CurrentActionRecord.class))).thenReturn(false);
        when(this.filter.hasRefundBeenRequested(any(CurrentActionRecord.class))).thenReturn(true);
        assertFalse(this.filter.matches(getTestCurrentActionRecord1()));
    }
    
    @Test
    public void instantiationTest(){
        assertNotNull(this.filter = new AdHocLoadReadyForCollectionNotificationFilterImpl());
    }
}
