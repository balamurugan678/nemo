package com.novacroft.nemo.tfl.batch.job.financial_services_centre;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.job.BaseFinancialServicesCentreFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import java.io.FileFilter;

/**
 * Import Financial Services Centre (FSC) cheques produced file (I-FSC-2b)
 */
public class ImportChequesProducedFileJob extends BaseFinancialServicesCentreFileImportJob implements Job {

    @Autowired
    protected FileFilter chequesProducedFileFilter;
    @Autowired
    protected ImportRecordDispatcher chequesProducedDispatcher;
    @Autowired
    protected Validator chequeProducedValidator;
    @Autowired
    protected ImportRecordConverter chequeProducedConverter;

    @Override
    protected String getJobName() {
        return JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUES_PRODUCED.code();
    }

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.chequesProducedDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.chequeProducedValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.chequeProducedConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return this.chequesProducedFileFilter;
    }
}
