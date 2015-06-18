package com.novacroft.nemo.tfl.batch.converter.impl;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.StringWriter;

/**
 * Base Import record converter
 */
public class BaseConverter {
    public String toCsv(String[] record) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
        csvWriter.writeNext(record);
        return stringWriter.toString();
    }
}
