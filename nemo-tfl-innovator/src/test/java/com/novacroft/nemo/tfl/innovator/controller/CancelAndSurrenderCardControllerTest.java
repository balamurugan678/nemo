package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesCmd1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd4;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO1;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CANCEL_AND_SURRENDER_CARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil;
import com.novacroft.nemo.test_support.RefundTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.CancelAndSurrenderService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.application_service.impl.AddUnlistedProductServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.form_validator.CancelAndSurrenderCardRefundCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.CancelAndSurrenderValidator;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.TradedTicketValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class CancelAndSurrenderCardControllerTest {
    private CancelAndSurrenderCardController controller;
    private CancelAndSurrenderCardRefundCartValidator mockCancelAndSurrenderCartValidator;
    private CancelAndSurrenderValidator mockCancelAndSurrenderValidator;
    private CancelAndSurrenderService mockCancelAndSurrenderService;
    private RefundPaymentValidator mockRefundPaymentValidator;
    private WorkFlowService mockWorkflowService;
    private CartCmdImpl mockCartCmdImpl;
    private HttpSession mockHttpSession;
    private CartService mockCartService;
    private CartAdministrationService mockNewCartAdministrationService;
    private RefundService mockRefundService;
    private CardDataService mockCardDataService;
    private RefundPaymentService mockRefundPaymentService;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    private GoodwillValidator mockGoodwillValidator;
    private AddUnattachedCardService mockAddUnattachedCardService;
    private BindingResult mockBindingResult;
    private RefundSelectListService mockRefundSelectListService;
    private RefundCartPaymentValidator mockRefundCartPaymentValidator;
    private GetCardService mockGetCardService;
    private TradedTicketValidator mockTradedTicketValidator;
    private CardPreferencesService mockCardPreferencesService;
    private LocationDataService mockLocationDataService;
    private CancelAndSurrenderCardController mockController;
    private AddUnlistedProductService mockAddUnlistedProductService;
    private TravelCardService mockTravelCardService;


    public static final Integer LINE_ITEM = 5;
    public static final Integer WEB_CREDIT_SETTLEMENT_BALANCE = 20;
    public static final int UPDATE_REFUND_CALCULATION_BASIS_LINE_NUMBER = 5;
    protected CountrySelectListService mockCountrySelectListService;

    @Before
    public void setUp() throws Exception {
        controller = new CancelAndSurrenderCardController();
        mockCancelAndSurrenderValidator = mock(CancelAndSurrenderValidator.class);
        mockCancelAndSurrenderCartValidator = mock(CancelAndSurrenderCardRefundCartValidator.class);
        mockCancelAndSurrenderService = mock(CancelAndSurrenderService.class);
        mockRefundPaymentValidator = mock(RefundPaymentValidator.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockCartCmdImpl = mock(CartCmdImpl.class);
        when(mockCartCmdImpl.getCardNumber()).thenReturn("100000014969");
        mockHttpSession = mock(HttpSession.class);
        mockCartService = mock(CartService.class);
        mockTravelCardService = mock(TravelCardService.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);
        mockRefundService = mock(RefundService.class);
        mockCardDataService = mock(CardDataService.class);
        mockRefundPaymentService = mock(RefundPaymentService.class);
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        mockBindingResult = mock(BindingResult.class);
        mockRefundSelectListService = mock(RefundSelectListService.class);
        mockRefundCartPaymentValidator = mock(RefundCartPaymentValidator.class);
        mockGetCardService = mock(GetCardService.class);
        mockCountrySelectListService = mock(CountrySelectListService.class);
        mockTradedTicketValidator = mock(TradedTicketValidator.class);
        mockCardPreferencesService = mock(CardPreferencesService.class);
        mockLocationDataService = mock(LocationDataService.class); 
        mockController = mock(CancelAndSurrenderCardController.class);
        mockAddUnlistedProductService = mock(AddUnlistedProductServiceImpl.class);
        
        controller.cancelandSurrenderValidator = mockCancelAndSurrenderValidator;
        controller.cancelAndSurrenderCartValidator = mockCancelAndSurrenderCartValidator;
        controller.cancelAndSurrenderService = mockCancelAndSurrenderService;
        controller.refundPaymentValidator = mockRefundPaymentValidator;
        controller.workflowService = mockWorkflowService;
        controller.cartService = mockCartService;
        controller.travelCardService = mockTravelCardService;
        controller.cartAdministrationService = mockNewCartAdministrationService;
        controller.refundService = mockRefundService;
        controller.cardDataService = mockCardDataService;
        controller.refundPaymentService = mockRefundPaymentService;
        controller.webCreditSettlementDataService = mockWebCreditSettlementDataService;
        controller.goodwillValidator = mockGoodwillValidator;
        controller.addUnattachedCardService = mockAddUnattachedCardService;
        controller.refundSelectListService = mockRefundSelectListService;
        controller.refundCartPaymentValidator = mockRefundCartPaymentValidator;
        controller.getCardService = mockGetCardService;
        controller.countrySelectListService = mockCountrySelectListService;
        controller.tradedTicketValidator = mockTradedTicketValidator;
        controller.cardPreferencesService = mockCardPreferencesService;
        controller.locationDataService = mockLocationDataService;
        controller.addUnlistedProductService = mockAddUnlistedProductService;
        mockController.addUnlistedProductService = mockAddUnlistedProductService;
        mockController.refundPaymentValidator = mockRefundPaymentValidator;
        mockController.refundCartPaymentValidator = mockRefundCartPaymentValidator;
        mockController.cancelAndSurrenderCartValidator = mockCancelAndSurrenderCartValidator;
        
        
        doNothing().when(mockRefundCartPaymentValidator).validate(mockCartCmdImpl, mockBindingResult);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCountrySelectListService.getSelectList()).thenReturn(new SelectListDTO());
        when(mockCartService.postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class))).then(returnsFirstArg());
        when(mockCartService.postProcessAndSortCartDTOWithoutRefundRecalculation(any(CartDTO.class))).then(returnsFirstArg());
    }

    @Test
    public void populateModelAttributesShouldPopulateModelWithPaymentTypes() {
        Model model = new BindingAwareModelMap();
        when(mockRefundSelectListService.getAnonymousGoodwillSelectListModel(any(Model.class))).thenReturn(new BindingAwareModelMap());
        controller.populateModelAttributes(model);
        assertNotNull(model);
    }

    @Test
    public void getRefundTypeShouldReturnInvCancelAndSurrenderCard() {
        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, controller.getRefundType());
    }

    @Test
    public void viewCartUsingCubicShouldReturnCancelAndSurrenderRefundView() {
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), (Boolean) anyObject())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        doNothing().when(mockHttpSession).setAttribute(anyString(), anyObject());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockWebCreditSettlementDataService.getBalance(anyLong())).thenReturn(WEB_CREDIT_SETTLEMENT_BALANCE);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());

        ModelAndView result = controller.viewCartUsingCubic(mockHttpSession, OYSTER_NUMBER_1);

        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
        verify(mockRefundService, atLeastOnce()).getCartDTOForRefund(anyString(), anyString(), (Boolean) anyObject());
    }
    
    @Test
    public void viewCartShouldReturnCancelAndSurrenderRefundView() {
    	when(mockCartService.findByApprovalId(anyLong())).thenReturn(getTestCartDTO1());
    	when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
    	ModelAndView result = controller.viewCart(mockHttpSession, OYSTER_NUMBER_1, null);
    	assertEquals(result.getViewName(), INV_CANCEL_AND_SURRENDER_CARD);
    }

    @Test
    public void updateCartTotalShouldReturnCancelAndSurrenderRefundView() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());

        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        when(mockRefundService.updateAdministrationFeeItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockRefundService.updateDateOfRefund(any(CartDTO.class), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());

        ModelAndView result = controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);

        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void addTravelCardShouldReturnCancelAndSurrenderRefundViewForDeceasedCustomerRefund() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        ModelAndView result = controller.addTravelCard(mockHttpSession, getTestCartCmd4(), mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void addTravelCardShouldReturnCancelAndSurrenderRefundViewForBackdatedRefund() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        CartCmdImpl cartCmdImpl = getTestCartCmd4();
        cartCmdImpl.getCartItemCmd().setBackdated(true);
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(false);
        
        ModelAndView result = controller.addTravelCard(mockHttpSession, cartCmdImpl, mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void addTravelCardShouldReturnCancelAndSurrenderRefundViewForWhenBackdatedRefundAndDeceasedCustomerFalse() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        CartCmdImpl cartCmdImpl = getTestCartCmd4();
        cartCmdImpl.getCartItemCmd().setBackdated(false);
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(false);
        cartCmdImpl.getCartItemCmd().setTicketUnused(false);
        
        ModelAndView result = controller.addTravelCard(mockHttpSession, cartCmdImpl, mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void addTravelCardShouldReturnRefundViewAndAddTravelCardAndAdministrationFeeItem() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        ModelAndView result = controller.addTravelCard(mockHttpSession, getTestCartCmd4(), mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void addTravelCardShouldReturnRefundViewAndShouldNotAddTravelCardAndAdministrationFeeItem() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());

        ModelAndView result = controller.addTravelCard(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(), mockBindingResult);

        verify(mockRefundService, never()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, never()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, never()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void deleteCancelAndSurrenderShouldReturnRefundTest() {
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        CartDTO testCartDto = CartTestUtil.getNewCartDTOWithItem1();
        when(mockCartService.deleteItem((CartDTO) anyObject(), anyLong())).thenReturn(testCartDto);

        ModelAndView result = controller.deleteTravelCard(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(), LINE_ITEM);

        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
    }

    @Test
    public void continueCartShouldReturnRefundView() {

        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd2();
        List<CartItemCmdImpl> emptyCartItemCmds = new ArrayList<CartItemCmdImpl>();
        cartCmdImpl.setCartItemList(emptyCartItemCmds);
      
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        doNothing().when(mockRefundPaymentValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(true);
       
        ModelAndView result = controller.refund(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }
    
    @Test
    public void continueCartShouldReturnRefundViewAndCalladdUpdateItemsForBusPass() {

        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        cartCmdImpl.setCartDTO(CartTestUtil.getNewCartDTOWithItem());
        cartCmdImpl.getCartItemCmd().setTicketType(TicketType.BUS_PASS.code());
      
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        doNothing().when(mockRefundPaymentValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockTravelCardService.addUpdateItems(anyLong(), any(CartItemCmdImpl.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockBindingResult.hasErrors()).thenReturn(true);
       
        ModelAndView result = controller.refund(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
        verify(mockTravelCardService, atLeastOnce()).addUpdateItems(anyLong(), any(CartItemCmdImpl.class));
    }
    
    
    @Test
    public void continueCartShouldReturnRefundViewNullTest() {

        CartCmdImpl cartCmdImpl = mock(CartCmdImpl.class);
        when(cartCmdImpl.getCartType()).thenReturn(CartType.CANCEL_SURRENDER_REFUND.code());        
        List<CartItemCmdImpl> mockedItems = new ArrayList<CartItemCmdImpl>();
        mockedItems.add(mock(CartItemCmdImpl.class));

        Mockito.doReturn(mockedItems).when(mockCancelAndSurrenderService).processCancelOrSurrenderRefund(any(CartCmdImpl.class));
        Mockito.doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(Errors.class));
        Mockito.doNothing().when(cartCmdImpl).setCartItemList(mockedItems);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        when(mockCardDataService.findById(CartTestUtil.getTestCartDTO1().getCardId())).thenReturn(CardTestUtil.getTestCardDTO1());

        ModelAndView result = controller.refund(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertNull(result.getViewName());
    }
    
    @Test
    public void continueCartShouldReturnPickUpStationName() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd2();
        List<CartItemCmdImpl> emptyCartItemCmds = new ArrayList<CartItemCmdImpl>();
        cartCmdImpl.setCartItemList(emptyCartItemCmds);
      
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        doNothing().when(mockRefundPaymentValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(true);
       
        ModelAndView result = controller.refund(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void continueCartShouldReturnNullWhenApprovalIdIsNull() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        when(mockCardDataService.findById(CartTestUtil.getTestCartDTO1().getCardId())).thenReturn(CardTestUtil.getTestCardDTO1());

        ModelAndView result = controller.refund(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(), mockBindingResult);

        assertNull(result.getViewName());
    }

    @Test
    public void setOysterCardsListTest() {
        SelectListDTO selectList = mock(SelectListDTO.class);
        Mockito.doReturn(selectList).when(mockRefundPaymentService).createCardsSelectListForAdHocLoad(Mockito.any(String.class));
        ModelAndView testView = new ModelAndView();
        controller.setOysterCardsList(mockCartCmdImpl, testView, null);

        Object actual = testView.getModel().get(PageCommandAttribute.FIELD_SELECT_CARD);
        assertEquals(selectList, actual);
    }

    @Test
    public void updateRefundCalculationBasisShouldReturnCancelAndSurrenderView() {
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.updateProductItemDTO(anyLong(), anyLong(), anyString())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        ModelAndView result = controller.updateRefundBasis(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(), UPDATE_REFUND_CALCULATION_BASIS_LINE_NUMBER, RefundCalculationBasis.PRO_RATA.code());
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void addGoodwillShouldReturnCancelAndSurrenderView() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd4();
        cartCmd.setCartItemCmd(new CartItemCmdImpl());

        Model mockModel = mock(Model.class);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        doNothing().when(mockGoodwillValidator).validate((CartCmdImpl) anyObject(), (BindingResult) anyObject());
        ModelAndView result = controller.addGoodwill(mockHttpSession, cartCmd, mockBindingResult, mockModel);

        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void deleteGoodwillShouldReturnCancelAndSurrenderView() {
        Model mockModel = mock(Model.class);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doNothing().when(mockGoodwillValidator).validate((CartCmdImpl) anyObject(), (BindingResult) anyObject());

        ModelAndView result = controller.deleteGoodwill(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(), LINE_ITEM, mockModel);

        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }

    @Test
    public void cancelRefundShouldReturnViewWorkflowItemView() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockAddUnattachedCardService.retrieveOysterDetails(anyString())).thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());

        ModelAndView result = controller.cancelRefund(mockHttpSession, CartCmdTestUtil.getTestCartCmd14(), mockBindingResult);

        assertNull(result.getViewName());
    }
    
    @Test 
    public void addTradedTravelCardShouldReturnCancelAndSurrenderCardRefundCartView() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        when(mockRefundService.updateDateOfRefund(any(CartDTO.class), any(Date.class))).thenReturn(getTestCartDTO1());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        doNothing().when(mockTradedTicketValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        
        CartCmdImpl testCart = RefundTestUtil.buildCommandObject();
        List<CartItemCmdImpl> cartItemList = new ArrayList<CartItemCmdImpl>();
        CartItemCmdImpl cartItemCmd = testCart.getCartItemCmd();
        cartItemCmd.setPreviouslyExchanged(true);
        cartItemList.add(cartItemCmd);
        testCart.setCartItemList(cartItemList);
        
        ModelAndView result = controller.addTradedTicket(mockHttpSession, testCart, mockBindingResult, 1);
        assertViewName(result, PageView.INV_CANCEL_AND_SURRENDER_CARD);
        assertModelAttributeAvailable(result, CART_DTO);
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
    }
    
    @Test
    public void shouldGetPreferredStationId() {
        when(mockCardPreferencesService.getPreferencesByCardNumber(anyString())).thenReturn(getTestCardPreferencesCmd1());
        assertEquals(getTestCardPreferencesCmd1().getStationId().toString(), controller.getPreferredStationId(OYSTER_NUMBER_1));
        verify(mockCardPreferencesService).getPreferencesByCardNumber(anyString());
    }
    
    @Test
    public void shouldValidatePickupLocationAndCardNumberForAdhocLoadRefund() {
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        cartCmdImpl.setCartDTO(CartTestUtil.getNewCartDTOWithItem());
        cartCmdImpl.setPaymentType(PaymentType.AD_HOC_LOAD.code());
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doCallRealMethod().when(mockController).refund(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        doNothing().when(mockCancelAndSurrenderValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        doNothing().when(mockRefundCartPaymentValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        doNothing().when(mockRefundPaymentValidator).validate(any(CartDTO.class), any(BindingResult.class));
        doNothing().when(mockController).updatePreviousOysterBalanceForAdhocLoad(cartCmdImpl);
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCartService.addUpdateItems(anyLong(), any(CartItemCmdImpl.class), eq(ProductItemDTO.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockBindingResult.hasErrors()).thenReturn(false, true);
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(getTestLocationDTO1());
        when(mockController.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(new CartDTO());
        ModelAndView result = mockController.refund(mockHttpSession, cartCmdImpl, mockBindingResult);
        assertEquals(INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }
    
    @Test
    public void updateCartTotalShouldSetCartTypeAsCancelAndSurrenderRefundTest() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockRefundService.updatePayAsYouGoItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());

        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        when(mockRefundService.updateAdministrationFeeItemDTO(any(CartDTO.class), any(Integer.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockRefundService.updateDateOfRefund(any(CartDTO.class), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());

        controller.updateCartTotal(mockHttpSession, cartCmdImpl, mockBindingResult);

        assertEquals(CartType.CANCEL_SURRENDER_REFUND.code(), cartCmdImpl.getCartItemCmd().getCartType());
    }
    
    @Test
    public void setDateOfRefundForDeceasedCustomerAndBackdatedRefundsTest(){
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(false);
        cartCmdImpl.getCartItemCmd().setDateOfCanceAndSurrender(DateTestUtil.getSun23Feb());
        controller.setDateOfRefundForDeceasedCustomerAndBackdatedRefunds(cartCmdImpl);
        assertEquals(cartCmdImpl.getCartItemCmd().getDateOfCanceAndSurrender(),cartCmdImpl.getDateOfRefund());
    }
    
    @Test
    public void addTravelCardShouldReturnRefundViewForBackDatedRefundTest() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        cartCmdImpl.getCartItemCmd().setBackdated(true);
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(false);
        ModelAndView result = controller.addTravelCard(mockHttpSession, cartCmdImpl, mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }
    
    @Test
    public void addTravelCardShouldReturnRefundViewForTicketNotUsedTest() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        cartCmdImpl.getCartItemCmd().setBackdated(false);
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(false);
        cartCmdImpl.getCartItemCmd().setTicketUnused(true);
        ModelAndView result = controller.addTravelCard(mockHttpSession, cartCmdImpl, mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }
    
    @Test
    public void addTravelCardShouldReturnRefundViewForRegularTravelCardTest() {

        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString())).thenReturn(
                        CartTestUtil.getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());

        when(mockRefundService.updateDateOfRefund((any(CartDTO.class)), any(Date.class))).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd4();
        cartCmdImpl.getCartItemCmd().setBackdated(false);
        cartCmdImpl.getCartItemCmd().setDeceasedCustomer(false);
        cartCmdImpl.getCartItemCmd().setTicketUnused(false);
        ModelAndView result = controller.addTravelCard(mockHttpSession, cartCmdImpl, mockBindingResult);

        verify(mockRefundService, atLeastOnce()).addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), anyString());
        verify(mockNewCartAdministrationService, atLeastOnce()).addOrRemoveAdministrationFeeToCart(any(CartDTO.class), anyLong(), anyString());
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD, result.getViewName());
    }
    
    @Test
    public void shouldInitBinder() {
        ServletRequestDataBinder mockBinder = mock(ServletRequestDataBinder.class);
        doNothing().when(mockBinder).registerCustomEditor(any(Class.class), any(PropertyEditor.class));
        
        mockController.initBinder(mockBinder);
        
        verify(mockBinder).registerCustomEditor(any(Class.class), any(PropertyEditor.class));
    }
}
