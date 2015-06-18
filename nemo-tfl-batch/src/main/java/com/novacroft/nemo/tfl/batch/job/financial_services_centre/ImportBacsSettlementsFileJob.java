package com.novacroft.nemo.tfl.batch.job.financial_services_centre;

import java.io.FileFilter;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.job.BaseFinancialServicesCentreFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;

/**
 * Import Financial Services Centre (FSC) bacs settlements file (I-FSC-7b)
 */
public class ImportBacsSettlementsFileJob extends BaseFinancialServicesCentreFileImportJob implements  Job {
    @Autowired
    protected FileFilter bacsSettlementsFileFilter;
    @Autowired
    protected Validator bacsPaymentSettlementValidator;
    @Autowired
    protected ImportRecordConverter bacsSettlementRecordConverter;
    @Autowired
    protected ImportRecordDispatcher bacsPaymentSettlementDispatcher;

    @Override
    protected String getJobName() {
        return JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_SETTLEMENTS.code();
    }

    @Override
    protected ImportRecordDispatcher getImportRecordDispatcher() {
        return this.bacsPaymentSettlementDispatcher;
    }

    @Override
    protected Validator getImportRecordValidator() {
        return this.bacsPaymentSettlementValidator;
    }

    @Override
    protected ImportRecordConverter getImportRecordConverter() {
        return this.bacsSettlementRecordConverter;
    }

    @Override
    protected FileFilter getImportFileFilter() {
        return this.bacsSettlementsFileFilter;
    }

}
