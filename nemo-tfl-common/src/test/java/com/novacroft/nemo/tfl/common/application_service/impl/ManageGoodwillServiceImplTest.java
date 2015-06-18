package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CartTestUtil.getTestCartDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.GoodwillReasonTestUtil;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.GoodwillReasonType;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public class ManageGoodwillServiceImplTest {
    private ManageGoodwillServiceImpl service;
    private CartCmdImpl cartCmdImpl;
    private CartService mockCartService;
    private GoodwillValidator mockGoodwillValidator;
    private GoodwillService mockGoodwillService;
    private CartDTO mockCartDTO;
    private CartItemCmdImpl mockCartItemCmdImpl;
    private BindingResult mockBindingResult;
    private Model model;

    @Before
    public void setUp() {
        service = new ManageGoodwillServiceImpl();
        cartCmdImpl = new CartCmdImpl();
        model = new BindingAwareModelMap();

        mockCartService = mock(CartService.class);
        mockGoodwillValidator = mock(GoodwillValidator.class);
        mockGoodwillService = mock(GoodwillService.class);
        mockBindingResult = mock(BindingResult.class);
        mockCartDTO = mock(CartDTO.class);
        mockCartItemCmdImpl = mock(CartItemCmdImpl.class);

        service.cartService = mockCartService;
        service.goodwillService = mockGoodwillService;
        service.goodwillValidator = mockGoodwillValidator;
    }

    @Test
    public void addGoodwillShouldCallNewCartServiceAndReturnCartCmdImpl() {
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(new SelectListDTO());
        when(mockGoodwillService.getGoodwillRefundExtraValidationMessages(anyString())).thenReturn(GoodwillReasonTestUtil.EXTRA_VALIDATION_CODE);
        doNothing().when(mockGoodwillValidator).validate(cartCmdImpl, mockBindingResult);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockCartService.addItem(mockCartDTO, mockCartItemCmdImpl, GoodwillPaymentItemDTO.class)).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());

        CartCmdImpl resultCart = service.addGoodwill(CartCmdTestUtil.getTestRenewCartCmd3(), mockBindingResult, CartType.STANDALONE_GOODWILL_REFUND.code(), model);

        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundTypes(anyString());
        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundExtraValidationMessages(anyString());
        verify(mockGoodwillValidator, atLeastOnce()).validate(any(CartCmdImpl.class), any(BindingResult.class));
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(GoodwillPaymentItemDTO.class));
        verify(mockCartService, atLeastOnce()).findById(anyLong());

        assertEquals(getTestCartDTO1().getCardId(), resultCart.getCartDTO().getCardId());
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES));
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES));

    }

    @Test
    public void addGoodwillShouldNotCallNewCartServiceWithValidationErrorsAndReturnCartCmdImpl() {
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(new SelectListDTO());
        when(mockGoodwillService.getGoodwillRefundExtraValidationMessages(anyString())).thenReturn(GoodwillReasonTestUtil.EXTRA_VALIDATION_CODE);
        doNothing().when(mockGoodwillValidator).validate(cartCmdImpl, mockBindingResult);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        CartCmdImpl resultCart = service.addGoodwill(CartCmdTestUtil.getTestRenewCartCmd3(), mockBindingResult, CartType.STANDALONE_GOODWILL_REFUND.code(), model);

        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundTypes(anyString());
        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundExtraValidationMessages(anyString());
        verify(mockGoodwillValidator, atLeastOnce()).validate(any(CartCmdImpl.class), any(BindingResult.class));
        verify(mockCartService, never()).findById(anyLong());

        assertEquals(CartType.STANDALONE_GOODWILL_REFUND.code(), resultCart.getCartType());
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES));
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES));

    }

    @Test
    public void addGoodwillShouldCallNewCartServiceAndReturnCartCmdImplForAnonymousGoodwillRefund() {
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(new SelectListDTO());
        when(mockGoodwillService.getGoodwillRefundExtraValidationMessages(anyString())).thenReturn(GoodwillReasonTestUtil.EXTRA_VALIDATION_CODE);
        doNothing().when(mockGoodwillValidator).validate(cartCmdImpl, mockBindingResult);
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockCartService.addItem(mockCartDTO, mockCartItemCmdImpl, GoodwillPaymentItemDTO.class)).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
        when(mockCartService.findById(anyLong())).thenReturn(getTestCartDTO1());

        CartCmdImpl resultCart = service.addGoodwill(CartCmdTestUtil.getTestRenewCartCmd3(), mockBindingResult, CartType.ANONYMOUS_GOODWILL_REFUND.code(), model);

        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundTypes(GoodwillReasonType.OYSTER.code());
        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundExtraValidationMessages(GoodwillReasonType.OYSTER.code());
        verify(mockGoodwillValidator, atLeastOnce()).validate(any(CartCmdImpl.class), any(BindingResult.class));
        verify(mockCartService, atLeastOnce()).addItem(any(CartDTO.class), any(CartItemCmdImpl.class), eq(GoodwillPaymentItemDTO.class));
        verify(mockCartService, atLeastOnce()).findById(anyLong());

        assertEquals(getTestCartDTO1().getCardId(), resultCart.getCartDTO().getCardId());
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES));
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES));

    }

    @Test
    public void addGoodwillShouldNotCallNewCartServiceWithValidationErrorsAndReturnCartCmdImplForAnonymousGoodwillRefund() {
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(new SelectListDTO());
        when(mockGoodwillService.getGoodwillRefundExtraValidationMessages(anyString())).thenReturn(GoodwillReasonTestUtil.EXTRA_VALIDATION_CODE);
        doNothing().when(mockGoodwillValidator).validate(cartCmdImpl, mockBindingResult);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        CartCmdImpl resultCart = service.addGoodwill(CartCmdTestUtil.getTestRenewCartCmd3(), mockBindingResult, CartType.ANONYMOUS_GOODWILL_REFUND.code(), model);

        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundTypes(GoodwillReasonType.OYSTER.code());
        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundExtraValidationMessages(GoodwillReasonType.OYSTER.code());
        verify(mockGoodwillValidator, atLeastOnce()).validate(any(CartCmdImpl.class), any(BindingResult.class));
        verify(mockCartService, never()).findById(anyLong());

        assertEquals(CartType.ANONYMOUS_GOODWILL_REFUND.code(), resultCart.getCartType());
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES));
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES));

    }

    @Test
    public void deleteGoodwillShouldCallNewCartServiceAndReturnCartCmdImpl() {
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(new SelectListDTO());
        when(mockGoodwillService.getGoodwillRefundExtraValidationMessages(anyString())).thenReturn(GoodwillReasonTestUtil.EXTRA_VALIDATION_CODE);
        when(mockCartService.deleteItem(any(CartDTO.class), anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithPayAsYouGoItem());

        CartCmdImpl resultCart = service.deleteGoodwill(CartCmdTestUtil.getTestRenewCartCmd3(), CartTestUtil.CART_ID_1, CartType.STANDALONE_GOODWILL_REFUND.code(), model);

        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundTypes(anyString());
        verify(mockGoodwillService, atLeastOnce()).getGoodwillRefundExtraValidationMessages(anyString());
        verify(mockCartService, atLeastOnce()).deleteItem(any(CartDTO.class), anyLong());

        assertEquals(CartTestUtil.getNewCartDTOWithPayAsYouGoItem().getCardId(), resultCart.getCartDTO().getCardId());
        assertEquals(CartType.STANDALONE_GOODWILL_REFUND.code(), resultCart.getCartType());
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES));
        assertTrue(model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES));

    }

}
