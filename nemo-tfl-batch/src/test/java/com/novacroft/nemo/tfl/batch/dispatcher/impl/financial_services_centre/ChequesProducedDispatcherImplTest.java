package com.novacroft.nemo.tfl.batch.dispatcher.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class ChequesProducedDispatcherImplTest {
    private ChequesProducedDispatcherImpl dispatcher;
    private ImportRecordFilter mockChequeForExistingOrderFilter;
    private ImportRecordAction mockReconcileChequeProducedAction;

    @Before
    public void setUp() {
        this.dispatcher = new ChequesProducedDispatcherImpl();

        this.mockChequeForExistingOrderFilter = mock(ImportRecordFilter.class);
        this.mockReconcileChequeProducedAction = mock(ImportRecordAction.class);

        this.dispatcher.chequeForExistingOrderFilter = this.mockChequeForExistingOrderFilter;
        this.dispatcher.reconcileChequeProducedAction = this.mockReconcileChequeProducedAction;
    }

    @Test
    public void shouldInitialiseActions() {
        this.dispatcher.initialiseActions();
        List result = (List) getField(this.dispatcher, "actions");
        assertEquals(1, result.size());
    }
}