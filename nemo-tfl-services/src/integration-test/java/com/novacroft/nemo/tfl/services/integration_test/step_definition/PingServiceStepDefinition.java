package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-services-context-integration-test.xml"})
public class PingServiceStepDefinition extends BaseServicesStepDefinition {
    public PingServiceStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @When("^I call the ping service with a get request$")
    public void whenICallThePingServiceWithAGetRequest() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/ping")));
    }

    @Then("^I should receive a ping get response$")
    public void thenIShouldReceiveAPingGetResponse() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(jsonPath("method").value("GET"));
    }

    @When("^I call the ping service with a post request$")
    public void whenICallThePingServiceWithAPostRequest() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(post("/ping")));
    }

    @Then("^I should receive a ping post response$")
    public void thenIShouldReceiveAPingPostResponse() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(jsonPath("method").value("POST"));
    }
}
