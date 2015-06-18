package com.novacroft.nemo.tfl.batch.job;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.createLog;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logEnd;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logMessage;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.novacroft.nemo.tfl.common.application_service.MessageService;
import com.novacroft.nemo.tfl.common.constant.JobName;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public class MessageSenderJob extends QuartzJobBean {
    protected static final Logger logger = LoggerFactory.getLogger(MessageSenderJob.class);

    @Autowired
    protected MessageService messageService;
    @Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobLogDTO log = createLog(JobName.SEND_MESSAGES.code());
        try {
            logMessage(log, "calling sendMessage in MessageService");
            this.messageService.processMessages(log);
        } finally {
            logEnd(log);
            this.jobLogDataService.create(log);
        }
    }
}
