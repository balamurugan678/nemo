package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;
import org.springframework.stereotype.Component;

/**
 * Filters cheques produced files - <code>CO&lt;sequence&gt;.csv</code>
 */
@Component("outdatedChequesFileFilter")
public class OutdatedChequesFileFilterImpl extends BaseImportFileFilter {
    protected static final String OUTDATED_CHEQUES_FILE_PATTERN = "CO\\d*.csv";

    @Override
    protected String getFileNamePattern() {
        return OUTDATED_CHEQUES_FILE_PATTERN;
    }
}
