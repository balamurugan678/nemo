package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportBacsSettlementsFileJob;
import com.novacroft.nemo.tfl.common.util.JobLogUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.Validator;

import java.io.FileFilter;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPenceToPounds;
import static com.novacroft.nemo.common.utils.CurrencyUtil.reverseSign;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ContextConfiguration({"classpath:nemo-tfl-batch-context-integration-test.xml"})
public class ImportBacsRequestsHandledFileStepDefinition extends BaseBatchStepDefinition {

    @Autowired
    protected FileFilter bacsSettlementsFileFilter;
    @Autowired
    protected Validator bacsPaymentSettlementValidator;
    @Autowired
    protected ImportRecordConverter bacsSettlementRecordConverter;
    @Autowired
    protected ImportRecordDispatcher bacsPaymentSettlementDispatcher;

    private ImportBacsSettlementsFileJob importBacsSettlementsFileJob;

    public ImportBacsRequestsHandledFileStepDefinition() {
    }

    @Given("^I have a valid BACS requests handled file record$")
    public void iHaveAValidBacsRequestsHandledFileRecord() throws Throwable {
        testContext.setBacsRequestHandledRecord(
                new String[]{testContext.getBacsSettlementDTO().getSettlementNumber().toString(), String.format("%.2f",
                        reverseSign(convertPenceToPounds(testContext.getBacsSettlementDTO().getAmount()))),
                        testContext.getBacsSettlementDTO().getPayeeName(),
                        testContext.getBacsSettlementDTO().getSettlementNumber().toString(), formatDate(getAug20())});
    }

    @Given("^I have a BACS requests handled file record with a non-matching amount$")
    public void iHaveABacsRequestsHandledFileRecordWithANonMatchingAmount() throws Throwable {
        testContext.setBacsRequestHandledRecord(
                new String[]{testContext.getBacsSettlementDTO().getSettlementNumber().toString(), String.format("%.2f",
                        reverseSign(convertPenceToPounds(testContext.getBacsSettlementDTO().getAmount() + 99))),
                        testContext.getBacsSettlementDTO().getPayeeName(),
                        testContext.getBacsSettlementDTO().getSettlementNumber().toString(), formatDate(getAug20())});
    }

    @Given("^I have a BACS requests handled file record with a non-matching payee$")
    public void iHaveABacsRequestsHandledFileRecordWithANonMatchingPayee() throws Throwable {
        testContext.setBacsRequestHandledRecord(
                new String[]{testContext.getBacsSettlementDTO().getSettlementNumber().toString(), String.format("%.2f",
                        reverseSign(convertPenceToPounds(testContext.getBacsSettlementDTO().getAmount()))),
                        testContext.getBacsSettlementDTO().getPayeeName() + "XX",
                        testContext.getBacsSettlementDTO().getSettlementNumber().toString(), formatDate(getAug20())});
    }

    @When("^I load the BACS requests handled file record$")
    public void iLoadTheBacsRequestsHandledFileRecord() throws Throwable {
        importBacsSettlementsFileJob = new ImportBacsSettlementsFileJob();
        setField(importBacsSettlementsFileJob, "bacsSettlementsFileFilter", bacsSettlementsFileFilter);
        setField(importBacsSettlementsFileJob, "bacsPaymentSettlementValidator", bacsPaymentSettlementValidator);
        setField(importBacsSettlementsFileJob, "bacsSettlementRecordConverter", bacsSettlementRecordConverter);
        setField(importBacsSettlementsFileJob, "bacsPaymentSettlementDispatcher", bacsPaymentSettlementDispatcher);

        testContext.setJobLogDTO(JobLogUtil.createLog("ImportBacsRequestsHandledCubicFile"));
        testContext.setJobDataMap(new JobDataMap());

        invokeMethod(importBacsSettlementsFileJob, "handleRecord", testContext.getBacsRequestHandledRecord(),
                testContext.getJobLogDTO(), testContext.getJobDataMap());
    }

    @Then("^the BACS requests handled file record error should be logged$")
    public void theBacsRequestsHandledFileRecordErrorShouldBeLogged() throws Throwable {
        assertTrue(testContext.getJobLogDTO().getLog().contains("Error"));
    }
}
