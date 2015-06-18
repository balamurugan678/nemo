package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CartTestUtil.getAnnualBusPassCartDTO;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_ADDED;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_DATE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * BusPassValidator unit tests
 */
public class BusPassValidatorTest {
    private BusPassValidator validator;
    private CartItemCmdImpl cmd;
    private CartService mockCartService;

    private static final String START_DATE = "21/12/2013";

    @Before
    public void setUp() {
        validator = new BusPassValidator();
        cmd = new CartItemCmdImpl();
        mockCartService = mock(CartService.class);

        validator.cartService = mockCartService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(BusPassCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldValidate() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        cmd.setStartDate(START_DATE);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullStartDate() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        cmd.setStartDate(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void sholdNotValidateNullCartItemCmd() {
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(null, errors);

        verify(mockCartService, never()).findById(anyLong());
    }

    @Test
    public void shouldNotValidateItemDtoIsAnnualBusPass() {
        when(mockCartService.findById(anyLong())).thenReturn(getAnnualBusPassCartDTO());
        cmd.setStartDate(null);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateStartDateErrors() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        cmd.setStartDate("");
        cmd.setEndDate("");
        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(false);
        when(errors.hasFieldErrors(FIELD_START_DATE)).thenReturn(true);
        validator.validate(cmd, errors);
        verify(errors, never()).reject(ALREADY_ADDED.errorCode());
    }

    @Test
    public void isItemDtoAnAnnualBusPassNullName() {
        ProductItemDTO itemDTO = new ProductItemDTO();
        itemDTO.setName(null);
        assertFalse(validator.isItemDTOAnAnnualBusPass(itemDTO));
    }

    @Test
    public void isItemDtoAnAnnualBusPassWrongName() {
        ProductItemDTO itemDTO = new ProductItemDTO();
        itemDTO.setName("Test");
        assertFalse(validator.isItemDTOAnAnnualBusPass(itemDTO));
    }
}
