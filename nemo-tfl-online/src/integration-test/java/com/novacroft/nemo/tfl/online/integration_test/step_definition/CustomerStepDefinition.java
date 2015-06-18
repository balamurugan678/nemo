package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_USER_EMAIL;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_USER_OYSTER_CARD_NUMBER;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-online-context-integration-test.xml"})
public class CustomerStepDefinition extends BaseOnlineStepDefinition {

    @Autowired
    private CustomerDataService customerDataService;
    @Autowired
    private CardDataService cardDataService;
    
    @Autowired
    protected OrderDataService orderDataService;

    public CustomerStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @Given("^I have a customer$")
    public void iHaveACustomer() {
        if (null == this.testContext.getCustomerDTO()) {
            this.testContext.setCustomerDTO(this.customerDataService.findByUsernameOrEmail(TEST_USER_EMAIL));
        }
        assertNotNull(this.testContext.getCustomerDTO());
    }

    @Given("^the customer has an Oyster card$")
    public void theCustomerHasAnOysterCard() {
        if (null == this.testContext.getCardDTO()) {
            this.testContext.setCardDTO(this.cardDataService
                    .findByCustomerIdAndCardNumber(this.testContext.getCustomerDTO().getId(), TEST_USER_OYSTER_CARD_NUMBER));
        }
        assertNotNull(this.testContext.getCardDTO());
    }
    
    @Given("^the customer has completed an order$")
    public void theCustomerHasCompletedAnOrder() {
        if (null == this.testContext.getOrders() ) {
            this.testContext.setOrders(this.orderDataService
                    .findByCustomerId(this.testContext.getCustomerDTO().getId()));
        }
        assertNotNull(this.testContext.getOrders());
        assertNotEquals(0, this.testContext.getOrders().size());
    }
}
