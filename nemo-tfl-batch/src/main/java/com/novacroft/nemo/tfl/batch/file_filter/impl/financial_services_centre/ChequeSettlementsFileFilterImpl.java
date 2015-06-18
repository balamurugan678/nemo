package com.novacroft.nemo.tfl.batch.file_filter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;
import org.springframework.stereotype.Component;

/**
 * Filters cheque settlements files - <code>HX&lt;sequence&gt;.csv</code>
 */
@Component("chequeSettlementsFileFilter")
public class ChequeSettlementsFileFilterImpl extends BaseImportFileFilter {
    protected static final String CHEQUE_SETTLEMENTS_FILE_PATTERN = "HX\\d*.csv";

    @Override
    protected String getFileNamePattern() {
        return CHEQUE_SETTLEMENTS_FILE_PATTERN;
    }
}
