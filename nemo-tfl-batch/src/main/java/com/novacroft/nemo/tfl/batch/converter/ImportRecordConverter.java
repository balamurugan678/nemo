package com.novacroft.nemo.tfl.batch.converter;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;

/**
 * Convert a "raw" import record to a bean specification
 */
public interface ImportRecordConverter {
    ImportRecord convert(String[] record);

    String toCsv(String[] record);
}
