package com.novacroft.nemo.tfl.batch.job;

import com.novacroft.nemo.common.application_service.CsvFileReaderService;
import com.novacroft.nemo.tfl.batch.constant.JobParameterName;
import com.novacroft.nemo.tfl.batch.constant.LogMessage;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logInComplete;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logMessage;

/**
 * Financial Services Centre (FSC) file import base
 */
public abstract class BaseFinancialServicesCentreFileImportJob extends BaseFileImportJob {

    @Autowired
    protected CsvFileReaderService csvFileReaderService;

    @Override
    protected String getImportRootDirectoryParameterName() {
        return JobParameterName.FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR;
    }

    @Override
    protected void handleFile(File importFile, JobDataMap jobDataMap) {
        JobLogDTO log = initialiseLog(importFile, getJobName());
        try {
            if (isFileOkToProcess(importFile, log)) {
                List<String[]> data = this.csvFileReaderService.readDataFromFile(importFile);
                if(isFileHasDataToProcess(data, log)) {
                	for (String[] record : data) {
                		handleRecord(record, log, null);
                	}
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logMessage(log, String.format(LogMessage.INVALID_FILEDATA.message(), log.getFileName()));
            logInComplete(log);
        } finally {
            finaliseLog(importFile, log);
        }
    }

    protected abstract String getJobName();
}
