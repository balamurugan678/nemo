package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.AnonymousGoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;
@RunWith(MockitoJUnitRunner.class)
public class AnonymousGoodwillRefundGoodwillControllerTest {
    private AnonymousGoodwillRefundGoodwillController controller;
    private CartCmdImpl mockCmd;
    private BindingResult mockBindingResult;
    private CartService mockCartService;
    private GoodwillValidator mockGoodwillValidator;;
    private HttpSession mockSession;
    private RefundSelectListService mockRefundSelectListService;
    @Mock
    protected RefundPaymentService  mockRefundPaymentService;
    @Mock
    protected RefundPaymentService refundPaymentService;
    @Mock
    protected WorkFlowService mockWorkFlowService;
    @Mock
    protected WorkFlowGeneratorStrategy mockWorkFlowGeneratorStrategy; 

    @Before
    public void setUp() throws Exception {

        controller = new AnonymousGoodwillRefundGoodwillController();

        mockCmd = mock(CartCmdImpl.class);
        mockCartService = mock(CartService.class);
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockSession = mock(HttpSession.class);
        mockBindingResult = mock(BindingResult.class);
        mockRefundSelectListService = mock(RefundSelectListService.class);

        controller.workFlowService= mockWorkFlowService;
        controller.cartService = mockCartService;
        controller.goodwillValidator = mockGoodwillValidator;
        controller.refundSelectListService = mockRefundSelectListService;
        controller.refundPaymentService = mockRefundPaymentService;
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
    }

    @Test
    public void populateModelAttributesShouldPopulateModelWithPaymentTypes() {
        Model model = new BindingAwareModelMap();
        when(mockRefundSelectListService.getAnonymousGoodwillSelectListModel(any(Model.class))).thenReturn(new BindingAwareModelMap());
        controller.populateModelAttributes(model);
        assertNotNull(model);
    }

    @Test
    public void viewCartShouldReturnAnonymousGoodwillRefundGoodwillView() {
        ModelAndView result = controller.viewCart(CardTestUtil.getTestCardObject1().getCardNumber(),
        		CartCmdTestUtil.getTestCartCmd2(), mockBindingResult);
        assertEquals(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, result.getViewName());
    }

    @Test
    public void continueCartShouldNotReturnNullView() {
    	controller = mock(AnonymousGoodwillRefundGoodwillController.class);
		mockCmd = mock(CartCmdImpl.class);
	    mockCartService = mock(CartService.class);
	    mockGoodwillValidator = mock(GoodwillValidator.class);
	    mockSession = mock(HttpSession.class);
	    mockBindingResult = mock(BindingResult.class);
	    mockRefundSelectListService = mock(RefundSelectListService.class);
	    controller.refundPaymentService  = refundPaymentService;
	   
	    
        BindingResult bindingResult = mock(BindingResult.class);
        controller.anonymousGoodwillValidator = new AnonymousGoodwillValidator();
        CartCmdImpl cartCmdImpl =   CartCmdTestUtil.getTestCartCmd2();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockSession)).thenReturn(new CartDTO());
        doCallRealMethod().when(controller).continueCart(mockSession, cartCmdImpl, bindingResult);
        ModelAndView result = controller.continueCart(mockSession, cartCmdImpl, bindingResult);
        assertNotNull(result.getViewName());
    }
    
    @Test
    public void continueCartShouldReturnNullView() {
    	controller = mock(AnonymousGoodwillRefundGoodwillController.class);
		mockCmd = mock(CartCmdImpl.class);
	    mockCartService = mock(CartService.class);
	    mockGoodwillValidator = mock(GoodwillValidator.class);
	    mockSession = mock(HttpSession.class);
	    mockBindingResult = mock(BindingResult.class);
	    mockRefundSelectListService = mock(RefundSelectListService.class);
	    controller.refundPaymentService  = refundPaymentService;
        BindingResult bindingResult = mock(BindingResult.class);
        controller.anonymousGoodwillValidator = new AnonymousGoodwillValidator();
        controller.workFlowService= mockWorkFlowService;
	    controller.workFlowGeneratorStrategy = mockWorkFlowGeneratorStrategy;
        CartCmdImpl cartCmdImpl =   CartCmdTestUtil.getTestCartCmd2();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(controller.getCartDTOUsingCartSessionDataDTOInSession(mockSession)).thenReturn(new CartDTO());
        doCallRealMethod().when(controller).continueCart(mockSession, cartCmdImpl, bindingResult);
        ModelAndView result = controller.continueCart(mockSession, cartCmdImpl, bindingResult);
        assertNull(result.getViewName());
    }

    @Test
    public void addGoodwillShouldReturnAnonymousGoodwillRefundGoodwillViewAndAddGoodWillItemToCart() {
        when(mockCartService.createCart()).thenReturn(new CartDTO());
        when(mockCartService.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(GoodwillPaymentItemDTO.class))).thenReturn(getNewCartDTOWithItem());
        doNothing().when(mockGoodwillValidator).validate(mockCmd, mockBindingResult);

        ModelAndView result = controller.addGoodwill(mockSession, CartCmdTestUtil.getTestRenewCartCmd3(), mockBindingResult);

        verify(mockCartService, atLeastOnce()).createCart();
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(GoodwillPaymentItemDTO.class));
        verify(mockGoodwillValidator, atLeastOnce()).validate(any(CartDTO.class), any(BindingResult.class));

        assertEquals(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, result.getViewName());
        assertNotNull(result.getModel().get(CART));
        assertNotNull(result.getModel().get(CART_DTO));
    }

    @Test
    public void deleteGoodwillShouldReturnAnonymousGoodwillRefundGoodwillViewAndDeleteGoodWillItemFromCart() {
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(getNewCartDTOWithItem());
        ModelAndView result = controller.deleteGoodwill(mockSession, CartCmdTestUtil.getTestCartCmd2(), 0);
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());
        assertEquals(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, result.getViewName());
        assertNotNull(result.getModel().get(CART));
        assertNotNull(result.getModel().get(CART_DTO));
    }

}
