package com.novacroft.nemo.common.application_service.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.novacroft.nemo.common.application_service.CsvFileReaderService;
import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Read data from CSV file into list of String arrays service implementation
 */
@Service("csvFileReaderService")
public class CsvFileReaderServiceImpl implements CsvFileReaderService {
    public List<String[]> readDataFromFile(File csvFile) {
        assert (csvFile != null);
        CSVReader csvReader = null;
        List<String[]> data = new ArrayList<String[]>();
        try {
            csvReader = new CSVReader(new FileReader(csvFile));
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                data.add(nextLine);
            }
        } catch (IOException e) {
            throw new ApplicationServiceException(CommonPrivateError.FILE_READER_FAILED.message(), e);
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    throw new ApplicationServiceException(CommonPrivateError.FILE_READER_FAILED.message(), e);
                }
            }
        }
        return data;
    }
}
