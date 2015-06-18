package com.novacroft.nemo.common.application_service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

/**
 * Read data from CSV file into list of String arrays service implementation
 *
 * <p>Last line of file is assumed to contain the check sum value.  Check sum is not included in the returned data.</p>
 */
@Service("csvFileWithCheckSumReaderService")
public class CsvFileWithCheckSumReaderServiceImpl extends CsvFileReaderServiceImpl {

    protected static final Logger logger = LoggerFactory.getLogger(CsvFileWithCheckSumReaderServiceImpl.class);

    public List<String[]> readDataFromFile(File csvFile) {
        List<String[]> data = super.readDataFromFile(csvFile);
        if (data.size() < 1) {
            throw new ApplicationServiceException(CommonPrivateError.CHECKSUM_MISSING.message());
        }
        logger.debug("File :" + csvFile.getName() + "; has total number of lines (including checksum):" + data.size());
        removeChecksumFromData(data);
        logger.debug("File :" + csvFile.getName() + "; has total number of lines (excluding checksum):" + data.size());
        return data;
    }

    protected void removeChecksumFromData(List<String[]> data) {
        int dataSize = data.size();
        if (dataSize > 0) {
            int lastLineIndex = dataSize - 1;
            logger.debug("Line is assumed to be part of the checksum, removing it.");
            data.remove(lastLineIndex);
            dataSize = data.size();
            if (dataSize > 0) {
                lastLineIndex = dataSize - 1;
                String[] recordElements = data.get(lastLineIndex);
                if (null != recordElements && recordElements.length == 1) {
                    logger.debug("Line is assumed to be part of the checksum (new line, cr, form feed), remove this entry" + Arrays.toString(recordElements));
                    data.remove(lastLineIndex);
                }
            }
        }
    }


}
