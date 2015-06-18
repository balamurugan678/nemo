package com.novacroft.nemo.tfl.innovator.controller.purchase;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmdWithOrder;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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

import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
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
    private FulfilmentQueuePopulationService mockFulfilmentQueuePopulationService;
    private HttpSession mockSession;
    private CustomerService mockCustomerService;

    @Before
    public void setUp() {
        controller = new CheckoutController();

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
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

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
    public void shouldupdateToPayWithWebCredit() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.updateToPayWithWebCredit(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageView.CHECKOUT, result.getViewName());
    }

    @Test
    public void shouldNotupdateToPayWithWebCredit() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.updateToPayWithWebCredit(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageView.CHECKOUT, result.getViewName());
    }

    @Test
    public void shouldupdateToPayWithZeroWebCredit() {
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCartDTO.getOrder()).thenReturn(getTestOrderDTO1());
        when(mockCartDTO.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTO1());
        when(mockPaymentService.createOrderAndSettlementsFromCart(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(mockCmd);
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.updateToPayWithWebCredit(mockSession, mockModel, mockCmd, mockResult);
        assertEquals(PageView.CHECKOUT, result.getViewName());
    }

    @Test
    public void shouldRemoveWebCreditForAutoTopUpAndPayAsYou() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        cartSessionData.setWebCreditAvailableAmount(5);
        cartSessionData.setTicketType(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        controller.removeWebCreditForAutoTopUpAndPayAsYou(cartSessionData);
        assertEquals(0, cartSessionData.getWebCreditAvailableAmount().intValue());
    }

    @Test
    public void applyWebCreditToPayAmountShouldSetWebCreditApplyAmountToZeroIfNullInCmd() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setWebCreditApplyAmount(null);
        controller.applyWebCreditToPayAmount(cartSessionData, cmd);
        assertEquals(0, cartSessionData.getWebCreditApplyAmount().intValue());
    }

    @Test
    public void shouldNotUpdatePaymentCardSettlementToCartSessionDataIfPaymentCardSettlementIsNull() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        controller.updateOrderSettlementToCartSessionData(getTestCartCmdWithOrder(), cartSessionData);
        assertNull(cartSessionData.getPaymentCardSettlementId());
    }

    @Test
    public void shouldNotUpdateWebCreditAndTotalsToCmdWhenCmdWebCreditApplyIsGreaterThanZero() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        cartSessionData.setWebCreditApplyAmount(5);
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setWebCreditApplyAmount(1);

        controller.updateWebCreditAndTotalsToCmd(cartSessionData, cmd);

        assertFalse(cartSessionData.getWebCreditApplyAmount().equals(cmd.getWebCreditApplyAmount()));
    }

    @Test
    public void shouldNotUpdateWebCreditAndTotalsToCmdWhenCartSessionDataWebCreditApplyIsNull() {
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        cartSessionData.setWebCreditApplyAmount(null);
        CartCmdImpl cmd = new CartCmdImpl();
        Integer expected = 5;
        cmd.setWebCreditApplyAmount(expected);

        controller.updateWebCreditAndTotalsToCmd(cartSessionData, cmd);

        assertEquals(expected, cmd.getWebCreditApplyAmount());
    }
}
