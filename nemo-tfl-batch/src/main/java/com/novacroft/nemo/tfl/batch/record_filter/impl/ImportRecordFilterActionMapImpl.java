package com.novacroft.nemo.tfl.batch.record_filter.impl;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;

/**
 * CUBIC batch import file item action / filter association implementation.
 *
 * <p>
 * This class associates an item filter with an item action.  A dispatch will apply the filter to an item and call the action
 * when there's a match.
 * </p>
 */
public class ImportRecordFilterActionMapImpl implements ImportRecordFilterActionMap {
    protected ImportRecordFilter filter;
    protected ImportRecordAction action;

    public ImportRecordFilterActionMapImpl(ImportRecordFilter filter, ImportRecordAction action) {
        this.filter = filter;
        this.action = action;
    }

    @Override
    public Boolean matches(ImportRecord record) {
        return this.filter.matches(record);
    }

    @Override
    public void handle(ImportRecord record) {
        this.action.handle(record);
    }
}
