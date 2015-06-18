package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.OutdatedChequeRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileOutdatedChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsToPence;

/**
 * Action to reconcile outdated cheques with cheques issued
 */
@Component("reconcileOutdatedChequeAction")
public class ReconcileOutdatedChequeActionImpl implements ImportRecordAction {

    @Autowired
    protected ReconcileOutdatedChequeService reconcileOutdatedChequeService;

    @Override
    public void handle(ImportRecord record) {
        OutdatedChequeRecord outdatedChequeRecord = (OutdatedChequeRecord) record;
        this.reconcileOutdatedChequeService.reconcileOutdatedCheque(outdatedChequeRecord.getReferenceNumber(),
                convertPoundsToPence(outdatedChequeRecord.getAmount()), outdatedChequeRecord.getCustomerName(),
                outdatedChequeRecord.getChequeSerialNumber(), outdatedChequeRecord.getOutdatedOn());
    }
}
