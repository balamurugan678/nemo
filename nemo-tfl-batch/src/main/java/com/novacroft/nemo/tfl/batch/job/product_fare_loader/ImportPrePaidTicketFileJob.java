package com.novacroft.nemo.tfl.batch.job.product_fare_loader;

import java.io.FileFilter;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.job.BasePrePaidTicketServicesCentreFileImportJob;
import com.novacroft.nemo.tfl.batch.job.BatchJobUpdateStrategyService;
import com.novacroft.nemo.tfl.common.constant.JobName;

public class ImportPrePaidTicketFileJob extends BasePrePaidTicketServicesCentreFileImportJob implements  Job {

	@Autowired
    protected FileFilter prePaidTicketFileFilter;
    @Autowired
    protected Validator prePaidTicketValidator;
    @Autowired
    protected ImportRecordConverter prePaidTicketRecordConverter;
    @Autowired
    protected ImportRecordDispatcher prePaidTicketDispatcher;
    
    @Autowired
    protected BatchJobUpdateStrategyService<ImportPrePaidTicketFileJob> batchUpdateStrategyService;
	
	@Override
	protected String getJobName() {
		return JobName.UPLOAD_PRE_PAID_TICKET_PRICE_DATA.code();
	}

	@Override
	protected ImportRecordDispatcher getImportRecordDispatcher() {
		return prePaidTicketDispatcher;
	}

	@Override
	protected Validator getImportRecordValidator() {
		return prePaidTicketValidator;
	}

	@Override
	protected ImportRecordConverter getImportRecordConverter() {
		return prePaidTicketRecordConverter;
	}

	@Override
	protected FileFilter getImportFileFilter() {
		return prePaidTicketFileFilter;
	}

	@Override
	protected BatchJobUpdateStrategyService<ImportPrePaidTicketFileJob> getBatchSpecialisedService() {

		batchUpdateStrategyService.setFileImportJob(this);
        return batchUpdateStrategyService;
	}
	
	

}
