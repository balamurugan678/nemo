package com.novacroft.nemo.tfl.batch.record_filter.impl.product_fare_loader;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;

@Component("prePaidTicketRecordFilter")
public class PrePaidTicketRecordFilterImpl implements ImportRecordFilter {

	@Override
	public Boolean matches(ImportRecord record) {
		return true;
	}

}
