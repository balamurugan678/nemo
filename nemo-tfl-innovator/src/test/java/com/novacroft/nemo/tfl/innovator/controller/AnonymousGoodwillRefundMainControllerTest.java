package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO2;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
@RunWith(MockitoJUnitRunner.class)
public class AnonymousGoodwillRefundMainControllerTest {
    private AnonymousGoodwillRefundMainController controller;
    private BindingResult mockBindingResult;
    private OysterCardValidator mockOysterCardValidator;
    private CardDataService mockCardDataService;
    private RefundService mockRefundService;
    private RefundSelectListService mockRefundSelectListService;
    private ServletRequestDataBinder mockServletRequestBinder;
    @Mock
    protected  HttpSession mockSession;
    @Mock
    protected RedirectAttributes mockRedirectAttributes;

    @Before
    public void setUp() throws Exception {
        controller = new AnonymousGoodwillRefundMainController();

        mockBindingResult = mock(BindingResult.class);
        mockOysterCardValidator = mock(OysterCardValidator.class);
        mockCardDataService = mock(CardDataService.class);
        mockRefundService = mock(RefundService.class);
        mockRefundSelectListService = mock(RefundSelectListService.class);
        mockServletRequestBinder = mock(ServletRequestDataBinder.class);

        controller.oysterCardValidator = mockOysterCardValidator;
        controller.cardDataService = mockCardDataService;
        controller.refundService = mockRefundService;
        controller.refundSelectListService = mockRefundSelectListService;
    }

    
    @Test
    public void initBinderShouldRegisterCustomEditor() {
        doNothing().when(mockServletRequestBinder).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
        controller.initBinder(mockServletRequestBinder);
        verify(mockServletRequestBinder, atLeastOnce()).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
    }
    
    @Test
    public void populateModelAttributesShouldPopulateModelWithPaymentTypesAndRefundTypes() {
        Model model = new BindingAwareModelMap();
        when(mockRefundSelectListService.getSelectListModel(any(Model.class))).thenReturn(new BindingAwareModelMap());
        controller.populateModelAttributes(model);
        assertNotNull(model);
    }

    @Test
    public void viewCartShouldReturnAnonymousGoodwillRefundGoodwillView() {
        
        CartCmdImpl testCartCmdImpl =   CartCmdTestUtil.getTestCartCmd2();
        testCartCmdImpl.getCartDTO().setCardId(1l);
        when(mockRefundService.createCartCmdImplWithCartDTO()).thenReturn(testCartCmdImpl);
        ModelAndView result = controller.viewCart(mockSession);
        assertEquals(PageView.INV_ANONYMOUS_GOODWILL_REFUND_MAIN, result.getViewName());
    }

    @Test
    public void continueCartShouldReturnNullViewIfValidationPasses() {
        doNothing().when(mockOysterCardValidator).validate(anyObject(), any(Errors.class));
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(getTestCardDTO1());
        when(mockBindingResult.hasErrors()).thenReturn(false);

        ModelAndView result = controller.continueCart(CartCmdTestUtil.getTestCartCmd2(), mockBindingResult, mockRedirectAttributes);
        
        assertNull(result.getViewName());
        assertEquals(PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL + "?" + PageAttribute.CARD_NUMBER + "=null", ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void shouldReturnToRefundPageIfValidationFails() {
        doNothing().when(mockOysterCardValidator).validate(anyObject(), any(Errors.class));
        when(mockCardDataService.findByCardNumber(OYSTER_NUMBER_1)).thenReturn(getTestCardDTO2());
        when(mockBindingResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.continueCart(CartCmdTestUtil.getTestCartCmd2(), mockBindingResult, mockRedirectAttributes);
        
        assertNotNull(result.getViewName());
        assertEquals(PageView.INV_ANONYMOUS_GOODWILL_REFUND_MAIN, result.getViewName());
    }
}
