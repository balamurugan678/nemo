package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
@RunWith(MockitoJUnitRunner.class)
public class ReconcileBacsFailedPaymentServiceImplTest {

    private static final long FINANACIAL_SERVICES_REFERENCE_NUMBER = 1000L;

	private static final String REJECT_CODE = "R";

	private ReconcileBacsFailedPaymentServiceImpl mockReconcileBacsFailedPaymentServiceImpl;
	@Mock
	private BACSSettlementDataService mockBACSSettlementDataService;
	
	private BACSSettlementDTO bacsSttlementDTO;
	
	@Before
	public void setUp()
	{
		mockReconcileBacsFailedPaymentServiceImpl = spy(new ReconcileBacsFailedPaymentServiceImpl());
		mockReconcileBacsFailedPaymentServiceImpl.bacsSettlementDataService = mockBACSSettlementDataService;
		bacsSttlementDTO = new BACSSettlementDTO();
	}
	
	@Test
	public void validateRequestStatusMustPass() throws Exception {
		bacsSttlementDTO.setStatus(SettlementStatus.SUCCESSFUL.code());
		mockReconcileBacsFailedPaymentServiceImpl.validateRequestedStatus(bacsSttlementDTO);
		
	}
	
	@Test(expected=ApplicationServiceException.class)
	public void validateRequestStatusMustThrowException() throws Exception {
		
		bacsSttlementDTO.setStatus(SettlementStatus.FAILED.code());
		mockReconcileBacsFailedPaymentServiceImpl.validateRequestedStatus(bacsSttlementDTO);
		
		
	}	
	
	
	@Test
	public void validateAMountSameValueMustPass() throws Exception {
		bacsSttlementDTO.setStatus(SettlementStatus.REQUESTED.code());
		bacsSttlementDTO.setAmount(-50);
		mockReconcileBacsFailedPaymentServiceImpl.validateAmount(bacsSttlementDTO, 50);
		
	}
	
	@Test(expected=ApplicationServiceException.class)
	public void validateAmountThrowsException() throws Exception {
		bacsSttlementDTO.setStatus(SettlementStatus.REQUESTED.code());
		bacsSttlementDTO.setAmount(-50);
		mockReconcileBacsFailedPaymentServiceImpl.validateAmount(bacsSttlementDTO, 0);
		
	}
	
	
	@Test
	public void updateSettlementDetailsMustSetData()  throws Exception {
		Date now = new Date();
		bacsSttlementDTO = mock(BACSSettlementDTO.class);
        doNothing().when(bacsSttlementDTO).setFinancialServicesRejectCode(BACSRejectCodeEnum.valueOf(REJECT_CODE));
        doNothing().when(bacsSttlementDTO).setStatus(SettlementStatus.FAILED.code());
        doNothing().when(bacsSttlementDTO).setPaymentFailedDate(now);
        when(mockBACSSettlementDataService.createOrUpdate(bacsSttlementDTO)).thenReturn(bacsSttlementDTO);
        
		
        mockReconcileBacsFailedPaymentServiceImpl.updateSettlementWithDetails(bacsSttlementDTO, REJECT_CODE, now);
		verify(bacsSttlementDTO).setFinancialServicesRejectCode(BACSRejectCodeEnum.valueOf(REJECT_CODE));
		verify(bacsSttlementDTO).setStatus(SettlementStatus.FAILED.code());
		verify(bacsSttlementDTO).setPaymentFailedDate(now);
		verify(mockBACSSettlementDataService).createOrUpdate(bacsSttlementDTO);
	}
	
	
	@Test
	public void shouldUpdateSettlements() {
		bacsSttlementDTO = mock(BACSSettlementDTO.class);
		Date now = new Date();
		when(mockBACSSettlementDataService.findByFinancialServicesReference(anyLong())).thenReturn(bacsSttlementDTO);
		doNothing().when(mockReconcileBacsFailedPaymentServiceImpl).validateRequestedStatus(bacsSttlementDTO);
		doNothing().when(mockReconcileBacsFailedPaymentServiceImpl).validateBacsRejectionCode(REJECT_CODE);
		doNothing().when(mockReconcileBacsFailedPaymentServiceImpl).validateAmount(bacsSttlementDTO, 3);
		doNothing().when(mockReconcileBacsFailedPaymentServiceImpl).updateSettlementWithDetails(bacsSttlementDTO, REJECT_CODE,  now);
		
		when(mockBACSSettlementDataService.findByFinancialServicesReference(FINANACIAL_SERVICES_REFERENCE_NUMBER)).thenReturn(bacsSttlementDTO);
		
		mockReconcileBacsFailedPaymentServiceImpl.reconcileBacsFailedPayment(REJECT_CODE, 3, now, FINANACIAL_SERVICES_REFERENCE_NUMBER);
		
		verify(mockReconcileBacsFailedPaymentServiceImpl).validateRequestedStatus(bacsSttlementDTO);
		verify(mockReconcileBacsFailedPaymentServiceImpl).validateBacsRejectionCode(anyString());
		verify(mockReconcileBacsFailedPaymentServiceImpl).validateAmount(bacsSttlementDTO, 3);
		verify(mockReconcileBacsFailedPaymentServiceImpl).updateSettlementWithDetails(bacsSttlementDTO, REJECT_CODE,  now);

		
	}
	
}
