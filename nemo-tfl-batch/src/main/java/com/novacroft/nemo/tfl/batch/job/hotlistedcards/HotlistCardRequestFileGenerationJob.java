package com.novacroft.nemo.tfl.batch.job.hotlistedcards;

import static com.novacroft.nemo.tfl.common.util.JobLogUtil.createLog;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logEnd;
import static com.novacroft.nemo.tfl.common.util.JobLogUtil.logMessage;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.novacroft.nemo.tfl.common.application_service.DataExportOrchestrator;
import com.novacroft.nemo.tfl.common.constant.JobName;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

/**
 * Hotlist Card Request File generation job.
 * 
 * <p>
 * Creates File with list of cards to be hotlisted.
 * </p>
 */
public class HotlistCardRequestFileGenerationJob extends QuartzJobBean {
    protected static final Logger logger = LoggerFactory.getLogger(HotlistCardRequestFileGenerationJob.class);

    @Autowired
    protected DataExportOrchestrator hotlistcardsExportService;
    @Autowired
    protected JobLogDataService jobLogDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobLogDTO log = createLog(JobName.EXPORT_HOTLISTCARD_REQUEST_FILE.code());
        try {
            logMessage(log, "calling exportPrestige() in DataExportOrchestrator");
            this.hotlistcardsExportService.exportPrestige(log);
        } finally {
            logEnd(log);
            this.jobLogDataService.create(log);
        }
    }
}
