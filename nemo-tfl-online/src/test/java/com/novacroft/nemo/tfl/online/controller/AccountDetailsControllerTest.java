package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.CardDataServiceImpl;
import com.novacroft.nemo.tfl.common.form_validator.CustomerRegistrationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for cart Controller
 */
@SuppressWarnings("deprecation")
public class AccountDetailsControllerTest {

    private AccountDetailsController controller;
    private CartCmdImpl cmd;
    private CartCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private CustomerService mockCustomerService;
    private CustomerRegistrationValidator mockCustomerRegistrationValidator;
    private HttpServletRequest mockHttpServletRequest;
    private PAFService mockPafService;
    private PostcodeValidator mockPostcodeValidator;
    private SecurityService mockSecurityService;
    private CountrySelectListService mockCountrySelectListService;
    private HttpSession mockSession;
    private BindingAwareModelMap model;
    private CartService mockCartService;
    private CardDataService mockCardDataService;
    private SelectListService mockSelectListService;
    
    @Before
    public void setUp() {
        controller = new AccountDetailsController();
        cmd = new CartCmdImpl();
        mockCmd = mock(CartCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockCustomerService = mock(CustomerService.class);
        mockCustomerRegistrationValidator = mock(CustomerRegistrationValidator.class);
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockPafService = mock(PAFService.class);
        mockPostcodeValidator = mock(PostcodeValidator.class);
        mockSecurityService = mock(SecurityService.class);
        mockSession = mock(HttpSession.class);
        mockCartService = mock(CartService.class);
        mockCountrySelectListService = mock(CountrySelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        model = new BindingAwareModelMap();
        mockCardDataService = mock(CardDataServiceImpl.class);
        
        controller.customerService = mockCustomerService;
        controller.customerRegistrationValidator = mockCustomerRegistrationValidator;
        controller.pafService = mockPafService;
        controller.postcodeValidator = mockPostcodeValidator;
        controller.countrySelectListService = mockCountrySelectListService;
        controller.cartService = mockCartService;
        controller.cardDataService = mockCardDataService;
        controller.selectListService = mockSelectListService;
        setField(controller, "securityService", mockSecurityService);

        doNothing().when(mockPostcodeValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockCustomerRegistrationValidator).validate(anyObject(), any(Errors.class));
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(controller.getCartCmd());
    }

    @Test
    public void shouldpopulateCountrySelectList() {
        when(mockCountrySelectListService.getSelectList()).thenReturn(new SelectListDTO());
        controller.populateCountrySelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.COUNTRIES));
    }
    
    @Test
    public void shouldpopulateTitlesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateTitlesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.TITLES));
    }

    @Test
    public void shouldShowAccountDetails() {
        ModelAndView result = controller.viewAccountDetails(mockCmd);
        assertEquals(PageView.ACCOUNT_DETAILS, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void shouldFindAddressOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        ModelAndView result = controller.findAddress(mockCmd, mockResult);
        verify(mockPafService, atLeastOnce()).getAddressesForPostcodeSelectList(anyString());
        assertEquals(PageView.ACCOUNT_DETAILS, result.getViewName());
    }

    @Test
    public void shouldNotFindAddressOnInValidInput() {
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.findAddress(mockCmd, mockResult);
        verify(mockPafService, never()).getAddressesForPostcodeSelectList(anyString());
        assertEquals(PageView.ACCOUNT_DETAILS, result.getViewName());
    }

    @Test
    public void shouldSelectAddress() {
        cmd.setAddressForPostcode(
                "{\"houseNameNumber\":\"test-housenumber\",\"street\":\"test-street\",\"town\":\"test-town\"," +
                        "\"country\":{\"code\":\"test-country-code\",\"name\":\"test-country\"}}");
        when(mockPafService.populateAddressFromJson(any(CommonOrderCardCmd.class), anyString())).thenReturn(CartCmdTestUtil.getTestCartCmd1());
        ModelAndView result = controller.selectAddress(cmd, mockResult);
        verify(mockPafService, atLeastOnce()).populateAddressFromJson(any(CommonOrderCardCmd.class), anyString());
        assertEquals(PageView.ACCOUNT_DETAILS, result.getViewName());
    }
    
    @Test
    public void selectAddressWithEmptyPostCodeShouldReturnToAccountDetails() {
        cmd.setAddressForPostcode("");
        ModelAndView result = controller.selectAddress(cmd, mockResult);
        verify(mockPafService, never()).populateAddressFromJson(any(CommonOrderCardCmd.class), anyString());
        assertEquals(PageView.ACCOUNT_DETAILS, result.getViewName());
    }

    @Test
    public void processAccountDetailsShouldShowSecurityQuestionOnValidInputWithCartSessionData() {
        when(mockResult.hasErrors()).thenReturn(false);
        cmd.setPageName(Page.OPEN_ACCOUNT);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        setField(controller, "securityService", mockSecurityService);
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        verify(mockCustomerService, atLeastOnce()).addCustomer(cmd);
        assertEquals(PageUrl.SECURITY_QUESTION, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void processAccountDetailsShouldShowDashboardOnValidInput() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        setField(controller, "securityService", mockSecurityService);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        cmd.setPageName(Page.OPEN_ACCOUNT);
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        verify(mockCustomerService, atLeastOnce()).addCustomer(cmd);
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void processAccountDetailsShouldShowSecurityQuestionOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        cmd.setPageName(Page.CART);
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        verify(mockCustomerService, atLeastOnce()).addCustomer(cmd);
        assertEquals(PageUrl.SECURITY_QUESTION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void processAccountDetailsShouldShowOysterHomeOnLoginFailure() {
        when(mockResult.hasErrors()).thenReturn(false);
        cmd.setPageName(Page.CART);
        doThrow(new BadCredentialsException("Login Failure")).when(mockSecurityService)
                        .login(anyString(), anyString(), any(HttpServletRequest.class));
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        verify(mockCustomerService, atLeastOnce()).addCustomer(cmd);
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void processAccountDetailsShouldShowOysterHomeOnLoginFailureForOpenAccount() {
        when(mockResult.hasErrors()).thenReturn(false);
        cmd.setPageName(Page.OPEN_ACCOUNT);
        doThrow(new BadCredentialsException("Login Failure")).when(mockSecurityService)
                        .login(anyString(), anyString(), any(HttpServletRequest.class));
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        verify(mockCustomerService, atLeastOnce()).addCustomer(cmd);
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void processAccountDetailsShouldShowOysterHomeWithEmptyPageName() {
        when(mockResult.hasErrors()).thenReturn(false);
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void processAccountDetailsShouldShowAccountDetailsOnInValidInput() {
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.processAccountDetails(cmd, mockResult, mockHttpServletRequest, this.mockSession);
        assertEquals(PageView.ACCOUNT_DETAILS, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToOysterHome() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void addCustomerShouldRedirectToOysterHome() {
        cmd.setPageName(Page.TOP_UP_TICKET);
        ModelAndView result = controller.addCustomer(cmd, mockHttpServletRequest, this.mockSession);
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void isCartInSessionShouldReturnFalseWhenNoCartId() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        cartSessionData.setCartId(null);
        assertFalse(controller.isCartInSession(cartSessionData));
    }
}
