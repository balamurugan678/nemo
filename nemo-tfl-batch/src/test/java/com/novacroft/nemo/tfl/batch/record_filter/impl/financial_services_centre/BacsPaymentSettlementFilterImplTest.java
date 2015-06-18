package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentSettledRecord;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

public class BacsPaymentSettlementFilterImplTest {
	
	 
	 private BACSSettlementDataService   mockBacsSettlementDataService;
	 
	 private BacsPaymentSettledRecord    bacsPaymentSettlementRecord;
	 
	 private BacsPaymentSettlementFilterImpl filter;
	 

	 @Before
	 public void setUp(){
		 
		 filter = new BacsPaymentSettlementFilterImpl();
		 mockBacsSettlementDataService = mock(BACSSettlementDataService.class);
	 }
	 
	 
    @Test
	public void shouldMatchWhenRecordFound()  {
    	bacsPaymentSettlementRecord = new BacsPaymentSettledRecord(3l, null, null, null, null);
    	BACSSettlementDTO  settlementDTO = new BACSSettlementDTO();
    	when(mockBacsSettlementDataService.findBySettlementNumber(bacsPaymentSettlementRecord.getPaymentReferenceNumber())).thenReturn(settlementDTO);
    	filter.bacsSettlementDataService = mockBacsSettlementDataService;
    	assertTrue(filter.matches(bacsPaymentSettlementRecord));
	}
	 
	 
    @Test
   	public void shouldReturnFalseWhenNotFound()  {
       	bacsPaymentSettlementRecord = new BacsPaymentSettledRecord(3l, null, null, null, null);
       	when(mockBacsSettlementDataService.findBySettlementNumber(bacsPaymentSettlementRecord.getPaymentReferenceNumber())).thenReturn(null);
       	filter.bacsSettlementDataService = mockBacsSettlementDataService;
       	assertFalse(filter.matches(bacsPaymentSettlementRecord));
   	} 
	 
	 

}
