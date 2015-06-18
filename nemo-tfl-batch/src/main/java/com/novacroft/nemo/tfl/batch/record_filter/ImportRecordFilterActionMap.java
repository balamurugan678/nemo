package com.novacroft.nemo.tfl.batch.record_filter;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

/**
 * Associate a filter with an action.
 */
public interface ImportRecordFilterActionMap {
    Boolean matches(ImportRecord record);

    void handle(ImportRecord record);
}
