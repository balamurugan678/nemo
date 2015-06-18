package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_ACTIVE_STATION_1;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_ACTIVE_STATION_2;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_ACTIVE_STATION_3;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_ALL_STATIONS_1;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_ALL_STATIONS_2;
import static com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants.TEST_ALL_STATIONS_3;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:nemo-tfl-services-context-integration-test.xml" })
public class StationServiceStepDefinition extends BaseServicesStepDefinition {
    public StationServiceStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @When("^I call the Station service with a get request of AllStations$")
    public void whenICallTheAllStationsServiceWithAGetRequest() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/station/allstations")));
    }

    @Then("^I should receive a AllStations get response$")
    public void thenIShouldReceiveAllStationsGetResponse() throws Exception {
        this.testContext.getResultActions().andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(containsString(TEST_ALL_STATIONS_1)))
                        .andExpect(content().string(containsString(TEST_ALL_STATIONS_2))).andExpect(content().string(containsString(TEST_ALL_STATIONS_3)))
                        .andExpect(status().isOk());
    }
 
    @When("^I call the Station service with a get request of ActiveStations$")
    public void whenICallTheActiveStationsServiceWithAGetRequest() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/station/stations")));
    }

    @Then("^I should receive a ActiveStations get response$")
    public void thenIShouldReceiveActiveStationsGetResponse() throws Exception {
        this.testContext.getResultActions().andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(containsString(TEST_ACTIVE_STATION_1)))
                        .andExpect(content().string(containsString(TEST_ACTIVE_STATION_2))).andExpect(content().string(containsString(TEST_ACTIVE_STATION_3)))
                        .andExpect(status().isOk());
    }
}
