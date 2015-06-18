package com.novacroft.nemo.tfl.batch.job.financial_services_centre;

import java.io.FileFilter;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.job.BaseFinancialServicesCentreFileImportJob;
import com.novacroft.nemo.tfl.common.constant.JobName;

public class ImportBacsFailureFileJob extends BaseFinancialServicesCentreFileImportJob implements  Job {

	@Autowired
    protected FileFilter bacsFailuresFileFilter;
    @Autowired
    protected Validator bacsFailureValidator;
    @Autowired
    protected ImportRecordConverter bacsFailedRecordConverter;
    @Autowired
    protected ImportRecordDispatcher bacsPaymentFailedDispatcher;
    
	
	@Override
	protected String getJobName() {
		return JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_FAILURES.code();
	}

	@Override
	protected ImportRecordDispatcher getImportRecordDispatcher() {
		return bacsPaymentFailedDispatcher;
	}

	@Override
	protected Validator getImportRecordValidator() {
		return bacsFailureValidator;
	}

	@Override
	protected ImportRecordConverter getImportRecordConverter() {
		return bacsFailedRecordConverter;
	}

	@Override
	protected FileFilter getImportFileFilter() {
		return bacsFailuresFileFilter;
	}

}
