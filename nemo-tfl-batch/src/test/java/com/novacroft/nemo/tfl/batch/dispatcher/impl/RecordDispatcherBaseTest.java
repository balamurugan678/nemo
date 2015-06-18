package com.novacroft.nemo.tfl.batch.dispatcher.impl;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;
import com.novacroft.nemo.tfl.batch.record_filter.impl.ImportRecordFilterActionMapImpl;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * CubicRecordDispatcherBase unit tests
 */
public class RecordDispatcherBaseTest {
    @Test(expected = AssertionError.class)
    public void dispatcherShouldFailWithNullActions() {
        BaseImportRecordDispatcher mockDispatcher = mock(BaseImportRecordDispatcher.class);
        doCallRealMethod().when(mockDispatcher).dispatch(any(ImportRecord.class));
        mockDispatcher.dispatch(mock(ImportRecord.class));
    }

    @Test
    public void dispatcherShouldCallActionWithFilterMatch() {
        ImportRecordFilter mockFilter = mock(ImportRecordFilter.class);
        when(mockFilter.matches(any(ImportRecord.class))).thenReturn(Boolean.TRUE);

        ImportRecordAction mockAction = mock(ImportRecordAction.class);
        doNothing().when(mockAction).handle(any(ImportRecord.class));

        ImportRecordFilterActionMap importRecordFilterActionMap = new ImportRecordFilterActionMapImpl(mockFilter, mockAction);

        BaseImportRecordDispatcher mockDispatcher = mock(BaseImportRecordDispatcher.class);
        doCallRealMethod().when(mockDispatcher).dispatch(any(ImportRecord.class));

        mockDispatcher.actions = new ArrayList<ImportRecordFilterActionMap>();
        mockDispatcher.actions.add(importRecordFilterActionMap);

        mockDispatcher.dispatch(mock(ImportRecord.class));

        verify(mockFilter).matches(any(ImportRecord.class));
        verify(mockAction).handle(any(ImportRecord.class));
    }

    @Test
    public void dispatcherShouldNotCallActionWithoutFilterMatch() {
        ImportRecordFilter mockFilter = mock(ImportRecordFilter.class);
        when(mockFilter.matches(any(ImportRecord.class))).thenReturn(Boolean.FALSE);

        ImportRecordAction mockAction = mock(ImportRecordAction.class);
        doNothing().when(mockAction).handle(any(ImportRecord.class));

        ImportRecordFilterActionMap importRecordFilterActionMap = new ImportRecordFilterActionMapImpl(mockFilter, mockAction);

        BaseImportRecordDispatcher mockDispatcher = mock(BaseImportRecordDispatcher.class);
        doCallRealMethod().when(mockDispatcher).dispatch(any(ImportRecord.class));

        mockDispatcher.actions = new ArrayList<ImportRecordFilterActionMap>();
        mockDispatcher.actions.add(importRecordFilterActionMap);

        mockDispatcher.dispatch(mock(ImportRecord.class));

        verify(mockFilter).matches(any(ImportRecord.class));
        verify(mockAction, never()).handle(any(ImportRecord.class));
    }
}
