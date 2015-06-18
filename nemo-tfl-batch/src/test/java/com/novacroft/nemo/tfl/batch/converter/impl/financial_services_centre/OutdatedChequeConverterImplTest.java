package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.OutdatedChequeRecordService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class OutdatedChequeConverterImplTest {
    private OutdatedChequeConverterImpl converter;
    private OutdatedChequeRecordService mockOutdatedChequeRecordService;

    @Before
    public void setUp() {
        this.converter = mock(OutdatedChequeConverterImpl.class);
        this.mockOutdatedChequeRecordService = mock(OutdatedChequeRecordService.class);
        this.converter.outdatedChequeRecordService = this.mockOutdatedChequeRecordService;
    }

    @Test
    public void shouldConvert() {
        when(this.converter.convert(any(String[].class))).thenCallRealMethod();
        when(this.mockOutdatedChequeRecordService.getReferenceNumberAsLong(any(String[].class))).thenReturn(null);
        when(this.mockOutdatedChequeRecordService.getAmountAsFloat(any(String[].class))).thenReturn(null);
        when(this.mockOutdatedChequeRecordService.getCustomerName(any(String[].class))).thenReturn(null);
        when(this.mockOutdatedChequeRecordService.getChequeSerialNumberAsLong(any(String[].class))).thenReturn(null);
        when(this.mockOutdatedChequeRecordService.getOutdatedOnAsDate(any(String[].class))).thenReturn(null);

        this.converter.convert(new String[0]);

        verify(this.mockOutdatedChequeRecordService).getReferenceNumberAsLong(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getAmountAsFloat(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getCustomerName(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getChequeSerialNumberAsLong(any(String[].class));
        verify(this.mockOutdatedChequeRecordService).getOutdatedOnAsDate(any(String[].class));
    }
}