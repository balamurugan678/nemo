package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.Gson;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

import cucumber.api.java.Before;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:nemo-tfl-services-context-integration-test.xml" })
public class ProductServiceStepDefinition extends BaseServicesStepDefinition {
    public ProductServiceStepDefinition() {
    }

    @Before
    public void setUp() {
        this.testSetUp();
    }

    @When("^I call the Product service with a get request of ReferenceData$")
    public void whenICallTheReferenceDataServiceWithAGetRequest() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/product/referenceData")));
    }

    @Then("^I should receive a ReferenceData get response$")
    public void thenIShouldReceiveReferenceDataGetResponse() throws Exception {
        this.testContext.getResultActions().andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(containsString("PayAsYouGoAmounts")))
                        .andExpect(content().string(containsString("28 days before expiry"))).andExpect(content().string(containsString("6 Month Travelcard")))
                        .andExpect(status().isOk());
    }
    
    @When("^I call the Product service with a get request of TravelCard$")
    public void whenICallTheGetTravelCardServiceWithAGetRequest(List<PrePaidTicket> prepaidTravelCard) throws Exception {
    	PrePaidTicket prePaidTicket = prepaidTravelCard.get(0);
        String travelCardJson = new Gson().toJson(prePaidTicket);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                post("/product/getTravelcard", this.testContext.getPrePaidTicket()).contentType(MediaType.APPLICATION_JSON).content(travelCardJson)));
    }

    @Then("^I should receive a TravelCard get response$")
    public void thenIShouldReceiveGetTravelCardGetResponse() throws Exception {
        this.testContext.getResultActions().andExpect(jsonPath("duration").value("1Month"))
        				.andExpect(jsonPath("startZone").value(1))
                        .andExpect(jsonPath("formattedStartDate").value("27/12/2014"))
                        .andExpect(status().isOk());
    }
    
    @But("^travelcard should not have any errors$")
    public void travelCardShouldNotHaveAnyErros() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("errors", nullValue()));
    }
    
    @When("^I call the Product service with a get request of PayAsYouGo$")
    public void whenICallTheGetPayAsYouGoServiceWithAGetRequest(List<PayAsYouGo> payAsYouGo) throws Exception {
    	PayAsYouGo payAsYouGoAmount = payAsYouGo.get(0);
        String payAsYouGoJson = new Gson().toJson(payAsYouGoAmount);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                post("/product/getPayAsYouGo", this.testContext.getPayAsYouGo()).contentType(MediaType.APPLICATION_JSON).content(payAsYouGoJson)));
    }

    @Then("^I should receive a PayAsYouGo get response$")
    public void thenIShouldReceiveGetPayAsYouGoGetResponse() throws Exception {
        this.testContext.getResultActions().andExpect(jsonPath("name").value("Pay as you go credit"))
        				.andExpect(jsonPath("price").value(2000))
                        .andExpect(jsonPath("productType").value("Pay as you go"))
                        .andExpect(status().isOk());
    }
    
    @But("^payAsYouGo should not have any errors$")
    public void getPayAsYouGoShouldNotHaveAnyErros() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("errors", nullValue()));
    }
    
    
}
