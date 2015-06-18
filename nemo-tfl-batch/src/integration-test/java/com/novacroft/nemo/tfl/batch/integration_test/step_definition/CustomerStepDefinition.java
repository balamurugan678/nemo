package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_USER_EMAIL;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_USER_OYSTER_CARD_NUMBER;

@ContextConfiguration({"classpath:nemo-tfl-batch-context-integration-test.xml"})
public class CustomerStepDefinition extends BaseBatchStepDefinition {

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;

    public CustomerStepDefinition() {
    }

    @Given("^I have a customer$")
    public void iHaveACustomer() {
        this.testContext.reset();
        testContext.setCustomerDTO(customerDataService.findByUsernameOrEmail(TEST_USER_EMAIL));
    }

    @Given("^the customer has an Oyster card$")
    public void theCustomerHasAnOysterCard() {
        testContext.setCardDTO(cardDataService
                .findByCustomerIdAndCardNumber(testContext.getCustomerDTO().getId(), TEST_USER_OYSTER_CARD_NUMBER));
    }
}
