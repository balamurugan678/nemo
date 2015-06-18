package com.novacroft.nemo.tfl.batch.job.journey_history;

import com.novacroft.nemo.tfl.batch.constant.JobParameterName;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
import com.novacroft.nemo.tfl.common.constant.JobName;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.*;

/**
 * Journey History monthly email job.
 *
 * <p>Sends email monthly statement for a card.</p>
 */
public class JourneyHistoryMonthlyEmailJob extends QuartzJobBean {
    protected static final Logger logger = LoggerFactory.getLogger(JourneyHistoryMonthlyEmailJob.class);

    @Autowired
    protected JourneyHistoryScheduledStatementService journeyHistoryScheduledStatementService;
    @Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobLogDTO log = createLog(JobName.JOURNEY_HISTORY_MONTHLY_EMAIL.code());
        try {
            Long cardId = jobExecutionContext.getMergedJobDataMap().getLongValue(JobParameterName.CARD_ID);
            Date rangeFrom = new Date(jobExecutionContext.getMergedJobDataMap().getLongValue(JobParameterName.RANGE_FROM));
            Date rangeTo = new Date(jobExecutionContext.getMergedJobDataMap().getLongValue(JobParameterName.RANGE_TO));
            logMessage(log, String.format("cardId [%s]; rangeFrom [%tF]; rangeTo [%tF]", cardId, rangeFrom, rangeTo));
            this.journeyHistoryScheduledStatementService.sendMonthlyStatement(cardId, rangeFrom, rangeTo);
        } finally {
            logEnd(log);
            this.jobLogDataService.create(log);
        }
    }
}
