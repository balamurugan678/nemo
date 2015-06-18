package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.tfl.common.command.AddPaymentCardActionCmd;
import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;
import com.novacroft.nemo.tfl.common.form_validator.AddPaymentCardActionValidator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * AddPaymentCardActionValidator unit tests
 */
public class AddPaymentCardActionValidatorTest {
    private AddPaymentCardActionValidator validator;
    private Errors mockErrors;
    private AddPaymentCardActionCmd mockAddPaymentCardActionCmd;

    @Before
    public void setUp() {
        this.validator = mock(AddPaymentCardActionValidator.class);
        this.mockErrors = mock(Errors.class);
        this.mockAddPaymentCardActionCmd = mock(AddPaymentCardActionCmd.class);
    }

    @Test
    public void shouldSupportClass() {
        when(validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(AddPaymentCardActionCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(CancelAndSurrenderCmd.class));
    }

    @Test
    public void shouldValidate() {
        doCallRealMethod().when(validator).validate(any(), any(Errors.class));
        doCallRealMethod().when(validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        validator.validate(mockAddPaymentCardActionCmd, mockErrors);
        verify(validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
    }
    
    @Test
    public void instantiationTest(){
        assertNotNull(validator = new AddPaymentCardActionValidator());
    }
}
