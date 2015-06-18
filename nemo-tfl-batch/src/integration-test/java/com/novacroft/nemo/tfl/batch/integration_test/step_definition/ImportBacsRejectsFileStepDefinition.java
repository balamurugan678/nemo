package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
import com.novacroft.nemo.tfl.batch.job.financial_services_centre.ImportBacsFailureFileJob;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;
import com.novacroft.nemo.tfl.common.util.JobLogUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.Validator;

import java.io.FileFilter;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPenceToPounds;
import static com.novacroft.nemo.common.utils.CurrencyUtil.reverseSign;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug21;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ContextConfiguration({"classpath:nemo-tfl-batch-context-integration-test.xml"})
public class ImportBacsRejectsFileStepDefinition extends BaseBatchStepDefinition {

    @Autowired
    protected FileFilter bacsFailuresFileFilter;
    @Autowired
    protected Validator bacsFailureValidator;
    @Autowired
    protected ImportRecordConverter bacsFailedRecordConverter;
    @Autowired
    protected ImportRecordDispatcher bacsPaymentFailedDispatcher;

    private ImportBacsFailureFileJob importBacsFailureFileJob;

    public ImportBacsRejectsFileStepDefinition() {
    }

    @Given("^I have a valid BACS rejects file record$")
    public void iHaveAValidBacsRejectsFileRecord() throws Throwable {
        testContext.setBacsRejectRecord(new String[]{testContext.getBacsSettlementDTO().getSettlementNumber().toString(),
                String.format("%.2f", reverseSign(convertPenceToPounds(testContext.getBacsSettlementDTO().getAmount()))),
                BACSRejectCodeEnum.I.name(), formatDate(getAug21())});
    }

    @Given("^I have a BACS rejects file record with a non-matching amount$")
    public void iHaveABacsRejectsFileRecordWithANonMatchingAmount() throws Throwable {
        testContext.setBacsRejectRecord(new String[]{testContext.getBacsSettlementDTO().getSettlementNumber().toString(),
                String.format("%.2f", reverseSign(convertPenceToPounds(testContext.getBacsSettlementDTO().getAmount() + 99))),
                BACSRejectCodeEnum.I.name(), formatDate(getAug21())});
    }

    @When("^I load the BACS rejects file record$")
    public void iLoadTheBacsRejectsFileRecord() throws Throwable {
        importBacsFailureFileJob = new ImportBacsFailureFileJob();
        setField(importBacsFailureFileJob, "bacsFailuresFileFilter", bacsFailuresFileFilter);
        setField(importBacsFailureFileJob, "bacsFailureValidator", bacsFailureValidator);
        setField(importBacsFailureFileJob, "bacsFailedRecordConverter", bacsFailedRecordConverter);
        setField(importBacsFailureFileJob, "bacsPaymentFailedDispatcher", bacsPaymentFailedDispatcher);

        testContext.setJobLogDTO(JobLogUtil.createLog("ImportBacsRejectsCubicFile"));
        testContext.setJobDataMap(new JobDataMap());

        invokeMethod(importBacsFailureFileJob, "handleRecord", testContext.getBacsRejectRecord(), testContext.getJobLogDTO(),
                testContext.getJobDataMap());
    }
}
