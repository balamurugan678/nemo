package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.command.SelectStationCmd;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * Unit tests for SelectStationValidator
 */
public class SelectStationValidatorTest {

    private SelectStationValidator validator;
    private Errors errors;
    private Errors mockErrors;
    private CardPreferencesCmdImpl cmd;
    private CartDTO cartDTO; 
    private CartDTO mockCartDTO; 

    @Before
    public void before() {
        validator = new SelectStationValidator();
        cmd = new CardPreferencesCmdImpl();
        cartDTO = new CartDTO();
        mockCartDTO = mock(CartDTO.class);
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        mockErrors = mock(Errors.class);
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(SelectStationCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setStationId(1L);
        validator.validate(cartDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidate() {
        cmd.setStationId(null);
        validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateIfPickUpLocationIsDifferentFromPPVLocation() {
        cartDTO.addCartItem(CartTestUtil.getTestItemDTOWithAdminFee());
        cartDTO.setPpvPickupLocationAddFlag(true);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldRejectIfPickUpStationIsDifferentFromPendingItemLocation() {
        when(mockCartDTO.isPpvPickupLocationAddFlag()).thenReturn(true);
        doNothing().when(mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        validator.rejectIfPickUpStationIsDifferentFromPendingItemLocation(mockCartDTO, mockErrors);
        verify(mockErrors, atLeastOnce()).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }

    @Test
    public void shouldNotRejectIfPickUpStationIsDifferentFromPendingItemLocation() {
        when(mockCartDTO.isPpvPickupLocationAddFlag()).thenReturn(false);
        validator.rejectIfPickUpStationIsDifferentFromPendingItemLocation(mockCartDTO, mockErrors);
        verify(mockErrors, never()).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
    }
}
