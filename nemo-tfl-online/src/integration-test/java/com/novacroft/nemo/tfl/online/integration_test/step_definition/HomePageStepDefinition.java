package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-online-context-integration-test.xml"})
public class HomePageStepDefinition extends BaseOnlineStepDefinition {

    public HomePageStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @When("^I go to the application root URL$")
    public void iGoToTheApplicationRootUrl() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/OysterHome.htm")));
    }

    @When("^I log in$")
    public void iLogIn() throws Exception {
        this.testContext.setResultActions(
                this.testContext.getMockMvc().perform(post("/Login.htm").param("username", "nemo").param("password", "nemo")));
    }

    @Then("^the home page should be displayed$")
    public void theHomePageShouldBeDisplayed() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(view().name(PageView.OYSTER_HOME));
    }

    @Then("^the dash board page should be displayed$")
    public void theDashBoardPageShouldBeDisplayed() throws Exception {
        this.testContext.getResultActions().andExpect(status().is(302)).andExpect(redirectedUrl(PageUrl.DASHBOARD));
    }
}
