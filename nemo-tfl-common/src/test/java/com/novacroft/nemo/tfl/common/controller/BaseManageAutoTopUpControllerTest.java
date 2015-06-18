package com.novacroft.nemo.tfl.common.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO5;
import static com.novacroft.nemo.test_support.LocationTestUtil.getLocationSelectList;
import static com.novacroft.nemo.tfl.common.constant.AutoLoadState.NO_TOP_UP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.test_support.PaymentCardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public class BaseManageAutoTopUpControllerTest {

    private BaseManageAutoTopUpController controller;
    private Model model;
    private Errors errors;
    private HttpSession httpSession;

    private LocationSelectListService locationSelectListService;
    private SelectListService selectListService;
    private PaymentCardSelectListService paymentCardSelectListService;
    private CardService cardService;
    private CustomerDataService customerDataService;
    private CardDataService cardDataService;
    private GetCardService getCardService;
    private CustomerService customerService;
    private SelectStationValidator selectStationValidator;
    private LocationDataService locationDataService;
    private CardUpdateService cardUpdateService;
    private CartService cartService;
    private PaymentService paymentService;
    private AutoTopUpConfigurationService autoTopUpConfigurationService;
    private PaymentCardService paymentCardService;
    private SecurityService mockSecurityService;

    @Before
    public void setUp() throws Exception {
        controller = mock(BaseManageAutoTopUpController.class);
        model = new ExtendedModelMap();

        errors = new BeanPropertyBindingResult(controller, "controller");

        locationSelectListService = mock(LocationSelectListService.class);
        selectListService = mock(SelectListService.class);
        paymentCardSelectListService = mock(PaymentCardSelectListService.class);
        cardService = mock(CardService.class);
        customerDataService = mock(CustomerDataService.class);
        cardDataService = mock(CardDataService.class);
        getCardService = mock(GetCardService.class);
        customerService = mock(CustomerService.class);
        selectStationValidator = mock(SelectStationValidator.class);
        locationDataService = mock(LocationDataService.class);
        cardUpdateService = mock(CardUpdateService.class);
        cartService = mock(CartService.class);
        paymentService = mock(PaymentService.class);
        autoTopUpConfigurationService = mock(AutoTopUpConfigurationService.class);
        paymentCardService = mock(PaymentCardService.class);
        mockSecurityService = mock(SecurityService.class);
        controller.paymentCardService = paymentCardService;
        controller.locationSelectListService = locationSelectListService;
        controller.selectListService = selectListService;
        controller.paymentCardSelectListService = paymentCardSelectListService;
        controller.cardService = cardService;
        controller.customerDataService = customerDataService;
        controller.cardDataService = cardDataService;
        controller.getCardService = getCardService;
        controller.customerService = customerService;
        controller.selectStationValidator = selectStationValidator;
        controller.locationDataService = locationDataService;
        controller.cardUpdateService = cardUpdateService;
        controller.cartService = cartService;
        controller.paymentService = paymentService;
        controller.autoTopUpConfigurationService = autoTopUpConfigurationService;
        controller.securityService = mockSecurityService;

        httpSession = mock(HttpSession.class);
    }

    @Test
    public void populateLocationsSelectList() {
        when(locationSelectListService.getLocationSelectList()).thenReturn(getLocationSelectList());
        doCallRealMethod().when(controller).populateLocationsSelectList(model);
        controller.populateLocationsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.LOCATIONS));
    }

    @Test
    public void populatePayAsYouGoAutoTopUpAmtsSelectList() {
        when(selectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        doCallRealMethod().when(controller).populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        controller.populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @Test
    public void populatePaymentCardTypeSelectList() {
        when(selectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        doCallRealMethod().when(controller).populatePaymentCardTypeSelectList(model);
        controller.populatePaymentCardTypeSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.PAYMENT_CARD_TYPES));
    }

    @Test
    public void checkManageCardCmdForChangesNoChanges() {
        ManageCardCmd manageCardCmd = new ManageCardCmd();
        when(controller.getCustomer()).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(controller).checkManageCardCmdForChanges(manageCardCmd);
        assertTrue(controller.checkManageCardCmdForChanges(manageCardCmd));
    }

    @Test
    public void getCustomerId() {
        when(customerDataService.findByCardId(anyLong())).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(controller).getCustomerId(CardTestUtil.CARD_ID);
        Long customerId = controller.getCustomerId(CardTestUtil.CARD_ID);
        assertNotNull(customerId);
    }

    @Test
    public void manageAutoTopUpActivateNoErrorsOrTopUpRequired() {
        ManageCardCmd cmd = new ManageCardCmd();
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setAutoTopUpState(0);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String iniDate = formatter.format(new Date());
        cmd.setStartDateforAutoTopUpCardActivate(iniDate);

        doNothing().when(selectStationValidator).validate(anyObject(), any(BindingResult.class));
        when(locationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1());
        when(cardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt()))
                .thenReturn(0);
        doCallRealMethod().when(controller).processManageAutoTopUpNoTopUpRequired(any(ManageCardCmd.class), anyLong());

        doCallRealMethod().when(controller).manageAutoTopUpActivate(httpSession, cmd, (BindingResult) errors);
        ModelAndView modelAndView = controller.manageAutoTopUpActivate(httpSession, cmd, (BindingResult) errors);
        assertNotNull(modelAndView);
    }
    
    @Test
    public void manageAutoTopUpActivateWithErrors() {
    	ManageCardCmd cmd = new ManageCardCmd();
    	cmd.setAutoTopUpState(AutoLoadState.TOP_UP_AMOUNT_2.state());
    	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	String iniDate = formatter.format(new Date());
    	cmd.setStartDateforAutoTopUpCardActivate(iniDate);
    	BindingResult mockResult = mock(BindingResult.class);
    	when(mockResult.hasErrors()).thenReturn(true);
    	doCallRealMethod().when(selectStationValidator).validate(anyObject(), any(BindingResult.class));
    	when(locationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1());
    	when(cardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt()))
    	.thenReturn(AutoLoadState.NO_TOP_UP.state());
    	doCallRealMethod().when(controller).processManageAutoTopUpNoTopUpRequired(any(ManageCardCmd.class), anyLong());
    	
    	doCallRealMethod().when(controller).manageAutoTopUpActivate(httpSession, cmd, mockResult);
    	ModelAndView modelAndView = controller.manageAutoTopUpActivate(httpSession, cmd, mockResult);
    	assertNotNull(modelAndView);
    	assertTrue(modelAndView.getViewName().equals(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP));
    }

    @Test
    public void manageAutoTopUpActivate() {
        ManageCardCmd cmd = new ManageCardCmd();
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setAutoTopUpState(AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String iniDate = formatter.format(new Date());
        cmd.setStartDateforAutoTopUpCardActivate(iniDate);

        doNothing().when(selectStationValidator).validate(anyObject(), any(BindingResult.class));
        when(locationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1());
        doCallRealMethod().when(controller).processManageAutoTopUp(any(HttpSession.class), any(ManageCardCmd.class));
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(cartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(cardService.getCardDTOById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt())).thenReturn(0);

        when(paymentService.createOrderAndSettlementsFromManagedAutoTopUp(any(CartDTO.class), any(CartCmdImpl.class)))
                .thenReturn(CartCmdTestUtil.getTestCartCmdWithOrder());
        when(paymentCardService.getPaymentCards(anyLong())).thenReturn(PaymentCardTestUtil.getManagePaymentCardCmdImpl1());

        doNothing().when(autoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        doCallRealMethod().when(controller).manageAutoTopUpActivate(httpSession, cmd, (BindingResult) errors);
        ModelAndView modelAndView = controller.manageAutoTopUpActivate(httpSession, cmd, (BindingResult) errors);
        assertNotNull(modelAndView);
        assertNotNull(cmd.getOrderNumber());
    }

    @Test
    public void fillValueFromManageCardCmdToCartCmdImpl() {
        ManageCardCmd cmd = new ManageCardCmd();
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setAutoTopUpState(AutoLoadState.TOP_UP_AMOUNT_2.state());
        cmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        cmd.setCardId(CardTestUtil.CARD_ID);
        cmd.setPaymentCardID(PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1);
        cmd.setStartDateforAutoTopUpCardActivate(DateTestUtil.DATE_20_07_2014);
        cmd.setEndDateforAutoTopUpCardActivate(DateTestUtil.DATE_31_07_2014);
        cmd.setStationName(LocationTestUtil.LOCATION_NAME_1);

        when(customerDataService.findByCardId(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        doCallRealMethod().when(controller).fillValueFromManageCardCmdToCartCmdImpl(any(ManageCardCmd.class));
        CartCmdImpl cartCmd = controller.fillValueFromManageCardCmdToCartCmdImpl(cmd);
        assertNotNull(cartCmd);
    }

    @Test
    public void createCartItemCmdImpl() {
        CartCmdImpl cmd = new CartCmdImpl();
        doCallRealMethod().when(controller).createCartItemCmdImpl(any(CartCmdImpl.class));
        CartItemCmdImpl itemCmd = controller.createCartItemCmdImpl(cmd);
        assertNotNull(itemCmd);
    }
    
    @Test
    public void shouldReturnCustomerEmailAddressForAutoTopUpConfirmationWithManageAutoTopUpActivate() {
    	CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        ManageCardCmd cmd = new ManageCardCmd();
        cmd.setStationId(LocationTestUtil.LOCATION_ID_1);
        cmd.setAutoTopUpState(AutoLoadState.TOP_UP_AMOUNT_2.topUpAmount());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String iniDate = formatter.format(new Date());
        cmd.setStartDateforAutoTopUpCardActivate(iniDate);
        doNothing().when(selectStationValidator).validate(anyObject(), any(BindingResult.class));
        when(locationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1());
        doCallRealMethod().when(controller).processManageAutoTopUp(any(HttpSession.class), any(ManageCardCmd.class));
        when(cartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(cardService.getCardDTOById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(cardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt())).thenReturn(0);
        when(paymentService.createOrderAndSettlementsFromManagedAutoTopUp(any(CartDTO.class), any(CartCmdImpl.class))).thenReturn(CartCmdTestUtil.getTestCartCmdWithOrder());
        when(paymentCardService.getPaymentCards(anyLong())).thenReturn(PaymentCardTestUtil.getManagePaymentCardCmdImpl1());
        when(controller.getCustomer()).thenReturn(getTestCustomerDTO5());
        doNothing().when(autoTopUpConfigurationService).changeConfiguration(anyLong(), anyLong(), anyInt(), anyLong());
        doCallRealMethod().when(controller).manageAutoTopUpActivate(httpSession, cmd, (BindingResult) errors);
        doCallRealMethod().when(controller).addCustomerEmailForAutoTopUpConfirmation(cmd);
        ModelAndView modelAndView = controller.manageAutoTopUpActivate(httpSession, cmd, (BindingResult) errors);
        verify(controller, atLeastOnce()).addCustomerEmailForAutoTopUpConfirmation(cmd);
        assertEquals(CustomerTestUtil.UNFORMATTED_EMAIL_ADDRESS_3,cmd.getEmailAddress());
        assertEquals(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP_CONFIRM, modelAndView.getViewName());
    }

    @Test
    public void shouldReformatDates() {
        ManageCardCmd cmd = mock(ManageCardCmd.class);
        when(cmd.getStartDateforAutoTopUpCardActivate()).thenReturn("16/12/2014");
        when(cmd.getEndDateforAutoTopUpCardActivate()).thenReturn("26/12/2014");
        doCallRealMethod().when(controller).updateDatesFormats(cmd);
        controller.updateDatesFormats(cmd);
        verify(cmd).setStartDateforAutoTopUpCardActivate("16 December 2014");
        verify(cmd).setEndDateforAutoTopUpCardActivate("26 December 2014");
    }
    
    @Test
    public void shouldNotUpdateDatesFormatsIfStartDateIsNull() {
        ManageCardCmd cmd = mock(ManageCardCmd.class);
        when(cmd.getStartDateforAutoTopUpCardActivate()).thenReturn(null);
        when(cmd.getEndDateforAutoTopUpCardActivate()).thenReturn("26/12/2014");
        doCallRealMethod().when(controller).updateDatesFormats(cmd);
        controller.updateDatesFormats(cmd);
        verify(cmd, never()).setStartDateforAutoTopUpCardActivate("16 December 2014");
        verify(cmd, never()).setEndDateforAutoTopUpCardActivate("26 December 2014");
    }
    
    @Test
    public void shouldNotUpdateDatesFormatsIfEndDateIsNull() {
        ManageCardCmd cmd = mock(ManageCardCmd.class);
        when(cmd.getStartDateforAutoTopUpCardActivate()).thenReturn("16/12/2014");
        when(cmd.getEndDateforAutoTopUpCardActivate()).thenReturn(null);
        doCallRealMethod().when(controller).updateDatesFormats(cmd);
        controller.updateDatesFormats(cmd);
        verify(cmd, never()).setStartDateforAutoTopUpCardActivate("16 December 2014");
        verify(cmd, never()).setEndDateforAutoTopUpCardActivate("26 December 2014");
    }
    
    @Test
    public void shouldNotReformatDates() {
    	ManageCardCmd cmd = mock(ManageCardCmd.class);
    	when(cmd.getStartDateforAutoTopUpCardActivate()).thenReturn("16/12/2014");
    	when(cmd.getEndDateforAutoTopUpCardActivate()).thenReturn("26/December/2014");
    	doCallRealMethod().when(controller).updateDatesFormats(cmd);
    	controller.updateDatesFormats(cmd);
    	verify(cmd, never()).setEndDateforAutoTopUpCardActivate("26 December 2014");
    }
    
    @Test
    public void shouldCancel() {
    	ManageCardCmd cmd = mock(ManageCardCmd.class);
    	doCallRealMethod().when(controller).cancel(cmd);
    	when(controller.cancelView(cmd)).thenReturn(new ModelAndView());
    	controller.cancel(cmd);
    	verify(controller).cancelView(cmd);
    }
    
    @Test
    public void shouldGoBackToManageAutoTopUp() {
    	ManageCardCmd cmd = mock(ManageCardCmd.class);
    	doCallRealMethod().when(controller).backToManageAutoTopUp(cmd);
    	ModelAndView modelAndView = controller.backToManageAutoTopUp(cmd);
    	assertTrue(modelAndView.getViewName().contains(PageView.MANAGE_AUTO_TOP_UP));
    }
    
    @Test
    public void shouldProcessManageAutoTopUpNoTopUpRequired() {
        ManageCardCmd cmd = mock(ManageCardCmd.class);
        when(cmd.getStationId()).thenReturn(3L);
        when(cardUpdateService.requestCardAutoLoadChange(5L, 3L, NO_TOP_UP.state())).thenReturn(3);
        doCallRealMethod().when(controller).processManageAutoTopUpNoTopUpRequired(cmd, 5L);
        controller.processManageAutoTopUpNoTopUpRequired(cmd, 5L);
        verify(cardUpdateService).requestCardAutoLoadChange(5L, 3L, NO_TOP_UP.state());
    }
    
    @Test
    public void shouldGetLoggedInUserCustomerId() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(controller).getLoggedInUserCustomerId();
        controller.getLoggedInUserCustomerId();
        verify(mockSecurityService).getLoggedInCustomer();
    }
    
}
