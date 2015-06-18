package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.file_filter.impl.BaseImportFileFilter;

/**
 * Filters CUBIC Ad-Hoc Distributions Files - <code>wad_&lt;UTSdate&gt;_&lt;sequence&gt;.dat</code>
 */
public class AdHocDistributionsFileFilter extends BaseImportFileFilter {
    protected static final String AD_HOC_DISTRIBUTIONS_FILE_PATTERN = "wad_\\d*_\\d{4}.dat";

    @Override
    protected String getFileNamePattern() {
        return AD_HOC_DISTRIBUTIONS_FILE_PATTERN;
    }
}
