package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

/**
 * Filters CUBIC Current Action List Files - <code>wac_<utsdate>_<sequence>.dat</code>
 */
public class AutoLoadChangesFileFilter extends BaseImportFileFilter {
    protected static final String AUTO_LOAD_CHANGES_FILE_PATTERN = "wac_\\d*_\\d{4}.dat";

    @Override
    protected String getFileNamePattern() {
        return AUTO_LOAD_CHANGES_FILE_PATTERN;
    }
}
