package com.novacroft.nemo.tfl.batch.job.cubic;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.impl.ImportRecordFilterActionMapImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * CubicRecordFilterActionMap unit tests
 */
public class ImportRecordFilterActionMapTest {
    @Test
    public void shouldMatch() {
        ImportRecord mockRecord = mock(ImportRecord.class);

        ImportRecordFilter mockFilter = mock(ImportRecordFilter.class);
        when(mockFilter.matches(any(ImportRecord.class))).thenReturn(Boolean.TRUE);

        ImportRecordAction mockWriter = mock(ImportRecordAction.class);
        doNothing().when(mockWriter).handle(any(ImportRecord.class));

        ImportRecordFilterActionMapImpl filterMap = new ImportRecordFilterActionMapImpl(mockFilter, mockWriter);

        assertTrue(filterMap.matches(mockRecord));
    }

    @Test
    public void shouldHandle() {
        ImportRecord mockRecord = mock(ImportRecord.class);

        ImportRecordFilter mockFilter = mock(ImportRecordFilter.class);
        when(mockFilter.matches(any(ImportRecord.class))).thenReturn(Boolean.TRUE);

        ImportRecordAction mockWriter = mock(ImportRecordAction.class);
        doNothing().when(mockWriter).handle(any(ImportRecord.class));

        ImportRecordFilterActionMapImpl filterMap = new ImportRecordFilterActionMapImpl(mockFilter, mockWriter);

        filterMap.handle(mockRecord);

        verify(mockWriter).handle(any(ImportRecord.class));
    }
}
