package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public abstract class BaseChequeReconcileService extends BaseReconciliationService {
	

	 protected void validateIssuedStatus(ChequeSettlementDTO chequeSettlementDTO) {
	        if (isNotIssuedStatus(chequeSettlementDTO)) {
	            throw new ApplicationServiceException(
	                    String.format(PrivateError.CHEQUE_SETTLEMENT_STATUS_ERROR.message(), SettlementStatus.ISSUED.code(),
	                            chequeSettlementDTO.getStatus())
	            );
	        }
	    }
	 
	 protected boolean isIssuedStatus(ChequeSettlementDTO chequeSettlementDTO) {
	        return SettlementStatus.ISSUED.code().equals(chequeSettlementDTO.getStatus());
	    }
	 
	 protected boolean isNotIssuedStatus(ChequeSettlementDTO chequeSettlementDTO) {
	        return !isIssuedStatus(chequeSettlementDTO);
	    }
}
