package com.novacroft.nemo.tfl.batch.job.journey_history;

import com.novacroft.nemo.tfl.batch.util.JobUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
import com.novacroft.nemo.tfl.common.constant.JobGroup;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;
import java.util.List;

import static com.novacroft.nemo.tfl.batch.constant.JobParameterName.*;
import static com.novacroft.nemo.tfl.batch.util.JobUtil.createIdentity;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_MASTER_WEEKLY_EMAIL;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_WEEKLY_EMAIL;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.*;

/**
 * Journey History weekly email master job.
 *
 * <p>Submits a job for each card email weekly statement.</p>
 */
public class JourneyHistoryWeeklyEmailMasterJob extends QuartzJobBean {
    protected static final Logger logger = LoggerFactory.getLogger(JourneyHistoryWeeklyEmailMasterJob.class);

    @Autowired
    protected JourneyHistoryScheduledStatementService journeyHistoryScheduledStatementService;
    @Autowired
    protected SchedulerFactoryBean schedulerFactory;
    @Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobLogDTO log = createLog(JOURNEY_HISTORY_MASTER_WEEKLY_EMAIL.code());
        try {
            Date rangeFrom = this.journeyHistoryScheduledStatementService.getStartOfLastWeek();
            Date rangeTo = this.journeyHistoryScheduledStatementService.getEndOfLastWeek();
            List<CardPreferencesDTO> cards = this.journeyHistoryScheduledStatementService.getCardsForWeeklyStatement();
            for (CardPreferencesDTO card : cards) {
                submitJobForCard(card, rangeFrom, rangeTo, log);
            }
        } finally {
            logEnd(log);
            this.jobLogDataService.create(log);
        }
    }

    protected void submitJobForCard(CardPreferencesDTO cardPreferencesDTO, Date rangeFrom, Date rangeTo, JobLogDTO log) {
        logMessage(log, String.format("cardId [%s]; rangeFrom [%tF]; rangeTo [%tF]", cardPreferencesDTO.getCardId(), rangeFrom,
                rangeTo));
        JobDetail jobDetail = JobBuilder.newJob(JourneyHistoryWeeklyEmailJob.class).withIdentity(
                JobUtil.createIdentity(JOURNEY_HISTORY_WEEKLY_EMAIL.code(), cardPreferencesDTO.getCardId().toString()),
                JobGroup.JOURNEY_HISTORY_STATEMENT.code()).usingJobData(CARD_ID, cardPreferencesDTO.getCardId())
                .usingJobData(RANGE_FROM, rangeFrom.getTime()).usingJobData(RANGE_TO, rangeTo.getTime()).build();
        Trigger trigger =
                TriggerBuilder.newTrigger().withIdentity(createIdentity(NOW), JobGroup.JOURNEY_HISTORY_STATEMENT.code())
                        .startNow().build();
        try {
            this.schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logMessage(log, e.getMessage());
            logger.error(e.getMessage(), e);
        }
    }
}
