package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileClearedChequeService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Reconcile cheques cleared from the Financial Services Centre (FSC) against cheques produced
 */
@Service("reconcileClearedChequeService")
public class ReconcileClearedChequeServiceImpl extends BaseChequeReconcileService implements ReconcileClearedChequeService {

    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    @Override
    public void reconcileClearedCheque(Long chequeSerialNumber, Long paymentReferenceNumber, String customerName,
                                       Date clearedOn, Integer amount) {
        ChequeSettlementDTO chequeSettlementDTO = this.chequeSettlementDataService.findByChequeSerialNumber(chequeSerialNumber);
        validateIssuedStatus(chequeSettlementDTO);
        validatePayeeAndAmount(chequeSettlementDTO, customerName, amount, PrivateError.CHEQUE_DETAILS_MATCH_ERROR);
        updateSettlementWithClearanceDetails(chequeSettlementDTO, paymentReferenceNumber, clearedOn);
    }

    protected void updateSettlementWithClearanceDetails(ChequeSettlementDTO chequeSettlementDTO, Long paymentReferenceNumber,
                                                        Date clearedOn) {
        chequeSettlementDTO.setPaymentReference(paymentReferenceNumber);
        chequeSettlementDTO.setClearedOn(clearedOn);
        chequeSettlementDTO.setStatus(SettlementStatus.CLEARED.code());
        this.chequeSettlementDataService.createOrUpdate(chequeSettlementDTO);
    }
}
