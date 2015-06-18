package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.Validator;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicDateConstant;
import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.integration_test.util.IntegrationTestConstants;
import com.novacroft.nemo.tfl.batch.job.cubic.ImportAdHocDistributionCubicFileJob;
import com.novacroft.nemo.tfl.common.util.JobLogUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration({"classpath:nemo-tfl-batch-context-integration-test.xml"})
public class ImportAdHocDistributionCubicFileStepDefinition extends BaseBatchStepDefinition {

    @Autowired
    protected Validator adHocDistributionValidator;
    @Autowired
    protected ImportRecordConverter adHocDistributionRecordConverter;
    @Autowired
    protected ImportRecordDispatcher adHocDistributionDispatcher;
    @Autowired
    protected MessageSource messageSource;

    private ImportAdHocDistributionCubicFileJob importAdHocDistributionCubicFileJob;


    @Given("^I have a valid AdHoc Load PickedUp file record$")
    public void iHaveAValidAdHocLoadPickedUpFileRecord() throws Throwable {
        testContext.setAdHocLoadPickedUpRecord(
                new String[]{testContext.getCardDTO().getCardNumber(), IntegrationTestConstants.ANGEL_STATION_CODE,
                        new org.joda.time.DateTime().toString(CubicDateConstant.DATE_AND_TIME_FORMAT),
                        testContext.getRequestSequenceNumber().toString(),StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,
                        IntegrationTestConstants.ZERO,IntegrationTestConstants.STATUS_OK,StringUtil.EMPTY_STRING});
    }
    
    @Given("^I have an invalid AdHoc Load PickedUp file record$")
    public void iHaveAnInValidAdHocLoadPickedUpFileRecord() throws Throwable {
        testContext.setAdHocLoadPickedUpRecord(
                new String[]{testContext.getCardDTO().getCardNumber(), IntegrationTestConstants.ANGEL_STATION_CODE,
                        new org.joda.time.DateTime().toString(CubicDateConstant.DATE_AND_TIME_FORMAT),
                        StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,StringUtil.EMPTY_STRING,
                        IntegrationTestConstants.ZERO,IntegrationTestConstants.STATUS_OK,StringUtil.EMPTY_STRING});
    }

    @When("^I load the AdHoc Load PickedUp file record$")
    public void iLoadTheBacsRequestsHandledFileRecord() throws Throwable {
        importAdHocDistributionCubicFileJob = new ImportAdHocDistributionCubicFileJob();
        setField(importAdHocDistributionCubicFileJob, "adHocDistributionValidator", adHocDistributionValidator);
        setField(importAdHocDistributionCubicFileJob, "adHocDistributionRecordConverter", adHocDistributionRecordConverter);
        setField(importAdHocDistributionCubicFileJob, "adHocDistributionDispatcher", adHocDistributionDispatcher);
        setField(importAdHocDistributionCubicFileJob, "messageSource", messageSource);

        testContext.setJobLogDTO(JobLogUtil.createLog("ImportAdHocDistributionCubicFile"));
        testContext.setJobDataMap(new JobDataMap());

        invokeMethod(importAdHocDistributionCubicFileJob, "handleRecord", testContext.getAdHocLoadPickedUpRecord(),
                testContext.getJobLogDTO(), testContext.getJobDataMap());
    }
    
    @Then("^the AdHoc load settlemnt requests file record error should be logged$")
    public void theBacsRequestsHandledFileRecordErrorShouldBeLogged() throws Throwable {
        assertTrue(testContext.getJobLogDTO().getLog().contains("Error"));
    }

   }
