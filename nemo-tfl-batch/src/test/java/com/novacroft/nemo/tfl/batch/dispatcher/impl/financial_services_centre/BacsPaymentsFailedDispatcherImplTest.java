package com.novacroft.nemo.tfl.batch.dispatcher.impl.financial_services_centre;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;

public class BacsPaymentsFailedDispatcherImplTest {
	

    private BacsPaymentFailedDispatcherImpl dispatcher;
    private ImportRecordFilter mockBacsFailedPaymentsFilter;
    private ImportRecordAction mockReconcileBacsPaymentFailedAction;

    @Before
    public void setUp() {
        this.dispatcher = new BacsPaymentFailedDispatcherImpl();

        this.mockBacsFailedPaymentsFilter = mock(ImportRecordFilter.class);
        this.mockReconcileBacsPaymentFailedAction = mock(ImportRecordAction.class);

        this.dispatcher.bacsFailedPaymentsFilter = this.mockBacsFailedPaymentsFilter;
        this.dispatcher.reconcileBacsPaymentFailedAction = this.mockReconcileBacsPaymentFailedAction;
    }

    @Test
    public void shouldInitialiseActions() {
        this.dispatcher.initialiseActions();
        List result = (List) getField(this.dispatcher, "actions");
        assertEquals(1, result.size());
    }	
	
	
	

}
