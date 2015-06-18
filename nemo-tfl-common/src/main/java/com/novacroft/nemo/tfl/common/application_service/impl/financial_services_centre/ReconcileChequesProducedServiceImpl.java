package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileChequesProducedService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

/**
 * Reconcile cheques produced by the Financial Services Centre against cheques requested
 */
@Service("reconcileChequesProducedService")
public class ReconcileChequesProducedServiceImpl implements ReconcileChequesProducedService {

    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    
    @Override
    public void reconcileChequeProduced(Long settlementNumber, Integer amount, String customerName, Long chequeSerialNumber,
                                        Date printedOn) {
        ChequeSettlementDTO chequeSettlementDTO = this.chequeSettlementDataService.findBySettlementNumber(settlementNumber);
        validateOnlyOneChequeSettlementWithRequestedStatus(chequeSettlementDTO);
        validateChequeDetails(chequeSettlementDTO, amount, customerName);
        updateChequeSettlementWithChequeDetails(chequeSettlementDTO, chequeSerialNumber, printedOn);
    }

    protected void validateOnlyOneChequeSettlementWithRequestedStatus(ChequeSettlementDTO chequeSettlementDTO) {
        if (null == chequeSettlementDTO || !isStatusRequested(chequeSettlementDTO.getStatus())) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.REQUESTED_CHEQUE_MATCH_ERROR.message(), chequeSettlementDTO));
        }
    }

    protected void validateChequeDetails(ChequeSettlementDTO chequeSettlementDTO, Integer amount, String customerName) {
        if (doAmountAndPayeeNotMatch(chequeSettlementDTO.getAmount() * -1, chequeSettlementDTO.getPayeeName(), amount,
                customerName)) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.CHEQUE_DETAILS_MATCH_ERROR.message(), chequeSettlementDTO.getAmount() * -1,
                            amount, chequeSettlementDTO.getPayeeName(), customerName)
            );
        }
    }

    protected void updateChequeSettlementWithChequeDetails(ChequeSettlementDTO chequeSettlementDTO, Long chequeSerialNumber,
                                                           Date printedOn) {
        chequeSettlementDTO.setChequeSerialNumber(chequeSerialNumber);
        chequeSettlementDTO.setPrintedOn(printedOn);
        chequeSettlementDTO.setStatus(SettlementStatus.ISSUED.code());
        this.chequeSettlementDataService.createOrUpdate(chequeSettlementDTO);
    }

    protected boolean doAmountAndPayeeMatch(Integer amountRequested, String customerNameRequested, Integer amountProduced,
                                            String customerNameProduced) {
        return amountRequested.equals(amountProduced) && customerNameRequested.equals(customerNameProduced);
    }

    protected boolean doAmountAndPayeeNotMatch(Integer amountRequested, String customerNameRequested, Integer amountProduced,
                                               String customerNameProduced) {
        return !doAmountAndPayeeMatch(amountRequested, customerNameRequested, amountProduced, customerNameProduced);
    }

    protected boolean isStatusRequested(String status) {
        return SettlementStatus.REQUESTED.code().equals(status);
    }
}
