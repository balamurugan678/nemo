package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.data_service.cubic.GetCardRequestDataService;
import com.novacroft.nemo.tfl.common.integration_test.IntegrationTestConstants;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.services.application_service.OysterCardService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-services-context-integration-test.xml"})

public class CardServiceStepDefinition extends BaseServicesStepDefinition {
    public CardServiceStepDefinition() {
    }
    @Autowired
    private GetCardRequestDataService getCardRequestDataService;
    @Autowired
    private OysterCardService oysterCardService;
    
    private CubicServiceAccess mockCubicServiceAccess;
    
    protected CustomerService mockCustomerService;
   
    @Before
    public void setUp() {
        this.testSetUp();
        this.mockCubicServiceAccess = mock(CubicServiceAccess.class);
        when(this.mockCubicServiceAccess.callCubic(anyString())).thenReturn(new StringBuffer(
                "<CardInfoResponseV2><CardCapability>1</CardCapability><CardDeposit>0</CardDeposit><CardType>10</CardType" +
                        "><CCCLostStolenDateTime></CCCLostStolenDateTime><PrestigeID>030000000258</PrestigeID" +
                        "><PhotocardNumber></PhotocardNumber><Registered>0</Registered><PassengerType>Adult</PassengerType" +
                        "><AutoloadState>1</AutoloadState><DiscountEntitleMent1></DiscountEntitleMent1><DiscountExpiry1" +
                        "></DiscountExpiry1><DiscountEntitleMent2></DiscountEntitleMent2><DiscountExpiry2></DiscountExpiry2" +
                        "><DiscountEntitleMent3></DiscountEntitleMent3><DiscountExpiry3></DiscountExpiry3><HotlistReasons" +
                        "/><CardHolderDetails><Title></Title><FirstName></FirstName><MiddleInitial></MiddleInitial><LastName" +
                        "></LastName><DayTimePhoneNumber></DayTimePhoneNumber><HouseNumber></HouseNumber><HouseName" +
                        "></HouseName><Street></Street><Town></Town><County></County><Postcode></Postcode><EmailAddress" +
                        "></EmailAddress><Password></Password></CardHolderDetails><PPVDetails><Balance>220</Balance><Currency" +
                        ">0</Currency></PPVDetails><PPTDetails><PPTSlot><SlotNumber>1</SlotNumber><Zone>1</Zone><StartDate" +
                        "></StartDate><ExpiryDate></ExpiryDate><State>1</State></PPTSlot><PPTSlot><SlotNumber>2</SlotNumber" +
                        "><Zone>2</Zone><StartDate></StartDate><ExpiryDate></ExpiryDate><State>2</State></PPTSlot><PPTSlot" +
                        "><SlotNumber>3</SlotNumber><Zone>3</Zone><StartDate></StartDate><ExpiryDate></ExpiryDate><State>3" +
                        "</State></PPTSlot></PPTDetails><PendingItems/></CardInfoResponseV2>"));

        ReflectionTestUtils.setField(this.getCardRequestDataService, "cubicServiceAccess", this.mockCubicServiceAccess);
    }

    @When("^The card service is invoked with card number$")
    public void whenICallTheCardServiceWithCardNumberAsInput() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/card/{cardNumber}",IntegrationTestConstants.TEST_USER_OYSTER_CARD_NUMBER)));
    }

    @Then("^I should receive card details for the card number$")
    public void thenIShouldReceiveTheCardDetailsAsResponse() throws Exception {
    	this.testContext.getResultActions().andExpect(status().isOk())
	    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    	.andReturn().getResponse().getContentAsString();
    }
    
    @Then("^the passenger type should be \"(.+)\"$")
    public void thenCheckThePassengerTypeFromResponse(String passengerType) throws Exception {
    	 this.testContext.getResultActions().andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("passengerType").value(passengerType));
    }
    
    @When("^I call the card service with valid Id (\\d+)$")
    public void whenICallTheCardServiceWithValidCustomerId(Long validCustomerId) throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(post("/card/{customerId}",validCustomerId)));
    }

    @Then("^the card should be created successfully$")
    public void thenIShouldReceiveTheSucessResponseWithCardWasCreated() throws Exception {
    	this.testContext.getResultActions().andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn().getResponse().getContentAsString().contains(WebServiceResultAttribute.SUCCESS.name());
    }
    
    @When("^I call the card service with valid customer Id and empty record$")
    public void whenICallTheCardServiceWithValidCustomerIdEmptyRecord() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(post("/card/{customerId}",IntegrationTestConstants.TEST_EXTERNAL_CUSTOMER_ID)));
    }

    @Then("^the card should not be created$")
    public void thenIShouldReceiveTheFailureResponseWithCardWasNotCreated() throws Exception {
    	this.mockCustomerService = mock(CustomerService.class);
    	when(this.mockCustomerService.createCard(anyLong(), anyString())).thenReturn(null);
    	ReflectionTestUtils.setField(this.oysterCardService, "customerService", this.mockCustomerService);
    	this.testContext.getResultActions().andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn().getResponse().getContentAsString().contains(WebServiceResultAttribute.CREATE_CARD_FAILURE.name());
    }

    @When("^I call the card service with invalid Id (\\d+)$")
    public void whenICallTheCardServiceWithInValidCustomerId(Long inValidCustomerId) throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(post("/card/{customerId}",inValidCustomerId)));
    }
    
    @Then("^response should be customer not found$")
    public void thenIShouldReceiveTheErrorResponseAsCustomerNotFound() throws Exception {
       	this.testContext.getResultActions().andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn().getResponse().getContentAsString().contains(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.contentCode());
    }
}
