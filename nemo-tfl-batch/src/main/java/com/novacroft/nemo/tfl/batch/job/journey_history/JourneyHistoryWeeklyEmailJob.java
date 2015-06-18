package com.novacroft.nemo.tfl.batch.job.journey_history;

import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

import static com.novacroft.nemo.tfl.batch.constant.JobParameterName.*;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_WEEKLY_EMAIL;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.*;

/**
 * Journey History weekly email job.
 *
 * <p>Sends email weekly statement for a card.</p>
 */
public class JourneyHistoryWeeklyEmailJob extends QuartzJobBean {
    protected static final Logger logger = LoggerFactory.getLogger(JourneyHistoryWeeklyEmailJob.class);

    @Autowired
    protected JourneyHistoryScheduledStatementService journeyHistoryScheduledStatementService;
    @Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobLogDTO log = createLog(JOURNEY_HISTORY_WEEKLY_EMAIL.code());
        try {
            Long cardId = jobExecutionContext.getMergedJobDataMap().getLongValue(CARD_ID);
            Date rangeFrom = new Date(jobExecutionContext.getMergedJobDataMap().getLongValue(RANGE_FROM));
            Date rangeTo = new Date(jobExecutionContext.getMergedJobDataMap().getLongValue(RANGE_TO));
            logMessage(log, String.format("cardId [%s]; rangeFrom [%tF]; rangeTo [%tF]", cardId, rangeFrom, rangeTo));
            this.journeyHistoryScheduledStatementService.sendWeeklyStatement(cardId, rangeFrom, rangeTo);
        } finally {
            logEnd(log);
            this.jobLogDataService.create(log);
        }
    }
}
