package com.novacroft.nemo.tfl.batch.action.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

/**
 * CUBIC batch import file record action specification.
 *
 * <p>An action is performed when an appropriate record is encountered.</p>
 */
public interface ImportRecordAction {
    void handle(ImportRecord record);
}
