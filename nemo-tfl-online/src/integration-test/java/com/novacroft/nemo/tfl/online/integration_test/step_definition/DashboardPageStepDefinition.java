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
public class DashboardPageStepDefinition extends BaseOnlineStepDefinition {

    public DashboardPageStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @When("^I navigate to the dashboard page$")
    public void whenINavigateToTheDashboardPage() throws Exception {
        securitySetUp();
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/Dashboard.htm")));
    }

    @Then("^the dashboard page should be displayed$")
    public void thenTheDashboardPageShouldBeDisplayed() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(view().name(PageView.DASHBOARD));
    }
}
