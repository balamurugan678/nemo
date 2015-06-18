package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTOHotlistStatus;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.LostOrStolenCmdTestUtil.getTestLostOrStolenCardCmdLostRefund;
import static com.novacroft.nemo.test_support.LostOrStolenCmdTestUtil.getTestLostOrStolenCardCmdLostTransfer;
import static com.novacroft.nemo.test_support.LostOrStolenCmdTestUtil.getTestLostOrStolenCardCmdStolenRefund;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.LostOrStolenValidator;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for LostOrStolenCardController
 */
public class LostOrStolenCardControllerTest {

    private LostOrStolenCardController controller;
    private HttpSession mockSession;   
    private LostOrStolenCardCmdImpl mockLostOrStolenCardCmdImpl;
    private CardDataService mockCardDataService;
    private RedirectAttributes redirectAttributes;
    private BeanPropertyBindingResult mockBindingResult;
    private HotlistCardService mockHotlistCardService;
    private CardUpdateService mockCardUpdateService;
    private LostOrStolenValidator mockLostOrStolenValidator;
    private SecurityService mockSecurityService;
    private CustomerDataService mockCustomerDataService;
    private ApplicationContext mockAppContext;
    
    @Before
    public void setUp() {
        mockSession = mock(HttpSession.class);
        mockLostOrStolenCardCmdImpl = mock(LostOrStolenCardCmdImpl.class);
        mockLostOrStolenValidator = mock(LostOrStolenValidator.class);
        mockHotlistCardService = mock(HotlistCardService.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockCardDataService = mock(CardDataService.class);
        controller = new LostOrStolenCardController();
        controller.cardDataService = mockCardDataService;
        redirectAttributes=mock(RedirectAttributes.class);
        mockSecurityService = mock(SecurityService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockAppContext = mock(ApplicationContext.class);
        
        controller.lostOrStolenValidator = mockLostOrStolenValidator;
        controller.hotlistCardService = mockHotlistCardService;
        controller.cardUpdateService = mockCardUpdateService;
        controller.customerDataService = mockCustomerDataService;
        controller.setApplicationContext(mockAppContext);
        setField(controller, "securityService", mockSecurityService);
        
        mockBindingResult = mock(BeanPropertyBindingResult.class);
        doNothing().when(mockHotlistCardService).toggleCardHotlisted(anyString(), anyInt());
        doNothing().when(mockCardUpdateService).createLostOrStolenEventForHotlistedCard(anyString(), anyInt());

        doNothing().when(mockLostOrStolenValidator).validate(any(Object.class), any(Errors.class));
        
    }
    
    @Test
    public void shouldPopulateHotlistReasonsSelectList() {
        when(mockHotlistCardService.getHotlistReasonSelectList()).thenReturn(getTestSelectListDTO());

        Model model = new ExtendedModelMap();

        LostOrStolenCardController controller = new LostOrStolenCardController();
        controller.hotlistCardService = mockHotlistCardService;

        controller.populateHotlistReasonsSelectList(model);

        assertTrue(model.asMap().containsKey(PageAttribute.HOTLIST_REASONS));
    }
    
    @Test
    public void shouldShowViewLostOrStolenCard() {
        when(mockSession.getAttribute(anyString())).thenReturn(CardTestUtil.CARD_ID_1);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        ModelAndView result = controller.viewLostOrStolenCard(mockLostOrStolenCardCmdImpl, mockSession);
        assertEquals(PageView.LOST_OR_STOLEN_CARD, result.getViewName());
    }

    @Test
    public void shouldSaveChangesWithValidInputRefundSelectedLostCard() {
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(getTestCardDTOHotlistStatus());
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
    	when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        ModelAndView result = controller.saveChanges(mockSession,getTestLostOrStolenCardCmdLostRefund(), mockBindingResult, redirectAttributes);
        assertEquals(PageUrl.LOST_CARD_REFUND, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shouldSaveChangesWithValidInputRefundSelectedStolenCard() {
    	when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(getTestCardDTOHotlistStatus());
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
    	when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(false);
        ModelAndView result = controller.saveChanges(mockSession, getTestLostOrStolenCardCmdStolenRefund(), mockBindingResult, redirectAttributes);
        assertEquals(PageUrl.LOST_CARD_REFUND, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shouldSaveChangesWithValidInputTransferSelected() {
    	when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(getTestCardDTOHotlistStatus());
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
    	when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        ModelAndView result = controller.saveChanges(mockSession,getTestLostOrStolenCardCmdLostTransfer(), mockBindingResult, redirectAttributes);
        assertEquals(PageUrl.TRANSFER_PRODUCT, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldSaveChangesWithValidInputOtherSelected() {
    	when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(getTestCardDTOHotlistStatus());
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
    	when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
    	when(mockBindingResult.hasErrors()).thenReturn(false);
    	LostOrStolenCardCmdImpl cmd = getTestLostOrStolenCardCmdLostTransfer();
    	cmd.setLostStolenOptions("");
    	ModelAndView result = controller.saveChanges(mockSession,cmd, mockBindingResult, redirectAttributes);
    	assertViewName(result, PageView.LOST_OR_STOLEN_CARD);
    }

    @Test
    public void shouldNotSaveChangesWithInvalidInput() {
    	when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(getTestCardDTOHotlistStatus());
    	when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
    	when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(true);
        controller.saveChanges(mockSession,getTestLostOrStolenCardCmdLostRefund(), mockBindingResult, redirectAttributes);
        verify(mockHotlistCardService, never()).toggleCardHotlisted(anyString(), anyInt());
        verify(mockCardUpdateService, never()).createLostOrStolenEventForHotlistedCard(anyString(), anyInt());
    }

    @Test
    public void cancelShouldRedirectToDashboard() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, ((RedirectView) result.getView()).getUrl());
    }
}
