package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageView.INV_GOODWILL_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_STANDALONE_GOODWILL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.GoodwillPaymentService;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
@Deprecated
@Ignore
public class GoodwillRefundControllerTest {
    private GoodwillRefundController controller;
    private CartService mockCartService;
    private GoodwillService mockGoodwillService;
    private GoodwillValidator mockGoodwillValidator;
    private Model mockModel;
    private GoodwillPaymentService mockGoodwillPaymentService;

    @Before
    public void setUp() throws Exception {
        controller = new GoodwillRefundController();
        mockCartService = mock(CartService.class);
        mockGoodwillService = mock(GoodwillService.class);
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockModel = mock(Model.class);
        mockGoodwillPaymentService = mock(GoodwillPaymentService.class);

        controller.cartService = mockCartService;
        controller.goodwillService = mockGoodwillService;
        controller.goodwillValidator = mockGoodwillValidator;
        controller.goodwillPaymentService = mockGoodwillPaymentService;
    }

    @Test
    public void deleteGoodwillShouldReturnGoodwillRefundView() {
        ModelAndView result = controller.deleteGoodwill(CartCmdTestUtil.getTestCartCmd2(), 1, CartType.FAILED_CARD_REFUND.code(), mockModel);

        assertEquals(INV_GOODWILL_REFUND, result.getViewName());
    }

    @Test
    public void deleteGoodwillShouldReturnStandaloneGoodwillRefundViewForStandaloneGoodwillRefundCartType() {
        ModelAndView result = controller.deleteGoodwill(CartCmdTestUtil.getTestCartCmd2(), 1, CartType.STANDALONE_GOODWILL_REFUND.code(), mockModel);
        
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
    }

    @Test
    public void addGoodwillShouldReturnGoodwillRefundView() {
    	BindingResult bindingResult = mock(BindingResult.class);
        when(mockCartService.findNotInWorkFlowFlightCartByCustomerId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
    	
        ModelAndView result = controller.addGoodwill(CartCmdTestUtil.getTestCartCmd13(), bindingResult, CartType.FAILED_CARD_REFUND.code(), mockModel);
        
        assertEquals(INV_GOODWILL_REFUND, result.getViewName());
    }

    @Test
    public void addGoodwillShouldReturnStandaloneGoodwillRefundViewForStandaloneGoodwillRefundCartType() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
        
        ModelAndView result = controller.addGoodwill(CartCmdTestUtil.getTestCartCmd13(), bindingResult, CartType.STANDALONE_GOODWILL_REFUND.code(), mockModel);
        
        assertEquals(INV_STANDALONE_GOODWILL, result.getViewName());
    }
}
