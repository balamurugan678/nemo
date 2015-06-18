package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.AUTO_TOPUP_AMT;
import static com.novacroft.nemo.test_support.CardTestUtil.AUTO_TOPUP_CREDIT_BALANCE;
import static com.novacroft.nemo.test_support.CardTestUtil.NULL_AUTO_TOPUP_AMOUNT;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getCartItemCmdWithFaileAutoTopUpCaseAdded;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getCartItemCmdWithOutFaileAutoTopUpCaseAdded;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getCartItemCmdWithOutFaileAutoTopUpCaseAddedWithStatusMessage;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil.getListOfNonPayAsYouGoItemDTOsWithNonZeroPrice;
import static com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil.getListOfNonPayAsYouGoItemDTOsWithZeroPrice;
import static com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil.getListOfPayAsYouGoItemDTOs;
import static com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil.getListOfPayAsYouGoItemDTOsWithZeroPrice;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.FailedAutoTopUpCaseService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.AutoTopUpPayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.EmailNotificationValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.TicketTypeValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.UserCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for TopUpTicket Controller
 */
public class TopUpTicketControllerTest {
    private TopUpTicketController controller;
    private TopUpTicketController mockController;
    private CartItemCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private BindingResult mockBindingResult;
    private RedirectAttributes mockRedirectAttributes;
    private AutoTopUpPayAsYouGoValidator mockAutoTopUpPayAsYouGoValidator;
    private CartService mockNewCartService;
    private PayAsYouGoValidator mockPayAsYouGoValidator;
    private SelectCardValidator mockSelectCardValidator;
    private TicketTypeValidator mockTicketTypeValidator;
    private TravelCardValidator mockTravelCardValidator;
    private UserCartValidator mockUserCartValidator;
    private ZoneMappingValidator mockZoneMappingValidator;
    private HttpSession mockSession;
    private CardDataService mockCardDataService;
    private CardService mockCardService;
    private BindingAwareModelMap model;
    private TravelCardService mockTravelCardService;
    private PayAsYouGoService mockPayAsYouGoService;
    private EmailNotificationValidator mockEmailNotificationValidator;
    private SystemParameterService mockSystemParameterService;
    private GetCardService mockGetCardService;
    private FailedAutoTopUpCaseService mockFailedAutoTopUpCaseService;
    protected SelectListService mockSelectListService;
    protected CartAdministrationService mockCartAdminService;
    private CartDTO mockCartDTO;
    private Model mockModel; 
    
    @Before
    public void setUp() {
        controller = new TopUpTicketController();

        mockCmd = mock(CartItemCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockBindingResult = mock(BindingResult.class);
        mockRedirectAttributes = mock(RedirectAttributes.class);
        mockAutoTopUpPayAsYouGoValidator = mock(AutoTopUpPayAsYouGoValidator.class);
        mockPayAsYouGoValidator = mock(PayAsYouGoValidator.class);
        mockSelectCardValidator = mock(SelectCardValidator.class);
        mockTicketTypeValidator = mock(TicketTypeValidator.class);
        mockTravelCardValidator = mock(TravelCardValidator.class);
        mockNewCartService = mock(CartService.class);
        mockUserCartValidator = mock(UserCartValidator.class);
        mockZoneMappingValidator = mock(ZoneMappingValidator.class);
        mockSession = mock(HttpSession.class);
        mockCardDataService = mock(CardDataService.class);
        model = new BindingAwareModelMap();
        mockTravelCardService = mock(TravelCardService.class);
        mockPayAsYouGoService = mock(PayAsYouGoService.class);
        mockCardService = mock(CardService.class);
        mockEmailNotificationValidator = mock(EmailNotificationValidator.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockGetCardService = mock(GetCardService.class);
        mockFailedAutoTopUpCaseService = mock(FailedAutoTopUpCaseService.class);
        mockSelectListService = mock(SelectListService.class);
        mockCartAdminService = mock(CartAdministrationService.class);
        
        mockCartDTO = mock(CartDTO.class);
        mockModel = mock(Model.class);
        
        controller.autoTopUpPayAsYouGoValidator = mockAutoTopUpPayAsYouGoValidator;
        controller.payAsYouGoValidator = mockPayAsYouGoValidator;
        controller.selectCardValidator = mockSelectCardValidator;
        controller.ticketTypeValidator = mockTicketTypeValidator;
        controller.travelCardValidator = mockTravelCardValidator;
        controller.cartService = mockNewCartService;
        controller.userCartValidator = mockUserCartValidator;
        controller.zoneMappingValidator = mockZoneMappingValidator;
        controller.cardDataService = mockCardDataService;
        controller.travelCardService = mockTravelCardService;
        controller.payAsYouGoService = mockPayAsYouGoService;
        controller.cardService = mockCardService;
        controller.emailNotificationValidator = mockEmailNotificationValidator;
        controller.getCardService = mockGetCardService;
        controller.failedAutoTopUpCaseService = mockFailedAutoTopUpCaseService;
        controller.selectListService = mockSelectListService;
        controller.cartAdminService = mockCartAdminService;
        
        setField(mockAutoTopUpPayAsYouGoValidator, "systemParameterService", mockSystemParameterService);

        doNothing().when(mockPayAsYouGoValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockAutoTopUpPayAsYouGoValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockTicketTypeValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockTravelCardValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockSelectCardValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockUserCartValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockZoneMappingValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        
        mockController = mock(TopUpTicketController.class);
        mockController.travelCardValidator = mockTravelCardValidator;
        mockController.cardService = mockCardService;
        mockController.zoneMappingValidator = mockZoneMappingValidator;
        mockController.emailNotificationValidator = mockEmailNotificationValidator;
        mockController.userCartValidator = mockUserCartValidator;
        mockController.travelCardService = mockTravelCardService;
        
    }

    @Test
    public void showTopUpTicketShouldShowTopupOptions() {
        when(controller.getFromSession(mockSession, CartAttribute.CARD_ID)).thenReturn(CardTestUtil.CARD_ID_1);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardService.getAutoTopUpVisibleOptionForCard(anyLong())).thenReturn(true);
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockNewCartService.createCartFromCardId(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockNewCartService.removeExpiredCartItems(any(CartDTO.class))).thenReturn(getNewCartDTOWithItem());
         
        ModelAndView result = controller.showTopUpTicket(mockSession, new CartItemCmdImpl(), model);
        assertEquals(PageView.TOP_UP_TICKET, result.getViewName());
        assertNotNull(((CartItemCmdImpl) result.getModel().get(PageCommand.CART_ITEM)).getCardId());
    }

    @Test
    public void topUpTicketShouldShowUserCartOnPayAsYouGoTicketTypeValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO.code());
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockPayAsYouGoService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(false);

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockNewCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageUrl.USER_CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void topUpTicketShouldShowTopUpTicketOnPayAsYouGoTicketTypeInValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO.code());

        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockNewCartService, never()).findById(anyLong());
        assertEquals(PageView.TOP_UP_TICKET, result.getViewName());
    }

    @Test
    public void topUpTicketShouldShowUserCartOnAutoTopUpPayAsYouGoTicketTypeValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockPayAsYouGoService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockNewCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void topUpTicketShouldShowTopUpTicketOnAutoTopUpPayAsYouGoTicketTypeInValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockNewCartService, never()).findById(anyLong());
        assertEquals(PageView.TOP_UP_TICKET, result.getViewName());
    }

    @Test
    public void topUpTicketShouldShowUserCartOnTravelCardTicketTypeValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.TRAVEL_CARD.code());
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(false);

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockNewCartService, atLeastOnce()).findById(anyLong());
        assertEquals(PageUrl.USER_CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void topUpTicketShouldShowTopUpTicketOnTravelCardTicketTypeInValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.TRAVEL_CARD.code());
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockNewCartService, never()).findById(anyLong());
        assertEquals(PageView.TOP_UP_TICKET, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToViewOysterCardManagementPage() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shoppingBasketShouldRedirectToCart() {
        ModelAndView result = controller.shoppingBasket(new CartItemCmdImpl());
        assertEquals(PageUrl.USER_CART, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void topUpTicketShouldShowUserCartOnAutoTopUpPayAsYouGoTicketTypeWithoutAutoTopUpAmount() {
    	when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
    	when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
     	when(mockResult.hasErrors()).thenReturn(false);
     	mockCmd.setAutoTopUpAmt(NULL_AUTO_TOPUP_AMOUNT);
     	ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
     	assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
     }
    
    @Test
    public void topUpTicketShouldShowTopUpTicketIncludingAutoTopOnAutoTopUpPayAsYouGoTicketTypeInValidInput() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockCardService, atLeastOnce()).getAutoTopUpVisibleOptionForCard(anyLong());
        assertEquals(PageView.TOP_UP_TICKET, result.getViewName());
    }
    
    @Test
    public void topUpTicketOnTravelCardTicketTypeShouldInvokeValidators() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.TRAVEL_CARD.code());
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(false);

        controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        verify(mockEmailNotificationValidator).validate(any(Object.class), any(Errors.class)) ;
    }
    
    @Test
    public void topUpTicketShouldShowNextPageWhenAutoTopUpPayAsYouGoLessThanExistingPayAsYouGoCreditLimit() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        doCallRealMethod().when(mockAutoTopUpPayAsYouGoValidator).validate(any(Object.class), any(Errors.class));
        mockCmd.setAutoTopUpAmt(AUTO_TOPUP_AMT);
        mockCmd.setAutoTopUpCreditBalance(AUTO_TOPUP_CREDIT_BALANCE);
        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
     }
    
    @Test
    public void getCardProductDTOFromCubicShouldInvokeService(){
        controller.getCardProductDTOFromCubic(OYSTER_NUMBER_1);
        verify(mockGetCardService).getCard(anyString());
    }
    
    
    @Test
    public void addPayAsYouGoShouldReturnNullWithErrors(){
        when(mockResult.hasErrors()).thenReturn(true);
        assertNull(controller.addPayAsYouGo(getNewCartDTOWithItem(), mockCmd, mockResult, mockRedirectAttributes));
    }
    
    @Test
    public void addAutoTopUpPayAsYouGoShouldReturnNullWithErrors(){
        when(mockResult.hasErrors()).thenReturn(true);
        assertNull(controller.addAutoTopUpPayAsYouGo(getNewCartDTOWithItem(), mockCmd, mockResult, mockRedirectAttributes));
    }
    
    @Test
    public void topUpTicketShouldShowTopUpTicketOnTravelCardForBusPassTest() {
        when(mockCmd.getTicketType()).thenReturn(TicketType.BUS_PASS.code());
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockTravelCardService.addCartItemForExistingCard(any(CartDTO.class), any(CartItemCmdImpl.class))).thenReturn(getNewCartDTOWithItem());
        when(mockResult.hasErrors()).thenReturn(false);

        ModelAndView result = controller.topUpTicket(mockSession, mockCmd, mockResult, mockRedirectAttributes, model);
        assertEquals(PageView.TOP_UP_TICKET, (result.getViewName()));
    }
    
    @Test
    public void topUpTicketShouldShowTopUpTicketPageIfOysterCardHasFailedAutoTopUpCase() {
    	CartItemCmdImpl cmdImplWithFATU = getCartItemCmdWithFaileAutoTopUpCaseAdded();
        when(mockFailedAutoTopUpCaseService.isOysterCardWithFailedAutoTopUpCase(anyString())).thenReturn(true);
        when(mockResult.hasErrors()).thenReturn(true);
        doCallRealMethod().when(mockAutoTopUpPayAsYouGoValidator).validate(any(Object.class), any(Errors.class));
        ModelAndView result = controller.topUpTicket(mockSession, cmdImplWithFATU, mockResult, mockRedirectAttributes, model);
        assertEquals(PageView.TOP_UP_TICKET, (result.getViewName()));
    }
    
    @Test
    public void topUpTicketShouldShowRedirectViewPageIfOysterCardHasNotFailedAutoTopUpCase() {
    	CartItemCmdImpl cmdImplWithOutFATU = getCartItemCmdWithOutFaileAutoTopUpCaseAdded();
        when(mockNewCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        doCallRealMethod().when(mockAutoTopUpPayAsYouGoValidator).validate(any(Object.class), any(Errors.class));
        mockCmd.setAutoTopUpAmt(AUTO_TOPUP_AMT);
        mockCmd.setAutoTopUpCreditBalance(AUTO_TOPUP_CREDIT_BALANCE);
        ModelAndView result = controller.topUpTicket(mockSession, cmdImplWithOutFATU, mockResult, mockRedirectAttributes, model);
        assertEquals(PageUrl.USER_CART, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void resettleShouldRedirectToResettleViewPage() {
        ModelAndView result = controller.resettleFailedAutoTopUp(new CartItemCmdImpl());
        assertEquals(PageUrl.RESETTLE_FAILED_AUTO_TOP_UP, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldPopulateModelAttributes() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        Model model = new ExtendedModelMap();
        controller.populatePayAsYouGoCreditBalancesSelectList(model);
        assertTrue(model.asMap().containsKey(PageAttribute.PAY_AS_YOU_GO_CREDIT_BALANCES));
    }

    @Test
    public void shouldPopulateTravelCardTypesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        Model model = new ExtendedModelMap();
        controller.populateTravelCardTypesSelectList(model);
        assertTrue(model.asMap().containsKey(PageAttribute.TRAVEL_CARD_TYPES));
    }
    
    @Test
    public void shouldPopulateTravelCardZonesSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        Model model = new ExtendedModelMap();
        controller.populateTravelCardZonesSelectList(model);
        assertTrue(model.asMap().containsKey(PageAttribute.TRAVEL_CARD_ZONES));
    }
    
    @Test
    public void shouldPopulateStartDatesSelectList() {
        when(mockCartAdminService.getUserProductStartDateList()).thenReturn(getTestSelectListDTO());
        Model model = new ExtendedModelMap();
        controller.populateStartDatesSelectList(model);
        assertTrue(model.asMap().containsKey(PageAttribute.START_DATES));
    }
    
    @Test
    public void shouldPopulateEmailRemindersSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        Model model = new ExtendedModelMap();
        controller.populateEmailRemindersSelectList(model);
        assertTrue(model.asMap().containsKey(PageAttribute.BASKET_EMAIL_REMINDERS));
    }
    
    @Test
    public void shouldPopulatePayAsYouGoAutoTopUpAmtsSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTO());
        Model model = new ExtendedModelMap();
        controller.populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        assertTrue(model.asMap().containsKey(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }
    
    @Test
    public void shouldNotAddTravelCardIfResultHasErrors() {
        when(mockBindingResult.hasErrors()).thenReturn(true);
        doCallRealMethod().when(mockController).addTravelCard(mockCartDTO, mockCmd, mockBindingResult, mockRedirectAttributes, mockModel);
        mockController.addTravelCard(mockCartDTO, mockCmd, mockBindingResult, mockRedirectAttributes, mockModel);
    }

    @Test
    public void shouldSetFlashStatusMessageForMethodAddTravelCard() {
        when(mockBindingResult.hasErrors()).thenReturn(false);
        doNothing().when(mockController).setFlashStatusMessage(any(RedirectAttributes.class), any(String.class));
        doCallRealMethod().when(mockController).addTravelCard(mockCartDTO, getCartItemCmdWithOutFaileAutoTopUpCaseAdded(), mockBindingResult, mockRedirectAttributes, mockModel);
        mockController.addTravelCard(mockCartDTO, getCartItemCmdWithOutFaileAutoTopUpCaseAddedWithStatusMessage(), mockBindingResult, mockRedirectAttributes, mockModel);
        verify(mockController, atLeastOnce()).setFlashStatusMessage(any(RedirectAttributes.class), any(String.class));
    }
    
    @Test
    public void shouldSetExistingCartDTOCreditBalance() {
        when(mockCartDTO.getCartItems()).thenReturn(getListOfPayAsYouGoItemDTOs());
        doNothing().when(mockCmd).setExistingCreditBalance(any(Integer.class));
        doCallRealMethod().when(mockController).setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        mockController.setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        verify(mockCmd, atLeastOnce()).setExistingCreditBalance(any(Integer.class));
    }

    @Test
    public void shouldNotSetExistingCartDTOCreditBalanceWithCurrentCmdWhenNotPayAsYouGoItemDTO() {
        when(mockCartDTO.getCartItems()).thenReturn(getListOfNonPayAsYouGoItemDTOsWithNonZeroPrice());
        doCallRealMethod().when(mockController).setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        mockController.setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        verify(mockCmd, never()).setExistingCreditBalance(any(Integer.class));        
    }

    @Test
    public void shouldNotSetExistingCartDTOCreditBalanceWithCurrentCmdWhenNotPayAsYouGoItemDTOAndExistingPayAsYouGoPriceZero() {
        when(mockCartDTO.getCartItems()).thenReturn(getListOfNonPayAsYouGoItemDTOsWithZeroPrice());
        doCallRealMethod().when(mockController).setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        mockController.setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        verify(mockCmd, never()).setExistingCreditBalance(any(Integer.class));
    }

    @Test
    public void shouldNotSetExistingCartDTOCreditBalanceWithCurrentCmdWhenPayAsYouGoItemDTOAndExistingPayAsYouGoPriceZero() {
        when(mockCartDTO.getCartItems()).thenReturn(getListOfPayAsYouGoItemDTOsWithZeroPrice());
        doCallRealMethod().when(mockController).setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        mockController.setExistingCartDTOCreditBalanceWithCurrentCmd(mockCartDTO, mockCmd);
        verify(mockCmd, never()).setExistingCreditBalance(any(Integer.class));
    }

}
