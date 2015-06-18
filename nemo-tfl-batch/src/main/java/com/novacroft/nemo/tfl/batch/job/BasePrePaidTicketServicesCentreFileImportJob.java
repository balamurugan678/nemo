package com.novacroft.nemo.tfl.batch.job;

import com.novacroft.nemo.common.application_service.CsvFileReaderService;
import com.novacroft.nemo.tfl.batch.constant.JobParameterName;
import com.novacroft.nemo.tfl.batch.constant.LogMessage;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logInComplete;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logMessage;

public abstract class BasePrePaidTicketServicesCentreFileImportJob extends	BaseFileImportJob {
		
    @Autowired
    protected CsvFileReaderService csvFileReaderService;

    @Override
    protected String getImportRootDirectoryParameterName() {
        return JobParameterName.PRE_PAID_TICKETS_ERVICES_CENTRE_IMPORT_ROOT_DIR;
    }

    @Override
    protected void handleFile(File importFile, JobDataMap jobDataMap) {
        JobLogDTO log = initialiseLog(importFile, getJobName());

        try {
            if (isFileOkToProcess(importFile, log)) {
                List<String[]> data = this.csvFileReaderService.readDataFromFile(importFile);
                if(isFileHasDataToProcess(data, log)) {
                	data.remove(0);
                	getBatchSpecialisedService().processBatchUpdate(data, log, jobDataMap);
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
 

    @Override
    protected boolean isFileOkToProcess(File importFile, JobLogDTO log) {
        return (isNotProcessLocked(importFile, log));
    }
    
    
    @Override
    protected void handleRecord(String[] record, JobLogDTO log, JobDataMap jobDataMap) {
            ImportRecord importRecord = convertRecord(record);
        preProcessRecord(importRecord, jobDataMap);
            processRecord(importRecord, log);
    }

    @Override
    protected void processRecord(ImportRecord importRecord, JobLogDTO log) {
    	 getImportRecordDispatcher().dispatch(importRecord);
    }
    
    @Override
    protected void preProcessRecord(ImportRecord importRecord, JobDataMap jobDataMap) {
        PrePaidTicketRecord prePaidTicketRecord = (PrePaidTicketRecord) importRecord;
        prePaidTicketRecord.setPriceEffectiveDate((Date) jobDataMap.get("priceEffectiveDate"));
    }
    
    @Override
    public boolean isFileHasDataToProcess(List<String[]> data, JobLogDTO log) {
    	if (data == null ||  (data.size() <= 1)) {
            logMessage(log, String.format(LogMessage.EMPTY_FILE.message(), log.getFileName()));
            return false;
        }
        return true;    
    }
    protected abstract String getJobName();
		

    protected abstract BatchJobUpdateStrategyService<?> getBatchSpecialisedService();
}
