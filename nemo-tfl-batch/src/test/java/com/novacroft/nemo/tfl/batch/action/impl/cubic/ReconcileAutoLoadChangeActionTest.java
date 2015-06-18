package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadChangeRecord;
import com.novacroft.nemo.tfl.common.application_service.ReconcileAutoLoadChangeService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * ReconcileAutoLoadChangeAction unit tests
 */
public class ReconcileAutoLoadChangeActionTest {
    private ReconcileAutoLoadChangeActionImpl service;
    private ReconcileAutoLoadChangeService mockReconcileAutoLoadChangeService;
    private AutoLoadChangeRecord mockAutoLoadChangeRecord;

    @Before
    public void setUp() {
        this.service = mock(ReconcileAutoLoadChangeActionImpl.class, CALLS_REAL_METHODS);
        this.mockReconcileAutoLoadChangeService = mock(ReconcileAutoLoadChangeService.class);
        this.service.reconcileAutoLoadChangeService = this.mockReconcileAutoLoadChangeService;

        this.mockAutoLoadChangeRecord = mock(AutoLoadChangeRecord.class);
    }

    @Test
    public void shouldHandle() {
        doNothing().when(this.mockReconcileAutoLoadChangeService)
                .reconcileChange(anyInt(), anyString(), anyInt(), anyString(), anyInt());
        this.service.handle(this.mockAutoLoadChangeRecord);
        verify(this.mockReconcileAutoLoadChangeService).reconcileChange(anyInt(), anyString(), anyInt(), anyString(), anyInt());
    }
}
