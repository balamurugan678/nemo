package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileBacsSettledPaymentService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "reconcileBacsPaymentSettledService")
public class ReconcileBacsPaymentSettledServiceImpl extends BaseReconciliationService
        implements ReconcileBacsSettledPaymentService {

    @Autowired
    protected BACSSettlementDataService bacsSettlementDataService;

    @Override
    public void reconcileBacsSettledPayment(Long paymentReferenceNumber, String customerName, Integer amount,
                                            Date settlementDate, Long financialServiceReference) {
        BACSSettlementDTO bacsSettlementDTO = this.bacsSettlementDataService.findBySettlementNumber(paymentReferenceNumber);
        validateRequestedStatus(bacsSettlementDTO, customerName, amount);
        validatePayeeAndAmount(bacsSettlementDTO, customerName, amount, PrivateError.BACS_SETTLED_PAYMENT_MATCH_ERROR);
        updateSettlementWithSettledDetails(bacsSettlementDTO, financialServiceReference, settlementDate);
    }

    protected void validateRequestedStatus(BACSSettlementDTO bacsSettlementDTO, String customerName, Integer amount) {
        if (!SettlementStatus.REQUESTED.code().equals(bacsSettlementDTO.getStatus())) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.BACS_SETTLED_PAYMENT_MATCH_ERROR.message(), bacsSettlementDTO.getAmount(),
                            amount, bacsSettlementDTO.getPayeeName(), customerName));
        }
    }

    protected void updateSettlementWithSettledDetails(final BACSSettlementDTO bacsSettlementDTO,
                                                      final Long paymentReferenceNumber, final Date settlementDate) {
        bacsSettlementDTO.setPaymentReference(paymentReferenceNumber);
        bacsSettlementDTO.setStatus(SettlementStatus.SUCCESSFUL.code());
        bacsSettlementDTO.setSettlementDate(settlementDate);
        this.bacsSettlementDataService.createOrUpdate(bacsSettlementDTO);
    }
}
