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
 * Import Financial Services Centre (FSC) outdated cheque file (I-FSC-9b)
 */
public class ImportOutdatedChequesFileJob extends BaseFinancialServicesCentreFileImportJob implements Job {

    @Autowired
    protected FileFilter outdatedChequesFileFilter;
    @Autowired
    protected Validator outdatedChequeValidator;
    @Autowired
    protected ImportRecordConverter outdatedChequeConverter;
    @Autowired
    protected ImportRecordDispatcher outdatedChequeDispatcher;

    @Override
    protected String getJobName() {
        return JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_OUTDATED_CHEQUES.code();
    }

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.outdatedChequeDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.outdatedChequeValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.outdatedChequeConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return this.outdatedChequesFileFilter;
    }
}
