package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileBacsFailedPaymentService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
@Service("reconcileBacsFailedPaymentService")
public class ReconcileBacsFailedPaymentServiceImpl  extends BaseReconciliationService implements ReconcileBacsFailedPaymentService {

	@Autowired
	protected BACSSettlementDataService bacsSettlementDataService;
	
	@Override
	public void reconcileBacsFailedPayment(String bacsRejectCode, Integer amount,	Date paymentFailedDate, Long financialServiceReference) {
		  BACSSettlementDTO bacsSettlementDTO = this.bacsSettlementDataService.findByFinancialServicesReference(financialServiceReference);
	      validateRequestedStatus(bacsSettlementDTO);
	      validateAmount(bacsSettlementDTO, amount);
		  validateBacsRejectionCode(bacsRejectCode);
	      updateSettlementWithDetails(bacsSettlementDTO, bacsRejectCode, paymentFailedDate);		
	}

	protected void validateBacsRejectionCode(String code) {
		try {
			BACSRejectCodeEnum.valueOf(code);
		} catch (RuntimeException e) {
			throw new ApplicationServiceException(String.format(PrivateError.FAILED_BACS_PAYMENT_REJECT_CODE_ERROR.message(), BACSRejectCodeEnum.values().toString(),  code));
  
		}
	}

	protected void  validateAmount(BACSSettlementDTO bacsSettlementDTO, Integer amount){
		if(!bacsSettlementDTO.getAmount().equals(amount * -1)) {
			throw new ApplicationServiceException(String.format(PrivateError.BACS_FAILED_PAYMENT_MATCH_ERROR.message(), bacsSettlementDTO.getAmount() * -1, amount));
		}
		
	}

	protected  void validateRequestedStatus(BACSSettlementDTO bacsSettlementDTO) {
		if(!SettlementStatus.SUCCESSFUL.code().equals(bacsSettlementDTO.getStatus())) {
			throw new ApplicationServiceException(String.format(PrivateError.REQUESTED_BACS_PAYMENT_MATCH_ERROR.message(), bacsSettlementDTO.getStatus()));
		}
		
	}

	protected void updateSettlementWithDetails(final BACSSettlementDTO bacsSettlementDTO, final String bacsRejectCode, final  Date paymentfailedDate) {
		bacsSettlementDTO.setStatus(SettlementStatus.FAILED.code());
		bacsSettlementDTO.setPaymentFailedDate(paymentfailedDate);
		BACSRejectCodeEnum rejectCode = BACSRejectCodeEnum.valueOf(bacsRejectCode);
		bacsSettlementDTO.setFinancialServicesRejectCode(rejectCode);
        this.bacsSettlementDataService.createOrUpdate(bacsSettlementDTO);
    }	

}
