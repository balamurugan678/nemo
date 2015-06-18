package com.novacroft.nemo.tfl.batch.action.impl.financial_services_centre;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsToPence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentFailedRecord;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ReconcileBacsFailedPaymentService;
@Component("reconcileBacsPaymentFailedAction")
public class ReconcileBacsPaymentFailedActionImpl implements ImportRecordAction {

	@Autowired
    protected ReconcileBacsFailedPaymentService reconcileBacsFailedPaymentService;

	@Override
	public void handle(ImportRecord record) {
		final BacsPaymentFailedRecord bacsPaymentFailedRecord =(BacsPaymentFailedRecord) record;
		reconcileBacsFailedPaymentService.reconcileBacsFailedPayment(bacsPaymentFailedRecord.getBacsRejectCode(), convertPoundsToPence(bacsPaymentFailedRecord.getAmount()), bacsPaymentFailedRecord.getFailedPaymentDate(), bacsPaymentFailedRecord.getFinancialServicesReference());
	}

}
