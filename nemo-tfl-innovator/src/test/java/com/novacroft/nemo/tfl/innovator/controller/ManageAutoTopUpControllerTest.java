package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.AutoTopUpOrderConstant;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseTestController;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.AutoTopUpValidator;
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
    private CardPreferencesCmdImpl cardReferencesCmd;
    private CardService mockCardService;
    private CardDataService mockCardDataService;
    private CustomerService mockCustomerService;
    private SelectStationValidator mockSelectStationValidator;
    private AutoTopUpValidator mockAutoTopUpValidator;
    private CardUpdateService mockCardUpdateService;
    private BeanPropertyBindingResult mockBindingResult;
    private HttpSession mockHttpSession;
    private CustomerService mockcustomerService;
    private SystemParameterService mocksystemParameterService;
    private CustomerDataService mockcustomerDataService;
    private CardPreferencesService mockcardPreferencesService;
    private PaymentCardSelectListService mockPaymentCardSelectListService;
    private MessageSource mockMessageSource;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        manageCardCmd = new ManageCardCmd();
        cardReferencesCmd = new CardPreferencesCmdImpl();
        mockHttpSession = mock(HttpSession.class);
        mockLocationSelectListService = mock(LocationSelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        mockManageCardCmd = mock(ManageCardCmd.class);
        mockCardService = mock(CardService.class);
        mockCustomerService = mock(CustomerService.class);
        mockcardPreferencesService = mock(CardPreferencesService.class);
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockAutoTopUpValidator = mock(AutoTopUpValidator.class);
        mockCardUpdateService = mock(CardUpdateService.class);
        mockBindingResult = mock(BeanPropertyBindingResult.class);
        mocksystemParameterService = mock(SystemParameterService.class);
        mockcustomerService = mock(CustomerService.class);
        mockcustomerDataService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockMessageSource = mock(MessageSource.class);
        mockPaymentCardSelectListService = mock(PaymentCardSelectListService.class);

        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(new SelectListDTO());
        when(mockcustomerDataService.findByCardId(anyLong())).thenReturn(new CustomerDTO());
        doNothing().when(mockHttpSession).setAttribute(anyString(), any());
        when(mockCustomerService.getPreferredStationId(anyLong())).thenReturn(1L);
        when(mockcardPreferencesService.getPreferredStationIdByCardId(cardReferencesCmd)).thenReturn(1L);
        doNothing().when(mockSelectStationValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockAutoTopUpValidator).validate(anyObject(), any(Errors.class));
        when(mockCardUpdateService.requestCardAutoLoadChange(anyLong(), anyLong(), anyInt())).thenReturn(1);
        when(mockHttpSession.getAttribute("OLD_CARD")).thenReturn(mockManageCardCmd);
        when(mockCardDataService.findById(anyLong())).thenReturn(new CardDTO());
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
        setField(controller, "cardPreferencesService", mockcardPreferencesService);
        setField(controller, "autoTopUpValidator", mockAutoTopUpValidator);
        controller.messageSource = mockMessageSource;
    }

    @Test
    public void testCancel() {
        ModelAndView result = controller.cancel(manageCardCmd);
        assertEquals(redirectViewCheck(result), PageUrl.INV_CARD_ADMIN);
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
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        when(mockCustomerDataService.findByCustomerId(anyLong())).thenReturn(getTestCustomerDTO1());
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(CardTestUtil.getTestCardDTO1());
        ModelAndView result = controller.postViewAutoTopUp(manageCardCmd, CUSTOMER_ID_1, OYSTER_NUMBER_1, mockHttpSession);
        assertViewName(result, PageView.MANAGE_AUTO_TOP_UP);
    }

    @Test
    public void testpostViewAutoTopUpShouldRetrunCompleteView() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("234");
        Mockito.doReturn(cardDTO).when(mockCardDataService).findById(anyLong());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(14901L);
        mockHttpSession.setAttribute(CartAttribute.CARD_ID, 14901L);
        when(mockCustomerDataService.findByCustomerId(anyLong())).thenReturn(getTestCustomerDTO1());
        when(mockPaymentCardSelectListService.getPaymentCardSelectList(anyLong())).thenReturn(null);
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockManageCardCmd.getCardNumber()).thenReturn(null);
        ModelAndView result = controller.postViewAutoTopUp(mockManageCardCmd, CUSTOMER_ID_1, OYSTER_NUMBER_1, mockHttpSession);
        assertNotNull(result.getModelMap().get(CartAttribute.CUSTOMER_ID));
    }

    @Test
    public void testManageAutoTopUpResultHasErrors() {
        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), anyString(), any(Locale.class))).thenReturn(ContentCode.NO_CHANGE_IN_AMOUNT_SET_FOR_AUTOTOPUP.textCode());
        ModelAndView result = controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        assertViewName(result, PageView.MANAGE_AUTO_TOP_UP);

    }
    
    @Test
    public void testManageAutoTopUpCommandsEqual() {
        mockManageCardCmd.setStartDateforAutoTopUpCardActivate(anyString());
        mockManageCardCmd.setEndDateforAutoTopUpCardActivate(anyString());
        mockHttpSession.setAttribute(AutoTopUpOrderConstant.COMMAND_OBJECT, mockManageCardCmd);
        ModelAndView result = controller.manageAutoTopUp(mockManageCardCmd, mockBindingResult, mockHttpSession);
        assertEquals(redirectViewCheck(result), PageUrl.INV_CARD_ADMIN);
    }

    @Test
    public void testManageAutoTopUpCommandsNotEqualDisableAutoTopUp() {
        manageCardCmd.setStationId(20L);
        manageCardCmd.setAutoTopUpState(0);
        ModelAndView result = controller.manageAutoTopUp(manageCardCmd, mockBindingResult, mockHttpSession);
        assertEquals(redirectViewCheck(result), PageUrl.INV_CARD_ADMIN);
    }

    @Test
    public void testManageAutoTopUpCommandsNotEqualEnableAutoTopUp() {
        manageCardCmd.setStationId(20L);
        manageCardCmd.setAutoTopUpState(2000);

        mockHttpSession.setAttribute(AutoTopUpOrderConstant.COMMAND_OBJECT, mockManageCardCmd);
        ModelAndView result = controller.manageAutoTopUp(manageCardCmd, mockBindingResult, mockHttpSession);
        assertEquals(redirectViewCheck(result), PageUrl.INV_CARD_ADMIN);
    }

    @Test
    public void testGetCustomer() {
        CustomerDTO customer = controller.getCustomer();
        assertEquals(customer, null);
    }

}
