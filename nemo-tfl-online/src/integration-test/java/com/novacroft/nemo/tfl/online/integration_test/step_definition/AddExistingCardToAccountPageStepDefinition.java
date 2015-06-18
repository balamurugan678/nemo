package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.constant.PageView;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-online-context-integration-test.xml"})
public class AddExistingCardToAccountPageStepDefinition extends BaseOnlineStepDefinition {

    public AddExistingCardToAccountPageStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @When("^I navigate to the Add Card page$")
    public void whenINavigateToTheAddCardPage() throws Exception {
        securitySetUp();
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/AddExistingCardToAccount.htm")));
    }

    @Then("^the Add Card page should be displayed$")
    public void thenTheAddCardPageShouldBeDisplayed() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(view().name(PageView.ADD_EXISTING_CARD_TO_ACCOUNT));
    }
}
