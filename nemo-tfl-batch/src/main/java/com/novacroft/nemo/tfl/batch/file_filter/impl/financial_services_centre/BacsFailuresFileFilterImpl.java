package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

/**
 * Filters bacs failures files - <code>BF&lt;sequence&gt;.csv</code>
 */
@Component("bacsFailuresFileFilter")
public class BacsFailuresFileFilterImpl extends BaseImportFileFilter {

	protected static final String BACS_FAILURES_FILE_PATTERN = "BF\\d*.csv";
	
	@Override
	protected String getFileNamePattern() {
		return BACS_FAILURES_FILE_PATTERN;
	}

}
