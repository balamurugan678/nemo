package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_3;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EMAIL_ADDRESS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.SR_FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.SR_LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.SR_POSTCODE;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TOKEN;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TOKEN_BLANK;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getSearchResultObject;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.EventTestUtil.getApplicationEventDTO1;
import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.WEB_ACCOUNT_ID_1;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.PERSONAL_DETAILS;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CUSTOMER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AgentService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicCardService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.DeactivateCustomerValidator;
import com.novacroft.nemo.tfl.common.form_validator.PersonalDetailsValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardStatusDTO;

public class CustomerControllerTest {

    private CustomerController controller;
    private CustomerController mockController;
    private PersonalDetailsService personalDetailsService;
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private PersonalDetailsCmdImpl personalDetailsCmdImpl;
    private BeanPropertyBindingResult mockBindingResult;
    private CardDataService cardDataService;
    private ApplicationEventService applicationEventService;
    private CustomerNameValidator customerNameValidator;
    private AddressValidator addressValidator;
    private CustomerService customerService;
    private CustomerDataService customerDataService;
    private AgentService agentService;
    private DeactivateCustomerValidator mockDeactivateCustomerValidator;
    private PersonalDetailsValidator mockPersonalDetailsValidator;
    private SystemParameterService systemParameterService;

    private Model mockModel;
    private CubicCardService mockCubicCardService;
    private LocationSelectListService mockLocationSelectListService;
    private SelectListService mockSelectListService;
    private CountrySelectListService mockCountrySelectListService;
    private SelectListDTO mockSelectListDTO;

    @Before
    public void setup() {
        controller = new CustomerController();
        personalDetailsService = mock(PersonalDetailsService.class);
        controller.personalDetailsService = personalDetailsService;
        personalDetailsCmdImpl = getTestPersonalDetailsCmd1();
        personalDetailsCmdImpl.setWebAccountId(null);
        when(personalDetailsService.getPersonalDetailsByCustomerId(anyLong())).thenReturn(personalDetailsCmdImpl);
        personalDetailsCmdImpl.setShowWebAccountDeactivationEnableFlag(true);
        mockRequest = mock(HttpServletRequest.class);
        mockSession = mock(HttpSession.class);
        mockDeactivateCustomerValidator = mock(DeactivateCustomerValidator.class);
        mockPersonalDetailsValidator = mock(PersonalDetailsValidator.class);
        systemParameterService = mock(SystemParameterService.class);

        when(mockRequest.getParameter("id")).thenReturn(CUSTOMER_ID_1.toString());
        doNothing().when(mockRequest).setAttribute(anyString(), anyObject());
        PersonalDetailsCmdImpl pdc = mock(PersonalDetailsCmdImpl.class);
        mockBindingResult = new BeanPropertyBindingResult(pdc, PERSONAL_DETAILS);
        cardDataService = mock(CardDataService.class);
        applicationEventService = mock(ApplicationEventService.class);
        customerService = mock(CustomerService.class);
        customerDataService = mock(CustomerDataService.class);
        List<CardDTO> cards = new ArrayList<CardDTO>();
        cards.add(getTestCardDTO1());
        when(cardDataService.findByCustomerId(anyLong())).thenReturn(cards);
        List<ApplicationEventDTO> applicationEvents = new ArrayList<ApplicationEventDTO>();
        applicationEvents.add(getApplicationEventDTO1());
        when(applicationEventService.findApplicationEventsForCustomer(anyLong())).thenReturn(applicationEvents);

        controller.cardDataService = cardDataService;
        controller.applicationEventService = applicationEventService;
        controller.customerService = customerService;
        controller.customerDataService = customerDataService;
        controller.deactivateCustomerValidator = mockDeactivateCustomerValidator;
        controller.systemParameterService = systemParameterService;

        addressValidator = mock(AddressValidator.class);
        customerNameValidator = mock(CustomerNameValidator.class);
        doNothing().when(customerNameValidator).validate(anyObject(), (Errors) anyObject());
        doNothing().when(addressValidator).validate(anyObject(), (Errors) anyObject());
        doNothing().when(mockPersonalDetailsValidator).validate(anyObject(), (Errors) anyObject());
        doNothing().when(customerService).customerExists((CommonOrderCardCmd) anyObject(), (BindingResult) anyObject());
        controller.personalDetailsValidator = mockPersonalDetailsValidator;
        agentService = mock(AgentService.class);
        controller.agentService = agentService;

        this.mockModel = mock(Model.class);
        this.mockCubicCardService = mock(CubicCardService.class);
        this.controller.cubicCardService = this.mockCubicCardService;
        this.mockLocationSelectListService = mock(LocationSelectListService.class);
        this.controller.locationSelectListService = this.mockLocationSelectListService;
        this.mockSelectListService = mock(SelectListService.class);
        this.controller.selectListService = this.mockSelectListService;
        this.mockCountrySelectListService = mock(CountrySelectListService.class);
        this.controller.countrySelectListService = this.mockCountrySelectListService;
        this.mockSelectListDTO = mock(SelectListDTO.class);
        
        mockController = mock(CustomerController.class);
    }

    /**
     * Return model with no cards
     */
    @Test
    public void showApplicationFormShouldReturnApplicationFormNoCardsModelAndView() {
        personalDetailsCmdImpl.setCustomerId(null);
        ModelAndView result = controller.load(1L, OYSTER_NUMBER_3, mockRequest);
        assertEquals(INV_CUSTOMER, result.getViewName());
        assertTrue(result.getModel().containsKey(PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertTrue(((PersonalDetailsCmdImpl) result.getModel().get(PERSONAL_DETAILS)).getFirstName().equals(FIRST_NAME_1));
        assertTrue(mockBindingResult.getGlobalErrorCount() == 0);
        assertFalse(result.getModel().containsKey("customerCards"));
    }

    /**
     * Return model and cards
     */
    @Test
    public void showApplicationFormShouldReturnApplicationFormAndCardsModelAndView() {
        personalDetailsCmdImpl.setCustomerId(Long.getLong(PageParameter.CUSTOMER_ID));
        ModelAndView result = controller.load(1L, OYSTER_NUMBER_3, mockRequest);
        assertEquals(INV_CUSTOMER, result.getViewName());
        assertTrue(result.getModel().containsKey(PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertEquals(FIRST_NAME_1, ((PersonalDetailsCmdImpl) result.getModel().get(PERSONAL_DETAILS)).getFirstName());
        assertTrue(mockBindingResult.getGlobalErrorCount() == 0);
    }

    /**
     * Test to catch the bind exception and make sure the correct view is returned
     */
    @Test
    public void throwServletRequestBindException() {
        when(mockRequest.getParameter(ID)).thenReturn(null);
        ModelAndView result = controller.load(null, OYSTER_NUMBER_3, mockRequest);
        assertEquals(INV_CUSTOMER, result.getViewName());
        assertTrue(result.getModel().containsKey(PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertTrue(result.getModel().containsKey("error"));
        assertEquals(result.getModel().get("error"), "customerid.parameter.error");
    }

    /**
     * Check to make sure the form saves
     */
    @Test
    public void saveCustomerShouldSave() {
        when(personalDetailsService.updatePersonalDetails(personalDetailsCmdImpl)).thenReturn(personalDetailsCmdImpl);
        ModelAndView result = controller.save(personalDetailsCmdImpl, mockBindingResult, mockRequest);
        assertEquals(INV_CUSTOMER, result.getViewName());
        assertTrue(result.getModel().containsKey(PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertFalse(mockBindingResult.getErrorCount() > 0);
    }

    @Test
    public void saveCustomerShouldNotSave() {
        personalDetailsCmdImpl.setFirstName(null);
        mockBindingResult.reject("ERROR");
        ModelAndView result = controller.save(personalDetailsCmdImpl, mockBindingResult, mockRequest);
        assertEquals(INV_CUSTOMER, result.getViewName());
        assertTrue(result.getModel().containsKey(PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
        assertTrue(mockBindingResult.getErrorCount() > 0);
    }

    @Test
    public void checkEmailAvailableShouldReturnTrue() {
        when(customerDataService.findByUsernameOrEmail(eq(EMAIL_ADDRESS_1))).thenReturn(getTestCustomerDTO1());
        String checkEmailAvailable = controller.checkEmailAvailable(EMAIL_ADDRESS_1 + "test", mockRequest);
        verify(customerDataService).findByUsernameOrEmail(anyString());
        assertEquals(checkEmailAvailable, "false");
    }

    @Test
    public void checkEmailAvailableShouldReturnCustomerId() {
        when(customerDataService.findByUsernameOrEmail(eq(EMAIL_ADDRESS_1))).thenReturn(getTestCustomerDTO1());
        String checkEmailAvailable = controller.checkEmailAvailable(EMAIL_ADDRESS_1, mockRequest);
        verify(customerDataService).findByUsernameOrEmail(anyString());
        assertEquals(checkEmailAvailable, WEB_ACCOUNT_ID_1.toString());
    }

    @Test
    public void checkEmailAvailablePassEmptyEmail() {
        when(customerDataService.findByUsernameOrEmail(eq(EMAIL_ADDRESS_1))).thenReturn(getTestCustomerDTO1());
        String checkEmailAvailable = controller.checkEmailAvailable(null, mockRequest);
        verify(customerDataService).findByUsernameOrEmail(anyString());
        assertEquals(checkEmailAvailable, "false");
    }

    @Test
    public void checkCustomerCustomerFound() {
        List<CustomerSearchResultDTO> results = new ArrayList<CustomerSearchResultDTO>();
        results.add(getSearchResultObject());
        when(customerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class))).thenReturn(results);
        String checkCustomer = controller.checkCustomer(SR_FIRST_NAME_1, SR_LAST_NAME_1, SR_POSTCODE);
        verify(customerDataService).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        String json = new Gson().toJson(results);
        assertEquals(checkCustomer, json);
    }

    @Test
    public void checkCustomerCustomerNotFound() {
        when(customerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class))).thenReturn(null);
        String checkCustomer = controller.checkCustomer(SR_FIRST_NAME_1, SR_LAST_NAME_1, SR_POSTCODE);
        verify(customerDataService).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        assertEquals(checkCustomer, "null");
    }

    @Test
    public void checkCustomerShouldReturnBlankStringIfCustomerFirstNameMissing() {
        String checkCustomer = controller.checkCustomer("", SR_LAST_NAME_1, SR_POSTCODE);
        verify(customerDataService, never()).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        assertEquals(checkCustomer, "");
    }

    @Test
    public void checkCustomerShouldReturnBlankStringIfCustomerLastNameMissing() {
        String checkCustomer = controller.checkCustomer(SR_FIRST_NAME_1, "", SR_POSTCODE);
        verify(customerDataService, never()).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        assertEquals(checkCustomer, "");
    }

    @Test
    public void checkCustomerShouldReturnBlankStringIfCustomerPostcodeMissing() {
        String checkCustomer = controller.checkCustomer(SR_FIRST_NAME_1, SR_LAST_NAME_1, "");
        verify(customerDataService, never()).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        assertEquals(checkCustomer, "");
    }

    @Test
    public void checkSuccessAgentLogon() throws Exception {
        String result = "SUCCESS?TOKEN=" + TOKEN;
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(CUSTOMER_ID_1.toString());
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn("SK11");
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn(TOKEN);
        String returnValue = controller.agentLogon(mockRequest);
        assertEquals(result, returnValue);
    }

    @Test
    public void checkFailAgentLogonWithNoWebaccount() throws Exception {
        String result = "FAIL";
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(null);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn("SK11");
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn(TOKEN);
        String returnValue = controller.agentLogon(mockRequest);

        assertEquals(result, returnValue);
    }

    @Test
    public void checkFailAgentLogonWithNullToken() throws Exception {
        String result = "FAIL";
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(PageParameter.CUSTOMER_ID.toString());
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn(null);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn("SK11");
        String returnValue = controller.agentLogon(mockRequest);
        assertEquals(result, returnValue);
    }

    @Test
    public void checkFailAgentLogonWithNoToken() throws Exception {
        String result = "FAIL";
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(PageParameter.CUSTOMER_ID.toString());
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn("");
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn("SK11");
        String returnValue = controller.agentLogon(mockRequest);
        assertEquals(result, returnValue);
    }

    @Test
    public void checkFailAgentLogonWithNoAgent() throws Exception {
        String result = "FAIL";
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(PageParameter.CUSTOMER_ID.toString());
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn(null);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn(null);
        String returnValue = controller.agentLogon(mockRequest);
        assertEquals(result, returnValue);
    }

    @Test
    public void checkFailAgentLogonWithNoSession() throws Exception {
        String result = "FAIL";
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(PageParameter.CUSTOMER_ID.toString());
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn(null);
        when(mockRequest.getSession(false)).thenReturn(null);
        when(mockSession.getAttribute("userId")).thenReturn(null);
        String returnValue = controller.agentLogon(mockRequest);
        assertEquals(result, returnValue);
    }

    @Test
    public void checkFailAgentLogonWithBlankToken() throws Exception {
        String result = "FAIL";
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(CUSTOMER_ID_1.toString());
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("userId")).thenReturn("SK11");
        when(agentService.allowAgent(anyString(), anyLong())).thenReturn(TOKEN_BLANK);
        String returnValue = controller.agentLogon(mockRequest);
        assertEquals(result, returnValue);
    }

    @Test
    public void showApplicationFormShouldReturnDeactivationFlagIfUserIsDeactivated() {
        personalDetailsCmdImpl.setCustomerId(Long.getLong(PageParameter.CUSTOMER_ID));
        ModelAndView result = controller.load(1L, OYSTER_NUMBER_3, mockRequest);
        assertTrue(((PersonalDetailsCmdImpl) result.getModel().get(PERSONAL_DETAILS)).isShowWebAccountDeactivationEnableFlag());
    }

    @Test
    public void saveNotGhostCustomer() {
        personalDetailsCmdImpl.setCustomerId(null);
        personalDetailsCmdImpl.setEmailAddress(CustomerTestUtil.EMAIL_ADDRESS_1);
        doNothing().when(mockPersonalDetailsValidator).validate(anyObject(), any(Errors.class));

        when(customerService.addCustomer(any(CommonOrderCardCmd.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        doNothing().when(mockRequest).setAttribute(anyString(), anyObject());
        when(systemParameterService.getParameterValue(anyString())).thenReturn("");

        assertNotNull(controller.save(personalDetailsCmdImpl, mockBindingResult, mockRequest));
    }

    @Test
    public void saveGhostCustomer() {
        personalDetailsCmdImpl.setCustomerId(null);
        personalDetailsCmdImpl.setEmailAddress("");
        doNothing().when(mockPersonalDetailsValidator).validate(anyObject(), any(Errors.class));
        when(customerService.createGhostEmail(any(CommonOrderCardCmd.class))).thenReturn(CustomerTestUtil.EMAIL_ADDRESS_1);
        when(customerService.addCustomer(any(CommonOrderCardCmd.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        doNothing().when(mockRequest).setAttribute(anyString(), anyObject());

        assertNotNull(controller.save(personalDetailsCmdImpl, mockBindingResult, mockRequest));
    }

    @Test
    public void shouldPopulateLocationsSelectList() {
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(this.mockModel);
        when(this.mockLocationSelectListService.getLocationSelectList()).thenReturn(this.mockSelectListDTO);
        when(this.mockCountrySelectListService.getSelectList()).thenReturn(this.mockSelectListDTO);
        this.controller.populateLocationsSelectList(this.mockModel);
        verify(this.mockModel, times(2)).addAttribute(anyString(), anyObject());
        verify(this.mockLocationSelectListService).getLocationSelectList();
        verify(this.mockCountrySelectListService).getSelectList();
    }

    @Test
    public void shouldAddOnlineServer() {
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(this.mockModel);
        when(this.systemParameterService.getParameterValue(anyString())).thenReturn(StringUtils.EMPTY);
        this.controller.addOnlineServer(this.mockModel);
        verify(this.mockModel).addAttribute(anyString(), anyObject());
        verify(this.systemParameterService).getParameterValue(anyString());
    }

    @Test
    public void shouldPopulateRefundsSelectList() {
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(this.mockModel);
        when(this.mockSelectListService.getSelectList(anyString())).thenReturn(this.mockSelectListDTO);
        this.controller.populateRefundsSelectList(this.mockModel);
        verify(this.mockModel).addAttribute(anyString(), anyObject());
        verify(this.mockSelectListService).getSelectList(anyString());
    }

    @Test
    public void shouldPopulateTitlesSelectList() {
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(this.mockModel);
        when(this.mockSelectListService.getSelectList(anyString())).thenReturn(this.mockSelectListDTO);
        this.controller.populateTitlesSelectList(this.mockModel);
        verify(this.mockModel).addAttribute(anyString(), anyObject());
        verify(this.mockSelectListService).getSelectList(anyString());
    }

    @Test
    public void shouldPopulateCustomerDeactivationReasonsSelectList() {
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(this.mockModel);
        when(this.mockSelectListService.getSelectList(anyString())).thenReturn(this.mockSelectListDTO);
        this.controller.populateCustomerDeactivationReasonsSelectList(this.mockModel);
        verify(this.mockModel).addAttribute(anyString(), anyObject());
        verify(this.mockSelectListService).getSelectList(anyString());
    }

    @Test
    public void shouldCheckCardStatus() {
        when(this.mockCubicCardService.checkCardStatusReturnMessage(anyString())).thenReturn(new CardStatusDTO());
        this.controller.checkCardStatus(CardTestUtil.OYSTER_NUMBER_1);
        verify(this.mockCubicCardService).checkCardStatusReturnMessage(anyString());
    }
    
    @Test
    public void shouldDeleteCartSessionData() {
        doCallRealMethod().when(mockController).deleteCartSessionData((HttpServletRequest)any());
        when(mockRequest.getSession(anyBoolean())).thenReturn(mock(HttpSession.class));
        when(mockController.getFromSession((HttpSession)any(), anyString())).thenReturn(true);
        mockController.deleteCartSessionData(mockRequest);
        verify(mockController, atLeastOnce()).deleteAttributeFromSession((HttpSession)any(), anyString());
    }

    @Test
    public void shouldNotDeleteCartSessionDataWhenSessionIsNull() {
        doCallRealMethod().when(mockController).deleteCartSessionData((HttpServletRequest)any());
        when(mockRequest.getSession(anyBoolean())).thenReturn(null);
        when(mockController.getFromSession((HttpSession)any(), anyString())).thenReturn(anyObject());
        mockController.deleteCartSessionData(mockRequest);
        verify(mockController, never()).deleteAttributeFromSession((HttpSession)any(), anyString());
    }

    @Test
    public void shouldNotDeleteCartSessionDataWhenSessionAttributeForShoppingCartDataDoesNotExist() {
        doCallRealMethod().when(mockController).deleteCartSessionData((HttpServletRequest)any());
        when(mockRequest.getSession(anyBoolean())).thenReturn(mock(HttpSession.class));
        when(mockController.getFromSession((HttpSession)any(), anyString())).thenReturn(null);
        mockController.deleteCartSessionData(mockRequest);
        verify(mockController, never()).deleteAttributeFromSession((HttpSession)any(), anyString());
    }
}
