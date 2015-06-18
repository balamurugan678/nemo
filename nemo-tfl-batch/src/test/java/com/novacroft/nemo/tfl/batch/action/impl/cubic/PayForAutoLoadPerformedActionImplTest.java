package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadPerformedRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.PayForAutoLoadPerformedService;
import com.novacroft.nemo.tfl.common.constant.cubic_import.CubicCurrency;

/**
 * Unit tests for PayForAutoLoadPerformedAction
 */
public class PayForAutoLoadPerformedActionImplTest {

    private PayForAutoLoadPerformedActionImpl payForAutoLoadPerformedAction;
    private PayForAutoLoadPerformedService mockPayForAutoLoadPerformedService;
    private AutoLoadPerformedRecord mockAutoLoadPerformedRecord;

    @Before
    public void setUp() {
        this.payForAutoLoadPerformedAction = mock(PayForAutoLoadPerformedActionImpl.class);
        this.mockPayForAutoLoadPerformedService = mock(PayForAutoLoadPerformedService.class);
        this.payForAutoLoadPerformedAction.payForAutoLoadPerformedService = mockPayForAutoLoadPerformedService;
        this.mockAutoLoadPerformedRecord = mock(AutoLoadPerformedRecord.class);
    }

    @Test
    public void getIsoCurrencyCodeShouldReturnEmptyString() {
        when(this.payForAutoLoadPerformedAction.getIsoCurrencyCode(any(AutoLoadPerformedRecord.class))).thenCallRealMethod();
        when(this.mockAutoLoadPerformedRecord.getCurrency()).thenReturn(-99);
        assertEquals("", this.payForAutoLoadPerformedAction.getIsoCurrencyCode(this.mockAutoLoadPerformedRecord));
    }

    @Test
    public void getIsoCurrencyCodeShouldReturnSterlingCode() {
        when(this.payForAutoLoadPerformedAction.getIsoCurrencyCode(any(AutoLoadPerformedRecord.class))).thenCallRealMethod();
        when(this.mockAutoLoadPerformedRecord.getCurrency()).thenReturn(CubicCurrency.GBP.cubicCode());
        assertEquals(CubicCurrency.GBP.isoCode(),
                this.payForAutoLoadPerformedAction.getIsoCurrencyCode(this.mockAutoLoadPerformedRecord));
    }

    @Test
    public void shouldHandle() {
        doCallRealMethod().when(this.payForAutoLoadPerformedAction).handle(any(ImportRecord.class));
        when(this.payForAutoLoadPerformedAction.getIsoCurrencyCode(any(AutoLoadPerformedRecord.class)))
                .thenReturn(CubicCurrency.GBP.isoCode());
        doNothing().when(this.mockPayForAutoLoadPerformedService).payForAutoLoadPerformed(anyString(), anyInt(), anyInt(), anyString());

        this.payForAutoLoadPerformedAction.handle(this.mockAutoLoadPerformedRecord);

        verify(this.payForAutoLoadPerformedAction).getIsoCurrencyCode(any(AutoLoadPerformedRecord.class));
        verify(this.mockPayForAutoLoadPerformedService).payForAutoLoadPerformed(anyString(), anyInt(), anyInt(), anyString());
    }
}
