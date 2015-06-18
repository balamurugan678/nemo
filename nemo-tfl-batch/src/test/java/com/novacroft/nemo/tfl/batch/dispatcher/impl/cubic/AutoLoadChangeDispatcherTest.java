package com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic;

import com.novacroft.nemo.tfl.batch.action.impl.cubic.ReconcileAutoLoadChangeActionImpl;
import com.novacroft.nemo.tfl.batch.file_filter.impl.cubic.AllAutoLoadChangesFilterImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;

/**
 * AutoLoadChangeDispatcher unit tests
 */
public class AutoLoadChangeDispatcherTest {
    protected static final String ACTIONS_FIELD_NAME = "actions";

    @Test
    public void shouldInitialiseActions() {
        AutoLoadChangeDispatcherImpl dispatcher = new AutoLoadChangeDispatcherImpl();

        dispatcher.allAutoLoadChangesFilter = new AllAutoLoadChangesFilterImpl();
        dispatcher.reconcileAutoLoadChangeAction = new ReconcileAutoLoadChangeActionImpl();

        dispatcher.initialiseActions();

        List resultActions = (List) getField(dispatcher, ACTIONS_FIELD_NAME);
        assertEquals(1, resultActions.size());
    }
}
