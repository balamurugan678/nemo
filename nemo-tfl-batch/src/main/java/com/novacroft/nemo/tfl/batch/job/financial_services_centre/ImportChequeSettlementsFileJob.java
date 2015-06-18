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
 * Import Financial Services Centre (FSC) cheque settlements file (I-FSC-3b)
 */
public class ImportChequeSettlementsFileJob extends BaseFinancialServicesCentreFileImportJob implements Job {

    @Autowired
    protected FileFilter chequeSettlementsFileFilter;
    @Autowired
    protected Validator chequeSettlementValidator;
    @Autowired
    protected ImportRecordConverter chequeSettlementRecordConverter;
    @Autowired
    protected ImportRecordDispatcher chequeSettlementDispatcher;

    @Override
    protected String getJobName() {
        return JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUE_SETTLEMENTS.code();
    }

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.chequeSettlementDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.chequeSettlementValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.chequeSettlementRecordConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return this.chequeSettlementsFileFilter;
    }
}
