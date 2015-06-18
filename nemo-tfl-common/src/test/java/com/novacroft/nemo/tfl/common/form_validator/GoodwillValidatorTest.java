package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANONYMOUS_GOODWILL_MAX_REFUND_AMOUNT;
import static com.novacroft.nemo.test_support.CartItemTestUtil.GOODWILL_AMOUNT;
import static com.novacroft.nemo.test_support.CartItemTestUtil.GOODWILL_AMOUNT_NEGATIVE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.GOODWILL_AMOUNT_ZERO;
import static com.novacroft.nemo.test_support.CartItemTestUtil.GOODWILL_OTHER_TEXT;
import static com.novacroft.nemo.test_support.CartItemTestUtil.GOODWILL_PAYMENT_ID_2;
import static com.novacroft.nemo.test_support.CartTestUtil.GOODWILL_REASON_ID;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithGoodwillItem;
import static com.novacroft.nemo.test_support.SelectListTestUtil.TEST_OPTION_OYSTER_GOODWILL_REASON_OTHER_VALUE;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO3;
import static com.novacroft.nemo.tfl.common.constant.RefundType.FAILED_CARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.GoodwillReasonType;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * GoodwillValidator unit tests
 */
public class GoodwillValidatorTest {

    private static final String CART_TYPE_OTHER_REFUND = "Other";

    static final Logger logger = LoggerFactory.getLogger(GoodwillValidatorTest.class);

    private GoodwillValidator validator;
    private CartCmdImpl mockCartCmdImpl;
    private CartItemCmdImpl mockCartItemCmdImpl;
    private GoodwillService mockGoodwillService;
    private CartDTO mockCartDTO;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        validator = new GoodwillValidator();

        mockCartCmdImpl = mock(CartCmdImpl.class);
        mockCartItemCmdImpl = mock(CartItemCmdImpl.class);
        mockGoodwillService = mock(GoodwillService.class);
        mockCartDTO = mock(CartDTO.class);
        mockSystemParameterService = mock(SystemParameterService.class);

        validator.goodwillService = mockGoodwillService;
        validator.systemParameterService = mockSystemParameterService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(BusPassCmd.class));
    }

    @Test
    public void shouldNotValidateIfMultipleGoodwillPaymentForSameReason() {
        when(mockCartCmdImpl.getCartDTO()).thenReturn(getNewCartDTOWithGoodwillItem());
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPaymentId()).thenReturn(GOODWILL_REASON_ID);
        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");
        when(mockCartCmdImpl.getCartType()).thenReturn(CartType.STANDALONE_GOODWILL_REFUND.code());

        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPaymentId();

        errors.getFieldError().getCode();
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateWithOtherPaymentId() {
        when(mockCartCmdImpl.getCartDTO()).thenReturn(getNewCartDTOWithGoodwillItem());
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPaymentId()).thenReturn(new Long(9));
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(false);
        when(mockCartCmdImpl.getCartType()).thenReturn(CartType.STANDALONE_GOODWILL_REFUND.code());
        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPaymentId();

    }

    @Test
    public void shouldValidateIfMultipleGoodwillPaymentForSameReason() {
        when(mockCartCmdImpl.getCartDTO()).thenReturn(getNewCartDTOWithGoodwillItem());
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPaymentId()).thenReturn(GOODWILL_REASON_ID);
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(false);
        when(mockCartCmdImpl.getCartType()).thenReturn(CartType.STANDALONE_GOODWILL_REFUND.code());
        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPaymentId();

    }

    @Test
    public void shouldValidateIfGoodwillAmountIsGreaterThanZero() {
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPrice()).thenReturn(GOODWILL_AMOUNT);
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(ANONYMOUS_GOODWILL_MAX_REFUND_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");

        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPrice();
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfGoodwillAmountIsZero() {
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPrice()).thenReturn(GOODWILL_AMOUNT_ZERO);
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");

        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPrice();
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithErrors(){
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPrice()).thenReturn(GOODWILL_AMOUNT_ZERO);
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(true);
        
        validator.validate(mockCartCmdImpl, errors);
       
    }

    @Test
    public void shouldNotValidateIfGoodwillAmountIsNegative() {
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPrice()).thenReturn(GOODWILL_AMOUNT_NEGATIVE);
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(ANONYMOUS_GOODWILL_MAX_REFUND_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");

        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPrice();
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfGoodwillReasonOtherTextEmpty() {
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPrice()).thenReturn(GOODWILL_AMOUNT);
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());

        when(mockCartItemCmdImpl.getGoodwillPaymentId()).thenReturn(GOODWILL_PAYMENT_ID_2);
        when(mockCartItemCmdImpl.getGoodwillOtherText()).thenReturn(null);
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(ANONYMOUS_GOODWILL_MAX_REFUND_AMOUNT);

        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");

        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPrice();
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfGoodwillReasonOtherTextEmpty() {
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartItemCmdImpl.getGoodwillPrice()).thenReturn(GOODWILL_AMOUNT);
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());

        when(mockCartItemCmdImpl.getGoodwillPaymentId()).thenReturn(GOODWILL_PAYMENT_ID_2);
        when(mockCartItemCmdImpl.getGoodwillOtherText()).thenReturn(GOODWILL_OTHER_TEXT);
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(ANONYMOUS_GOODWILL_MAX_REFUND_AMOUNT);

        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");

        validator.validate(mockCartCmdImpl, errors);

        verify(mockCartCmdImpl, atLeastOnce()).getCartItemCmd();
        verify(mockCartItemCmdImpl, atLeastOnce()).getGoodwillPrice();
        assertTrue(errors.hasErrors());
    }

    @Test
    public void getGoodwillReasonTypeFromCartTypeTest1() {
        assertEquals("Non standalone goodwill refund", GoodwillReasonType.OYSTER.code(),
                        validator.getGoodwillReasonTypeFromCartType(CART_TYPE_OTHER_REFUND));
    }

    @Test
    public void getGoodwillReasonTypeFromCartTypeTest2() {
        assertEquals("Standalone goodwill refund", GoodwillReasonType.CONTACTLESS_PAYMENT_CARD.code(),
                        validator.getGoodwillReasonTypeFromCartType(CartType.STANDALONE_GOODWILL_REFUND.code()));
    }

    @Test
    public void isGoodwillRefundTypeOtherTest1() {
        when(mockGoodwillService.getGoodwillRefundTypes(anyString())).thenReturn(getTestSelectListDTO3());
        assertFalse(validator.isGoodwillRefundTypeOther(Long.parseLong(TEST_OPTION_OYSTER_GOODWILL_REASON_OTHER_VALUE) - 1, "1"));
    }

    @Test
    public void isGoodwillPaymentIdInItemDTOTest1() {
        assertFalse(validator.isGoodwillPaymentIdInItemDTO(null, null));
    }

    @Test
    public void isGoodwillPaymentIdInItemDTOTest2() {
        ItemDTO itemDTO = CartTestUtil.getNewGoodwillPaymentItemDTO();

        assertFalse(validator.isGoodwillPaymentIdInItemDTO(itemDTO, ItemTestUtil.ITEM_ID));
    }

    @Test
    public void rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTONullCartDTO() {
        when(mockCartCmdImpl.getCartDTO()).thenReturn(null);
        assertFalse(validator.rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTO(mockCartCmdImpl));
    }

    @Test
    public void rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTONullCartItems() {
        CartDTO cartDTO = getNewCartDTOWithGoodwillItem();
        cartDTO.setCartItems(null);
        when(mockCartCmdImpl.getCartDTO()).thenReturn(cartDTO);
        assertFalse(validator.rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTO(mockCartCmdImpl));
    
    }
    
    @Test
    public void rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTOGoodwillPaymentNull() {
        CartDTO cartDTO = getNewCartDTOWithGoodwillItem();
        when(mockCartCmdImpl.getCartDTO()).thenReturn(cartDTO);
        when(mockCartCmdImpl.getCartItemCmd()).thenReturn(mockCartItemCmdImpl);
        when(mockCartCmdImpl.getCartItemCmd().getGoodwillPaymentId()).thenReturn(null);
        assertFalse(validator.rejectIfMultipleGoodwillPaymentForSameReasonFromCartDTO(mockCartCmdImpl));
    }
}
