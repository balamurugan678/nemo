package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeProducedRecordService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ChequeProducedConverterImplTest {
    private ChequeProducedConverterImpl converter;
    private ChequeProducedRecordService mockChequeProducedRecordService;

    @Before
    public void setUp() {
        this.converter = mock(ChequeProducedConverterImpl.class);
        this.mockChequeProducedRecordService = mock(ChequeProducedRecordService.class);
        this.converter.chequeProducedRecordService = this.mockChequeProducedRecordService;
    }

    @Test
    public void shouldConvert() {
        when(this.converter.convert(any(String[].class))).thenCallRealMethod();
        when(this.mockChequeProducedRecordService.getReferenceNumberAsLong(any(String[].class))).thenReturn(null);
        when(this.mockChequeProducedRecordService.getAmountAsFloat(any(String[].class))).thenReturn(null);
        when(this.mockChequeProducedRecordService.getCustomerName(any(String[].class))).thenReturn(null);
        when(this.mockChequeProducedRecordService.getChequeSerialNumberAsLong(any(String[].class))).thenReturn(null);
        when(this.mockChequeProducedRecordService.getPrintedOnAsDate(any(String[].class))).thenReturn(null);

        this.converter.convert(new String[0]);

        verify(this.mockChequeProducedRecordService).getReferenceNumberAsLong(any(String[].class));
        verify(this.mockChequeProducedRecordService).getAmountAsFloat(any(String[].class));
        verify(this.mockChequeProducedRecordService).getCustomerName(any(String[].class));
        verify(this.mockChequeProducedRecordService).getChequeSerialNumberAsLong(any(String[].class));
        verify(this.mockChequeProducedRecordService).getPrintedOnAsDate(any(String[].class));
    }
}