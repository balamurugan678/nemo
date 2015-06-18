package com.novacroft.nemo.tfl.batch.job;

import com.novacroft.nemo.common.application_service.FileFinderService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.batch.constant.LogMessage;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.common.application_service.JobLogService;
import com.novacroft.nemo.tfl.common.application_service.ProcessLockService;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Locale;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.*;
import static org.springframework.validation.ValidationUtils.invokeValidator;

/**
 * Base class for file import jobs
 */
public abstract class BaseFileImportJob {
    public static final String LOCK_FILE_EXTENSION = ".locked";
    protected static final Logger logger = LoggerFactory.getLogger(BaseFileImportJob.class);
    protected static final String BRITISH_ENGLISH_LOCALE_CODE = "en_GB";

    @Autowired
    protected FileFinderService fileFinderService;
    @Autowired
    protected ProcessLockService processLockService;
    @Autowired
    protected JobLogService jobLogService;
    @Autowired
    protected JobLogDataService jobLogDataService;
    @Autowired
    protected MessageSource messageSource;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String importRootDirectory = jobDataMap.getString(getImportRootDirectoryParameterName());
        List<File> filesToProcess = this.fileFinderService.findFiles(new File(importRootDirectory), getImportFileFilter());
        for (File importFile : filesToProcess) {
            handleFile(importFile, jobDataMap);
        }
    }

    protected void handleRecord(String[] record, JobLogDTO log, JobDataMap jobDataMap) {
        if (isRecordValid(record, log)) {
            ImportRecord importRecord = convertRecord(record);
            preProcessRecord(importRecord, jobDataMap);
            processRecord(importRecord, log);
        }
    }

    protected void preProcessRecord(ImportRecord importRecord, JobDataMap jobDataMap) {
    }

    protected boolean isRecordValid(String[] record, JobLogDTO log) {
        Errors errors = new BeanPropertyBindingResult(record, "");
        invokeValidator(getImportRecordValidator(), record, errors);
        if (errors.hasErrors()) {
            logMessage(log, getImportRecordConverter().toCsv(record));
            logInComplete(log);
            for (ObjectError error : errors.getAllErrors()) {
                logMessage(log, messageSource.getMessage(error, new Locale(BRITISH_ENGLISH_LOCALE_CODE)));
            }
        }
        return !errors.hasErrors();
    }

    protected ImportRecord convertRecord(String[] record) {
        return getImportRecordConverter().convert(record);
    }

    protected void processRecord(ImportRecord importRecord, JobLogDTO log) {
        try {
            getImportRecordDispatcher().dispatch(importRecord);
        } catch (ApplicationServiceException e) {
            logMessage(log, importRecord.toString());
            logMessage(log, e.getMessage());
            logInComplete(log);
        }
    }

    protected boolean isFileOkToProcess(File importFile, JobLogDTO log) {
        return (isNotFileAlreadyProcessed(importFile, log) && isNotProcessLocked(importFile, log));
    }

    protected boolean isNotFileAlreadyProcessed(File importFile, JobLogDTO log) {
        if (this.jobLogService.isFileAlreadyProcessed(importFile.getName())) {
            logMessage(log, String.format(LogMessage.ALREADY_PROCESSED.message(), importFile.getName()));
            logInComplete(log);
            return false;
        }
        return true;
    }

    protected boolean isNotProcessLocked(File importFile, JobLogDTO log) {
        if (this.processLockService.isLocked(getLockFileDirectory(importFile), getLockFileName(importFile))) {
            logMessage(log, String.format(LogMessage.PROCESS_LOCKED.message(), getLockFileName(importFile)));
            logInComplete(log);
            return false;
        }
        this.processLockService.acquireLock(getLockFileDirectory(importFile), getLockFileName(importFile));
        return true;
    }

    protected JobLogDTO initialiseLog(File importFile, String jobName) {
        String message = String.format(LogMessage.FILE_ANNOUNCEMENT.message(), importFile.getName());
        logger.info(message);
        JobLogDTO log = createLog(jobName, importFile.getName());
        logMessage(log, message);
        return log;
    }

    protected void finaliseLog(File importFile, JobLogDTO log) {
        logEnd(log);
        markFileProcessed(importFile, log);
        this.processLockService.releaseLock(getLockFileDirectory(importFile), getLockFileName(importFile));
        this.jobLogDataService.create(log);
    }

    protected void markFileProcessed(File importFile, JobLogDTO log) {
        importFile.renameTo(new File(importFile.getAbsolutePath() + "." + log.getStatus()));
    }

    protected File getLockFileDirectory(File importFile) {
        return importFile.getParentFile();
    }

    protected String getLockFileName(File importFile) {
        return importFile.getName() + LOCK_FILE_EXTENSION;
    }

    public boolean isFileHasDataToProcess(List<String[]> data, JobLogDTO log) {
    	if ((null != data) && (data.size()==0)) {
            logMessage(log, String.format(LogMessage.EMPTY_FILE.message(), log.getFileName()));
            return false;
        }
        return true;    
    }
 
    protected abstract ImportRecordDispatcher getImportRecordDispatcher();

    protected abstract Validator getImportRecordValidator();

    protected abstract ImportRecordConverter getImportRecordConverter();

    protected abstract String getImportRootDirectoryParameterName();

    protected abstract FileFilter getImportFileFilter();

    protected abstract void handleFile(File importFile, JobDataMap jobDataMap);
}
