package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;
import org.springframework.stereotype.Component;

/**
 * Filters cheques produced files - <code>RC&lt;sequence&gt;.csv</code>
 */
@Component("chequesProducedFileFilter")
public class ChequesProducedFileFilterImpl extends BaseImportFileFilter {
    protected static final String CHEQUES_PRODUCED_FILE_PATTERN = "RC\\d*.csv";

    @Override
    protected String getFileNamePattern() {
        return CHEQUES_PRODUCED_FILE_PATTERN;
    }
}
