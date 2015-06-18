package com.novacroft.nemo.tfl.batch.job.journey_history;

import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;

import java.util.Date;

import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.get31Jan;
import static org.mockito.Mockito.*;

/**
 * JourneyHistoryWeeklyEmailJob unit tests
 */
public class JourneyHistoryWeeklyEmailJobTest {

    @Test
    public void shouldExecuteInternal() throws JobExecutionException {
        JobDataMap mockJobDataMap = mock(JobDataMap.class);
        when(mockJobDataMap.getLongValue(eq("cardId"))).thenReturn(CARD_ID_1);
        when(mockJobDataMap.getLongValue(eq("rangeFrom"))).thenReturn(get1Jan().getTime());
        when(mockJobDataMap.getLongValue(eq("rangeTo"))).thenReturn(get31Jan().getTime());

        JobExecutionContext jobExecutionContext = mock(JobExecutionContextImpl.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(mockJobDataMap);

        JourneyHistoryScheduledStatementService mockJourneyHistoryScheduledStatementService =
                mock(JourneyHistoryScheduledStatementService.class);
        doNothing().when(mockJourneyHistoryScheduledStatementService)
                .sendWeeklyStatement(anyLong(), any(Date.class), any(Date.class));

        JobLogDataService mockJobLogDataService = mock(JobLogDataService.class);
        doNothing().when(mockJobLogDataService).create(any(JobLogDTO.class));

        JourneyHistoryWeeklyEmailJob job = mock(JourneyHistoryWeeklyEmailJob.class, CALLS_REAL_METHODS);
        job.journeyHistoryScheduledStatementService = mockJourneyHistoryScheduledStatementService;
        job.jobLogDataService = mockJobLogDataService;

        job.executeInternal(jobExecutionContext);

        verify(mockJobDataMap).getLongValue(eq("cardId"));
        verify(mockJobDataMap).getLongValue(eq("rangeFrom"));
        verify(mockJobDataMap).getLongValue(eq("rangeTo"));
        verify(jobExecutionContext, times(3)).getMergedJobDataMap();
        verify(mockJourneyHistoryScheduledStatementService).sendWeeklyStatement(anyLong(), any(Date.class), any(Date.class));
        verify(mockJobLogDataService).create(any(JobLogDTO.class));
    }
}
