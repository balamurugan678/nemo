package com.novacroft.nemo.tfl.batch.job.product_fare_loader;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.novacroft.nemo.tfl.batch.job.HibernateBatchJobUpdateStrategyServiceImpl;
import com.novacroft.nemo.tfl.batch.job.product_fare_loader.ImportPrePaidTicketFileJob;


public class ImportPrePaidTicketFileJobTest {


    protected ImportPrePaidTicketFileJob importPrePaidTicketFileJob = new ImportPrePaidTicketFileJob();

    protected HibernateBatchJobUpdateStrategyServiceImpl hibernateBatchJobUpdateStrategyServiceImpl = new HibernateBatchJobUpdateStrategyServiceImpl();;


    @Test
    public void testGetBatchSpecialisedService() {

        importPrePaidTicketFileJob.batchUpdateStrategyService = hibernateBatchJobUpdateStrategyServiceImpl;
        assertTrue(hibernateBatchJobUpdateStrategyServiceImpl.equals(importPrePaidTicketFileJob.getBatchSpecialisedService()));
    }

}
