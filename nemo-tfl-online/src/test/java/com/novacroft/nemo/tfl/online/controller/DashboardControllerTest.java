package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCard1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmdForDeactivatedAccountCmd;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.form_validator.ViewCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * DashboardController unit tests
 */
public class DashboardControllerTest {
    
    private DashboardController controller;
    private DashboardController mockController;
    private ManageCardCmd mockManageCardCmd;
    private ViewCardValidator mockViewCardValidator;
    private HttpSession mockSession;   
    private CustomerDataService mockCustomerDataService;
    private BeanPropertyBindingResult bindingResult;
    private BeanPropertyBindingResult mockResult;
    private final String ERROR_MESSAGE = "Card Update request failed";
    private RedirectAttributes redirectAttributes;
    private WebCreditService mockWebCreditService;
    private GetCardService mockGetCardService;
    private SecurityService mockSecurityService;
    private PersonalDetailsService mockPersonalDetailsService;
    private CardDataService mockCardDataService;
    private TopUpTicketService mockTopUpTicketService;
    private FailedAutoTopUpCaseDataService mockFailedAutoTopUpCaseDataService;
    
    @Before
    public void setUp() {
        controller = new DashboardController();
        mockController = mock(DashboardController.class);
        mockManageCardCmd = mock(ManageCardCmd.class);
        mockViewCardValidator = mock(ViewCardValidator.class);
        mockSession = mock(HttpSession.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockTopUpTicketService = mock(TopUpTicketService.class);
        bindingResult = new BeanPropertyBindingResult(mockManageCardCmd, "cmd");
        
        redirectAttributes = new RedirectAttributesModelMap();


        mockSecurityService = mock(SecurityService.class);
        when(mockSecurityService.getLoggedInUsername()).thenReturn(USERNAME_1);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());

        mockPersonalDetailsService = mock(PersonalDetailsService.class);
        when(mockPersonalDetailsService.getPersonalDetails(anyString())).thenReturn(getTestPersonalDetailsCmd1());
        
        mockCardDataService = mock(CardDataService.class);
        mockController.cardDataService = mockCardDataService;
        when(mockCardDataService.findByCustomerId(anyLong())).thenReturn(getTestCard1());
        
        mockGetCardService = mock(GetCardService.class);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO1());
        
        mockWebCreditService = mock(WebCreditService.class);
        when(mockWebCreditService.getAvailableBalance(anyLong())).thenReturn(100);
        
        when(mockCustomerDataService.findById(anyLong())).thenReturn(getTestCustomerDTO1());
        when(mockCardDataService.findByCustomerId(anyLong())).thenReturn(CardTestUtil.getTestCardList1());
        when(mockController.getCustomer()).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(mockController).showDashboard(any(RedirectAttributes.class), any(HttpSession.class));
        
        mockFailedAutoTopUpCaseDataService = mock(FailedAutoTopUpCaseDataService.class);
        when(mockFailedAutoTopUpCaseDataService.findPendingAmountByCustomerId(anyLong())).thenReturn(0);
        
        mockController.personalDetailsService = mockPersonalDetailsService;
        mockController.cardDataService = mockCardDataService;
        mockController.getCardService = mockGetCardService;
        mockController.webCreditService = mockWebCreditService;
        mockController.topUpTicketService = mockTopUpTicketService;
        mockController.failedAutoTopUpCaseDataService = mockFailedAutoTopUpCaseDataService;
        setField(mockController, "securityService", mockSecurityService);
        
        controller.viewCardValidator = mockViewCardValidator;
        controller.customerDataService = mockCustomerDataService;
        controller.failedAutoTopUpCaseDataService = mockFailedAutoTopUpCaseDataService;
         
    }
    
    @Test
    public void shouldShowDasboard() {
        
        ModelAndView result = mockController.showDashboard(redirectAttributes, mockSession);
        
        verify(mockCardDataService).findByCustomerId(anyLong());
        verify(mockWebCreditService).getAvailableBalance(anyLong());
        verify(mockGetCardService).getCard(anyString());
        verify(mockTopUpTicketService).removeExpiredPrePaidTicketsInACard(any(CardDTO.class), any(CardInfoResponseV2DTO.class));
        
        assertEquals(PageView.DASHBOARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }
    
    @Test
    public void shouldShowDasboardShouldThrowException() {

        when(mockGetCardService.getCard(anyString())).thenThrow(new ApplicationServiceException(ERROR_MESSAGE));

        ModelAndView result = mockController.showDashboard(redirectAttributes, mockSession);

        verify(mockCardDataService).findByCustomerId(anyLong());
        verify(mockWebCreditService).getAvailableBalance(anyLong());
        verify(mockGetCardService).getCard(anyString());

        assertEquals(PageView.DASHBOARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }

    @Test(expected = AssertionError.class)
    public void showDasboardShouldThrowAssertionException() {
        
        when(mockGetCardService.getCard(anyString())).thenReturn(null);
        when(mockController.getFromSession(any(HttpSession.class), anyString())).thenReturn("");

        ModelAndView result = mockController.showDashboard(redirectAttributes, mockSession);

        verify(mockCardDataService).findByCustomerId(anyLong());
        verify(mockWebCreditService).getAvailableBalance(anyLong());
        verify(mockGetCardService).getCard(anyString());
        verify(mockTopUpTicketService).removeExpiredPrePaidTicketsInACard(any(CardDTO.class), any(CardInfoResponseV2DTO.class));
        
        assertEquals(PageView.DASHBOARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }

    @Test
    public void shouldSelectCardWithoutErrors() {
        doNothing().when(mockManageCardCmd).setCardId(anyLong());
        doNothing().when(mockViewCardValidator).validate(anyObject(), any(Errors.class)); 
        doNothing().when(mockSession).setAttribute(anyString(), anyObject()); 
        ModelAndView result = controller.selectCard(mockManageCardCmd, CardTestUtil.CARD_ID_1, mockSession, bindingResult);
        assertEquals(PageUrl.VIEW_OYSTER_CARD, ((RedirectView) result.getView()).getUrl());
        verify(mockSession).setAttribute(anyString(), anyObject());        
    }

    @Test(expected = ApplicationServiceException.class)
    public void shouldSelectCardWithErrors() {
        when(mockResult.hasErrors()).thenReturn(true);
        controller.selectCard(mockManageCardCmd, CardTestUtil.CARD_ID_1, mockSession, mockResult);
        verify(mockSession, never()).setAttribute(anyString(), anyObject());
    }
    
    @Test
    public void shouldShowOysterHomePageForDeactivatedAccount(){
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        SecurityService mockSecurityService = mock(SecurityService.class);
        PersonalDetailsService mockPersonalDetailsService = mock(PersonalDetailsService.class);
        controller.personalDetailsService = mockPersonalDetailsService;
        setField(controller, "securityService", mockSecurityService);
        HttpSession session = mock(HttpSession.class);
        when(mockPersonalDetailsService.getPersonalDetails(anyString())).thenReturn(getTestPersonalDetailsCmdForDeactivatedAccountCmd());
        ModelAndView result = controller.showDashboard(redirectAttributes, session);
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldShowDasboardWithCardIdSessionRemoved() {
        when(mockController.getFromSession(any(HttpSession.class), anyString())).thenReturn("");
        ModelAndView result = mockController.showDashboard(redirectAttributes, mockSession);
        
        verify(mockCardDataService).findByCustomerId(anyLong());
        verify(mockWebCreditService).getAvailableBalance(anyLong());
        verify(mockGetCardService).getCard(anyString());
        verify(mockTopUpTicketService).removeExpiredPrePaidTicketsInACard(any(CardDTO.class), any(CardInfoResponseV2DTO.class));
        
        assertEquals(PageView.DASHBOARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }
    
    @Test
    public void shouldShowDasboardWithCartSessionDataRemoved() {
        when(mockController.getFromSession(any(HttpSession.class), anyString())).thenReturn("");
        ModelAndView result = mockController.showDashboard(redirectAttributes, mockSession);
        
        verify(mockCardDataService).findByCustomerId(anyLong());
        verify(mockWebCreditService).getAvailableBalance(anyLong());
        verify(mockGetCardService).getCard(anyString());
        verify(mockTopUpTicketService).removeExpiredPrePaidTicketsInACard(any(CardDTO.class), any(CardInfoResponseV2DTO.class));
        
        assertEquals(PageView.DASHBOARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.PERSONAL_DETAILS));
        assertTrue(result.getModel().get(PageCommand.PERSONAL_DETAILS) instanceof PersonalDetailsCmdImpl);
    }
    
}
