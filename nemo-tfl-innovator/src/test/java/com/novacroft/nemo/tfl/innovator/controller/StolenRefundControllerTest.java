package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.CART_ITEM_ID;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.LINE_NO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_STOLEN_REFUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.RefundTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.form_validator.AddUnlistedProductValidator;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.TradedTicketValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

public class StolenRefundControllerTest {
    private StolenRefundController controller;
    private RefundCartValidator mockRefundCartValidator;
    private BindingResult mockBindingResult;
    private RefundPaymentValidator mockRefundPaymentValidator;
    private WorkFlowService mockWorkflowService;
    private RefundService mockRefundService;
    private CartService mockCartService;
    private HotlistCardService mockHotlistCardService;
    private RefundPaymentService mockRefundPaymentService;
    private CartCmdImpl mockCartCmdImpl;
    private CardDataService mockCardDataService;
    private CardService mockCardService;
    private HttpSession mockHttpSession;
    private CartAdministrationService mockNewCartAdministrationService;
    private RefundCartPaymentValidator mockRefundCartPaymentValidator;
    private WebCreditSettlementDataService mockWebCreditSettlementDataService;
    private StolenRefundController mockStolenCardRefundController;
	private GetCardService mockGetCardService;
	private AddUnlistedProductValidator mockAddUnlistedProductValidator;
	private AddUnlistedProductService mockAddUnlistedProductService;
	private TradedTicketValidator mockTradedTicketValidator;
	private RefundSelectListService mockRefundSelectListService;
    private CountrySelectListService mockCountrySelectListService;
    private GoodwillValidator mockGoodwillValidator;
    private Model mockModel;
	
    @Before
    public void setUp() throws Exception {
        controller = new StolenRefundController();
        mockStolenCardRefundController = mock(StolenRefundController.class);
        mockRefundCartValidator = mock(RefundCartValidator.class);
        mockBindingResult = mock(BindingResult.class);
        mockRefundPaymentValidator = mock(RefundPaymentValidator.class);
        mockWorkflowService = mock(WorkFlowService.class);
        mockRefundService = mock(RefundService.class);
        mockCartService = mock(CartService.class);
        mockHotlistCardService = mock(HotlistCardService.class);
        mockRefundPaymentService = mock(RefundPaymentService.class);
        mockCartCmdImpl = mock(CartCmdImpl.class);
        when(mockCartCmdImpl.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        mockCardDataService = mock(CardDataService.class);
        mockCardService = mock(CardService.class);
        mockHttpSession = mock(HttpSession.class);
        mockNewCartAdministrationService = mock(CartAdministrationService.class);
        mockRefundCartPaymentValidator = mock(RefundCartPaymentValidator.class);
        mockWebCreditSettlementDataService = mock(WebCreditSettlementDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockAddUnlistedProductValidator = mock(AddUnlistedProductValidator.class);
        mockAddUnlistedProductService = mock(AddUnlistedProductService.class);
        mockTradedTicketValidator = mock(TradedTicketValidator.class);
        mockRefundSelectListService = mock(RefundSelectListService.class);
        mockCountrySelectListService = mock(CountrySelectListService.class);
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockModel = mock(Model.class);
        
        doNothing().when(mockRefundCartValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(mockRefundCartPaymentValidator).validate(anyObject(), any(Errors.class));

        controller.refundCartValidator = mockRefundCartValidator;
        controller.refundPaymentValidator = mockRefundPaymentValidator;
        controller.workflowService = mockWorkflowService;
        ReflectionTestUtils.setField(this.controller, "refundCartValidator", mockRefundCartValidator);
        controller.refundService = mockRefundService;
        controller.cartService = mockCartService;
        controller.hotlistCardService = mockHotlistCardService;
        controller.refundPaymentService = mockRefundPaymentService;
        controller.cardDataService = mockCardDataService;
        controller.cardService = mockCardService;
        controller.cartAdministrationService = mockNewCartAdministrationService;
        controller.refundCartPaymentValidator = mockRefundCartPaymentValidator;
        controller.webCreditSettlementDataService = mockWebCreditSettlementDataService;
        controller.addUnlistedProductValidator = mockAddUnlistedProductValidator;
        controller.addUnlistedProductService = mockAddUnlistedProductService;
        controller.tradedTicketValidator = mockTradedTicketValidator;
        controller.getCardService = mockGetCardService;
        controller.refundSelectListService = mockRefundSelectListService;
        controller.countrySelectListService = mockCountrySelectListService;
        controller.goodwillValidator = mockGoodwillValidator;

        mockStolenCardRefundController = spy(controller);
        
        when(mockCartService.postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class))).then(returnsFirstArg());
        when(mockCartService.postProcessAndSortCartDTOWithoutRefundRecalculation(any(CartDTO.class))).then(returnsFirstArg());
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        doNothing().when(mockHttpSession).setAttribute(anyString(), any());
    }

    @Test
    public void viewCartUsingCubicShouldReturnStolenRefundView() {
        when(mockWebCreditSettlementDataService.getBalance(any(Long.class))).thenReturn(666);
        when(mockCardDataService.findByCardNumber(any(String.class))).thenReturn(getTestCardDTO1());
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), (Boolean) anyObject())).thenReturn(getNewCartDTOWithItem());
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());

        ModelAndView result = controller.viewCartUsingCubic(mockHttpSession, OYSTER_NUMBER_1);

        assertEquals(INV_STOLEN_REFUND, result.getViewName());
        verify(mockRefundService, atLeastOnce()).getCartDTOForRefund(anyString(), anyString(), (Boolean) anyObject());
    }

    @Test
    public void continueCartShouldReturnNullView() {
        SelectListDTO selectList = mock(SelectListDTO.class);
        Mockito.doReturn(selectList).when(mockRefundPaymentService).createCardsSelectListForAdHocLoad(Mockito.any(String.class));
        ReflectionTestUtils.setField(this.controller, "refundCartValidator", mockRefundCartValidator);
        when(mockStolenCardRefundController.getCartDTOUsingCartSessionDataDTOInSession(mockHttpSession)).thenReturn(getNewCartDTOWithItem());
		when(mockCardDataService.findByCardNumber(any(String.class))).thenReturn(CardTestUtil.getTestCardDTO1());
    	when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
    	when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
    	when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        ModelAndView result = mockStolenCardRefundController.refund(mockHttpSession, CartCmdTestUtil.getTestCartCmd2(), mockBindingResult);

        assertNull(result.getViewName());
    }

    @Test
    public void updateCartTotalShouldReturnStolenRefundView() {
    	StolenRefundController mockController = mock(StolenRefundController.class);
    	RefundCartValidator mockRefundCartValidator = mock(RefundCartValidator.class); 
    	mockController.refundService = mockRefundService;
    	
    	when(mockController.getValidator()).thenReturn(mockRefundCartValidator);
    	when(mockController.getRefundType()).thenReturn(INV_STOLEN_REFUND);
    	when(mockController.getCartDTOUsingCartSessionDataDTOInSession(any(HttpSession.class))).thenReturn(getTestCartDTO1());
    	when(mockRefundService.updateDateOfRefund(any(CartDTO.class), any(Date.class))).thenReturn(getTestCartDTO1());
    	when(mockRefundService.getUpdatedCart(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
    	when(mockController.updateCartTotal(any(HttpSession.class), any(CartCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
    	when(mockController.getCartDTOUsingCartSessionDataDTOInSession(any(HttpSession.class))).thenReturn(getTestCartDTO1());
    	
    	ModelAndView result = mockController.updateCartTotal(mockHttpSession, CartCmdTestUtil.getTestCartCmd2(), mockBindingResult);
    	
    	assertEquals(INV_STOLEN_REFUND, result.getViewName());
        assertNotNull(result.getModel().get(CART_DTO));
    }

    @Test
    public void addTravelCardShouldReturnStolenRefundView() {
    	when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
    	doNothing().when(mockAddUnlistedProductValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
    	doNothing().when(mockAddUnlistedProductService).setTravelcardTypeByFormTravelCardType(any(CartItemCmdImpl.class));
    	when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
    	when(mockRefundService.addTravelCardToCart(any(CartDTO.class), any(CartCmdImpl.class), any(String.class))).thenReturn(getTestCartDTO1());
    	CartCmdImpl testCart = RefundTestUtil.buildCommandObject();
    	
        ModelAndView result = controller.addTravelCard(mockHttpSession, testCart, mockBindingResult);
        
        assertViewName(result, INV_STOLEN_REFUND);
        assertModelAttributeAvailable(result, CART_DTO);
    }

    @Test
    public void deleteTravelCardShouldReturnStolenRefundView() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        CartDTO testCartDto = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.deleteItem((CartDTO) anyObject(), anyLong())).thenReturn(testCartDto);

        ModelAndView result = controller.deleteTravelCard(mockHttpSession, CartCmdTestUtil.getTestCartCmd11(), CART_ITEM_ID);
        
        assertViewName(result, INV_STOLEN_REFUND);
        verify(mockCartService).deleteItem(any(CartDTO.class), anyLong());
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(testCartDto);
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
    public void addTradedTravelCardShouldReturnStolenCardRefundCartView() {
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());
        when(mockRefundService.updateDateOfRefund(any(CartDTO.class), any(Date.class))).thenReturn(getTestCartDTO1());
        doNothing().when(mockNewCartAdministrationService).addOrRemoveAdministrationFeeToCart((CartDTO) anyObject(), anyLong(), anyString());
        doNothing().when(mockTradedTicketValidator).validate(any(CartCmdImpl.class), any(BindingResult.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        
        CartCmdImpl testCart = RefundTestUtil.buildCommandObject();
        List<CartItemCmdImpl> cartItemList = new ArrayList<CartItemCmdImpl>();
        CartItemCmdImpl cartItemCmd = testCart.getCartItemCmd();
        cartItemCmd.setPreviouslyExchanged(true);
        cartItemList.add(cartItemCmd);
        testCart.setCartItemList(cartItemList);
        ModelAndView result = controller.addTradedTicket(mockHttpSession, testCart, mockBindingResult, 1);
        assertViewName(result, INV_STOLEN_REFUND);
        assertModelAttributeAvailable(result, CART_DTO);
        verify(mockRefundService, atLeastOnce()).updateAdministrationFeeValueInCartCmdImpl(any(CartCmdImpl.class), any(CartDTO.class));
    }
    
    @Test
    public void initBinderShouldRegisterCustomerEditor() {
        ServletRequestDataBinder mockBinder = mock(ServletRequestDataBinder.class);
        doNothing().when(mockBinder).registerCustomEditor(any(Class.class), any(PropertyEditor.class));
        
        mockStolenCardRefundController.initBinder(mockBinder);
        
        verify(mockBinder).registerCustomEditor(any(Class.class), any(PropertyEditor.class));
    }
    
    @Test
    public void shouldPopulateModelAttributes() {
        when(mockModel.addAttribute(anyString(), any())).thenReturn(mockModel);
        when(mockRefundSelectListService.getSelectListModel(mockModel)).thenReturn(mockModel);
        when(mockCountrySelectListService.getSelectList()).thenReturn(null);
        
        controller.populateModelAttributes(mockModel);
        
        verify(mockRefundSelectListService).getSelectListModel(mockModel);
        verify(mockCountrySelectListService).getSelectList();
    }
    
    @Test
    public void addGoodwillCardShouldReturnStolenRefundCartView() {
        CartCmdImpl cartCmd = CartCmdTestUtil.getTestCartCmd4();
        cartCmd.setCartItemCmd(new CartItemCmdImpl());
        doNothing().when(mockGoodwillValidator).validate(any(), any(Errors.class));
        when(mockBindingResult.hasErrors()).thenReturn(true);
        
        ModelAndView result = controller.addGoodwill(mockHttpSession, cartCmd, mockBindingResult, mockModel);
        
        assertViewName(result, INV_STOLEN_REFUND);
        assertModelAttributeAvailable(result, CART);
    }
    
    @Test
    public void deleteGoodwillCardShouldReturnStolenRefundCartView() {
        CartDTO testCartDto = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(testCartDto);
        doNothing().when(mockStolenCardRefundController).setOysterCardsList(any(CartCmdImpl.class), any(ModelAndView.class), any(CartDTO.class));
        
        ModelAndView result = mockStolenCardRefundController.deleteGoodwill(mockHttpSession, CartCmdTestUtil.getTestCartCmd4(), LINE_NO, mockModel);
        
        verify(mockCartService).deleteItem(any(CartDTO.class), anyLong());
        verify(mockCartService).postProcessAndSortCartDTOAndRecalculateRefund(testCartDto);
        assertViewName(result, INV_STOLEN_REFUND);
        assertModelAttributeAvailable(result, CART);
    }
}
