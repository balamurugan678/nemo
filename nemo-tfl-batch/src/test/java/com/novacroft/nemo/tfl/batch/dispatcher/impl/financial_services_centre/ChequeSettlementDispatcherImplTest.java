package com.novacroft.nemo.tfl.batch.dispatcher.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class ChequeSettlementDispatcherImplTest {
    private ChequeSettlementDispatcherImpl dispatcher;
    private ImportRecordFilter mockClearanceForExistingChequeFilter;
    private ImportRecordAction mockReconcileChequeClearanceAction;

    @Before
    public void setUp() {
        this.dispatcher = new ChequeSettlementDispatcherImpl();

        this.mockClearanceForExistingChequeFilter = mock(ImportRecordFilter.class);
        this.mockReconcileChequeClearanceAction = mock(ImportRecordAction.class);

        this.dispatcher.clearanceForExistingChequeFilter = this.mockClearanceForExistingChequeFilter;
        this.dispatcher.reconcileChequeClearanceAction = this.mockReconcileChequeClearanceAction;
    }

    @Test
    public void shouldInitialiseActions() {
        this.dispatcher.initialiseActions();
        List result = (List) getField(this.dispatcher, "actions");
        assertEquals(1, result.size());
    }
}