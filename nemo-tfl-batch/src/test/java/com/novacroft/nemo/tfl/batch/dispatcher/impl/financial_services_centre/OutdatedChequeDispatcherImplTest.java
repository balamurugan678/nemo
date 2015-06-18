package com.novacroft.nemo.tfl.batch.dispatcher.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class OutdatedChequeDispatcherImplTest {

    private OutdatedChequeDispatcherImpl dispatcher;
    private ImportRecordFilter mockChequeForExistingOrderFilter;
    private ImportRecordAction mockReconcileOutdatedChequeAction;

    @Before
    public void setUp() {
        this.dispatcher = new OutdatedChequeDispatcherImpl();

        this.mockChequeForExistingOrderFilter = mock(ImportRecordFilter.class);
        this.mockReconcileOutdatedChequeAction = mock(ImportRecordAction.class);

        this.dispatcher.chequeForExistingOrderFilter = this.mockChequeForExistingOrderFilter;
        this.dispatcher.reconcileOutdatedChequeAction = this.mockReconcileOutdatedChequeAction;
    }

    @Test
    public void shouldInitialiseActions() {
        this.dispatcher.initialiseActions();
        List result = (List) getField(this.dispatcher, "actions");
        assertEquals(1, result.size());
    }
}