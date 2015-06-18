package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

/**
 * Filters bacs settlements files - <code>BO&lt;sequence&gt;.csv</code>
 */
@Component("bacsSettlementsFileFilter")
public class BacsSettlementsFileFilterImpl extends BaseImportFileFilter {
	
	protected static final String BACS_SETTLEMENTS_FILE_PATTERN = "BO\\d*.csv";
	
	@Override
	protected String getFileNamePattern() {
		return BACS_SETTLEMENTS_FILE_PATTERN;
	}

}
