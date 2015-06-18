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
import com.novacroft.nemo.tfl.common.application_service.AdHocLoadReadyForCollectionStatusService;

public class AdHocLoadReadyForCollectionFilterImplTest {

    private AdHocLoadReadyForCollectionFilterImpl filter;
    private AdHocLoadReadyForCollectionStatusService mockAdHocLoadReadyForCollectionStatusService;

    @Before
    public void setUp() {
        this.filter = mock(AdHocLoadReadyForCollectionFilterImpl.class);
        this.mockAdHocLoadReadyForCollectionStatusService = mock(AdHocLoadReadyForCollectionStatusService.class);
        this.filter.adHocLoadReadyForCollectionStatusService = this.mockAdHocLoadReadyForCollectionStatusService;
    }

    @Test
    public void shouldMatch() {
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadReadyForCollectionStatusService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.TRUE);
        assertTrue(filter.matches(getTestCurrentActionRecord1()));
    }

    @Test
    public void shouldNotMatch() {
        when(filter.matches(any(ImportRecord.class))).thenCallRealMethod();
        when(mockAdHocLoadReadyForCollectionStatusService.hasAdHocLoadBeenRequested(anyInt(), anyString())).thenReturn(Boolean.FALSE);
        assertFalse(filter.matches(getTestCurrentActionRecord1()));
    }
    
    @Test
    public void instantiationTest(){
        assertNotNull(filter = new AdHocLoadReadyForCollectionFilterImpl());
    }
}
