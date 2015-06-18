package com.novacroft.nemo.tfl.batch.record_filter;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

/**
 * CUBIC batch import record filter specification.
 *
 * <p>Filters are used to determine which actions to apply to a record.</p>
 */
public interface ImportRecordFilter {
    Boolean matches(ImportRecord record);
}
