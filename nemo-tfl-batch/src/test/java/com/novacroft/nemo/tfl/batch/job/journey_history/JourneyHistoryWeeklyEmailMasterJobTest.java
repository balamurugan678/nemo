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
import static com.novacroft.nemo.test_support.DateTestUtil.getMon10Feb;
import static com.novacroft.nemo.test_support.DateTestUtil.getSun16Feb;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.createLog;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * JourneyHistoryWeeklyEmailMasterJob unit tests
 */
public class JourneyHistoryWeeklyEmailMasterJobTest {

    @Test
    public void shouldSubmitJobForCard() throws SchedulerException {

        Scheduler mockScheduler = mock(Scheduler.class);
        when(mockScheduler.scheduleJob(any(JobDetail.class), any(Trigger.class))).thenReturn(null);

        SchedulerFactoryBean mockSchedulerFactoryBean = mock(SchedulerFactoryBean.class);
        when(mockSchedulerFactoryBean.getScheduler()).thenReturn(mockScheduler);

        JourneyHistoryWeeklyEmailMasterJob job = new JourneyHistoryWeeklyEmailMasterJob();
        job.schedulerFactory = mockSchedulerFactoryBean;

        JobLogDTO log = createLog("testLog");
        CardPreferencesDTO cardPreferencesDTO = getTestCardPreferencesDTO1();

        job.submitJobForCard(cardPreferencesDTO, getMon10Feb(), getSun16Feb(), log);

        verify(mockScheduler).scheduleJob(any(JobDetail.class), any(Trigger.class));
        verify(mockSchedulerFactoryBean).getScheduler();
        assertTrue(log.getLog().contains("cardId [2]; rangeFrom [2014-02-10]; rangeTo [2014-02-16]"));
    }

    @Test
    public void shouldSubmitJobForCardWithError() throws SchedulerException {

        SchedulerException schedulerException = new SchedulerException("Test Error");

        Scheduler mockScheduler = mock(Scheduler.class);
        when(mockScheduler.scheduleJob(any(JobDetail.class), any(Trigger.class))).thenThrow(schedulerException);

        SchedulerFactoryBean mockSchedulerFactoryBean = mock(SchedulerFactoryBean.class);
        when(mockSchedulerFactoryBean.getScheduler()).thenReturn(mockScheduler);

        JourneyHistoryWeeklyEmailMasterJob job = new JourneyHistoryWeeklyEmailMasterJob();
        job.schedulerFactory = mockSchedulerFactoryBean;

        JobLogDTO log = createLog("testLog");
        CardPreferencesDTO cardPreferencesDTO = getTestCardPreferencesDTO1();

        job.submitJobForCard(cardPreferencesDTO, getMon10Feb(), getSun16Feb(), log);

        verify(mockScheduler).scheduleJob(any(JobDetail.class), any(Trigger.class));
        verify(mockSchedulerFactoryBean).getScheduler();
        assertTrue(log.getLog().contains("cardId [2]; rangeFrom [2014-02-10]; rangeTo [2014-02-16]"));
        assertTrue(log.getLog().contains("Test Error"));
    }

    @Test
    public void shouldExecuteInternal() throws JobExecutionException {
        JobExecutionContext mockJobExecutionContext = mock(JobExecutionContext.class);

        JobLogDataService mockJobLogDataService = mock(JobLogDataService.class);
        doNothing().when(mockJobLogDataService).create(any(JobLogDTO.class));

        JourneyHistoryScheduledStatementService mockJourneyHistoryScheduledStatementService =
                mock(JourneyHistoryScheduledStatementService.class);
        when(mockJourneyHistoryScheduledStatementService.getStartOfLastWeek()).thenReturn(getMon10Feb());
        when(mockJourneyHistoryScheduledStatementService.getEndOfLastWeek()).thenReturn(getSun16Feb());
        when(mockJourneyHistoryScheduledStatementService.getCardsForWeeklyStatement())
                .thenReturn(getTestCardPreferencesDTOList1());

        JourneyHistoryWeeklyEmailMasterJob job = mock(JourneyHistoryWeeklyEmailMasterJob.class);
        doCallRealMethod().when(job).executeInternal(any(JobExecutionContext.class));
        doNothing().when(job)
                .submitJobForCard(any(CardPreferencesDTO.class), any(Date.class), any(Date.class), any(JobLogDTO.class));
        job.journeyHistoryScheduledStatementService = mockJourneyHistoryScheduledStatementService;
        job.jobLogDataService = mockJobLogDataService;

        job.executeInternal(mockJobExecutionContext);

        verify(mockJobLogDataService).create(any(JobLogDTO.class));
        verify(mockJourneyHistoryScheduledStatementService).getStartOfLastWeek();
        verify(mockJourneyHistoryScheduledStatementService).getEndOfLastWeek();
        verify(mockJourneyHistoryScheduledStatementService).getCardsForWeeklyStatement();
        verify(job).submitJobForCard(any(CardPreferencesDTO.class), any(Date.class), any(Date.class), any(JobLogDTO.class));
    }
}
