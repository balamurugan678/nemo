package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeSettledRecord;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class ClearanceForExistingChequeFilterImplTest {
    private ClearanceForExistingChequeFilterImpl filter;
    private ChequeSettlementDataService mockChequeSettlementDataService;

    private ChequeSettledRecord mockChequeSettledRecord;
    private ChequeSettlementDTO mockChequeSettlementDTO;

    @Before
    public void setUp() {
        this.filter = mock(ClearanceForExistingChequeFilterImpl.class, CALLS_REAL_METHODS);
        this.mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        this.filter.chequeSettlementDataService = this.mockChequeSettlementDataService;

        this.mockChequeSettledRecord = mock(ChequeSettledRecord.class);
        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);

    }

    @Test
    public void matchesShouldReturnTrue() {
        when(this.mockChequeSettlementDataService.findByChequeSerialNumber(anyLong())).thenReturn(this.mockChequeSettlementDTO);
        assertTrue(this.filter.matches(this.mockChequeSettledRecord));
    }

    @Test
    public void matchesShouldReturnFalse() {
        when(this.mockChequeSettlementDataService.findByChequeSerialNumber(anyLong())).thenReturn(null);
        assertFalse(this.filter.matches(this.mockChequeSettledRecord));
    }
}