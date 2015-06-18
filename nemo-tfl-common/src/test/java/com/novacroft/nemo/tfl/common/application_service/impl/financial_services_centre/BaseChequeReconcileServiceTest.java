package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public class BaseChequeReconcileServiceTest {
	
	private BaseChequeReconcileService service;

    private ChequeSettlementDTO mockChequeSettlementDTO;

    @Before
    public void setUp() {
        this.service = mock(BaseChequeReconcileService.class);
        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);
    }
	
    
    @Test
	public void isNotIssuedStatusReturnsTrue() throws Exception {
    	when(this.service.isNotIssuedStatus(any(ChequeSettlementDTO.class))).thenCallRealMethod();
        when(this.service.isIssuedStatus(any(ChequeSettlementDTO.class))).thenReturn(false);
        assertTrue(this.service.isNotIssuedStatus(this.mockChequeSettlementDTO));
		
	}    

    @Test
	public void isNotIssuedStatusReturnsFalse() throws Exception {
    	when(this.service.isNotIssuedStatus(any(ChequeSettlementDTO.class))).thenCallRealMethod();
        when(this.service.isIssuedStatus(any(ChequeSettlementDTO.class))).thenReturn(true);
        assertFalse(this.service.isNotIssuedStatus(this.mockChequeSettlementDTO));
		
	} 
    
    @Test
    public void isIssuedStatusShouldReturnTrue() {
        when(this.service.isIssuedStatus(any(ChequeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockChequeSettlementDTO.getStatus()).thenReturn(SettlementStatus.ISSUED.code());
        assertTrue(this.service.isIssuedStatus(this.mockChequeSettlementDTO));
    }

    @Test
    public void isIssuedStatusShouldReturnFalse() {
        when(this.service.isIssuedStatus(any(ChequeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockChequeSettlementDTO.getStatus()).thenReturn(SettlementStatus.NEW.code());
        assertFalse(this.service.isIssuedStatus(this.mockChequeSettlementDTO));
    }
    
    public void shouldValidateIssuedStatus() {
        doCallRealMethod().when(this.service).validateIssuedStatus(any(ChequeSettlementDTO.class));
        when(this.service.isNotIssuedStatus(any(ChequeSettlementDTO.class))).thenReturn(false);
        this.service.validateIssuedStatus(this.mockChequeSettlementDTO);
        verify(this.service).isNotIssuedStatus(any(ChequeSettlementDTO.class));
    }

    @Test(expected = ApplicationServiceException.class)
    public void validateIssuedStatusShouldFail() {
        doCallRealMethod().when(this.service).validateIssuedStatus(any(ChequeSettlementDTO.class));
        when(this.service.isNotIssuedStatus(any(ChequeSettlementDTO.class))).thenReturn(true);
        this.service.validateIssuedStatus(this.mockChequeSettlementDTO);
        verify(this.service).isNotIssuedStatus(any(ChequeSettlementDTO.class));
    }

    
}
