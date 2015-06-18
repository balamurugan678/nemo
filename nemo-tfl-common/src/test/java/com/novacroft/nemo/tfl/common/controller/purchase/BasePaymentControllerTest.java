package com.novacroft.nemo.tfl.common.controller.purchase;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.getTestReplyArguments;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertAndReturnModelAttributeOfType;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.AutoTopUpTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourcePostService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSoapService;
import com.novacroft.nemo.tfl.common.application_service.impl.PaymentServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AddPaymentCardAction;
import com.novacroft.nemo.tfl.common.constant.CookieConstant;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceHeartbeatDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.form_validator.AddPaymentCardActionValidator;
import com.novacroft.nemo.tfl.common.form_validator.CyberSourcePostReplyValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for PaymentController
 */
public class BasePaymentControllerTest {
    private static final String DUMMY_IP_ADDRESS = "0.0.0.0";
    private static final String TEST_URL = "http://www.novacroft.com";
    private BasePaymentController controller;
    private CyberSourcePostReplyDTO mockPostReply;
    private PaymentServiceImpl mockPaymentService;
    private CartCmdImpl mockCartCmd;
    private CartDTO mockCartDTO;
    private BeanPropertyBindingResult mockBindingResult;
    private CyberSourcePostReplyValidator mockCyberSourcePostReplyValidator;
    private AddPaymentCardActionValidator mockAddPaymentCardActionValidator;
    private HttpServletRequest mockHttpRequest;
    private HttpServletResponse mockHttpResponse;
    private HttpSession mockHttpSession;
    private CyberSourcePostRequestDTO mockCyberSourcePostRequest;
    private CyberSourceSoapRequestDTO mockCyberSourceSoapRequest;
    private CyberSourceSoapService mockCyberSourceSoapService;
    private SelectListService mockSelectListService;
    private CountrySelectListService mockCountrySelectListService;
    private PaymentCardSelectListService mockPaymentCardSelectListService;
    private CyberSourcePostService mockCyberSourcePostService;
    private PaymentCardDataService mockPaymentCardDataService;
    private CyberSourceTransactionDataService mockCyberSourceTransactionDataService;
    private PaymentCardDTO mockPaymentCardDTO;
    private PaymentDetailsCmdImpl mockPaymentDetailsCmd;
    private SelectListDTO mockSelectListDTO;
    private CyberSourceHeartbeatDataService mockCyberSourceHeartbeatDataService;
    private PaymentCardService mockPaymentCardService;
    private CartService mockCartService;
    private CardService mockCardService;
    private PaymentCardSettlementDataService mockPaymentCardSettlementDataService;
    private OrderDataService mockOrderDataService;

    @Before
    public void setUp() {
        this.controller = mock(BasePaymentController.class);
        this.mockPostReply = mock(CyberSourcePostReplyDTO.class);
        this.mockPaymentService = mock(PaymentServiceImpl.class);
        this.mockCartCmd = mock(CartCmdImpl.class);
        this.mockCartDTO = mock(CartDTO.class);
        this.mockBindingResult = mock(BeanPropertyBindingResult.class);
        this.mockCyberSourcePostReplyValidator = mock(CyberSourcePostReplyValidator.class);
        this.mockAddPaymentCardActionValidator = mock(AddPaymentCardActionValidator.class);
        this.mockHttpRequest = mock(HttpServletRequest.class);
        this.mockHttpResponse = mock(HttpServletResponse.class);
        this.mockHttpSession = mock(HttpSession.class);
        this.mockCyberSourcePostRequest = mock(CyberSourcePostRequestDTO.class);
        this.mockCyberSourceSoapService = mock(CyberSourceSoapService.class);
        this.mockSelectListService = mock(SelectListService.class);
        this.mockPaymentCardSelectListService = mock(PaymentCardSelectListService.class);
        this.mockCyberSourcePostService = mock(CyberSourcePostService.class);
        this.mockCyberSourceSoapService = mock(CyberSourceSoapService.class);
        this.mockPaymentCardDataService = mock(PaymentCardDataService.class);
        this.mockCyberSourceTransactionDataService = mock(CyberSourceTransactionDataService.class);
        this.mockCyberSourcePostRequest = mock(CyberSourcePostRequestDTO.class);
        this.mockCyberSourceSoapRequest = mock(CyberSourceSoapRequestDTO.class);
        this.mockPaymentCardDTO = mock(PaymentCardDTO.class);
        this.mockPaymentDetailsCmd = mock(PaymentDetailsCmdImpl.class);
        this.mockSelectListDTO = mock(SelectListDTO.class);
        this.mockCyberSourceHeartbeatDataService = mock(CyberSourceHeartbeatDataService.class);
        this.controller.cyberSourceHeartbeatDataService = mockCyberSourceHeartbeatDataService;
        this.mockPaymentCardService = mock(PaymentCardService.class);
        this.controller.paymentCardService = this.mockPaymentCardService;
        this.mockCartService = mock(CartService.class);
        this.mockCountrySelectListService = mock(CountrySelectListService.class);
        this.mockCardService = mock(CardService.class);
        this.mockPaymentCardSettlementDataService = mock(PaymentCardSettlementDataService.class);
        this.mockOrderDataService = mock(OrderDataService.class);

        this.controller.paymentService = this.mockPaymentService;
        this.controller.cyberSourcePostReplyValidator = this.mockCyberSourcePostReplyValidator;
        this.controller.selectListService = this.mockSelectListService;
        this.controller.paymentCardSelectListService = this.mockPaymentCardSelectListService;
        this.controller.addPaymentCardActionValidator = this.mockAddPaymentCardActionValidator;
        this.controller.cyberSourcePostService = this.mockCyberSourcePostService;
        this.controller.cyberSourceSoapService = this.mockCyberSourceSoapService;
        this.controller.paymentCardDataService = this.mockPaymentCardDataService;
        this.controller.cyberSourceTransactionDataService = this.mockCyberSourceTransactionDataService;
        this.controller.paymentGatewayUrl = TEST_URL;
        this.controller.cartService = mockCartService;
        this.controller.countrySelectListService = mockCountrySelectListService;
        this.controller.cardService = this.mockCardService;

        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO1());
        when(this.mockCartCmd.getCartDTO()).thenReturn(this.mockCartDTO);

    }

    @Test
    public void isCreateTokenShouldReturnTrue() {
        when(this.controller.isCreateToken(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartCmd.getPaymentCardAction()).thenReturn(AddPaymentCardAction.ADD_AND_SAVE.code());
        assertTrue(this.controller.isCreateToken(this.mockCartCmd));
    }

    @Test
    public void isCreateTokenShouldReturnFalse() {
        when(this.controller.isCreateToken(any(CartCmdImpl.class))).thenCallRealMethod();
        when(this.mockCartCmd.getPaymentCardAction()).thenReturn(AddPaymentCardAction.ADD.code());
        assertFalse(this.controller.isCreateToken(this.mockCartCmd));
    }

    @Test
    public void shouldPopulatePaymentCardTypeSelectList() {
        doCallRealMethod().when(this.controller).populatePaymentCardTypeSelectList(any(Model.class));
        Model model = new ExtendedModelMap();
        when(this.mockSelectListService.getSelectList(anyString())).thenReturn(this.mockSelectListDTO);
        this.controller.populatePaymentCardTypeSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAYMENT_CARD_TYPES));
        verify(this.mockSelectListService).getSelectList(anyString());
    }

    @Test
    public void shouldPopulateCountrySelectList() {
        doCallRealMethod().when(this.controller).populateCountrySelectList(any(Model.class));
        Model model = new ExtendedModelMap();
        when(this.mockCountrySelectListService.getSelectList()).thenReturn(this.mockSelectListDTO);
        this.controller.populateCountrySelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.COUNTRIES));
        verify(this.mockCountrySelectListService).getSelectList();
    }

    @Test
    public void shouldGetConfirmExistingCardView() {
        when(this.controller.getConfirmExistingCardView(any(CartCmdImpl.class), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(DUMMY_IP_ADDRESS);
        when(this.mockCyberSourceSoapService.preparePaymentRequestData(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyString())).thenReturn(this.mockCyberSourceSoapRequest);
        when(this.mockPaymentCardDataService.findById(anyLong())).thenReturn(this.mockPaymentCardDTO);
        when(this.mockCartCmd.getPaymentCardAction()).thenReturn(String.valueOf(TEST_PAYMENT_CARD_ID_1));

        ModelAndView result = this.controller.getConfirmExistingCardView(this.mockCartCmd, this.mockHttpRequest);

        assertViewName(result, PageView.CONFIRM_EXISTING_PAYMENT_CARD_PAYMENT);
        assertAndReturnModelAttributeOfType(result, PageCommand.CART, CartCmdImpl.class);
        assertAndReturnModelAttributeOfType(result, PageAttribute.PAYMENT_CARD_DISPLAY_NAME, String.class);

        verify(this.controller).getClientIpAddress(any(HttpServletRequest.class));
        verify(this.mockCyberSourceSoapService).preparePaymentRequestData(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyString());
        verify(this.mockPaymentCardDataService).findById(anyLong());
        verify(this.mockCartCmd).getPaymentCardAction();
    }

    @Test
    public void shouldGetEnterPaymentCardDetailsView() {
        when(this.controller.getEnterPaymentCardDetailsView(any(CartDTO.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.isCreateToken(any(CartCmdImpl.class))).thenReturn(true);
        when(this.controller.isCookieEnabledOnClient(anyString())).thenReturn(true);
        when(this.controller.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(DUMMY_IP_ADDRESS);
        when(this.controller.getLoggedInUsername()).thenReturn(USERNAME_1);
        when(this.mockCyberSourcePostService.preparePaymentRequestData(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyBoolean(), anyBoolean(), anyString())).thenReturn(this.mockCyberSourcePostRequest);
        when(this.mockPaymentService.populatePaymentDetails(anyLong())).thenReturn(this.mockPaymentDetailsCmd);

        ModelAndView result = this.controller.getEnterPaymentCardDetailsView(getNewCartDTOWithItem(), this.mockCartCmd, this.mockBindingResult, CookieConstant.COOKIE_STATUS_ON, this.mockHttpRequest);

        assertViewName(result, PageView.ENTER_PAYMENT_CARD_DETAILS);
        assertAndReturnModelAttributeOfType(result, PageCommand.PAYMENT_DETAILS, PaymentDetailsCmdImpl.class);

        verify(this.controller).isCreateToken(any(CartCmdImpl.class));
        verify(this.controller).isCookieEnabledOnClient(anyString());
        verify(this.controller).getClientIpAddress(any(HttpServletRequest.class));
        verify(this.mockCyberSourcePostService).preparePaymentRequestData(any(OrderDTO.class), any(PaymentCardSettlementDTO.class), anyBoolean(), anyBoolean(), anyString());
        verify(this.mockPaymentService).populatePaymentDetails(anyLong());
    }

    @Test
    public void resolveViewNameShouldReturnAccepted() {
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenCallRealMethod();
        when(this.mockPostReply.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());
        assertEquals(PageView.PAYMENT_ACCEPTED, this.controller.resolveViewName(mockPostReply));
    }

    @Test
    public void resolveViewNameShouldReturnCancelled() {
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenCallRealMethod();
        when(this.mockPostReply.getDecision()).thenReturn(CyberSourceDecision.CANCEL.code());
        assertEquals(PageView.PAYMENT_CANCELLED, controller.resolveViewName(mockPostReply));
    }

    @Test
    public void resolveViewNameShouldReturnIncomplete() {
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenCallRealMethod();
        when(this.mockPostReply.getDecision()).thenReturn(CyberSourceDecision.ERROR.code());
        assertEquals(PageView.PAYMENT_INCOMPLETE, controller.resolveViewName(mockPostReply));
    }

    @Test(expected = ControllerException.class)
    public void resolveViewNameShouldError() {
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenCallRealMethod();
        when(this.mockPostReply.getDecision()).thenReturn("Rubbish!");
        controller.resolveViewName(mockPostReply);
    }

    @Test
    public void cancelShouldRedirectToDashboard() {
        when(this.controller.cancel()).thenCallRealMethod();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) this.controller.cancel().getView()).getUrl());
    }

    @Test
    public void shouldReturnPaymentGatewayUrl() {
        when(this.controller.getPaymentGatewayUrl()).thenCallRealMethod();
        assertEquals(TEST_URL, controller.getPaymentGatewayUrl());
    }

    @Test
    public void shouldShowEnterPaymentPaymentDetails() {
        when(this.controller.choosePaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.getEnterPaymentCardDetailsView(any(CartDTO.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.isAddPaymentCard(any(CartCmdImpl.class))).thenReturn(true);
        when(this.mockPaymentService.populatePaymentDetails(anyLong())).thenReturn(new PaymentDetailsCmdImpl());
        when(this.mockBindingResult.hasErrors()).thenReturn(false);
        when(this.mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = this.controller.choosePaymentCard(mockHttpSession, this.mockCartCmd, mockBindingResult, CookieConstant.COOKIE_STATUS_OFF, this.mockHttpRequest);

        assertViewName(result, PageView.ENTER_PAYMENT_CARD_DETAILS);
        assertAndReturnModelAttributeOfType(result, PageCommand.CART, CartCmdImpl.class);
        assertAndReturnModelAttributeOfType(result, PageCommand.PAYMENT_DETAILS, PaymentDetailsCmdImpl.class);
    }

    @Test
    public void shouldShowChoosePaymentCardDetailsInCaseOfServiceNotAvailableException() {
        when(this.controller.choosePaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.getEnterPaymentCardDetailsView(any(CartDTO.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        doThrow(ServiceNotAvailableException.class).when(this.mockCyberSourceHeartbeatDataService).checkHeartbeat(anyLong());
        when(this.controller.isAddPaymentCard(any(CartCmdImpl.class))).thenReturn(true);
        when(this.mockPaymentService.populatePaymentDetails(anyLong())).thenReturn(new PaymentDetailsCmdImpl());
        when(this.mockBindingResult.hasErrors()).thenReturn(false);
        when(this.mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = this.controller.choosePaymentCard(mockHttpSession, this.mockCartCmd, mockBindingResult, CookieConstant.COOKIE_STATUS_OFF, this.mockHttpRequest);

        assertViewName(result, PageView.CHOOSE_PAYMENT_CARD);
        assertAndReturnModelAttributeOfType(result, PageCommand.CART, CartCmdImpl.class);
    }

    @Test
    public void showEnterPaymentCardDetailsShouldShowChooseCard() {
        when(this.controller.choosePaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.mockPaymentService.populatePaymentDetails(null)).thenReturn(new PaymentDetailsCmdImpl());
        when(this.mockBindingResult.hasErrors()).thenReturn(true);
        when(this.controller.getShowChoosePaymentCardView(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(new ModelAndView(PageView.CHOOSE_PAYMENT_CARD));
        when(this.mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = this.controller.choosePaymentCard(mockHttpSession, this.mockCartCmd, mockBindingResult, CookieConstant.COOKIE_STATUS_OFF, this.mockHttpRequest);

        assertViewName(result, PageView.CHOOSE_PAYMENT_CARD);
    }

    @Test
    public void showEnterPaymentCardDetailsShouldShowChooseExistingCard() {
        when(this.controller.choosePaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyString(), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.isAddPaymentCard(any(CartCmdImpl.class))).thenReturn(false);
        when(this.mockPaymentService.populatePaymentDetails(null)).thenReturn(new PaymentDetailsCmdImpl());
        when(this.mockBindingResult.hasErrors()).thenReturn(false);
        when(this.controller.getConfirmExistingCardView(any(CartCmdImpl.class), any(HttpServletRequest.class))).thenReturn(new ModelAndView(PageView.CONFIRM_EXISTING_PAYMENT_CARD_PAYMENT));
        when(this.mockCartCmd.getPaymentCardAction()).thenReturn(String.valueOf(TEST_PAYMENT_CARD_ID_1));
        when(this.mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = this.controller.choosePaymentCard(mockHttpSession, this.mockCartCmd, mockBindingResult, CookieConstant.COOKIE_STATUS_OFF, this.mockHttpRequest);

        assertViewName(result, PageView.CONFIRM_EXISTING_PAYMENT_CARD_PAYMENT);
    }

    @Test(expected = ControllerException.class)
    public void receiveCyberSourcePostReplyShouldFailWithInvalidReply() {
        when(this.controller.receiveCyberSourcePostReply(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyMap())).thenCallRealMethod();
        when(mockCartDTO.getCyberSourceReply()).thenReturn(mockPostReply);
        when(mockPostReply.toString()).thenReturn("");

        when(this.mockBindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        doNothing().when(this.mockCyberSourcePostReplyValidator).validate(anyObject(), any(Errors.class));

        this.controller.receiveCyberSourcePostReply(mockHttpSession, mockCartCmd, this.mockBindingResult, getTestReplyArguments());
    }

    @Test
    public void receiveCyberSourcePostReplyShouldReturnView() {
        when(this.controller.receiveCyberSourcePostReply(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyMap())).thenCallRealMethod();
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenReturn(PageView.PAYMENT_ACCEPTED);
        when(this.mockBindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        when(this.mockPostReply.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());
        doNothing().when(this.mockCyberSourcePostReplyValidator).validate(anyObject(), any(Errors.class));
        when(mockCartDTO.getCyberSourceReply()).thenReturn(mockPostReply);
        when(mockPostReply.toString()).thenReturn("");
        when(mockPostReply.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());

        ModelAndView result = this.controller.receiveCyberSourcePostReply(mockHttpSession, mockCartCmd, this.mockBindingResult, getTestReplyArguments());

        assertViewName(result, PageView.PAYMENT_ACCEPTED);
    }
    
    @Test
    public void receiveCyberSourcePostReplyShouldNotRemoveSessionDataIfViewNotPaymentAccepted() {
        when(this.controller.receiveCyberSourcePostReply(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), anyMap())).thenCallRealMethod();
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenReturn(PageView.PAYMENT_INCOMPLETE);
        when(this.mockBindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        doNothing().when(this.mockCyberSourcePostReplyValidator).validate(anyObject(), any(Errors.class));
        when(mockCartDTO.getCyberSourceReply()).thenReturn(mockPostReply);
        when(mockPostReply.toString()).thenReturn("");

        ModelAndView result = this.controller.receiveCyberSourcePostReply(mockHttpSession, mockCartCmd, this.mockBindingResult, getTestReplyArguments());

        assertViewName(result, PageView.PAYMENT_INCOMPLETE);
    }

    @Test
    public void payUsingSavedPaymentCardShouldReturnAcceptedView() {
        when(this.controller.payUsingSavedPaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenReturn(PageView.PAYMENT_ACCEPTED);
        when(this.mockPostReply.getDecision()).thenReturn(CyberSourceDecision.ACCEPT.code());
        when(mockCartDTO.getCyberSourceReply()).thenReturn(mockPostReply);
        when(mockPostReply.toString()).thenReturn("");

        ModelAndView result = this.controller.payUsingSavedPaymentCard(mockHttpSession, mockCartCmd, mockHttpRequest);

        assertViewName(result, PageView.PAYMENT_ACCEPTED);
    }

    @Test
    public void payUsingSavedPaymentCardShouldReturnCancelledView() {
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setToPayAmount(CartTestUtil.TEST_TOPAY_AMOUT);
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(sessionData);
        when(this.controller.payUsingSavedPaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(HttpServletRequest.class))).thenCallRealMethod();
        when(this.controller.resolveViewName(any(CyberSourcePostReplyDTO.class))).thenReturn(PageView.PAYMENT_CANCELLED);
        when(this.controller.isCardPaymentRequired(any(CartSessionData.class))).thenReturn(true);
        when(this.mockPostReply.getDecision()).thenReturn(CyberSourceDecision.CANCEL.code());
        when(mockCartDTO.getCyberSourceReply()).thenReturn(mockPostReply);
        when(mockPostReply.toString()).thenReturn("");
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = this.controller.payUsingSavedPaymentCard(mockHttpSession, mockCartCmd, mockHttpRequest);

        assertViewName(result, PageView.PAYMENT_CANCELLED);
    }

    @Test
    public void showChoosePaymentCardShouldReturnChooseView() {
        when(this.controller.showChoosePaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(HttpServletResponse.class))).thenCallRealMethod();
        when(this.controller.isCardPaymentRequired(any(CartSessionData.class))).thenReturn(true);
        when(this.controller.getShowChoosePaymentCardView(any(CartDTO.class), any(CartCmdImpl.class), any(HttpServletResponse.class))).thenReturn(new ModelAndView());
        when(this.mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        this.controller.showChoosePaymentCard(mockHttpSession, mockCartCmd, mockHttpResponse);

        verify(this.controller).getShowChoosePaymentCardView(any(CartDTO.class), any(CartCmdImpl.class), any(HttpServletResponse.class));
        verify(this.controller, never()).getConfirmPaymentView(any(CartCmdImpl.class));
    }

    @Test
    public void showChoosePaymentCardShouldReturnConfirmView() {
        when(this.controller.showChoosePaymentCard(any(HttpSession.class), any(CartCmdImpl.class), any(HttpServletResponse.class))).thenCallRealMethod();
        when(this.controller.isCardPaymentRequired(getTestCartSessionDataDTO1())).thenReturn(false);
        when(this.controller.getShowChoosePaymentCardView(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(new ModelAndView());
        when(this.controller.getConfirmPaymentView(any(CartCmdImpl.class))).thenReturn(new ModelAndView());
        when(this.mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        this.controller.showChoosePaymentCard(mockHttpSession, mockCartCmd, mockHttpResponse);

        verify(this.controller, never()).getShowChoosePaymentCardView(any(CartDTO.class), any(CartCmdImpl.class), any(HttpServletResponse.class));
        verify(this.controller).getConfirmPaymentView(any(CartCmdImpl.class));
    }

    @Test
    public void shouldSetCookieStatus() {
        doCallRealMethod().when(this.controller).setCookieStatus(any(HttpServletResponse.class));
        doNothing().when(this.mockHttpResponse).addCookie(any(Cookie.class));
        this.controller.setCookieStatus(this.mockHttpResponse);
        verify(this.mockHttpResponse).addCookie(any(Cookie.class));
    }

    @Test
    public void isCookieEnabledOnClientShouldReturnTrue() {
        when(this.controller.isCookieEnabledOnClient(anyString())).thenCallRealMethod();
        assertTrue(this.controller.isCookieEnabledOnClient(CookieConstant.COOKIE_STATUS_ON));
    }

    @Test
    public void isCookieEnabledOnClientShouldReturnFalse() {
        when(this.controller.isCookieEnabledOnClient(anyString())).thenCallRealMethod();
        assertFalse(this.controller.isCookieEnabledOnClient(CookieConstant.COOKIE_STATUS_OFF));
    }

    @Test
    public void shouldAddPaymentCardSelectListToModelForAutoTopOrder() {
        doCallRealMethod().when(this.controller).addPaymentCardSelectListToModel(any(ModelAndView.class), any(CartDTO.class));
        when(this.mockPaymentCardSelectListService.getPaymentCardSelectListWithOnlySaveOption(anyLong())).thenReturn(this.mockSelectListDTO);
        when(this.mockPaymentCardSelectListService.getPaymentCardSelectListWithAllOptions(anyLong())).thenReturn(this.mockSelectListDTO);

        ModelAndView modelAndView = new ModelAndView();
        CartCmdImpl cmd = new CartCmdImpl();
        this.controller.addPaymentCardSelectListToModel(modelAndView, getNewCartDTOWithItem());
        assertAndReturnModelAttributeOfType(modelAndView, PageAttribute.PAYMENT_CARDS, SelectListDTO.class);
    }

    @Test
    public void shouldAddPaymentCardSelectListToModelForNotAutoTopOrder() {
        doCallRealMethod().when(this.controller).addPaymentCardSelectListToModel(any(ModelAndView.class), any(CartDTO.class));
        when(this.mockPaymentCardSelectListService.getPaymentCardSelectListWithOnlySaveOption(anyLong())).thenReturn(this.mockSelectListDTO);
        when(this.mockPaymentCardSelectListService.getPaymentCardSelectListWithAllOptions(anyLong())).thenReturn(this.mockSelectListDTO);

        ModelAndView modelAndView = new ModelAndView();
        this.controller.addPaymentCardSelectListToModel(modelAndView, getNewCartDTOWithItem());
        assertAndReturnModelAttributeOfType(modelAndView, PageAttribute.PAYMENT_CARDS, SelectListDTO.class);

        verify(this.mockPaymentCardSelectListService, never()).getPaymentCardSelectListWithOnlySaveOption(anyLong());
        verify(this.mockPaymentCardSelectListService).getPaymentCardSelectListWithAllOptions(anyLong());
    }

    @Test
    public void shouldShowChoosePaymentCardView() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartDTO cartDTO = getNewCartDTOWithItem();
        cartDTO.addCartItem(AutoTopUpTestUtil.getTestAutoTopUpItemDTO1());
        this.controller = new BasePaymentController();
        this.controller.cardService = this.mockCardService;
        this.controller.paymentCardSelectListService = this.mockPaymentCardSelectListService;

        when(this.mockCardService.getCardDTOById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(this.mockPaymentCardSelectListService.getPaymentCardSelectListWithOnlySaveOption(anyLong())).thenReturn(new SelectListDTO());
        doNothing().when(mockHttpResponse).addCookie(any(Cookie.class));

        ModelAndView result = this.controller.getShowChoosePaymentCardView(cartDTO, cmd, mockHttpResponse);

        assertEquals(PageView.CHOOSE_PAYMENT_CARD, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void shouldShowConfirmPaymentView() {
        CartCmdImpl cmd = new CartCmdImpl();
        this.controller = new BasePaymentController();

        ModelAndView result = this.controller.getConfirmPaymentView(cmd);

        assertEquals(PageView.CONFIRM_EXISTING_PAYMENT_CARD_PAYMENT, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.CART));
        assertTrue(result.getModel().get(PageCommand.CART) instanceof CartCmdImpl);
    }

    @Test
    public void updateOrderAndPaymentCardSettlementToCmdShouldReturnCmd() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setOrderId(OrderTestUtil.ORDER_ID);
        sessionData.setPaymentCardSettlementId(TEST_PAYMENT_CARD_ID_1);
        this.controller = new BasePaymentController();
        this.controller.orderDataService = this.mockOrderDataService;
        this.controller.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;

        when(this.mockOrderDataService.findById(anyLong())).thenReturn(getTestOrderDTO1());
        when(this.mockPaymentCardSettlementDataService.findById(anyLong())).thenReturn(getTestPaymentCardSettlementDTO1());

        CartCmdImpl result = this.controller.updateOrderAndPaymentCardSettlementToCmd(sessionData, CartTestUtil.getCartDTOWithAllItemsForOrderNewCard(), cmd);

        assertNotNull(result);
        assertNotNull(result.getCartDTO().getOrder());
        assertEquals(getTestOrderDTO1().getOrderDate(), result.getCartDTO().getOrder().getOrderDate());
    }

    @Test
    public void updateOrderAndPaymentCardSettlementToCmdShouldNotSetPaymentCardIfNotUsed() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setOrderId(OrderTestUtil.ORDER_ID);
        this.controller = new BasePaymentController();
        this.controller.orderDataService = this.mockOrderDataService;
        this.controller.paymentCardSettlementDataService = this.mockPaymentCardSettlementDataService;

        when(this.mockOrderDataService.findById(anyLong())).thenReturn(getTestOrderDTO1());

        CartCmdImpl result = this.controller.updateOrderAndPaymentCardSettlementToCmd(sessionData,
                        CartTestUtil.getCartDTOWithAllItemsForOrderNewCard(), cmd);

        assertNotNull(result);
        assertNotNull(result.getCartDTO().getOrder());
        assertEquals(getTestOrderDTO1().getOrderDate(), result.getCartDTO().getOrder().getOrderDate());
        verify(this.mockPaymentCardSettlementDataService, never()).findById(anyLong());
    }

    @Test
    public void updateCartSessionTotalsToCmdShouldUpdateCmd() {
        CartCmdImpl cmd = new CartCmdImpl();
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setCartTotal(CartTestUtil.TEST_TOPAY_AMOUT);
        sessionData.setToPayAmount(CartTestUtil.TEST_CART_TOTOAL);
        this.controller = new BasePaymentController();
        this.controller.updateCartSessionTotalsToCmd(sessionData, cmd);

        assertNotNull(cmd);
    }

    @Test
    public void setCartCmdPropertiesForProcessPaymentGatewayReplyShouldUpdateCartCmd() {
        CartCmdImpl cmd = new CartCmdImpl();
        this.controller = new BasePaymentController();
        CartDTO cartDTO = getNewCartDTOWithItem();
        cartDTO.setCustomerId(CartTestUtil.CUSTOMER_ID);
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setWebCreditApplyAmount(CartTestUtil.TEST_TRAVEL_CARD_PRICE);
        sessionData.setToPayAmount(CartTestUtil.TEST_TOPAY_AMOUT);

        this.controller.setCartCmdPropertiesForProcessPaymentGatewayReply(sessionData, cartDTO, cmd);

        assertNotNull(cmd);
        assertNull(cmd.getTotalAmt());
    }

    @Test
    public void isAddPaymentCardShouldReturnTrue() {
        CartCmdImpl cmd = new CartCmdImpl();
        this.controller = new BasePaymentController();
        cmd.setPaymentCardAction(AddPaymentCardAction.ADD.name());
        assertTrue(this.controller.isAddPaymentCard(cmd));
    }

    @Test
    public void isCardPaymentRequiredShouldReturnTrue() {
        this.controller = new BasePaymentController();
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setToPayAmount(CartTestUtil.TEST_CART_TOTOAL);
        assertTrue(this.controller.isCardPaymentRequired(sessionData));
    }

    @Test
    public void isAddPaymentCardShouldReturnFalse() {
        CartCmdImpl cmd = new CartCmdImpl();
        this.controller = new BasePaymentController();
        cmd.setPaymentCardAction("Test");
        assertFalse(this.controller.isAddPaymentCard(cmd));
    }

    @Test
    public void isCardPaymentRequiredShouldReturnFalse() {
        this.controller = new BasePaymentController();
        CartSessionData sessionData = getTestCartSessionDataDTO1();
        sessionData.setToPayAmount(0);
        assertFalse(this.controller.isCardPaymentRequired(sessionData));
    }
    
    @Test
    public void cancelPaymentPageShouldRedirectToCheckoutPageTest(){
        when(this.controller.cancelPaymentPage()).thenCallRealMethod();
        assertEquals(PageUrl.CHECKOUT, ((RedirectView) this.controller.cancelPaymentPage().getView()).getUrl());
    }
    
    @Test
    public void showChoosePaymentCardForCancelShouldRedirectToChoosePaymentCardPageTest(){
        when(this.controller.showChoosePaymentCardForCancel(any(HttpSession.class),any(CartCmdImpl.class))).thenCallRealMethod();
        when(mockCartService.findById(TEST_PAYMENT_CARD_ID_1)).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = this.controller.showChoosePaymentCardForCancel(mockHttpSession, mockCartCmd);
        assertViewName(result,PageView.CHOOSE_PAYMENT_CARD);
    }
}
