package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.WebAccountTestUtil.EMAIL_ADDRESS_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.EmailAddressCmd;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * ExistingEmailAddressValidator unit tests
 */
public class ExistingEmailAddressValidatorTest {
    private ExistingEmailAddressValidator validator;
    private EmailAddressCmd mockEmailAddressCmd;
    private Errors mockErrors;
    private CustomerDataService mockCustomerDataService;
    private CustomerDTO mockCustomerDTO;

    @Before
    public void beforeTest() {
        this.validator = mock(ExistingEmailAddressValidator.class);
        this.mockEmailAddressCmd = mock(EmailAddressCmd.class);
        this.mockErrors = mock(Errors.class);
        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(this.validator.supports(EmailAddressCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(this.validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidateExistingEmailAddress() {
        when(mockEmailAddressCmd.getEmailAddress()).thenReturn(EMAIL_ADDRESS_1);
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        when(this.mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(this.mockCustomerDTO);
        this.validator.customerDataService = mockCustomerDataService;
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        doNothing().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());

        this.validator.validate(this.mockEmailAddressCmd, this.mockErrors);

        verify(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        verify(this.mockErrors, never()).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        verify(this.mockCustomerDataService).findByUsernameOrEmail(anyString());
    }

    @Test
    public void shouldNotValidateNonExistingEmailAddress() {
        when(mockEmailAddressCmd.getEmailAddress()).thenReturn(EMAIL_ADDRESS_1);
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        when(this.mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(null);
        this.validator.customerDataService = mockCustomerDataService;
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        doNothing().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());

        this.validator.validate(this.mockEmailAddressCmd, this.mockErrors);

        verify(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
        verify(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        verify(this.mockCustomerDataService).findByUsernameOrEmail(anyString());
    }
    
    @Test
    public void shouldNotValidateNoEmptyEmailAddress() {
        when(mockEmailAddressCmd.getEmailAddress()).thenReturn("");
        doNothing().when(this.mockErrors).rejectValue(anyString(), anyString(), any(Object[].class), anyString());
        when(this.mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(null);
        this.validator.customerDataService = mockCustomerDataService;
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        doNothing().when(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());

        this.validator.validate(this.mockEmailAddressCmd, this.mockErrors);

        verify(this.validator).rejectIfMandatoryFieldEmpty(any(Errors.class), anyString());
    }
    
    @Test
    public void instantiationTest(){
        this.validator = new ExistingEmailAddressValidator();
        assertNotNull(this.validator);
    }
}
