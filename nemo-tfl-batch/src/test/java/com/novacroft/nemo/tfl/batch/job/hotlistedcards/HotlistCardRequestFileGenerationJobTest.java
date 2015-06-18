package com.novacroft.nemo.tfl.batch.job.hotlistedcards;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;

import com.novacroft.nemo.tfl.common.application_service.DataExportOrchestrator;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * Hotlist Card Request File generation job unit tests
 */
public class HotlistCardRequestFileGenerationJobTest {

    @Test
    public void shouldExecuteInternal() throws JobExecutionException {
        JobDataMap mockJobDataMap = mock(JobDataMap.class);

        JobExecutionContext jobExecutionContext = mock(JobExecutionContextImpl.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(mockJobDataMap);

        DataExportOrchestrator mockHotlistcardsExportService = mock(DataExportOrchestrator.class);
        doNothing().when(mockHotlistcardsExportService).exportPrestige(any(JobLogDTO.class));

        JobLogDataService mockJobLogDataService = mock(JobLogDataService.class);
        doNothing().when(mockJobLogDataService).create(any(JobLogDTO.class));

        HotlistCardRequestFileGenerationJob job = mock(HotlistCardRequestFileGenerationJob.class, CALLS_REAL_METHODS);
        job.hotlistcardsExportService = mockHotlistcardsExportService;
        job.jobLogDataService = mockJobLogDataService;

        job.executeInternal(jobExecutionContext);

        verify(mockHotlistcardsExportService).exportPrestige(any(JobLogDTO.class));
        verify(mockJobLogDataService).create(any(JobLogDTO.class));
    }
}
