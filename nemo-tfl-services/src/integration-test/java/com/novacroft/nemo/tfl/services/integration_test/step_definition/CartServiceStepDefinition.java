package com.novacroft.nemo.tfl.services.integration_test.step_definition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.cubic.GetCardRequestDataService;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.CheckoutRequest;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListResult;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-services-context-integration-test.xml"})
public class CartServiceStepDefinition extends BaseServicesStepDefinition {

    @Autowired
    private GetCardRequestDataService getCardRequestDataService;

    private CubicServiceAccess mockCubicServiceAccess;

    @Autowired
    private TravelCardService travelCardService;

    @Autowired
    private PayAsYouGoService payAsYouService;

    @Autowired
    private CardDataService cardDataServiceImpl;

    @Autowired
    private CartDataService cartDataServiceImpl;

    private CardUpdateService mockCardUpdateService;

    @Autowired
    private CustomerDataService customerDataService;

    private Gson gson;

    private Cart cart;

    @Before
    public void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(date.getTime());
            }

        });
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer() {

            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(json.getAsLong());
                return calendar.getTime();
            }
        });
        gson = gsonBuilder.create();

        this.mockCubicServiceAccess = mock(CubicServiceAccess.class);
        when(this.mockCubicServiceAccess.callCubic(anyString())).thenReturn(
                        new StringBuffer("<CardInfoResponseV2><CardCapability>1</CardCapability><CardDeposit>0</CardDeposit>"
                                        + "<CardType>10</CardType><PrestigeID>030000000515</PrestigeID><PhotocardNumber>"
                                        + "</PhotocardNumber><Registered>0</Registered><PassengerType>Adult</PassengerType>"
                                        + "<AutoloadState>1</AutoloadState><DiscountEntitleMent1></DiscountEntitleMent1><DiscountExpiry1>"
                                        + "</DiscountExpiry1><DiscountEntitleMent2></DiscountEntitleMent2><DiscountExpiry2></DiscountExpiry2>"
                                        + "<DiscountEntitleMent3></DiscountEntitleMent3><DiscountExpiry3></DiscountExpiry3><HotlistReasons/>"
                                        + "<CardHolderDetails><Title></Title><FirstName></FirstName><MiddleInitial></MiddleInitial><LastName>"
                                        + "</LastName><DayTimePhoneNumber></DayTimePhoneNumber><HouseNumber></HouseNumber>"
                                        + "<HouseName></HouseName><Street></Street><Town></Town><County></County><Postcode></Postcode>"
                                        + "<EmailAddress></EmailAddress><Password></Password></CardHolderDetails><PPVDetails>"
                                        + "<Balance>220</Balance><Currency>0</Currency></PPVDetails><PPTDetails><PPTSlot>"
                                        + "<SlotNumber>1</SlotNumber><Zone>1</Zone><StartDate></StartDate><ExpiryDate></ExpiryDate>"
                                        + "<State>1</State></PPTSlot><PPTSlot><SlotNumber>2</SlotNumber><Zone>2</Zone><StartDate>"
                                        + "</StartDate><ExpiryDate></ExpiryDate><State>2</State></PPTSlot><PPTSlot><SlotNumber>3</SlotNumber>"
                                        + "<Zone>3</Zone><StartDate></StartDate><ExpiryDate></ExpiryDate><State>3</State></PPTSlot>"
                                        + "</PPTDetails><PendingItems/></CardInfoResponseV2>"));

        ReflectionTestUtils.setField(this.getCardRequestDataService, "cubicServiceAccess", this.mockCubicServiceAccess);
    }

    public static final Object unwrapProxy(Object bean) throws Exception {
        if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
            Advised advised = (Advised) bean;
            bean = advised.getTargetSource().getTarget();
        }
        return bean;
    }

    public CartServiceStepDefinition() {
    }

    @When("^I call create cart with card number (.+)$")
    public void whenICallCreateCartWithcardId(String cardNumber) throws Exception {
        CardDTO cardDTO = cardDataServiceImpl.findByCardNumber(cardNumber);
        this.testContext.setCardDTO(cardDTO);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(post("/cart/{cardId}", cardDTO.getExternalId())));
    }

    @Then("^I should receive a cart with card number (.+)$")
    public void thenIShouldReceiveACartWithCardId(String cardNumber) throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk())
                        .andExpect(jsonPath("cardId").value(this.testContext.getCardDTO().getExternalId().intValue()));
    }

    @When("^I retrieve a cart$")
    public void whenIRetrieveACartWithId() throws Exception {
        CartDTO cartDTO = cartDataServiceImpl.findByCardId(this.testContext.getCardDTO().getId());
        this.testContext.setCartDTO(cartDTO);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/cart/{cartId}", this.testContext.getCartDTO().getExternalId())));
    }

    @Then("^I should receive a cart$")
    public void thenIShouldReceiveACartWithId() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk())
                        .andExpect(jsonPath("id").value(this.testContext.getCartDTO().getExternalId().intValue()));
    }

    @When("^I have a  cart$")
    public void whenIupdatTheCartWithCartId() throws Exception {
        cart = new Cart();
        cart.setId(this.testContext.getCartDTO().getExternalId());
        cart.setCardId(this.testContext.getCartDTO().getCardId());
    }

    @And("^I have a travel card$")
    public void thenIhaveATravelCard(List<Item> items) throws Exception {
        List<Item> itemsList = new ArrayList<Item>();
        itemsList.add(items.get(0));
        this.cart.setCartItems(itemsList);
    }

    @Then("^I have a pay  as you go$")
    public void thenIHaveAPayAsYouGo(List<Item> items) throws Exception {
        this.cart.getCartItems().add(items.get(0));
    }

    @Then("^I should update the cart$")
    public void thenIShouldUpdateTheCart() throws Exception {
        String json_String = gson.toJson(this.cart);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        put("/cart/{cartId}", this.cart.getId()).contentType(MediaType.APPLICATION_JSON).content(json_String)));
    }

    @Then("^I should retrieve a cart  with a travel card$")
    public void thenIShouldRetrieveACartWithTravelCard(List<Item> items) throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        Cart cart = gson.fromJson(resultJsonString, Cart.class);
        for (Item item : cart.getCartItems()) {
            if (item.getProductType().equalsIgnoreCase("Travelcard")) {
                assertEquals(item.getEndZone(), items.get(0).getEndZone());
            }
        }
    }

    @Then("^I should retrieve a cart  with a pay  as you go$")
    public void thenIShouldRetrieveACartWithPayAsYouGo(List<Item> items) throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        Cart cart = gson.fromJson(resultJsonString, Cart.class);
        for (Item item : cart.getCartItems()) {
            if (item.getProductType().equalsIgnoreCase("Pay as you go")) {
                assertEquals(item.getPrice(), items.get(0).getPrice());
            }
        }
    }

    @When("^I have customer with customer email \"(.+)\"$")
    public void whenIHaveCustomerId(String customerEmail) throws Exception {
        CustomerDTO customerDTO = customerDataService.findByUsernameOrEmail(customerEmail);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(get("/carts/customer/{customerId}", customerDTO.getExternalId())));
    }

    @Then("^I should get the list of carts$")
    public void thenIShouldgetTheListOfCarts() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        Type listType = new TypeToken<ListResult<Cart>>() {
        }.getType();
        ListResult<Cart> listResult = gson.fromJson(resultJsonString, listType);
        for (Cart cart : listResult.getResultList()) {
            assertNotNull(cart.getId());
        }
    }
    
    @When("^I begin checkout with checkoutrequest$")
    public void whenIBeginCheckOutWithcartId(List<CheckoutRequest> checkOutRequests) throws Exception {
        String json_string = gson.toJson(checkOutRequests.get(0));
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        post("/cart/{cartId}/checkout/start", this.testContext.getCartDTO().getExternalId()).contentType(MediaType.APPLICATION_JSON)
                                        .content(json_string)));
    }
    
    @Then("^I should get the checkout result$")
    public void IShouldGetCheckOutResult() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        CheckoutResult checkoutResult = gson.fromJson(resultJsonString, CheckoutResult.class);
        this.testContext.setCheckOutResult(checkoutResult);
        assertNotNull(checkoutResult.getOrderId());
    }

    @When("^I authorise cart with checkout request$")
    public void whenIAuthoriseCheckOutWithcartId(List<CheckoutRequest> checkOutRequests) throws Exception {
        CheckoutRequest checkOutRequest = checkOutRequests.get(0);
        checkOutRequest.setPaymentAuthoristationReference("987654321");
        checkOutRequest.setOrderId(this.testContext.getCheckOutResult().getOrderId());
        checkOutRequest.setPaymentCardSettlementId(this.testContext.getCheckOutResult().getPaymentSettlementId());
        String json_string = gson.toJson(checkOutRequest);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        post("/cart/{cartId}/checkout/authorise", this.testContext.getCartDTO().getExternalId()).contentType(
                                        MediaType.APPLICATION_JSON).content(json_string)));
    }

    @Then("^I should get authorise CheckoutResult$")
    public void IShouldGetAuthoriseCheckOutResult() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        CheckoutResult checkoutResult = gson.fromJson(resultJsonString, CheckoutResult.class);
        assertEquals("SUCCESS", checkoutResult.getResult());
    }

    @When("^I fail cart with checkout request$")
    public void whenIFailCheckOutWithcartId(List<CheckoutRequest> checkOutRequests) throws Exception {
        CheckoutRequest checkOutRequest = checkOutRequests.get(0);
        checkOutRequest.setOrderId(this.testContext.getCheckOutResult().getOrderId());
        checkOutRequest.setPaymentCardSettlementId(this.testContext.getCheckOutResult().getPaymentSettlementId());
        String json_string = gson.toJson(checkOutRequest);
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        put("/cart/{cartId}/checkout/authorise/fail", this.testContext.getCartDTO().getExternalId()).contentType(
                                        MediaType.APPLICATION_JSON)
                                        .content(json_string)));
    }

    @Then("^I should get  fail CheckoutResult$")
    public void IShouldGetFailCheckOutResult() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        CheckoutResult checkoutResult = gson.fromJson(resultJsonString, CheckoutResult.class);
        assertEquals("SUCCESS", checkoutResult.getResult());
    }

    @When("^I complete  cart with checkout request$")
    public void whenICompleteCheckOutWithcartId(List<CheckoutRequest> checkOutRequests) throws Exception {



        this.mockCardUpdateService = mock(CardUpdateService.class);
        when(mockCardUpdateService.requestCardUpdatePrePayValue(anyString(), anyLong(), anyInt(), anyInt(), anyString())).thenReturn(
                      (int)new Date().getTime());
        when(mockCardUpdateService.requestCardUpdatePrePayTicket(anyString(), anyInt(), anyString(), anyString(), anyInt(), anyLong())).thenReturn(
                        (int) new Date().getTime() + 1);

        TravelCardService pc = (TravelCardService) unwrapProxy(travelCardService);
        ReflectionTestUtils.setField(pc, "cardUpdateService", this.mockCardUpdateService);
        PayAsYouGoService pc1 = (PayAsYouGoService) unwrapProxy(payAsYouService);
        ReflectionTestUtils.setField(pc1, "cardUpdateService", this.mockCardUpdateService);

        CheckoutRequest checkOutRequest = checkOutRequests.get(0);
        checkOutRequest.setOrderId(this.testContext.getCheckOutResult().getOrderId());
        checkOutRequest.setPaymentCardSettlementId(this.testContext.getCheckOutResult().getPaymentSettlementId());
        String json_string = gson.toJson(checkOutRequest);


        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        post("/cart/{cartId}/checkout/complete", this.testContext.getCartDTO().getExternalId()).contentType(MediaType.APPLICATION_JSON).content(json_string)));
    }

    @Then("^I should get  complete CheckoutResult$")
    public void IShouldGetCompleteCheckOutResult() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        CheckoutResult checkoutResult = gson.fromJson(resultJsonString, CheckoutResult.class);
        assertEquals("SUCCESS", checkoutResult.getResult());
    }

    @When("^I delete cart with customer email \"(.+)\"")
    public void whenIdeleteCart(String customerEmail) throws Exception {
        whenICallCreateCartWithcardId("030000000515");
        CustomerDTO customerDTO = customerDataService.findByUsernameOrEmail(customerEmail);
        CartDTO cartDTO = cartDataServiceImpl.findByCardId(this.testContext.getCardDTO().getId());
        this.testContext.setResultActions(this.testContext.getMockMvc().perform(
                        delete("/cart/{cartId}/customer/{customerId}", cartDTO.getExternalId(), customerDTO.getExternalId())));
    }

    @Then("^I should get webServiceResult$")
    public void iShouldGetWebServiceResult() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.testContext.getResultActions().andExpect(status().isOk()).andReturn().getResponse();
        String resultJsonString = mockHttpServletResponse.getContentAsString();
        WebServiceResult webServiceResult = gson.fromJson(resultJsonString, WebServiceResult.class);
        assertEquals("SUCCESS", webServiceResult.getResult());
    }

}


