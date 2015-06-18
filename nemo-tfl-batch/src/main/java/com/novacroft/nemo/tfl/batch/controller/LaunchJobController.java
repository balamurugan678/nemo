package com.novacroft.nemo.tfl.batch.controller;

import static com.novacroft.nemo.tfl.batch.constant.JobParameterName.CUBIC_IMPORT_ROOT_DIR;
import static com.novacroft.nemo.tfl.batch.constant.JobParameterName.FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR;
import static com.novacroft.nemo.tfl.batch.constant.JobParameterName.NOW;
import static com.novacroft.nemo.tfl.batch.constant.JobParameterName.PRE_PAID_TICKETS_ERVICES_CENTRE_IMPORT_ROOT_DIR;
import static com.novacroft.nemo.tfl.batch.util.JobUtil.createIdentity;
import static com.novacroft.nemo.tfl.common.constant.JobGroup.CUBIC_IMPORT;
import static com.novacroft.nemo.tfl.common.constant.JobGroup.FINANCIAL_SERVICES_CENTRE_IMPORT;
import static com.novacroft.nemo.tfl.common.constant.JobGroup.HOTLISTED_CARDS_EXPORT;
import static com.novacroft.nemo.tfl.common.constant.JobGroup.MESSAGES;
import static com.novacroft.nemo.tfl.common.constant.JobGroup.JOURNEY_HISTORY_STATEMENT;
import static com.novacroft.nemo.tfl.common.constant.JobGroup.PRE_PAID_TICKET_DATA_IMPORT;
import static com.novacroft.nemo.tfl.common.constant.JobName.EXPORT_HOTLISTCARD_REQUEST_FILE;
import static com.novacroft.nemo.tfl.common.constant.JobName.SEND_MESSAGES;
import static com.novacroft.nemo.tfl.common.constant.JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_FAILURES;
import static com.novacroft.nemo.tfl.common.constant.JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_SETTLEMENTS;
import static com.novacroft.nemo.tfl.common.constant.JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUES_PRODUCED;
import static com.novacroft.nemo.tfl.common.constant.JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUE_SETTLEMENTS;
import static com.novacroft.nemo.tfl.common.constant.JobName.FINANCIAL_SERVICES_CENTRE_IMPORT_OUTDATED_CHEQUES;
import static com.novacroft.nemo.tfl.common.constant.JobName.IMPORT_AD_HOC_DISTRIBUTION_CUBIC_FILE;
import static com.novacroft.nemo.tfl.common.constant.JobName.IMPORT_AUTO_LOADS_PERFORMED_CUBIC_FILE;
import static com.novacroft.nemo.tfl.common.constant.JobName.IMPORT_AUTO_LOAD_CHANGES_CUBIC_FILE;
import static com.novacroft.nemo.tfl.common.constant.JobName.IMPORT_CURRENT_ACTION_LIST_CUBIC_FILE;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_MASTER_MONTHLY_EMAIL;
import static com.novacroft.nemo.tfl.common.constant.JobName.JOURNEY_HISTORY_MASTER_WEEKLY_EMAIL;
import static com.novacroft.nemo.tfl.common.constant.JobName.UPLOAD_PRE_PAID_TICKET_PRICE_DATA;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.batch.command.LaunchJobCmd;
import com.novacroft.nemo.tfl.batch.job.MessageSenderJob;
import com.novacroft.nemo.tfl.batch.job.cubic.ImportAdHocDistributionCubicFileJob;
import com.novacroft.nemo.tfl.batch.job.cubic.ImportAutoLoadChangesCubicFileJob;
import com.novacroft.nemo.tfl.batch.job.cubic.ImportAutoLoadsPerformedCubicFileJob;
import com.novacroft.nemo.tfl.batch.job.cubic.ImportCurrentActionListCubicFileJob;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportBacsFailureFileJob;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportBacsSettlementsFileJob;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportChequeSettlementsFileJob;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportChequesProducedFileJob;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportOutdatedChequesFileJob;
import com.novacroft.nemo.tfl.batch.job.hotlistedcards.HotlistCardRequestFileGenerationJob;
import com.novacroft.nemo.tfl.batch.job.journey_history.JourneyHistoryMonthlyEmailMasterJob;
import com.novacroft.nemo.tfl.batch.job.journey_history.JourneyHistoryWeeklyEmailMasterJob;
import com.novacroft.nemo.tfl.batch.job.product_fare_loader.ImportPrePaidTicketFileJob;
import com.novacroft.nemo.tfl.batch.scheduler.SchedulerWrapper;

/**
 * Launch Job
 *
 * <p>
 * TODO This is a lash up so that jobs can be tested. Further thought is required on how best to provide this capability.
 * Should probably be available
 * in InNovator so that Contact Centre Advisors may request jobs.
 * </p>
 */
@Controller
@RequestMapping("LaunchJob.htm")
public class LaunchJobController {
    protected static final Logger logger = LoggerFactory.getLogger(LaunchJobController.class);
    protected static final String LAUNCH_JOB_VIEW = "LaunchJobView";
    protected static final String COMMAND = "cmd";

    @Value("${cubic.import.location:unknown}")
    protected String cubicImportLocation;
    @Value("${financialServicesCentre.import.location:unknown}")
    protected String financialServicesCentreImportLocation;
    @Value("${prePaidTicketServicesCentre.import.location:unknown}")
    protected String prePaidTicketServicesCentreImportLocation;

    @Autowired
    protected SchedulerWrapper schedulerWrapper;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, new LaunchJobCmd());
    }

    @RequestMapping(params = "targetAction=importCurrentActionListCubicFile")
    public ModelAndView launchCubicCurrentActionListFileImporter() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(ImportCurrentActionListCubicFileJob.class)
                        .withIdentity(IMPORT_CURRENT_ACTION_LIST_CUBIC_FILE.code(), CUBIC_IMPORT.code())
                        .usingJobData(CUBIC_IMPORT_ROOT_DIR, this.cubicImportLocation).build(), CUBIC_IMPORT.code(),
                "Import Current Action List CUBIC Files Job Scheduled"));
    }

    @RequestMapping(params = "targetAction=importAdHocDistributionCubicFile")
    public ModelAndView launchCubicAdHocDistributionFileImporter() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(ImportAdHocDistributionCubicFileJob.class)
                        .withIdentity(IMPORT_AD_HOC_DISTRIBUTION_CUBIC_FILE.code(), CUBIC_IMPORT.code())
                        .usingJobData(CUBIC_IMPORT_ROOT_DIR, this.cubicImportLocation).build(), CUBIC_IMPORT.code(),
                "Import Ad Hoc Distribution CUBIC Files Job Scheduled"));
    }

    @RequestMapping(params = "targetAction=importAutoLoadChangeCubicFile")
    public ModelAndView launchCubicAutoLoadChangeFileImporter() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(ImportAutoLoadChangesCubicFileJob.class)
                        .withIdentity(IMPORT_AUTO_LOAD_CHANGES_CUBIC_FILE.code(), CUBIC_IMPORT.code())
                        .usingJobData(CUBIC_IMPORT_ROOT_DIR, this.cubicImportLocation).build(), CUBIC_IMPORT.code(),
                "Import Auto Load Change CUBIC Files Job Scheduled"));
    }

    @RequestMapping(params = "targetAction=importAutoLoadPerformedCubicFile")
    public ModelAndView launchCubicAutoLoadPerformedFileImporter() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(ImportAutoLoadsPerformedCubicFileJob.class)
                        .withIdentity(IMPORT_AUTO_LOADS_PERFORMED_CUBIC_FILE.code(), CUBIC_IMPORT.code())
                        .usingJobData(CUBIC_IMPORT_ROOT_DIR, this.cubicImportLocation).build(), CUBIC_IMPORT.code(),
                "Import Auto Load Performed CUBIC Files Job Scheduled"));
    }

    @RequestMapping(params = "targetAction=runJourneyHistoryWeeklyEmailStatement")
    public ModelAndView runJourneyHistoryWeeklyEmailStatementJob() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(JourneyHistoryWeeklyEmailMasterJob.class)
                        .withIdentity(createIdentity(JOURNEY_HISTORY_MASTER_WEEKLY_EMAIL.code()),
                                JOURNEY_HISTORY_STATEMENT.code()).build(), JOURNEY_HISTORY_STATEMENT.code(),
                "Journey History Weekly Email Statement Scheduled"));
    }

    @RequestMapping(params = "targetAction=runJourneyHistoryMonthlyEmailStatement")
    public ModelAndView runJourneyHistoryMonthlyEmailStatementJob() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(JourneyHistoryMonthlyEmailMasterJob.class)
                        .withIdentity(createIdentity(JOURNEY_HISTORY_MASTER_MONTHLY_EMAIL.code()),
                                JOURNEY_HISTORY_STATEMENT.code()).build(), JOURNEY_HISTORY_STATEMENT.code(),
                "Journey History Monthly Email Statement Scheduled"));
    }

    @RequestMapping(params = "targetAction=importFscChequesProduced")
    public ModelAndView importFscChequesProduced() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(JobBuilder.newJob(ImportChequesProducedFileJob.class)
                        .withIdentity(FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUES_PRODUCED.code(),
                                FINANCIAL_SERVICES_CENTRE_IMPORT.code())
                        .usingJobData(FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR, this.financialServicesCentreImportLocation)
                        .build(), FINANCIAL_SERVICES_CENTRE_IMPORT.code(),
                "Financial Services Centre Cheques Produced File Import Scheduled"));
    }

    @RequestMapping(params = "targetAction=importFscChequeSettlements")
    public ModelAndView importFscChequeSettlements() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(JobBuilder.newJob(ImportChequeSettlementsFileJob.class)
                        .withIdentity(FINANCIAL_SERVICES_CENTRE_IMPORT_CHEQUE_SETTLEMENTS.code(),
                                FINANCIAL_SERVICES_CENTRE_IMPORT.code())
                        .usingJobData(FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR, this.financialServicesCentreImportLocation)
                        .build(), FINANCIAL_SERVICES_CENTRE_IMPORT.code(),
                "Financial Services Centre Cheque Settlements File Import Scheduled"));
    }

    @RequestMapping(params = "targetAction=importFscBacsSettlements")
    public ModelAndView importFscBacsSettlements() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(JobBuilder.newJob(ImportBacsSettlementsFileJob.class)
                        .withIdentity(FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_SETTLEMENTS.code(),
                                FINANCIAL_SERVICES_CENTRE_IMPORT.code())
                        .usingJobData(FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR, this.financialServicesCentreImportLocation)
                        .build(), FINANCIAL_SERVICES_CENTRE_IMPORT.code(),
                "Financial Services Centre Bacs Settlements File Import Scheduled"));
    }

    @RequestMapping(params = "targetAction=importFscFailedBacsPayments")
    public ModelAndView importFscBacsFailures() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(JobBuilder.newJob(ImportBacsFailureFileJob.class)
                        .withIdentity(FINANCIAL_SERVICES_CENTRE_IMPORT_BACS_FAILURES.code(),
                                FINANCIAL_SERVICES_CENTRE_IMPORT.code())
                        .usingJobData(FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR, this.financialServicesCentreImportLocation)
                        .build(), FINANCIAL_SERVICES_CENTRE_IMPORT.code(),
                "Financial Services Centre Bacs Failures File Import Scheduled"));
    }

    @RequestMapping(params = "targetAction=importFscOutdatedCheques")
    public ModelAndView importFscOutdatedCheques() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(JobBuilder.newJob(ImportOutdatedChequesFileJob.class)
                        .withIdentity(FINANCIAL_SERVICES_CENTRE_IMPORT_OUTDATED_CHEQUES.code(),
                                FINANCIAL_SERVICES_CENTRE_IMPORT.code())
                        .usingJobData(FINANCIAL_SERVICES_CENTRE_IMPORT_ROOT_DIR, this.financialServicesCentreImportLocation)
                        .build(), FINANCIAL_SERVICES_CENTRE_IMPORT.code(),
                "Financial Services Centre Outdated Cheques File Import Scheduled"));
    }

    @RequestMapping(params = "targetAction=exportHotlistcardRequestFile")
    public ModelAndView exportHotlistcardRequestFile() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(HotlistCardRequestFileGenerationJob.class)
                        .withIdentity(createIdentity(EXPORT_HOTLISTCARD_REQUEST_FILE.code()), HOTLISTED_CARDS_EXPORT.code())
                        .build(), HOTLISTED_CARDS_EXPORT.code(), "Hotlisted Card Request File Export Scheduled"));

    }
    
    @RequestMapping(params = "targetAction=sendMessages")
    public ModelAndView sendMessages() {
        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, scheduleJobNow(
                JobBuilder.newJob(MessageSenderJob.class)
                        .withIdentity(createIdentity(SEND_MESSAGES.code()), MESSAGES.code())
                        .build(), MESSAGES.code(), "Message Sending Scheduled"));

    }

    @RequestMapping(params = "targetAction=importPrePaidTicketPriceData")
    public ModelAndView importPrePaidTicketPriceData(final LaunchJobCmd cmd) {
        if (cmd.getPriceEffectiveDate() == null) {
            cmd.setMessage("Please sepcify the price effective date");
            return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND, cmd);

        }
        final JobDetail jobDetail = JobBuilder.newJob(ImportPrePaidTicketFileJob.class)
                .withIdentity(createIdentity(UPLOAD_PRE_PAID_TICKET_PRICE_DATA.code()), PRE_PAID_TICKET_DATA_IMPORT.code())
                .build();
        jobDetail.getJobDataMap().put("priceEffectiveDate", cmd.getPriceEffectiveDate());
        jobDetail.getJobDataMap()
                .put(PRE_PAID_TICKETS_ERVICES_CENTRE_IMPORT_ROOT_DIR, this.prePaidTicketServicesCentreImportLocation);

        return new ModelAndView(LAUNCH_JOB_VIEW, COMMAND,
                scheduleJobNow(jobDetail, PRE_PAID_TICKET_DATA_IMPORT.code(), "PrePaid Ticket Data Upload Scheduled"));

    }


    protected LaunchJobCmd scheduleJobNow(JobDetail job, String jobGroup, String message) {
        LaunchJobCmd cmd = new LaunchJobCmd();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(createIdentity(NOW), jobGroup).startNow().build();
        try {
            schedulerWrapper.scheduleJob(job, trigger);
            cmd.setMessage(message);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            cmd.setMessage(e.getMessage());
        }
        return cmd;
    }
}
