package com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;
import com.novacroft.nemo.tfl.batch.record_filter.impl.ImportRecordFilterActionMapImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

/**
 * AutoLoadPerformedDispatcher unit tests
 */
public class AutoLoadPerformedDispatcherImplTest {
    private AutoLoadPerformedDispatcherImpl autoLoadPerformedDispatcher;

    private ImportRecordFilter mockAutoLoadPerformedForExistingCardRecordFilter;
    private ImportRecordAction mockPayForAutoLoadPerformedAction;

    @Before
    public void setUp() {
        this.autoLoadPerformedDispatcher = mock(AutoLoadPerformedDispatcherImpl.class, CALLS_REAL_METHODS);
        this.mockAutoLoadPerformedForExistingCardRecordFilter = mock(ImportRecordFilter.class);
        this.mockPayForAutoLoadPerformedAction = mock(ImportRecordAction.class);
        this.autoLoadPerformedDispatcher.autoLoadPerformedForExistingCardRecordFilter =
                this.mockAutoLoadPerformedForExistingCardRecordFilter;
        this.autoLoadPerformedDispatcher.payForAutoLoadPerformedAction = this.mockPayForAutoLoadPerformedAction;
    }

    @Test
    public void shouldInitialiseActions() {
        this.autoLoadPerformedDispatcher.initialiseActions();

        List<ImportRecordFilterActionMap> actions = (List) getField(this.autoLoadPerformedDispatcher, "actions");
        assertEquals(1, actions.size());

        ImportRecordFilterActionMapImpl filterActionMap = (ImportRecordFilterActionMapImpl) actions.iterator().next();

        ImportRecordFilter recordFilter = (ImportRecordFilter) getField(filterActionMap, "filter");
        assertEquals(this.mockAutoLoadPerformedForExistingCardRecordFilter, recordFilter);

        ImportRecordAction recordAction = (ImportRecordAction) getField(filterActionMap, "action");
        assertEquals(this.mockPayForAutoLoadPerformedAction, recordAction);
    }
}
