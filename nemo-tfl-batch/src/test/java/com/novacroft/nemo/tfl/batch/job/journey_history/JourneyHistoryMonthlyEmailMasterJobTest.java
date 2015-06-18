package com.novacroft.nemo.tfl.batch.job.journey_history;

import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.junit.Test;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTO1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTOList1;
import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.get31Jan;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.createLog;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * JourneyHistoryMonthlyEmailMasterJob unit tests
 */
public class JourneyHistoryMonthlyEmailMasterJobTest {

    @Test
    public void shouldSubmitJobForCard() throws SchedulerException {

        Scheduler mockScheduler = mock(Scheduler.class);
        when(mockScheduler.scheduleJob(any(JobDetail.class), any(Trigger.class))).thenReturn(null);

        SchedulerFactoryBean mockSchedulerFactoryBean = mock(SchedulerFactoryBean.class);
        when(mockSchedulerFactoryBean.getScheduler()).thenReturn(mockScheduler);

        JourneyHistoryMonthlyEmailMasterJob job = new JourneyHistoryMonthlyEmailMasterJob();
        job.schedulerFactory = mockSchedulerFactoryBean;

        JobLogDTO log = createLog("testLog");
        CardPreferencesDTO cardPreferencesDTO = getTestCardPreferencesDTO1();

        job.submitJobForCard(cardPreferencesDTO, get1Jan(), get31Jan(), log);

        verify(mockScheduler).scheduleJob(any(JobDetail.class), any(Trigger.class));
        verify(mockSchedulerFactoryBean).getScheduler();
        assertTrue(log.getLog().contains("cardId [2]; rangeFrom [2014-01-01]; rangeTo [2014-01-31]"));
    }

    @Test
    public void shouldSubmitJobForCardWithError() throws SchedulerException {

        SchedulerException schedulerException = new SchedulerException("Test Error");

        Scheduler mockScheduler = mock(Scheduler.class);
        when(mockScheduler.scheduleJob(any(JobDetail.class), any(Trigger.class))).thenThrow(schedulerException);

        SchedulerFactoryBean mockSchedulerFactoryBean = mock(SchedulerFactoryBean.class);
        when(mockSchedulerFactoryBean.getScheduler()).thenReturn(mockScheduler);

        JourneyHistoryMonthlyEmailMasterJob job = new JourneyHistoryMonthlyEmailMasterJob();
        job.schedulerFactory = mockSchedulerFactoryBean;

        JobLogDTO log = createLog("testLog");
        CardPreferencesDTO cardPreferencesDTO = getTestCardPreferencesDTO1();

        job.submitJobForCard(cardPreferencesDTO, get1Jan(), get31Jan(), log);

        verify(mockScheduler).scheduleJob(any(JobDetail.class), any(Trigger.class));
        verify(mockSchedulerFactoryBean).getScheduler();
        assertTrue(log.getLog().contains("cardId [2]; rangeFrom [2014-01-01]; rangeTo [2014-01-31]"));
        assertTrue(log.getLog().contains("Test Error"));
    }

    @Test
    public void shouldExecuteInternal() throws JobExecutionException {
        JobExecutionContext mockJobExecutionContext = mock(JobExecutionContext.class);

        JobLogDataService mockJobLogDataService = mock(JobLogDataService.class);
        doNothing().when(mockJobLogDataService).create(any(JobLogDTO.class));

        JourneyHistoryScheduledStatementService mockJourneyHistoryScheduledStatementService =
                mock(JourneyHistoryScheduledStatementService.class);
        when(mockJourneyHistoryScheduledStatementService.getStartOfLastMonth()).thenReturn(get1Jan());
        when(mockJourneyHistoryScheduledStatementService.getEndOfLastMonth()).thenReturn(get31Jan());
        when(mockJourneyHistoryScheduledStatementService.getCardsForMonthlyStatement())
                .thenReturn(getTestCardPreferencesDTOList1());

        JourneyHistoryMonthlyEmailMasterJob job = mock(JourneyHistoryMonthlyEmailMasterJob.class);
        doCallRealMethod().when(job).executeInternal(any(JobExecutionContext.class));
        doNothing().when(job)
                .submitJobForCard(any(CardPreferencesDTO.class), any(Date.class), any(Date.class), any(JobLogDTO.class));
        job.journeyHistoryScheduledStatementService = mockJourneyHistoryScheduledStatementService;
        job.jobLogDataService = mockJobLogDataService;

        job.executeInternal(mockJobExecutionContext);

        verify(mockJobLogDataService).create(any(JobLogDTO.class));
        verify(mockJourneyHistoryScheduledStatementService).getStartOfLastMonth();
        verify(mockJourneyHistoryScheduledStatementService).getEndOfLastMonth();
        verify(mockJourneyHistoryScheduledStatementService).getCardsForMonthlyStatement();
        verify(job).submitJobForCard(any(CardPreferencesDTO.class), any(Date.class), any(Date.class), any(JobLogDTO.class));
    }
}
