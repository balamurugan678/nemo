package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

/**
 * Filters CUBIC Current Action List Files - <code>wap_<utsdate>_<sequence>.dat</code>
 */
public class AutoLoadsPerformedFileFilter extends BaseImportFileFilter {
    protected static final String AUTO_LOADS_PERFORMED_FILE_PATTERN = "wap_\\d*_\\d{4}.dat";

    @Override
    protected String getFileNamePattern() {
        return AUTO_LOADS_PERFORMED_FILE_PATTERN;
    }
}
