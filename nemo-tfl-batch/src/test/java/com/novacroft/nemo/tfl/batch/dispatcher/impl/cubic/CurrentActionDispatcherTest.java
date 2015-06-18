package com.novacroft.nemo.tfl.batch.dispatcher.impl.cubic;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import java.util.List;

import org.junit.Test;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadReadyForCollectionActionImpl;
import com.novacroft.nemo.tfl.batch.action.impl.cubic.AdHocLoadReadyForCollectionNotificationActionImpl;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadReadyForCollectionFilterImpl;
import com.novacroft.nemo.tfl.batch.record_filter.impl.cubic.AdHocLoadReadyForCollectionNotificationFilterImpl;

/**
 * CubicCurrentActionDispatcher unit tests
 */
public class CurrentActionDispatcherTest {
    @Test
    public void shouldInitialiseActions() {
        ImportRecordFilter refundFilter = new AdHocLoadReadyForCollectionFilterImpl();
        ImportRecordAction refundAction = new AdHocLoadReadyForCollectionActionImpl();
        ImportRecordFilter refundNotificationFilter = new AdHocLoadReadyForCollectionNotificationFilterImpl();
        ImportRecordAction refundNotificationAction = new AdHocLoadReadyForCollectionNotificationActionImpl();

        CurrentActionDispatcherImpl currentActionDispatcher = new CurrentActionDispatcherImpl();
        currentActionDispatcher.adHocLoadReadyForCollectionFilter = refundFilter;
        currentActionDispatcher.adHocLoadReadyForCollectionAction = refundAction;
        currentActionDispatcher.adHocLoadReadyForCollectionFilter = refundNotificationFilter;
        currentActionDispatcher.adHocLoadReadyForCollectionAction = refundNotificationAction;

        currentActionDispatcher.initialiseActions();

        List resultActions = (List) getField(currentActionDispatcher, "actions");
        assertEquals(2, resultActions.size());
    }
}
