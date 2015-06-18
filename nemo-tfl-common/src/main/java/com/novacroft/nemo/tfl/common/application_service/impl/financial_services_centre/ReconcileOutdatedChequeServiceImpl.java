package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileOutdatedChequeService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Reconcile outdated cheques from the Financial Services Centre (FSC) against cheques issued
 */
@Service("reconcileOutdatedChequeService")
public class ReconcileOutdatedChequeServiceImpl extends BaseChequeReconcileService implements ReconcileOutdatedChequeService {

    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    @Override
    public void reconcileOutdatedCheque(Long orderNumber, Integer amount, String customerName, Long chequeSerialNumber,
                                        Date outdatedOn) {
        ChequeSettlementDTO chequeSettlementDTO = this.chequeSettlementDataService.findByChequeSerialNumber(chequeSerialNumber);
        assert (chequeSettlementDTO != null);
        validateIssuedStatus(chequeSettlementDTO);
        validatePayeeAndAmount(chequeSettlementDTO, customerName, amount,  PrivateError.CHEQUE_DETAILS_MATCH_ERROR);
        updateSettlementWithOutdatedDetails(chequeSettlementDTO, outdatedOn);
    }

    protected void updateSettlementWithOutdatedDetails(ChequeSettlementDTO chequeSettlementDTO, Date outdatedOn) {
        chequeSettlementDTO.setOutdatedOn(outdatedOn);
        chequeSettlementDTO.setStatus(SettlementStatus.OUTDATED.code());
        this.chequeSettlementDataService.createOrUpdate(chequeSettlementDTO);
    }
}
