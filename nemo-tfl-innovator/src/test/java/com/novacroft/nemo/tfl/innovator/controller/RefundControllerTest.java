package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO5;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesCmd1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.CART_ITEM_ID;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd16;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CANCEL_AND_SURRENDER_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_FAILED_CARD_REFUND;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.MIN_START_DATE_FOR_CALENDAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil;
import com.novacroft.nemo.test_support.RefundTestUtil;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.form_validator.AddUnlistedProductValidator;
import com.novacroft.nemo.tfl.common.form_validator.AdministrationFeeValidator;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;
import com.novacroft.nemo.tfl.common.form_validator.TradedTicketValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class RefundControllerTest {
    private RefundController controller;
    private RefundController initController;
    private RefundCartValidator mockFailedCardRefundCartValidator;
    private BindingResult mockBindingResult;
    private RefundPaymentValidator mockRefundPaymentValidator;
    private WorkFlowService mockWorkflowService;
    private RefundService mockRefundService;
    private CartService mockCartService;
    private HotlistCardService mockHotlistCardService;
    private RefundPaymentService mockRefundPaymentService;
    private CartCmdImpl mockCartCmdImpl;
    private CardService mockCardService;
    private HttpSession mockHttpSession;
    private CartAdministrationService mockNewCartAdministrationService;
    private RefundCartPaymentValidator mockRefundCartPaymentValidator;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    private CardDataService mockCardDataService;
    private AddUnlistedProductValidator mockAddUnlistedProductValidator;
    private AddUnlistedProductService mockAddUnlistedProductService;
    private LocationDataService mockLocationDataService;
    private GoodwillValidator mockGoodwillValidator;
    private Model mockModel;
    private CardPreferencesService mockCardPreferencesService;
    private TradedTicketValidator mockTradedTicketValidator;
    private GetCardService mockGetCardService;
    private AddUnattachedCardService mockAddUnattachedCardService;
    private RefundSelectListService mockRefundSelectService;
    private CountrySelectListService mockCountrySelectService;
    private CountryDataService mockCountryDataService;
    private ServletRequestDataBinder mockServletRequestBinder;
    private AdministrationFeeValidator mockAdministrationFeeValidator;
    private SelectStationValidator mockSelectStationValidator;
    private SystemParameterService mockSystemParameterService;

    private final int PICKUP_LOCATION_ID = 1;
    private final int INT_LOCATION_ID_99 = 99;
    private final int WEB_CREDIT_TEST = 655;
    private final int CART_REFUND_TOTAL = -1220;
    public static final Long UPDATE_REFUND_CALCULATION_BASIS_LINE_NUMBER = 5L;
    public static final String FAILED_VALIDATION = "failedValidation";
    public static final Long  TEST_APPROVAL_ID = 1l;

    @Before
    public void setUp() throws Exception {
        controller = mock(RefundController.class);
        mockFailedCardRefundCartValidator = mock(RefundCartValidator.class);
        mockBindingResult = mock(BindingResult.class);
        mockRefundPaymentValidator = mock(RefundPaymentValidator.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockRefundService = mock(RefundService.class);
        mockCartService = mock(CartService.class);
        mockHotlistCardService = mock(HotlistCardService.class);
        mockRefundPaymentService = mock(RefundPaymentService.class);
        mockCartCmdImpl = mock(CartCmdImpl.class);
        when(mockCartCmdImpl.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        mockCardService = mock(CardService.class);
        mockHttpSession = mock(HttpSession.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);
        mockRefundCartPaymentValidator = mock(RefundCartPaymentValidator.class);
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockAddUnlistedProductValidator = mock(AddUnlistedProductValidator.class);
        mockAddUnlistedProductService = mock(AddUnlistedProductService.class);
        mockLocationDataService = mock(LocationDataService.class);
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockModel = mock(Model.class);
        mockCardPreferencesService = mock(CardPreferencesService.class);
        mockTradedTicketValidator = mock(TradedTicketValidator.class);
        mockGetCardService = mock(GetCardService.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        mockRefundSelectService = mock(RefundSelectListService.class);
        mockCountrySelectService = mock(CountrySelectListService.class);
        mockCountryDataService = mock(CountryDataService.class);
        mockServletRequestBinder = mock(ServletRequestDataBinder.class); 
        mockAdministrationFeeValidator = mock(AdministrationFeeValidator.class); 
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        
        doNothing().when(mockFailedCardRefundCartValidator).validate(anyObject(), any(Errors.class));
        when(controller.getValidator()).thenReturn(mockFailedCardRefundCartValidator);

        doNothing().when(mockRefundCartPaymentValidator).validate(anyObject(), any(Errors.class));

        controller.refundPaymentValidator = mockRefundPaymentValidator;
        controller.workflowService = mockWorkflowService;
        controller.refundService = mockRefundService;
        controller.cartService = mockCartService;
        controller.hotlistCardService = mockHotlistCardService;
        controller.refundPaymentService = mockRefundPaymentService;
        controller.cardService = mockCardService;
        controller.cartAdministrationService = mockNewCartAdministrationService;
        controller.refundCartPaymentValidator = mockRefundCartPaymentValidator;
        controller.addUnlistedProductValidator = mockAddUnlistedProductValidator;
        controller.addUnlistedProductService = mockAddUnlistedProductService;
        controller.locationDataService = mockLocationDataService;
        controller.goodwillValidator = mockGoodwillValidator;
        controller.cardPreferencesService = mockCardPreferencesService;
        controller.tradedTicketValidator = mockTradedTicketValidator;
        controller.cardDataService = mockCardDataService;
        controller.getCardService = mockGetCardService;
        controller.addUnattachedCardService = mockAddUnattachedCardService;
        controller.refundSelectListService = mockRefundSelectService;
        controller.countrySelectListService = mockCountrySelectService;
        controller.countryDataService = mockCountryDataService;
        controller.selectStationValidator = mockSelectStationValidator;
        controller.systemParameterService = mockSystemParameterService;
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO5());
        when(controller.returnToWorkflow(anyLong())).thenCallRealMethod();
        when(controller.cancelRefund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        doCallRealMethod().when(controller).clearGoodwillAmountAndRefundReasonFromCommand(any(CartCmdImpl.class));
        doCallRealMethod().when(controller).clearTravelcardDetailsFromCommand(any(CartItemCmdImpl.class));
        doCallRealMethod().when(mockSelectStationValidator).validate(CartTestUtil.getTestCartDTOWithPpvPickupLocation(),mockBindingResult);
        
        when(mockCartService.postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class))).then(returnsFirstArg());
        when(mockCartService.postProcessAndSortCartDTOWithoutRefundRecalculation(any(CartDTO.class))).then(returnsFirstArg());
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doNothing().when(mockHttpSession).setAttribute(anyString(), any());
    }

    @Test
    public void viewCartShouldReturnFailedCardRefundCartView() {
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.viewCart(mockHttpSession, OYSTER_NUMBER_1, TEST_APPROVAL_ID)).thenCallRealMethod();
       
        ModelAndView result = controller.viewCart(mockHttpSession, OYSTER_NUMBER_1, TEST_APPROVAL_ID);

        assertEquals(INV_FAILED_CARD_REFUND, result.getViewName());
      
    }

    @Test
    public void viewCartUsingCubicShouldReturnFailedCardRefundCartView() {
        when(mockRefundService.getCartDTOForRefund(OYSTER_NUMBER_1, RefundType.FAILED_CARD.code(), true)).thenReturn(getNewCartDTOWithItem());
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.viewCartUsingCubic(mockHttpSession, OYSTER_NUMBER_1)).thenCallRealMethod();

        ModelAndView result = controller.viewCartUsingCubic(mockHttpSession, OYSTER_NUMBER_1);

        assertEquals(INV_FAILED_CARD_REFUND, result.getViewName());
        verify(mockRefundService, atLeastOnce()).getCartDTOForRefund(anyString(), anyString(), (Boolean) anyObject());
    }

    @Test
    public void continueCartShouldReturnNullView() {
        when(controller.refund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.complete(any(ModelAndView.class), any(CartCmdImpl.class))).thenCallRealMethod();
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());

        ModelAndView result = controller.refund(mockHttpSession, CartCmdTestUtil.getTestCartCmd14(), mockBindingResult);

        assertNull(result.getViewName());
    }

    @Test
    public void continueCartShouldReturnPickUpStationName() {
        when(controller.refund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.complete(any(ModelAndView.class), any(CartCmdImpl.class))).thenCallRealMethod();
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());

        ModelAndView result = controller.refund(mockHttpSession, CartCmdTestUtil.getTestCartCmd14(), mockBindingResult);

        assertNull(result.getViewName());
    }

    @Test
    public void updateRefundCalculationBasisShouldReturnCancelAndSurrenderView() {
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockRefundService.updateProductItemDTO(anyLong(), anyLong(), anyString())).thenReturn(getTestCartDTO1());
        when(mockRefundPaymentService.createCardsSelectListForAdHocLoad(anyString())).thenReturn(SelectListTestUtil.getTestSelectListDTO());

        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenCallRealMethod();
        when(controller.getCartSessionDataDTOFromSession(mockHttpSession)).thenCallRealMethod();
        when(controller.updateRefundCalculationBasis((HttpSession) anyObject(), (CartCmdImpl) anyObject(), anyLong(), anyString()))
                        .thenCallRealMethod();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);

        ModelAndView result = controller.updateRefundCalculationBasis(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(),
                        UPDATE_REFUND_CALCULATION_BASIS_LINE_NUMBER, RefundCalculationBasis.PRO_RATA.code());

        assertEquals(PageView.INV_FAILED_CARD_REFUND, result.getViewName());
    }

    @Test
    public void addTravelCardShouldReturnFailedCardRefundCartView() {
        when(controller.addTravelCard(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        doNothing().when(mockAddUnlistedProductValidator).validate(CartCmdTestUtil.getTestCartCmd15(), mockBindingResult);
        doNothing().when(mockAddUnlistedProductService).setTravelcardTypeByFormTravelCardType(any(CartItemCmdImpl.class));
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(any(HttpSession.class))).thenCallRealMethod();
        when(controller.getCartSessionDataDTOFromSession(any(HttpSession.class))).thenCallRealMethod();
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(getTestCartDTO1());

        ModelAndView result = controller.addTravelCard(mockHttpSession, CartCmdTestUtil.getTestCartCmd15(), mockBindingResult);

        assertEquals(INV_FAILED_CARD_REFUND, result.getViewName());
    }

    @Test
    public void addTravelCardWithErrorsShouldReturnFailedCardRefundCartView() {
        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(controller.addTravelCard(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        doNothing().when(mockAddUnlistedProductValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        doNothing().when(mockAddUnlistedProductService).setTravelcardTypeByFormTravelCardType(any(CartItemCmdImpl.class));
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(any(HttpSession.class))).thenCallRealMethod();
        when(controller.getCartSessionDataDTOFromSession(any(HttpSession.class))).thenCallRealMethod();
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(getTestCartDTO1());

        ModelAndView result = controller.addTravelCard(mockHttpSession, CartCmdTestUtil.getTestCartCmd15(), mockBindingResult);

        assertEquals(INV_FAILED_CARD_REFUND, result.getViewName());
    }

    @Test
    public void deleteTravelCardShouldReturnFailedCardRefundCartView() {
        when(controller.deleteTravelCard((HttpSession) anyObject(), any(CartCmdImpl.class), any(int.class))).thenCallRealMethod();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        CartDTO testCartDto = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.deleteItem((CartDTO) anyObject(), anyLong())).thenReturn(testCartDto);

        ModelAndView result = controller.deleteTravelCard(mockHttpSession, CartCmdTestUtil.getTestCartCmd11(), CART_ITEM_ID);
        assertViewName(result, INV_FAILED_CARD_REFUND);
        verify(mockCartService).deleteItem(any(CartDTO.class), anyLong());
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(testCartDto);
    }

    @Test
    public void setOysterCardsListTest() {
        ModelAndView testView = new ModelAndView();
        Mockito.doCallRealMethod().when(controller).setOysterCardsList(mockCartCmdImpl, testView, null);
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);

        SelectListDTO selectList = mock(SelectListDTO.class);
        Mockito.doReturn(selectList).when(mockRefundPaymentService).createCardsSelectListForAdHocLoad(Mockito.any(String.class));
        controller.setOysterCardsList(mockCartCmdImpl, testView, null);

        Object actual = testView.getModel().get(PageCommandAttribute.FIELD_SELECT_CARD);
        assertEquals(selectList, actual);
    }

    @Test
    public void continueCartWithFixedCartItemsShouldReturnMatchingRefund() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        cartDTO.setCartItems(items);
        cartCmdImpl.setCartDTO(cartDTO);

        doCallRealMethod().when(controller).refund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        doCallRealMethod().when(controller).complete(any(ModelAndView.class), any(CartCmdImpl.class));
        doCallRealMethod().when(controller).getCartDTOUsingCartSessionDataDTOInSession((HttpSession) anyObject());
        doCallRealMethod().when(controller).getCartSessionDataDTOFromSession((HttpSession) anyObject());

        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());

        ModelAndView modelAndView = controller.refund(mockHttpSession, CartCmdTestUtil.getTestCartCmd14(), mockBindingResult);

        CartDTO cartDTOFromSession = (CartDTO) modelAndView.getModel().get(PageAttribute.CART_DTO);
        int actual = cartDTOFromSession.getCartRefundTotal();
        assertTrue(actual >= CART_REFUND_TOTAL);
    }

    @Test
    public void updatePreviousWebCreditShouldUpdateWebCredit() {
        controller.webCreditSettlementDataService = mockWebCreditSettlementDataService;
        Mockito.doCallRealMethod().when(controller).updatePreviousWebCredit(any(CartCmdImpl.class));
        CardDTO card = CardTestUtil.getTestCardDTO1();
        when(mockCardDataService.findByCardNumber(any(String.class))).thenReturn(card);
        when(mockWebCreditSettlementDataService.getBalance(card.getCustomerId())).thenReturn(WEB_CREDIT_TEST);
        CartCmdImpl cartCmdImpl = new CartCmdImpl();

        assertNull(cartCmdImpl.getWebCreditAvailableAmount());
        controller.updatePreviousWebCredit(cartCmdImpl);
        assertTrue(cartCmdImpl.getWebCreditAvailableAmount() == WEB_CREDIT_TEST);
    }

    @Test
    public void shouldUpdateCartTotal() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd14();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(cartDTO);
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        when(mockRefundService.updateAdministrationFeeItemDTO((any(CartDTO.class)), any(Integer.class))).thenReturn(cartDTO);

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(cartDTO);
        when(mockRefundService.getUpdatedCart(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));

        doCallRealMethod().when(controller).updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        ModelAndView modelAndView = controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);
        CartDTO cartDTOFromSession = (CartDTO) modelAndView.getModel().get(PageAttribute.CART_DTO);
        assertEquals(cartCmdImpl.getDateOfRefund(), cartDTOFromSession.getDateOfRefund());
    }
    
    @Test
    public void viewCartShouldInvokeUpdatePaymentDetailsInCartCmdImplTest() {
    	when(mockCartService.findByApprovalId(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.viewCart(mockHttpSession, OYSTER_NUMBER_1, TEST_APPROVAL_ID)).thenCallRealMethod();

        controller.viewCart(mockHttpSession, OYSTER_NUMBER_1, TEST_APPROVAL_ID);

        verify(mockRefundService, atLeastOnce()).updatePaymentDetailsInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
    }

    @Test
    public void shouldAddGoodWillItem() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd14();
        cartCmd.setCartItemCmd(new CartItemCmdImpl());
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenCallRealMethod();
        when(controller.getCartSessionDataDTOFromSession(mockHttpSession)).thenCallRealMethod();
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockGoodwillValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(controller).setOysterCardsList(any(CartCmdImpl.class), any(ModelAndView.class), any(CartDTO.class));
        when(controller.addGoodwill(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), any(Model.class)))
                        .thenCallRealMethod();
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenReturn(cartDTO);
        controller.addGoodwill(mockHttpSession, cartCmd, mockBindingResult, mockModel);
        verify(mockCartService, times(1)).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class));
    }

    @Test
    public void shouldNotAddGoodWillItemDueToValidationFailure() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd14();
        cartCmd.setCartItemCmd(new CartItemCmdImpl());
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenCallRealMethod();
        when(controller.getCartSessionDataDTOFromSession(mockHttpSession)).thenCallRealMethod();
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockGoodwillValidator).validate(anyObject(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(true);
        doNothing().when(controller).setOysterCardsList(any(CartCmdImpl.class), any(ModelAndView.class), any(CartDTO.class));
        when(controller.addGoodwill(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class), any(Model.class))).thenCallRealMethod();
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenReturn(cartDTO);
        controller.addGoodwill(mockHttpSession, cartCmd, mockBindingResult, mockModel);
        verify(mockCartService, never()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class));
    }

    @Test
    public void shouldDeleteGoodWillItem() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd14();
        cartCmd.setCartItemCmd(new CartItemCmdImpl());
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        int lineNo = 5;
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenCallRealMethod();
        when(controller.getCartSessionDataDTOFromSession(mockHttpSession)).thenCallRealMethod();
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
        doNothing().when(controller).setOysterCardsList(any(CartCmdImpl.class), any(ModelAndView.class), any(CartDTO.class));
        when(controller.deleteGoodwill(any(HttpSession.class), any(CartCmdImpl.class), anyInt(), any(Model.class))).thenCallRealMethod();
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(cartDTO);
        controller.deleteGoodwill(mockHttpSession, cartCmd, lineNo, mockModel);
        verify(mockCartService, times(1)).deleteItem(any(CartDTO.class), anyLong());
    }

    @Test
    public void completeShouldReturnToWorkflow() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd14();
        CartDTO cartDTO = CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId();
        cartCmd.setCartDTO(cartDTO);
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.complete(any(ModelAndView.class), any(CartCmdImpl.class))).thenCallRealMethod();
        doNothing().when(mockWorkflowService).getChangesAfterEditRefunds(any(CartCmdImpl.class));

        ModelAndView result = controller.complete(new ModelAndView(), cartCmd);
        verify(mockWorkflowService, times(1)).getChangesAfterEditRefunds(any(CartCmdImpl.class));

        assertEquals(((RedirectView) result.getView()).getUrl(), PageUrl.VIEW_WORKFLOW_ITEM);
        assertEquals(result.getModel().get(CASE_NUMBER), cartDTO.getApprovalId());
        assertEquals(result.getModel().get(EDIT), Boolean.TRUE);
    }

    @Test
    public void completeShouldReturnToRefundSummary() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd14();
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithProductItemWithRefund();
        cartCmd.setCartDTO(cartDTO);
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.complete(any(ModelAndView.class), any(CartCmdImpl.class))).thenCallRealMethod();
        ModelAndView result = controller.complete(new ModelAndView(), cartCmd);

        assertEquals(((RedirectView) result.getView()).getUrl(), PageUrl.INV_REFUND_SUMMARY);
    }

    @Test
    public void shouldGetPreferredStationId() {
        when(mockCardPreferencesService.getPreferencesByCardNumber(anyString())).thenReturn(getTestCardPreferencesCmd1());
        when(controller.getPreferredStationId(anyString())).thenCallRealMethod();
        assertEquals(getTestCardPreferencesCmd1().getStationId().toString(), controller.getPreferredStationId(OYSTER_NUMBER_1));
        verify(mockCardPreferencesService).getPreferencesByCardNumber(anyString());
    }
    
    @Test
    public void getPreferredStationIdShouldReturnNull() {
        when(mockCardPreferencesService.getPreferencesByCardNumber(anyString())).thenReturn(new CardPreferencesCmdImpl());
        when(controller.getPreferredStationId(anyString())).thenCallRealMethod();
        assertNull(controller.getPreferredStationId(OYSTER_NUMBER_1));
        verify(mockCardPreferencesService).getPreferencesByCardNumber(anyString());
    }
    
    @Test 
    public void addTradedTravelCardShouldReturnFailedCardRefundCartView() {
        when(controller.addTradedTicket(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class),anyLong())).thenCallRealMethod();
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        when(mockRefundService.updateDateOfRefund(any(CartDTO.class), any(Date.class))).thenReturn(getTestCartDTO1());
        when(mockRefundService.attachPreviouslyExchangedTicketToTheExistingProductItem(any(CartItemCmdImpl.class), any(CartDTO.class), anyLong())).thenReturn(getTestCartDTO1());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        doNothing().when(mockTradedTicketValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(getTestCartDTO1());
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        CartCmdImpl testCart = RefundTestUtil.buildCommandObject();
        List<CartItemCmdImpl> cartItemList = new ArrayList<CartItemCmdImpl>();
        CartItemCmdImpl cartItemCmd = testCart.getCartItemCmd();
        cartItemCmd.setPreviouslyExchanged(true);
        cartItemList.add(cartItemCmd);
        testCart.setCartItemList(cartItemList);
        ModelAndView result = controller.addTradedTicket(mockHttpSession, testCart, mockBindingResult, 1);
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertViewName(result, INV_FAILED_CARD_REFUND);
    }
    
    @Test
    public void shouldValidatePickupLocationIsDifferentFromStationSelected() { 
        doCallRealMethod().when(controller).updateCountryDTOInCartCmdImpl(any(CartCmdImpl.class));
        doCallRealMethod().when(controller).updatePreviousOysterBalanceForAdhocLoad(any(CartCmdImpl.class));
        when(controller.refund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        when(mockBindingResult.hasErrors()).thenReturn(false,true); 
        when(mockCardDataService.findByCardNumber(any(String.class))).thenReturn(getTestCardDTO1());
        when(controller.getCartTypeCode()).thenReturn(RefundType.FAILED_CARD.code());
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());
        ModelAndView modelAndView = controller.refund(mockHttpSession, getTestCartCmd16(), mockBindingResult);
        assertViewName(modelAndView, INV_FAILED_CARD_REFUND);
        
    }
    
    @Test
    public void cancelRefundShouldReturnToWorkflow() {
        ModelAndView result = controller.cancelRefund(mockHttpSession, getTestCartCmd16(), mockBindingResult);
        assertEquals(PageUrl.VIEW_WORKFLOW_ITEM, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void cancelRefundShouldReturnToCustomerView() {
        when(mockAddUnattachedCardService.retrieveOysterDetails(anyString()))
                .thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());
        CartCmdImpl cmd = getTestCartCmd16();
        cmd.setApprovalId(null);
        ModelAndView result = controller.cancelRefund(mockHttpSession, cmd, mockBindingResult);
        assertEquals(PageUrl.INV_CUSTOMER, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldPopulateModelAttributes() {
        Model model = new BindingAwareModelMap();
        when(mockRefundSelectService.getSelectListModel(model)).thenReturn(model);
        when(mockCountrySelectService.getSelectList()).thenReturn(new SelectListDTO());
        doCallRealMethod().when(controller).populateModelAttributes(model);
        controller.populateModelAttributes(model);
        
        assertTrue(model.containsAttribute(PageAttribute.COUNTRIES));
        verify(mockRefundSelectService).getSelectListModel(model);
    }
    
    @Test
    public void shouldPopulateMinStartDateForCalendarIcon() {
        Model model = new BindingAwareModelMap();
        when(mockSystemParameterService.getParameterValue(MIN_START_DATE_FOR_CALENDAR.code())).thenReturn("01/07/2003");
        doCallRealMethod().when(controller).populateMinStartDateForCalendarIcon(model);
        controller.populateMinStartDateForCalendarIcon(model);
        assertTrue(model.containsAttribute(PageAttribute.MIN_START_DATE_FOR_REFUND_CALENDAR));
    }
    
    @Test
    public void shouldUpdateCountryDTOInCartCmdImpl() {
        when(mockCountryDataService.findCountryByCode(anyString())).thenReturn(getTestCountryDTO1());
        doCallRealMethod().when(controller).updateCountryDTOInCartCmdImpl(any(CartCmdImpl.class));
        
        CartCmdImpl testCmd = new CartCmdImpl();
        testCmd.setPayeeAddress(getTestAddressDTO1());
        controller.updateCountryDTOInCartCmdImpl(testCmd);
        
        verify(mockCountryDataService).findCountryByCode(anyString());
    }
    
    @Test
    public void shouldUpdateCardNumberInCartCmdImpl() {
        doCallRealMethod().when(controller).updateCardNumberInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        doNothing().when(mockCartCmdImpl).setCardNumber(anyString());
        
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCardId(CARD_ID_1);
        controller.updateCardNumberInCartCmdImpl(mockCartCmdImpl, cartDTO);
        
        verify(mockCardDataService).findById(CARD_ID_1);
        verify(mockCartCmdImpl).setCardNumber(OYSTER_NUMBER_1);
    }
    
    @Test
    public void shouldUpdateTotalIntoCmd() {
        doCallRealMethod().when(controller).updateTotalIntoCmd(any(CartCmdImpl.class), any(CartDTO.class),anyString());
        doNothing().when(mockCartCmdImpl).setToPayAmount(anyInt());
        
        controller.updateTotalIntoCmd(mockCartCmdImpl, getTestCartDTO1(),CartType.LOST_REFUND.code());
        
        verify(mockCartCmdImpl).setToPayAmount(anyInt());
    }
    
    @Test
    public void shouldSetStationNameInCartCmdByStationId() {
        doCallRealMethod().when(controller).setStationNameInCartCmd(any(CartCmdImpl.class));
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());
        when(mockCartCmdImpl.getStationId()).thenReturn(LOCATION_ID_1);
        when(mockCartCmdImpl.getTargetPickupLocationId()).thenReturn(0);
        doNothing().when(mockCartCmdImpl).setStationName(anyString());
        
        controller.setStationNameInCartCmd(mockCartCmdImpl);
        
        verify(mockLocationDataService).getActiveLocationById(INT_LOCATION_ID_99);
        verify(mockCartCmdImpl).setStationName(anyString());
    }
    
    @Test
    public void shouldSetStationNameInCartCmdByPickupLocationId() {
        doCallRealMethod().when(controller).setStationNameInCartCmd(any(CartCmdImpl.class));
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());
        when(mockCartCmdImpl.getStationId()).thenReturn(LOCATION_ID_1);
        when(mockCartCmdImpl.getTargetPickupLocationId()).thenReturn(PICKUP_LOCATION_ID);
        when(mockCartCmdImpl.getCartDTO()).thenReturn(getTestCartDTO1());
        doNothing().when(mockCartCmdImpl).setCartDTO(any(CartDTO.class));
        
        controller.setStationNameInCartCmd(mockCartCmdImpl);
        
        verify(mockLocationDataService).getActiveLocationById(PICKUP_LOCATION_ID);
        verify(mockCartCmdImpl).setCartDTO(any(CartDTO.class));
    }
    
    @Test
    public void initBinderShouldRegisterCustomEditor() {
        doNothing().when(mockServletRequestBinder).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
        initController = new FailedCardRefundCartController();
        initController.initBinder(mockServletRequestBinder);
        doCallRealMethod().when(controller).initBinder(mockServletRequestBinder);
        verify(mockServletRequestBinder, atLeastOnce()).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
    }

    @Test
    public void shouldUpdateCartTotalWithValidationError() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd18();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        when(controller.getValidator()).thenReturn(mockFailedCardRefundCartValidator);
        doCallRealMethod().when(mockFailedCardRefundCartValidator).validate(cartCmdImpl, mockBindingResult);
        doCallRealMethod().when(controller).updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        doCallRealMethod().when(mockAdministrationFeeValidator).validate(cartCmdImpl,mockBindingResult );
        when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView modelAndView = controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertViewName(modelAndView, INV_FAILED_CARD_REFUND);
    }
    
    @Test
    public void addTradedTravelCardShouldReturnBindingError() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestRenewCartCmd4();
    	when(controller.addTradedTicket(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class),anyLong())).thenCallRealMethod();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        when(mockBindingResult.hasErrors()).thenReturn(true);
        doCallRealMethod().when(mockTradedTicketValidator).validate(cartCmdImpl,mockBindingResult );
        ModelAndView modelAndView = controller.addTradedTicket(mockHttpSession, cartCmdImpl, mockBindingResult, 1L);
        assertViewName(modelAndView, INV_FAILED_CARD_REFUND);
    }

    @Test 
    public void refundShouldReturnValidationError() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        when(controller.getRefundType()).thenReturn(INV_FAILED_CARD_REFUND);
        CartDTO cartDTO = new CartDTO();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        cartDTO.setCartItems(items);
        cartCmdImpl.setCartDTO(cartDTO);
        doCallRealMethod().when(controller).refund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        doCallRealMethod().when(controller).complete(any(ModelAndView.class), any(CartCmdImpl.class));
        doCallRealMethod().when(controller).getCartDTOUsingCartSessionDataDTOInSession((HttpSession) anyObject());
        doCallRealMethod().when(controller).getCartSessionDataDTOFromSession((HttpSession) anyObject());
        when(controller.getValidator()).thenReturn(mockFailedCardRefundCartValidator);
        doNothing().when(mockRefundCartPaymentValidator).validate(cartCmdImpl, mockBindingResult);
        doCallRealMethod().when(mockRefundPaymentValidator).validate(cartCmdImpl,mockBindingResult );
        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());
        ModelAndView modelAndView = controller.refund(mockHttpSession, CartCmdTestUtil.getTestCartCmd14(), mockBindingResult);
        assertEquals(modelAndView.getModel().get(FAILED_VALIDATION), true);
        assertViewName(modelAndView, INV_FAILED_CARD_REFUND);
    }
    
    @Test
    public void shouldUpdateCartTotalForCancelAndSurrenderOfDeceasedCustomerInvokeServiceTest() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd15();
        CartItemCmdImpl cartItemCmd = cartCmdImpl.getCartItemCmd(); 
        
        cartItemCmd.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        cartItemCmd.setDeceasedCustomer(Boolean.TRUE);
        cartItemCmd.setBackdated(Boolean.FALSE);
        
        cartItemCmd.setDateOfCanceAndSurrender(null);
        cartItemCmd.setDateOfLastUsage(DateUtil.getFiveDaysBefore(new Date()));
        
        when(controller.getRefundType()).thenReturn(INV_CANCEL_AND_SURRENDER_CARD);
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(cartDTO);
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        when(mockRefundService.updateAdministrationFeeItemDTO((any(CartDTO.class)), any(Integer.class))).thenReturn(cartDTO);

        when(mockRefundService.updateDateOfRefundAndRefundCalculationBasis((any(CartDTO.class)), any(Date.class), anyString())).thenReturn(cartDTO);
        when(mockRefundService.getUpdatedCart(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));

        doCallRealMethod().when(controller).updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);
        verify(mockRefundService).updateDateOfRefundAndRefundCalculationBasis(any(CartDTO.class), any(Date.class), anyString());
    }
    
    @Test
    public void shouldUpdateCartTotalForCancelAndSurrenderOfBackDatedCustomerInvokeServiceTest() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd15();
        CartItemCmdImpl cartItemCmd = cartCmdImpl.getCartItemCmd(); 
        
        cartItemCmd.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        cartItemCmd.setDeceasedCustomer(Boolean.FALSE);
        cartItemCmd.setBackdated(Boolean.TRUE);
        
        cartItemCmd.setDateOfCanceAndSurrender(DateUtil.getFiveDaysBefore(new Date()));
        cartItemCmd.setDateOfLastUsage(null);
        
        when(controller.getRefundType()).thenReturn(INV_CANCEL_AND_SURRENDER_CARD);
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(cartDTO);
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        when(mockRefundService.updateAdministrationFeeItemDTO((any(CartDTO.class)), any(Integer.class))).thenReturn(cartDTO);

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(cartDTO);
        when(mockRefundService.getUpdatedCart(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));

        doCallRealMethod().when(controller).updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);
        verify(mockRefundService).updateDateOfRefund(any(CartDTO.class), any(Date.class));
    }
    
    @Test
    public void shouldUpdateCartTotalForCancelAndSurrenderWithNoBackdatedOrDeceasedCustomerTest () {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd15();
        CartItemCmdImpl cartItemCmd = cartCmdImpl.getCartItemCmd(); 
        
        cartItemCmd.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        cartItemCmd.setDeceasedCustomer(Boolean.FALSE);
        cartItemCmd.setBackdated(Boolean.FALSE);
        
        cartItemCmd.setDateOfCanceAndSurrender(null);
        cartItemCmd.setDateOfLastUsage(null);
        
        when(controller.getRefundType()).thenReturn(INV_CANCEL_AND_SURRENDER_CARD);
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(cartDTO);
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        when(mockRefundService.updateAdministrationFeeItemDTO((any(CartDTO.class)), any(Integer.class))).thenReturn(cartDTO);

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(cartDTO);
        when(mockRefundService.getUpdatedCart(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));

        doCallRealMethod().when(controller).updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);
        verify(mockRefundService).updateDateOfRefund(any(CartDTO.class), any(Date.class));
    }
    
    @Test
    public void shouldUpdateCartTotalForCancelAndSurrenderWithNoCartCmdImplTest () {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd14();
        
        when(controller.getRefundType()).thenReturn(INV_CANCEL_AND_SURRENDER_CARD);
        CartDTO cartDTO = CartTestUtil.getTestCartDTO1();
        cartDTO.addCartItem(CartTestUtil.getPayAsYouGoItemDTO());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(cartDTO);
        cartDTO.addCartItem(CartTestUtil.getNewAdminFeeItemDTO());
        when(mockRefundService.updateAdministrationFeeItemDTO((any(CartDTO.class)), any(Integer.class))).thenReturn(cartDTO);

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(cartDTO);
        when(mockRefundService.getUpdatedCart(anyLong())).thenReturn(cartDTO);
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        doNothing().when(mockRefundService).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));

        doCallRealMethod().when(controller).updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);
        verify(mockRefundService).updateDateOfRefund(any(CartDTO.class), any(Date.class));
    }

    @Test
    public void shouldRedirectToTransfers() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setPageName(Page.TRANSFER_PRODUCT);
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockCardDataService.findById(anyLong())).thenReturn(new CardDTO());
        doCallRealMethod().when(controller).redirectToTransfers(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        doCallRealMethod().when(controller).createCartSessionData(any(HttpSession.class), any(CartCmdImpl.class));
        ModelAndView modelAndView = controller.redirectToTransfers(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertNotNull(modelAndView);
        CartCmdImpl cartCmd = (CartCmdImpl) modelAndView.getModelMap().get(PageCommand.CART);
        assertTrue(cartCmd.isLostOrStolenMode());
    }

    @Test
    public void shouldRedirectToTransfersWithNullCartSession() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setPageName(Page.TRANSFER_PRODUCT);
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.CARD_ID);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(null);
        when(mockCartService.emptyCart(any(CartDTO.class))).thenReturn(new CartDTO());
        when(mockCardDataService.findById(anyLong())).thenReturn(new CardDTO());
        doCallRealMethod().when(controller).redirectToTransfers(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        doCallRealMethod().when(controller).createCartSessionData(any(HttpSession.class), any(CartCmdImpl.class));
        ModelAndView modelAndView = controller.redirectToTransfers(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertNotNull(modelAndView);
        CartCmdImpl cartCmd = (CartCmdImpl) modelAndView.getModelMap().get(PageCommand.CART);
        assertTrue(cartCmd.isLostOrStolenMode());
    }
 
    
    @Test
    public void testUpdateTotalWithDepositForFailedCardRefund() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        CartDTO cartDTO= CartTestUtil.getNewCartDTOWithCardRefundableDepositItemDTO();
        doCallRealMethod().when(controller).updateTotalIntoCmd(any(CartCmdImpl.class), any(CartDTO.class), anyString());
        controller.updateTotalIntoCmd(cartCmdImpl, cartDTO, CartType.FAILED_CARD_REFUND.code());
        assertEquals(new Integer(500),cartCmdImpl.getToPayAmount());
        
    }
    
    @Test
    public void shouldUpdatePreviousOysterBalanceForAdhocLoad() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd1();
        cartCmdImpl.setPaymentType(PaymentType.AD_HOC_LOAD.code());
        cartCmdImpl.setTargetCardNumber(OYSTER_NUMBER_1);
        doCallRealMethod().when(controller).updatePreviousOysterBalanceForAdhocLoad(any(CartCmdImpl.class));
        controller.updatePreviousOysterBalanceForAdhocLoad(cartCmdImpl);
        verify(mockGetCardService).getCard(anyString());
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(anyString());
        assertNotNull(cartCmdImpl.getPreviousCreditAmountOnCard());
    }
    
    
    @Test
    public void testStoreCartIdInCartSessionDataDTOInSession() {
        doCallRealMethod().when(controller).storeCartIdInCartSessionDataDTOInSession(any(HttpSession.class), anyLong());
        controller.storeCartIdInCartSessionDataDTOInSession(mockHttpSession, CartTestUtil.CART_ID_1);
        verify(mockHttpSession, atLeastOnce()).setAttribute(anyString(), any(CartSessionData.class));
        
    }
}
