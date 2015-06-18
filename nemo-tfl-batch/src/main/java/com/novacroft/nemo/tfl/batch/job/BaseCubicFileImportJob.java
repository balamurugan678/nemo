package com.novacroft.nemo.tfl.batch.job;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logComplete;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logInComplete;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logMessage;

import java.io.File;
import java.util.List;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.application_service.impl.CsvFileWithCheckSumReaderServiceImpl;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.batch.constant.JobParameterName;
import com.novacroft.nemo.tfl.batch.constant.LogMessage;
import com.novacroft.nemo.tfl.common.application_service.CubicChecksumVerificationService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * CUBIC batch file import base
 */
public abstract class BaseCubicFileImportJob extends BaseFileImportJob {
    protected static final Logger logger = LoggerFactory.getLogger(BaseCubicFileImportJob.class);

    @Autowired
    protected CsvFileWithCheckSumReaderServiceImpl csvFileWithCheckSumReaderService;
    @Autowired
    protected CubicChecksumVerificationService cubicChecksumVerificationService;

    protected String getImportRootDirectoryParameterName() {
        return JobParameterName.CUBIC_IMPORT_ROOT_DIR;
    }

    @Override
    protected void handleFile(File cubicFile, JobDataMap jobDataMap) {
        JobLogDTO log = initialiseLog(cubicFile, getJobName());
        try {
            if (isFileOkToProcess(cubicFile, log) && isChecksumVerified(cubicFile, log)) {
                List<String[]> data = this.csvFileWithCheckSumReaderService.readDataFromFile(cubicFile);
                if(isFileHasDataToProcess(data, log)) {
                	for (String[] record : data) {
                		handleRecord(record, log, null);
                	}
                    logMessage(log, StringUtil.EMPTY_STRING);
                	logComplete(log);
               } 
            } 
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logMessage(log, String.format(LogMessage.INVALID_FILEDATA.message(), log.getFileName()));
            logInComplete(log);
        } finally {
            finaliseLog(cubicFile, log);
        }
    } 

    protected boolean isChecksumVerified(File cubicFile, JobLogDTO log) {
        if (!this.cubicChecksumVerificationService.isChecksumVerified(cubicFile)) {
            logMessage(log, String.format(LogMessage.ALREADY_PROCESSED.message(), cubicFile.getName()));
            logInComplete(log);
            return false;
        }
        return true;
    }

    protected abstract String getJobName();
}
