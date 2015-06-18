package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentFailedRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;

/**
 * Filters Bacs Failed  records for BACS Payment Request that exist
 */
@Component("bacsFailedPaymentsFilter")
public class BacsPaymentFailedFilterImpl implements ImportRecordFilter {

	@Autowired
    protected BACSSettlementDataService bacsSettlementDataService;
	
	@Override
	public Boolean matches(ImportRecord record) {
		BacsPaymentFailedRecord bacsPaymentFailedRecord = (BacsPaymentFailedRecord) record;
		return bacsSettlementDataService.findByFinancialServicesReference(bacsPaymentFailedRecord.getFinancialServicesReference()) != null;
	}

}
