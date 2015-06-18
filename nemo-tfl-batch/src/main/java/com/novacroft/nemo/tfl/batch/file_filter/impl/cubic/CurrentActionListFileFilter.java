package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

/**
 * Filters CUBIC Current Action List Files - <code>wca_<utsdate>_<sequence>.dat</code>
 */
public class CurrentActionListFileFilter extends BaseImportFileFilter {
    protected static final String CURRENT_ACTION_LIST_FILE_PATTERN = "wca_\\d*_\\d{4}.dat";

    @Override
    protected String getFileNamePattern() {
        return CURRENT_ACTION_LIST_FILE_PATTERN;
    }
}
