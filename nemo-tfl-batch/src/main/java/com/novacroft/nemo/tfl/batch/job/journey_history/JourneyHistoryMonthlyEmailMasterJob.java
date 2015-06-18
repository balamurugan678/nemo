package com.novacroft.nemo.tfl.batch.job.journey_history;

import com.novacroft.nemo.tfl.batch.util.JobUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
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
import static com.novacroft.nemo.tfl.common.constant.JobGroup.JOURNEY_HISTORY_STATEMENT;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_MASTER_MONTHLY_EMAIL;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_MONTHLY_EMAIL;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.*;

/**
 * Journey History monthly email master job.
 *
 * <p>Submits a job for each card email monthly statement.</p>
 */
public class JourneyHistoryMonthlyEmailMasterJob extends QuartzJobBean {
    protected static final Logger logger = LoggerFactory.getLogger(JourneyHistoryMonthlyEmailMasterJob.class);

    @Autowired
    protected JourneyHistoryScheduledStatementService journeyHistoryScheduledStatementService;
    @Autowired
    protected SchedulerFactoryBean schedulerFactory;
    @Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobLogDTO log = createLog(JOURNEY_HISTORY_MASTER_MONTHLY_EMAIL.code());
        try {
            Date rangeFrom = this.journeyHistoryScheduledStatementService.getStartOfLastMonth();
            Date rangeTo = this.journeyHistoryScheduledStatementService.getEndOfLastMonth();
            List<CardPreferencesDTO> cards = this.journeyHistoryScheduledStatementService.getCardsForMonthlyStatement();
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
        JobDetail jobDetail = JobBuilder.newJob(JourneyHistoryMonthlyEmailJob.class).withIdentity(
                JobUtil.createIdentity(JOURNEY_HISTORY_MONTHLY_EMAIL.code(), cardPreferencesDTO.getCardId().toString()),
                JOURNEY_HISTORY_STATEMENT.code()).usingJobData(CARD_ID, cardPreferencesDTO.getCardId())
                .usingJobData(RANGE_FROM, rangeFrom.getTime()).usingJobData(RANGE_TO, rangeTo.getTime()).build();
        Trigger trigger =
                TriggerBuilder.newTrigger().withIdentity(createIdentity(NOW), JOURNEY_HISTORY_STATEMENT.code()).startNow()
                        .build();
        try {
            this.schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logMessage(log, e.getMessage());
            logger.error(e.getMessage(), e);
        }
    }
}
