package com.novacroft.nemo.tfl.batch.record_filter.impl.financial_services_centre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.financial_services_centre.BacsPaymentSettledRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;

/**
 * Filters Bacs settlement  records for BacsSttlement that exist
 */
@Component("bacsPaymentSettlementFilter")
public class BacsPaymentSettlementFilterImpl implements ImportRecordFilter  {

	@Autowired
    protected BACSSettlementDataService   bacsSettlementDataService;
	
	@Override
	public Boolean matches(ImportRecord record) {
		BacsPaymentSettledRecord bacsPaymentSettledRecord = (BacsPaymentSettledRecord) record;
		return bacsSettlementDataService.findBySettlementNumber(bacsPaymentSettledRecord.getPaymentReferenceNumber()) != null;
	}

}
