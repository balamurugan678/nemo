package com.novacroft.nemo.tfl.batch.dispatcher;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

/**
 * The dispatcher controls which actions should be executed against a record
 */
public interface ImportRecordDispatcher {
    void initialiseActions();

    void dispatch(ImportRecord record);
}
