package com.novacroft.nemo.tfl.batch.file_filter.impl.product_fare_loader;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

@Component("prePaidTicketFileFilter")
public class PrePaidTicketFileFilterImpl extends BaseImportFileFilter {

	protected static final String PRE_PAID_DATA_FILE_PATTERN = "PPD\\d*.csv";
	
	@Override
	protected String getFileNamePattern() {
		return PRE_PAID_DATA_FILE_PATTERN;
	}

}
