package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadPerformedRecord;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AutoLoadPerformedForExistingCardRecordFilterImpl;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.PayForAutoLoadPerformedService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * AutoLoadPerformedForExistingCardRecordFilter unit tests
 */
public class AutoLoadPerformedForExistingCardRecordFilterImplTest {

    private AutoLoadPerformedForExistingCardRecordFilterImpl autoLoadPerformedForExistingCardRecordFilter;
    private PayForAutoLoadPerformedService mockPayForAutoLoadPerformedService;
    private AutoLoadPerformedRecord mockAutoLoadPerformedRecord;

    @Before
    public void setUp() {
        this.autoLoadPerformedForExistingCardRecordFilter =
                mock(AutoLoadPerformedForExistingCardRecordFilterImpl.class, CALLS_REAL_METHODS);
        this.mockPayForAutoLoadPerformedService = mock(PayForAutoLoadPerformedService.class);
        ReflectionTestUtils.setField(this.autoLoadPerformedForExistingCardRecordFilter, "payForAutoLoadPerformedService",
                mockPayForAutoLoadPerformedService);
        this.mockAutoLoadPerformedRecord = mock(AutoLoadPerformedRecord.class);
    }

    @Test
    public void shouldMatch() {
        when(this.mockPayForAutoLoadPerformedService.isExistingCard(anyString())).thenReturn(true);
        assertTrue(this.autoLoadPerformedForExistingCardRecordFilter.matches(this.mockAutoLoadPerformedRecord));
    }

    @Test
    public void shouldNotMatch() {
        when(this.mockPayForAutoLoadPerformedService.isExistingCard(anyString())).thenReturn(false);
        assertFalse(this.autoLoadPerformedForExistingCardRecordFilter.matches(this.mockAutoLoadPerformedRecord));
    }
}
