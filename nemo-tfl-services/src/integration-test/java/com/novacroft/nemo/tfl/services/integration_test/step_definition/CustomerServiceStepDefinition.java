package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.Gson;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;

import cucumber.api.java.Before;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-services-context-integration-test.xml"})
public class CustomerServiceStepDefinition extends BaseServicesStepDefinition {
    
    private Map<String, Customer> customerMap;
    
    @Autowired
    protected CustomerDataService customerDataService;
    
    @Autowired
    protected AddressDataService addressDataService;
    
    @Autowired
    protected ContactDataService contactDataService;
    
    @Before
    public void setUp() {
        this.testSetUp();
    }
    
    @Given("^I have these customers$")
    public void iHaveTheseCustomers(List<Customer> customersList) {
        customerMap = new HashMap<String, Customer>();
        for(Customer customer : customersList){
            String emailAddress = customer.getEmailAddress();
            customerMap.put(emailAddress, customer);
        }
    }

    @When("^the create customer web service is invoked by passing customer with emailaddress (.+)$")
    public void theCreateCustomerWebServiceIsInvokedByPassingCustomerWithEmailAddress(String emailAddress) throws Exception {
        Customer customer = customerMap.get(emailAddress);
        String customerJson = new Gson().toJson(customer);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        put("/customer").contentType(MediaType.APPLICATION_JSON).content(customerJson).header("Authorization", "Basic dGZsLXVzZXI6dzNiNTNydjFjM3MjJA==")));

    }

    @Then("^a new customer is created and returned with houseNameNumber (.+) and street (.+) and username (.+)$")
    public void thenNewCustomerIsCreatedAndReturned(String houseNameNumber, String street,  String username) throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("street").value(street)).andExpect(jsonPath("houseNameNumber").value(houseNameNumber)).andExpect(jsonPath("username").value(username));
    }
    
    @When("^the createCustomer web service is invoked with invalid customerJSON$")
    public void theCreateCustomerWebServiceIsInvokedWithInvalidCustomerJSON(String invalidCustomerJson) throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        put("/customer").contentType(MediaType.APPLICATION_JSON).content(invalidCustomerJson).header("Authorization", "Basic dGZsLXVzZXI6dzNiNTNydjFjM3MjJA==")));
    }

    @Then("^a customer with errors must be created and returned$")
    public void thenCustomerWithErrorsMustBeCreatedAndReturned() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("errors", notNullValue()));
    }
    
    @But("^customer should not have valid id$")
    public void customerShouldNotHaveValidId() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("id", nullValue()));
    }
    
    @But("^customer should not have any errors$")
    public void customerShouldNotHaveAnyErros() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("errors", nullValue()));
    }
    
    @Given("^I have a customer with emailaddress (.+)$")
    public void iHaveCustomerWithId(String emailAddress) {
        this.testContext.setCustomerDTO(this.customerDataService.findByUsernameOrEmail(emailAddress));
        assertNotNull(this.testContext.getCustomerDTO());
    }
    
    @When("^the update customer web service is invoked$")
    public void theUpdateCustomerWebServiceIsInvoked(List<Customer> updatedCustomer) throws Exception {
        Customer customer = updatedCustomer.get(0);
        customer.setId(this.testContext.getCustomerDTO().getExternalId());
        String customerJson = new Gson().toJson(customer);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        put("/customer/{customerId}", this.testContext.getCustomerDTO().getExternalId()).contentType(MediaType.APPLICATION_JSON).content(customerJson).header("Authorization", "Basic dGZsLXVzZXI6dzNiNTNydjFjM3MjJA==")));

    }

    @Then("^customer is updated and returned with houseNameNumber (.+) and street (.+) and username (.+)$")
    public void theCustomerIsUpdatedAndReturnedWith(String houseNameNumber, String street,  String username) throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("street").value(street)).andExpect(jsonPath("houseNameNumber").value(houseNameNumber)).andExpect(jsonPath("username").value(username));
    }
    
    @When("^the get customer web service is invoked$")
    public void theGetCustomerWebserviceIsInvoked() throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/customer/{customerId}" , this.testContext.getCustomerDTO().getExternalId())));
    }
    
    @When("^the get customer web service is invoked with id (\\d+)$")
    public void theGetCustomerWebserviceIsInvokedWithId(Long customerId) throws Exception {
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/customer/{customerId}" , customerId)));
    }

    @Then("^check and return existing customer with firstName (.+) and lastName (.+)$")
    public void checkAndReturnExistingCustomerWithFirstNameAndLastName(String firstName, String LastName) throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("firstName").value(firstName)).andExpect(jsonPath("lastName").value(LastName));
    }
    
        
    @Then("^a customer with error should be returned$")
    public void aCustomerWithErrorShouldBeReturned() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("errors", notNullValue()));
    }
    
    
    @When("^the delete customer web service is invoked$")
    public void theDeleteCustomerWebServiceIsInvoked(List<DeleteCustomer> customer) throws Exception {
        DeleteCustomer customerToBeDeleted = customer.get(0);
        String customerJson = new Gson().toJson(customerToBeDeleted);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        post("/customer/{customerId}", this.testContext.getCustomerDTO().getExternalId()).contentType(MediaType.APPLICATION_JSON).content(customerJson).header("Authorization", "Basic dGZsLXVzZXI6dzNiNTNydjFjM3MjJA==")));

    }

    
    @When("^the delete customer web service is invoked with id (\\d+)$")
    public void theDeleteCustomerWebServiceIsInvokedWithCustomerId(Long customerId, List<DeleteCustomer> customer) throws Exception {
        DeleteCustomer customerToBeDeleted = customer.get(0);
        String deleteCustomerJson = new Gson().toJson(customerToBeDeleted);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        post("/customer/{customerId}", customerId).contentType(MediaType.APPLICATION_JSON).content(deleteCustomerJson).header("Authorization", "Basic dGZsLXVzZXI6dzNiNTNydjFjM3MjJA==")));

    }
    
    @Then("^I should receive the success response indicating customer is deleted successfully$")
    public void iShouldReceiveSuccessResponseIndicatingCustomerIsDeleted() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString().contains(WebServiceResultAttribute.SUCCESS.name());;
    }
    
    @Then("^I should receive the customer not found response$")
    public void iShouldReceiveCustomerNotFoundResponse() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString().contains(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.contentCode());
    }

  }
