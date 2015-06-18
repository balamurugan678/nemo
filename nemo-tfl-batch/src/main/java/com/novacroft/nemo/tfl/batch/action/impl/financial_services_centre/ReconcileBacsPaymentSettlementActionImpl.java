package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentSettledRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileBacsSettledPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsToPence;

@Component(value = "reconcileBacsPaymentSettlementAction")
public class ReconcileBacsPaymentSettlementActionImpl implements ImportRecordAction {

    @Autowired
    protected ReconcileBacsSettledPaymentService reconcileBacsPaymentSettledService;

    @Override
    public void handle(ImportRecord record) {
        BacsPaymentSettledRecord bacsSettledRecord = (BacsPaymentSettledRecord) record;
        this.reconcileBacsPaymentSettledService
                .reconcileBacsSettledPayment(bacsSettledRecord.getPaymentReferenceNumber(), bacsSettledRecord.getCustomerName(),
                        convertPoundsToPence(bacsSettledRecord.getAmount()), bacsSettledRecord.getPaymentDate(),
                        bacsSettledRecord.getFinancialServicesReference());
    }
}
