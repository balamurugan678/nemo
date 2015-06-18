package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithAutoloadStateForNoPendingTopUp;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithAutoloadStateForPendingTopUp;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.AutoTopUpOrderConstant;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseTestController;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.AutoTopUpValidator;
import com.novacroft.nemo.tfl.common.form_validator.ChoosePaymentCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.PaymentTermsValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

public class ManageAutoTopUpControllerTest extends BaseTestController {

    private ManageAutoTopUpController controller;
    private BindingAwareModelMap model;
    private SelectListService mockSelectListService;
    private LocationSelectListService mockLocationSelectListService;
    private ManageCardCmd mockManageCardCmd;
    private ManageCardCmd manageCardCmd;
    private CardService mockCardService;
    private CardDataService mockCardDataService;
    private CustomerService mockCustomerService;
    private SelectStationValidator mockSelectStationValidator;
    private CardUpdateService mockCardUpdateService;
    private BeanPropertyBindingResult mockBindingResult;
    private HttpSession mockHttpSession;
    private CustomerService mockcustomerService;
    private SystemParameterService mocksystemParameterService;
    private CustomerDataService mockcustomerDataService;
    private PaymentCardSelectListService mockPaymentCardSelectListService;
    private AutoTopUpValidator mockAutoTopUpValidator;
    private ChoosePaymentCardValidator mockChoosePaymentCardValidator;
    private OrderService mockOrderService;
    private PaymentTermsValidator mockPaymentTermsValidator;
    private GetCardService mockGetCardService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        manageCardCmd = new ManageCardCmd();
        mockHttpSession = mock(HttpSession.class);
        mockLocationSelectListService = mock(LocationSelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        mockManageCardCmd = mock(ManageCardCmd.class);
        mockCardService = mock(CardService.class);
        mockCustomerService = mock(CustomerService.class);
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockBindingResult = mock(BeanPropertyBindingResult.class);
        mocksystemParameterService = mock(SystemParameterService.class);
        mockcustomerService = mock(CustomerService.class);
        mockcustomerDataService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockPaymentCardSelectListService = mock(PaymentCardSelectListService.class);
        mockAutoTopUpValidator = mock(AutoTopUpValidator.class);
        mockChoosePaymentCardValidator = mock(ChoosePaymentCardValidator.class);
        mockOrderService = mock(OrderService.class);
        mockPaymentTermsValidator = mock(PaymentTermsValidator.class);
        mockGetCardService = mock(GetCardService.class);

        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(new SelectListDTO());
        when(mockcustomerDataService.findByCardId(anyLong())).thenReturn(new CustomerDTO());
        doNothing().when(mockHttpSession).setAttribute(anyString(), any());
        when(mockCustomerService.getPreferredStationId(anyLong())).thenReturn(1L);
        doNothing().when(mockSelectStationValidator).validate(anyObject(), any(Errors.class));
        when(mockCardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt())).thenReturn(1);
        when(mockHttpSession.getAttribute("OLD_CARD")).thenReturn(mockManageCardCmd);
        when(mockCardDataService.findById(anyLong())).thenReturn(new CardDTO());
        doNothing().when(mockChoosePaymentCardValidator).validate(any(Object.class), any(Errors.class));
        controller = new ManageAutoTopUpController();
        model = new BindingAwareModelMap();

        setField(controller, "selectListService", mockSelectListService);
        setField(controller, "locationSelectListService", mockLocationSelectListService);
        setField(controller, "cardService", mockCardService);
        setField(controller, "customerService", mockCustomerService);
        setField(controller, "cardUpdateService", mockCardUpdateService);
        setField(controller, "selectStationValidator", mockSelectStationValidator);
        setField(controller, "securityService", mockSecurityService);
        setField(controller, "systemParameterService", mocksystemParameterService);
        setField(controller, "customerService", mockcustomerService);
        setField(controller, "customerDataService", mockcustomerDataService);
        setField(controller, "cardDataService", mockCardDataService);
        setField(controller, "paymentCardSelectListService", mockPaymentCardSelectListService);
        setField(controller, "autoTopUpValidator", mockAutoTopUpValidator);
        setField(controller, "choosePaymentCardValidator", mockChoosePaymentCardValidator);
        setField(controller, "orderService", mockOrderService);
        setField(controller, "paymentTermsValidator", mockPaymentTermsValidator);
        setField(controller, "getCardService", mockGetCardService);
    }

    @Test
    public void testCancel() {
        ModelAndView result = controller.cancel(manageCardCmd);
        assertEquals(redirectViewCheck(result), PageUrl.VIEW_OYSTER_CARD);
    }

    @Test
    public void testPopulatePayAsYouGoAutoTopUpAmtsSelectList() {
        controller.populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @Test
    public void testPopulateLocationsSelectList() {
        controller.populateLocationsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.LOCATIONS));
    }

    @Test
    public void testViewAutoTopUpLoadManageAutoTopUp() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("234");
        Mockito.doReturn(cardDTO).when(mockCardDataService).findById(anyLong());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(OrderTestUtil.getOrderDTOWithCompleteItemsManageATU());
        ModelAndView result = controller.postViewAutoTopUp(manageCardCmd, mockHttpSession);
        assertViewName(result, PageView.MANAGE_AUTO_TOP_UP);
    }

    @Test
    public void testViewAutoTopUpLoadManageAutoTopUpWithErrorOrder() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("234");
        Mockito.doReturn(cardDTO).when(mockCardDataService).findById(anyLong());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(OrderTestUtil.getOrderDTOWithErrorItemsManageATU());
        ModelAndView result = controller.postViewAutoTopUp(manageCardCmd, mockHttpSession);
        assertViewName(result, PageView.MANAGE_AUTO_TOP_UP);
    }

    @Test
    public void testManageAutoTopUpResultHasErrors() {
        CardDTO mockCardDTO = new CardDTO();
        mockCardDTO.setCardNumber(OYSTER_NUMBER_1);
        doNothing().when(mockAutoTopUpValidator).validate(anyObject(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(true);
        Mockito.doReturn(mockCardDTO).when(mockCardDataService).findById(anyLong());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadStateForPendingTopUp());
        when(mockManageCardCmd.getAutoTopUpState()).thenReturn(2);
        ModelAndView result = controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        assertViewName(result, PageView.MANAGE_AUTO_TOP_UP);
    }

    @Test
    public void testManageAutoTopUpCommandsEqual() {
        CardDTO mockCardDTO = new CardDTO();
        mockCardDTO.setCardNumber(OYSTER_NUMBER_1);
        doNothing().when(mockAutoTopUpValidator).validate(anyObject(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(false);
        Mockito.doReturn(mockCardDTO).when(mockCardDataService).findById(anyLong());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadStateForNoPendingTopUp());
        mockManageCardCmd.setStartDateforAutoTopUpCardActivate(anyString());
        mockManageCardCmd.setEndDateforAutoTopUpCardActivate(anyString());
        when(mockManageCardCmd.getAutoTopUpStateExistingPendingAmount()).thenReturn(3);
        when(mockManageCardCmd.getAutoTopUpState()).thenReturn(2);
        mockHttpSession.setAttribute(AutoTopUpOrderConstant.COMMAND_OBJECT, mockManageCardCmd);
        ModelAndView result = controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        assertEquals(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP, result.getViewName());
    }

    @Test
    public void testManageAutoTopUpCommandsEqualWithPendingAmount() {
        CardDTO mockCardDTO = new CardDTO();
        mockCardDTO.setCardNumber(OYSTER_NUMBER_1);
        doNothing().when(mockAutoTopUpValidator).validate(anyObject(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(false);
        Mockito.doReturn(mockCardDTO).when(mockCardDataService).findById(anyLong());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadStateForNoPendingTopUp());
        mockManageCardCmd.setStartDateforAutoTopUpCardActivate(anyString());
        mockManageCardCmd.setEndDateforAutoTopUpCardActivate(anyString());
        when(mockManageCardCmd.getAutoTopUpStateExistingPendingAmount()).thenReturn(2);
        when(mockManageCardCmd.getAutoTopUpState()).thenReturn(2);
        mockHttpSession.setAttribute(AutoTopUpOrderConstant.COMMAND_OBJECT, mockManageCardCmd);
        ModelAndView result = controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        assertEquals(PageUrl.AUTO_TOP_UP_CONFIRMATION_ON_PAYMENT_CARD_CHANGE, redirectViewCheck(result));
    }

    @Test
    public void testManageAutoTopUpCommandsEqualWithNoAutoTopUp() {
        CardDTO mockCardDTO = new CardDTO();
        mockCardDTO.setCardNumber(OYSTER_NUMBER_1);
        doNothing().when(mockAutoTopUpValidator).validate(anyObject(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(false);
        Mockito.doReturn(mockCardDTO).when(mockCardDataService).findById(anyLong());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadStateForNoPendingTopUp());
        mockManageCardCmd.setStartDateforAutoTopUpCardActivate(anyString());
        mockManageCardCmd.setEndDateforAutoTopUpCardActivate(anyString());
        when(mockManageCardCmd.getAutoTopUpStateExistingPendingAmount()).thenReturn(3);
        when(mockManageCardCmd.getAutoTopUpState()).thenReturn(null);
        mockHttpSession.setAttribute(AutoTopUpOrderConstant.COMMAND_OBJECT, mockManageCardCmd);
        ModelAndView result = controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        assertEquals(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP, result.getViewName());
    }

    @Test
    public void testMycardAutoTopUpView() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("15351");
        Mockito.doReturn(cardDTO).when(mockCardDataService).findById(anyLong());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(15351L);
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        ModelAndView result = controller.mycardAutoTopUpView(manageCardCmd, mockHttpSession);
        assertViewName(result, PageView.MANAGE_AUTO_TOP_UP);
    }

    @Test
    public void addPaymentCardRedirectsToTopUpTicket() {

        RedirectView result = controller.addNewPaymentCard(manageCardCmd, null, mockHttpSession);

        assertNotNull(result.getAttributesMap().get(PageCommand.CART_ITEM));
        assertEquals(PageUrl.TOP_UP_TICKET, result.getUrl());
    }

    @Test
    public void testSetAutoTopUpAmountStatusNew() {
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(OrderTestUtil.getOrderDTOWithNewItemsManageATU());
        controller.setAutoTopUpAmountStatus(manageCardCmd);
        assertTrue(manageCardCmd.isDisableAutoTopUpConfigurationChange());
    }

    @Test
    public void testSetAutoTopUpAmountStatusNotNew() {
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(OrderTestUtil.getOrderDTOWithPaidItemsManageATU());
        controller.setAutoTopUpAmountStatus(manageCardCmd);
        assertNotEquals("New", manageCardCmd.isDisableAutoTopUpConfigurationChange());
    }

    @Test
    public void testViewAutoTopUpLoadManageAutoTopUpWithoutCardNumber() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(null);
        Mockito.doReturn(cardDTO).when(mockCardDataService).findById(anyLong());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        when(mockOrderService.findOrderItemsByCustomerId(anyLong())).thenReturn(OrderTestUtil.getOrderDTOWithCancelledItemsManageATU());
        RedirectView redirectView = (RedirectView) controller.postViewAutoTopUp(manageCardCmd, mockHttpSession).getView();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, redirectView.getUrl());
    }

    @Test
    public void myCardAutoTopUpViewWithoutCardNumber() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(null);
        Mockito.doReturn(cardDTO).when(mockCardDataService).findById(anyLong());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(15351L);
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        RedirectView redirectView = (RedirectView) controller.mycardAutoTopUpView(manageCardCmd, mockHttpSession).getView();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, redirectView.getUrl());
    }

    @Test
    public void getCustomerShouldInvokeServicesTest() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockcustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        controller.getCustomer();
        verify(mockSecurityService).getLoggedInCustomer();
        verify(mockcustomerDataService).findById(any(Long.class));
    }

    @Test
    public void validateAutoTopUpStatePendingAmountTest() {
        ManageCardCmd cmd = new ManageCardCmd();
        when(mockCardDataService.findById(any(Long.class))).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadStateForNoPendingTopUp());
        controller.validateAutoTopUpStatePendingAmount(cmd, mockHttpSession);
        assertEquals(new Integer(0), cmd.getAutoTopUpState());
    }

    @Test
    public void shouldPopulatePaymentcardSelectList() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        when(mockcustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        controller.populatePaymentcardSelectList(model);
        verify(mockPaymentCardSelectListService).getPaymentCardSelectListForAdHocLoad(anyLong());
    }

    @Test
    public void manageAutoTopUpShouldInvokePaymentTermsValidator() {
        CardDTO mockCardDTO = new CardDTO();
        mockCardDTO.setCardNumber(OYSTER_NUMBER_1);
        doNothing().when(mockAutoTopUpValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockPaymentTermsValidator).validate(anyObject(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(true);
        Mockito.doReturn(mockCardDTO).when(mockCardDataService).findById(anyLong());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithAutoloadStateForPendingTopUp());
        when(mockManageCardCmd.getAutoTopUpState()).thenReturn(2);
        controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        verify(mockPaymentTermsValidator).validate(any(Object.class), any(Errors.class));
    }
}
