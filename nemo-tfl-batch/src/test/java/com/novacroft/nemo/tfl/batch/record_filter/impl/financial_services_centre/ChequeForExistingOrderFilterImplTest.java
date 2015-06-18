package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeProducedRecord;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public class ChequeForExistingOrderFilterImplTest {

    private ChequeForExistingOrderFilterImpl filter;
    private ChequeSettlementDataService mockChequeSettlementDataService;

    private ChequeProducedRecord mockChequeProducedRecord;
    private ChequeSettlementDTO mockChequeSettlementDTO;

    private List<ChequeSettlementDTO> chequeSettlementDTOList;

    @Before
    public void setUp() {
        this.filter = mock(ChequeForExistingOrderFilterImpl.class, CALLS_REAL_METHODS);
        this.mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        this.filter.chequeSettlementDataService = this.mockChequeSettlementDataService;

        this.mockChequeProducedRecord = mock(ChequeProducedRecord.class);
        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);

        this.chequeSettlementDTOList = new ArrayList<ChequeSettlementDTO>();
        this.chequeSettlementDTOList.add(this.mockChequeSettlementDTO);
    }

    @Test
    public void matchesShouldReturnTrue() {
        when(this.mockChequeSettlementDataService.findBySettlementNumber((anyLong()))).thenReturn(mockChequeSettlementDTO);
        assertTrue(this.filter.matches(this.mockChequeProducedRecord));
    }

    @Test
    public void matchesShouldReturnFalse() {
        when(this.mockChequeSettlementDataService.findAllByOrderNumber(anyLong())).thenReturn(Collections.EMPTY_LIST);
        assertFalse(this.filter.matches(this.mockChequeProducedRecord));
    }
}