package com.novacroft.nemo.common.application_service;

import java.io.File;
import java.util.List;

/**
 * Read CSV file into list of String arrays service specification
 */
public interface CsvFileReaderService {
    List<String[]> readDataFromFile(File csvFile);
}
