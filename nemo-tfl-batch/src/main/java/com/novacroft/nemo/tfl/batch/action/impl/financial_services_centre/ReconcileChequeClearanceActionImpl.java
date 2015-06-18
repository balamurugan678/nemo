package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeSettledRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileClearedChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsToPence;

/**
 * Action to reconcile cheques settlements (clearances) with cheques produced
 */
@Component("reconcileChequeClearanceAction")
public class ReconcileChequeClearanceActionImpl implements ImportRecordAction {

    @Autowired
    protected ReconcileClearedChequeService reconcileClearedChequeService;

    @Override
    public void handle(ImportRecord record) {
        ChequeSettledRecord chequeSettledRecord = (ChequeSettledRecord) record;
        this.reconcileClearedChequeService.reconcileClearedCheque(chequeSettledRecord.getChequeSerialNumber(),
                chequeSettledRecord.getPaymentReferenceNumber(), chequeSettledRecord.getCustomerName(),
                chequeSettledRecord.getClearedOn(), convertPoundsToPence(chequeSettledRecord.getAmount()));
    }
}
