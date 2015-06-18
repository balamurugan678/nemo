package com.novacroft.nemo.tfl.batch.job;

import java.util.List;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.batch.job.product_fare_loader.ImportPrePaidTicketFileJob;
import com.novacroft.nemo.tfl.common.data_access.PrePaidTicketDAO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * This class provides an implementation for the <code>BatchJobUpdateStrategyService</code> commit strategy. This is needed as <b>spring AOP</b> does
 * not allow to have transactional method calls of methods within the same class
 * 
 * @author vijay.dabas
 * 
 */
@Service("hibernateBatchUpdateStrategyService")
public class HibernateBatchJobUpdateStrategyServiceImpl implements	BatchJobUpdateStrategyService<ImportPrePaidTicketFileJob> {


	@Autowired
	protected PrePaidTicketDAO paidTicketDAO;
	
	@Value("${hibernate.jdbc.batch_size}")
	protected int flushCounterSize;
	
	protected ImportPrePaidTicketFileJob importPrePaidTicketFileJob;
	
	@Override
	public void setFileImportJob(ImportPrePaidTicketFileJob fileJob) {
		importPrePaidTicketFileJob = fileJob;
		
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void processBatchUpdate(List<String[]> data, JobLogDTO log, JobDataMap jobDataMap) {
		
		int flushCounter = 0;
		boolean hasValidationErrors = false;
		for (String[] record : data) {
			if(!importPrePaidTicketFileJob.isRecordValid(record, log)){
				hasValidationErrors = true;
            } else {
				importPrePaidTicketFileJob.handleRecord(record, log, jobDataMap);
				flushCounter++;
				if (flushCounter == flushCounterSize) {
					paidTicketDAO.flush();
					paidTicketDAO.clear();
					flushCounter = 0;
				}
			}
			
    	}
		if(hasValidationErrors) {
			throw new ApplicationServiceException("Validation for records failed. Please find the Errors in log.");
		}
		
	}

	

}
