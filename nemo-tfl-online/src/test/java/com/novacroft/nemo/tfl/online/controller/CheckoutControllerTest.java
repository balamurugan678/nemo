package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.WEB_CREDIT_AVAILABLE_AMOUNT;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO2;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO3;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO5;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithPAYGAndATUItems;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PaymentTermsValidator;
import com.novacroft.nemo.tfl.common.form_validator.WebCreditPurchaseValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for Checkout Controller
 */
public class CheckoutControllerTest {
    private CheckoutController controller;
    private CartCmdImpl mockCmd;
    private CartDTO mockCartDTO;
    private BeanPropertyBindingResult mockResult;
    private PaymentTermsValidator mockPaymentTermsValidator;
    private PaymentService mockPaymentService;
    private SecurityService mockSecurityService;
    private WebCreditService mockWebCreditService;
    private WebCreditPurchaseValidator mockWebCreditPurchaseValidator;
    private Model mockModel;
    private CartService mockCartService;    
    private HttpSession mockSession; 
    private CustomerService mockCustomerService;
    private FulfilmentQueuePopulationService mockFulfilmentQueuePopulationService;
    private CartSessionData cartSessionData;
    private CartCmdImpl cartCmdImpl;

    @Before
    public void setUp() {
        controller = new CheckoutController();
        cartCmdImpl = new CartCmdImpl();
        
        mockCmd = mock(CartCmdImpl.class);
        mockCartDTO = mock(CartDTO.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockPaymentTermsValidator = mock(PaymentTermsValidator.class);
        mockPaymentService = mock(PaymentService.class);
        mockSecurityService = mock(SecurityService.class);
        mockWebCreditService = mock(WebCreditService.class);
        mockWebCreditPurchaseValidator = mock(WebCreditPurchaseValidator.class);
        mockModel = mock(Model.class);
        mockCartService = mock(CartService.class);        
        mockSession = mock(HttpSession.class);
        mockCustomerService = mock(CustomerService.class);
        mockFulfilmentQueuePopulationService=mock(FulfilmentQueuePopulationService.class);
        cartSessionData = new CartSessionData(null);
                        
        controller.paymentTermsValidator = mockPaymentTermsValidator;
        controller.paymentService = mockPaymentService;
        controller.cartService = mockCartService;
        setField(controller, "securityService", mockSecurityService);
        controller.webCreditService = mockWebCreditService;
        controller.webCreditPurchaseValidator = mockWebCreditPurchaseValidator;
        controller.fulfilmentQueuePopulationService=mockFulfilmentQueuePopulationService;
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }

    @Test
    public void shouldShowExistingUserCheckout() {
        when(mockWebCreditService.getAvailableBalance(anyLong())).thenReturn(99);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.showCheckout(mockSession, mockModel, getTestCartCmd1());
        verify(mockCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageView.CHECKOUT, result.getViewName());
    }

    @Test
    public void shouldShowNewUserCheckout() {
        when(mockWebCreditService.getAvailableBalance(anyLong())).thenReturn(99);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.showCheckout(mockSession, mockModel, getTestCartCmd1());

        assertEquals(PageView.CHECKOUT, result.getViewName());
    }

    @Test
    public void shouldShowPaymentPageOnSuccessfulCheckout() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCmd.getCartDTO()).thenReturn(mockCartDTO);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(),anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.checkout(mockSession, mockModel, this.mockCmd, mockResult);
        assertEquals(PageUrl.PAYMENT, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shouldShowCheckoutPageOnError() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.checkout(mockSession, mockModel, this.mockCmd, mockResult);
        assertEquals(PageView.CHECKOUT, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToCollectPurchase() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shouldUpdateToPayWithWebCredit() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());        
        when(mockResult.hasErrors()).thenReturn(false);        
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.updateToPayWithWebCredit(mockSession, mockModel, this.mockCmd, mockResult);
        assertEquals(PageView.CHECKOUT, result.getViewName());
    }
    
    @Test
    public void getCartCmdShouldReturnCartCmdImpl() {
        CartCmdImpl cartCmdImpl = controller.getCartCmd();
        assertNotNull(cartCmdImpl);
        
    } 
    
    @Test
    public void shouldShowPaymentPageOnSuccessfulCheckoutWithManageAutoTopUpModeTrue() {
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO2());
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCmd.getCartDTO()).thenReturn(mockCartDTO);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.checkout(mockSession, mockModel, this.mockCmd, mockResult);
        assertEquals(PageUrl.PAYMENT, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void removeWebCreditForAutoTopUpAndPayAsYou() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO2();
        controller.removeWebCreditForAutoTopUpAndPayAsYou(cartSessionData, getNewCartDTOWithPAYGAndATUItems());
        assertEquals(CartItemTestUtil.ZERO, cartSessionData.getWebCreditAvailableAmount());
    }
    
    @Test
    public void applyWebCreditToPayAmountShouldSetToZero() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO2();       
        when(this.mockCmd.getWebCreditApplyAmount()).thenReturn(null);

        controller.applyWebCreditToPayAmount(cartSessionData, this.mockCmd);
        assertEquals(CartItemTestUtil.ZERO, cartSessionData.getWebCreditApplyAmount());
    }
    
    @Test
    public void checkoutShouldDoImplicitUpdateTest(){
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCmd.getCartDTO()).thenReturn(mockCartDTO);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO3());
        
        cartCmdImpl.setWebCreditApplyAmount(50);
        controller.checkout(mockSession, mockModel, cartCmdImpl, mockResult);
        
        cartSessionData = CartUtil.getCartSessionDataDTOFromSession(mockSession);
        assertTrue(450 == cartSessionData.getToPayAmount());
        
    }
    
    @Test
    public void shouldUpdateWebCreditAndTotalsToCmdWithNewWebCreditAvailableBalance() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO5();       
        controller.updateWebCreditAndTotalsToCmd(cartSessionData, cartCmdImpl);
        int expectedValue = WEB_CREDIT_AVAILABLE_AMOUNT;
        assertEquals(expectedValue, cartCmdImpl.getWebCreditAvailableAmount().intValue());
    }

    @Test
    public void shouldSetWebCreditAndTotalsToCmdWithNewWebCreditAvailableBalance() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO5();
        controller.setWebCreditAndTotalsToCmd(cartSessionData, cartCmdImpl);
        int expectedValue = WEB_CREDIT_AVAILABLE_AMOUNT;
        assertEquals(expectedValue, cartCmdImpl.getWebCreditAvailableAmount().intValue());
    }
}
