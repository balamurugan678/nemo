package com.novacroft.nemo.tfl.batch.job;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.batch.job.product_fare_loader.ImportPrePaidTicketFileJob;
import com.novacroft.nemo.tfl.common.data_access.PrePaidTicketDAO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

@RunWith(MockitoJUnitRunner.class)
public class HibernateBatchJobUpdateStrategyServiceImplTest {

    public static final String[] CSV_RECORD_1 = "102,Annual Travelcard Zones 1 to 2,12/14/2002,No Discount,0,Zones 1 to 2,Y,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,N,annual,Y,1256.00"
                    .split(",");

    public static final String[] CSV_RECORD_2 = "103,Annual Travelcard Zones 1 to 3,12/14/2002,No Discount,0,Zones 1 to 3,Y,Y,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,annual,Y,1472.00"
                    .split(",");

    @Mock
    protected PrePaidTicketDAO mockPaidTicketDAO;

    protected final int flushCounterSize = 2;

    @Mock
    protected JobDataMap mockJobDataMap;

    protected JobLogDTO jobLogDTO = new JobLogDTO();


    @Mock
    protected ImportPrePaidTicketFileJob mockImportPrePaidTicketFileJob;

    protected HibernateBatchJobUpdateStrategyServiceImpl batchJobUpdateStrategyServiceImpl;



    @Before
    public void setUp() {
        batchJobUpdateStrategyServiceImpl = new HibernateBatchJobUpdateStrategyServiceImpl();
        batchJobUpdateStrategyServiceImpl.importPrePaidTicketFileJob = mockImportPrePaidTicketFileJob;
        batchJobUpdateStrategyServiceImpl.paidTicketDAO = mockPaidTicketDAO;
        batchJobUpdateStrategyServiceImpl.flushCounterSize = flushCounterSize;
    }
    @Test
    public void testProcessBatchUpdateShouldFlushAndClearAFterFlusCounterSizeReached() {
        List<String[]> list = new ArrayList<>();
        list.add(CSV_RECORD_1);
        list.add(CSV_RECORD_2);
        when(mockImportPrePaidTicketFileJob.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenReturn(true);
        batchJobUpdateStrategyServiceImpl.processBatchUpdate(list, jobLogDTO, mockJobDataMap);
        verify(mockPaidTicketDAO).flush();
        verify(mockPaidTicketDAO).clear();

    }

    @Test
    public void testFlushAndClearNOTCalledBeforeFlusCounterSizeReached() {
        List<String[]> list = new ArrayList<>();
        list.add(CSV_RECORD_1);
        when(mockImportPrePaidTicketFileJob.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenReturn(true);
        batchJobUpdateStrategyServiceImpl.processBatchUpdate(list, jobLogDTO, mockJobDataMap);
        verify(mockPaidTicketDAO, never()).flush();
        verify(mockPaidTicketDAO, never()).clear();

    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldThrowAnExceptionWhen() {
        List<String[]> list = new ArrayList<>();
        list.add(CSV_RECORD_1);
        when(mockImportPrePaidTicketFileJob.isRecordValid(any(String[].class), any(JobLogDTO.class))).thenReturn(false);
        batchJobUpdateStrategyServiceImpl.processBatchUpdate(list, jobLogDTO, mockJobDataMap);
        verify(mockPaidTicketDAO, never()).flush();
        verify(mockPaidTicketDAO, never()).clear();

    }


}
