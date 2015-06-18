package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
public class ReconcileBacsSettledPaymentServiceImplTest {

	
	private ReconcileBacsPaymentSettledServiceImpl mockReconcileBacsPaymentSettledServiceImpl;
	
	private BACSSettlementDTO bacsSttlementDTO;
	
	
	
	@Before
	public void setUp()
	{
		mockReconcileBacsPaymentSettledServiceImpl = spy(new ReconcileBacsPaymentSettledServiceImpl());
		bacsSttlementDTO = new BACSSettlementDTO();
	}
	
	@Test
	public void validateRequestStatusMustReturnTrue() throws Exception {
		bacsSttlementDTO.setStatus(SettlementStatus.REQUESTED.code());
		mockReconcileBacsPaymentSettledServiceImpl.validateRequestedStatus(bacsSttlementDTO, null, null);
		
	}
	
	@Test(expected=ApplicationServiceException.class)
	public void validateRequestStatusMustThrowException() throws Exception {
		
		bacsSttlementDTO.setStatus(SettlementStatus.FAILED.code());
		mockReconcileBacsPaymentSettledServiceImpl.validateRequestedStatus(bacsSttlementDTO, "name", 10);
		
	}
}
