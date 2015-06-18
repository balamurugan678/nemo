package com.novacroft.nemo.tfl.batch.dispatcher.impl;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilterActionMap;

import java.util.List;

/**
 * CUBIC batch import file record base dispatcher.
 *
 * <p>
 * Dispatcher implementations should extend this class and populate the <code>actions</code> list.
 * </p>
 */
public abstract class BaseImportRecordDispatcher {
    protected List<ImportRecordFilterActionMap> actions = null;

    public void dispatch(ImportRecord record) {
        assert (this.actions != null);
        for (ImportRecordFilterActionMap filterActionMap : this.actions) {
            if (filterActionMap.matches(record)) {
                filterActionMap.handle(record);
            }
        }
    }
}
