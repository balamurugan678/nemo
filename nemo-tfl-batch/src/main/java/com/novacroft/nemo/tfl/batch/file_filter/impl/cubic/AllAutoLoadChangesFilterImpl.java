package com.novacroft.nemo.tfl.batch.file_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import org.springframework.stereotype.Component;

/**
 * Filters all records from the CUBIC batch import auto load changes file that have errors.
 */
@Component("allAutoLoadChangesFilter")
public class AllAutoLoadChangesFilterImpl implements ImportRecordFilter {
    @Override
    public Boolean matches(ImportRecord record) {
        return true;
    }
}
