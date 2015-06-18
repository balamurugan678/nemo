package com.novacroft.nemo.tfl.online.integration_test.step_definition;

import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.cubic.GetCardRequestDataService;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:nemo-tfl-online-context-integration-test.xml"})
public class ViewOysterCardPageStepDefinition extends BaseOnlineStepDefinition {

    @Autowired
    private MockHttpSession session;
    @Autowired
    private GetCardRequestDataService getCardRequestDataService;

    private CubicServiceAccess mockCubicServiceAccess;

    public ViewOysterCardPageStepDefinition() {
    }

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

    @When("^I navigate to the view oyster card page$")
    public void iNavigateToTheViewOysterCardPage() throws Exception {
        securitySetUp();
        this.session.setAttribute(CartAttribute.CARD_ID, this.testContext.getCardDTO().getId());
        this.testContext
                .setResultActions(this.testContext.getMockMvc().perform(get("/ViewOysterCard.htm").session(this.session)));
    }

    @Then("^the view oyster card page should be displayed$")
    public void theViewOysterCardPageShouldBeDisplayed() throws Exception {
        this.testContext.getResultActions().andExpect(status().isOk()).andExpect(view().name(PageView.VIEW_OYSTER_CARD))
                .andExpect(model().attributeExists("manageCardCmd"));

        ManageCardCmd cmd = (ManageCardCmd) this.testContext.getResultActions().andReturn().getModelAndView().getModel()
                .get("manageCardCmd");
        assertEquals(this.testContext.getCardDTO().getId(), cmd.getCardId());
        assertEquals(this.testContext.getCardDTO().getCardNumber(), cmd.getCardNumber());
    }
}
