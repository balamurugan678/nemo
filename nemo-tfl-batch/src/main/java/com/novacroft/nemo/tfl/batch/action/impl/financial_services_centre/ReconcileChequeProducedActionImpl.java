package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.ChequeProducedRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileChequesProducedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsToPence;

/**
 * Action to reconcile cheques produced with cheques requested
 */
@Component("reconcileChequeProducedAction")
public class ReconcileChequeProducedActionImpl implements ImportRecordAction {

    @Autowired
    protected ReconcileChequesProducedService reconcileChequesProducedService;

    @Override
    public void handle(ImportRecord record) {
        ChequeProducedRecord chequeProducedRecord = (ChequeProducedRecord) record;
        this.reconcileChequesProducedService.reconcileChequeProduced(chequeProducedRecord.getReferenceNumber(),
                convertPoundsToPence(chequeProducedRecord.getAmount()), chequeProducedRecord.getCustomerName(),
                chequeProducedRecord.getChequeSerialNumber(), chequeProducedRecord.getPrintedOn());
    }
}
