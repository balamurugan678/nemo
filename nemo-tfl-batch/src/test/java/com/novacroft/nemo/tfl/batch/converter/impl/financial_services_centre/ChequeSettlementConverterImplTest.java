package com.novacroft.nemo.tfl.batch.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeSettlementRecordService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ChequeSettlementConverterImplTest {
    private ChequeSettlementConverterImpl converter;
    private ChequeSettlementRecordService mockChequeSettlementRecordService;

    @Before
    public void setUp() {
        this.converter = mock(ChequeSettlementConverterImpl.class);
        this.mockChequeSettlementRecordService = mock(ChequeSettlementRecordService.class);
        this.converter.chequeSettlementRecordService = this.mockChequeSettlementRecordService;
    }

    @Test
    public void shouldConvert() {
        when(this.converter.convert(any(String[].class))).thenCallRealMethod();

        when(this.mockChequeSettlementRecordService.getChequeSerialNumberAsLong(any(String[].class))).thenReturn(null);
        when(this.mockChequeSettlementRecordService.getPaymentReferenceNumberAsLong(any(String[].class))).thenReturn(null);
        when(this.mockChequeSettlementRecordService.getCustomerName(any(String[].class))).thenReturn(null);
        when(this.mockChequeSettlementRecordService.getClearedOnAsDate(any(String[].class))).thenReturn(null);
        when(this.mockChequeSettlementRecordService.getCurrency(any(String[].class))).thenReturn(null);
        when(this.mockChequeSettlementRecordService.getAmountAsFloat(any(String[].class))).thenReturn(null);

        this.converter.convert(new String[0]);

        verify(this.mockChequeSettlementRecordService).getChequeSerialNumberAsLong(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getPaymentReferenceNumberAsLong(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getCustomerName(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getClearedOnAsDate(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getCurrency(any(String[].class));
        verify(this.mockChequeSettlementRecordService).getAmountAsFloat(any(String[].class));
    }
}
