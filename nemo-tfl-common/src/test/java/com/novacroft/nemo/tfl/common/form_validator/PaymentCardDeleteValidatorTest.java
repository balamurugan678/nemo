package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

/**
 * ManagePaymentCardValidator unit tests
 */
public class PaymentCardDeleteValidatorTest {
    private PaymentCardDeleteValidator validator;
    private Errors mockErrors;
    private PaymentCardCmdImpl mockPaymentCardCmd;
    private PaymentCardService mockPaymentCardService;
    private PaymentCardDTO mockPaymentCardDTO;

    @Before
    public void setUp() {
        this.validator = mock(PaymentCardDeleteValidator.class);

        this.mockPaymentCardService = mock(PaymentCardService.class);
        this.validator.paymentCardService = this.mockPaymentCardService;

        this.mockErrors = mock(Errors.class);
        this.mockPaymentCardCmd = mock(PaymentCardCmdImpl.class);
        this.mockPaymentCardDTO = mock(PaymentCardDTO.class);

        when(this.mockPaymentCardCmd.getPaymentCardDTO()).thenReturn(this.mockPaymentCardDTO);
        when(this.mockPaymentCardDTO.getId()).thenReturn(TEST_PAYMENT_CARD_ID_1);
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(PaymentCardCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(NewPasswordCmd.class));
    }

    @Test
    public void validateShouldPassWithCardNotInUse() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));

        when(this.mockPaymentCardService.isCardInUse(anyLong())).thenReturn(false);
        doNothing().when(this.mockErrors).reject(anyString());

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        verify(this.mockPaymentCardService).isCardInUse(anyLong());
        verify(this.mockErrors, never()).reject(anyString());
    }

    @Test
    public void validateShouldFailWithCardInUse() {
        doCallRealMethod().when(this.validator).validate(anyObject(), any(Errors.class));

        when(this.mockPaymentCardService.isCardInUse(anyLong())).thenReturn(true);
        doNothing().when(this.mockErrors).reject(anyString());

        this.validator.validate(this.mockPaymentCardCmd, this.mockErrors);

        verify(this.mockPaymentCardService).isCardInUse(anyLong());
        verify(this.mockErrors).reject(anyString());
    }
    
    @Test 
    public void instantiationTest(){
        validator = new PaymentCardDeleteValidator();
        assertNotNull(validator);
    }
}
