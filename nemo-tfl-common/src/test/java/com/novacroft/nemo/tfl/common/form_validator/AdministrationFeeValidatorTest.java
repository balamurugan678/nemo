package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINSTRATION_FEE_GREATER_THAN_ZERO_VALUE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINSTRATION_FEE_NEGATIVE_VALUE;
import static com.novacroft.nemo.tfl.common.constant.RefundType.FAILED_CARD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * GoodwillValidator unit tests
 */
public class AdministrationFeeValidatorTest {

    static final Logger logger = LoggerFactory.getLogger(AdministrationFeeValidatorTest.class);

    private AdministrationFeeValidator validator;
    private CartCmdImpl mockCartCmdImpl;
    private CartDTO mockCartDTO;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        validator = new AdministrationFeeValidator();
        mockCartCmdImpl = mock(CartCmdImpl.class);
        mockCartDTO = mock(CartDTO.class);
        mockSystemParameterService = mock(SystemParameterService.class);
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldValidateIfAdministrationFeeAmountIsGreaterThanZero() {
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(ADMINSTRATION_FEE_GREATER_THAN_ZERO_VALUE);
        when(mockCartCmdImpl.getAdministrationFeeValue()).thenReturn(ADMINSTRATION_FEE_GREATER_THAN_ZERO_VALUE);
        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");
        mockCartCmdImpl.setAdministrationFeeValue(ADMINSTRATION_FEE_GREATER_THAN_ZERO_VALUE);
        validator.validate(mockCartCmdImpl, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldValidateIfAdministrationFeeAmountIsLessThanZero() {
        when(mockCartCmdImpl.getCartType()).thenReturn(FAILED_CARD.code());
        when(mockCartCmdImpl.getCartDTO()).thenReturn(mockCartDTO);
        when(mockCartCmdImpl.getAdministrationFeeValue()).thenReturn(ADMINSTRATION_FEE_NEGATIVE_VALUE);
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(ADMINSTRATION_FEE_NEGATIVE_VALUE);
        Errors errors = new BeanPropertyBindingResult(mockCartCmdImpl, "cmd");
        mockCartCmdImpl.setAdministrationFeeValue(ADMINSTRATION_FEE_NEGATIVE_VALUE);
        validator.validate(mockCartCmdImpl, errors);
        assertTrue(errors.hasErrors());
    } 
}
