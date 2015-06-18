package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.ContactDetailsValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.CustomerRegistrationValidator;
import com.novacroft.nemo.tfl.common.form_validator.UserCredentialsValidator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * CustomerRegistrationValidator unit tests
 */
public class CustomerRegistrationValidatorTest {
    private CustomerRegistrationValidator validator;
    private CustomerService mockCustomerService;
    private CartCmdImpl cmd;
    private AddressValidator mockAddressValidator;
    private ContactDetailsValidator ContactDetailsValidator;
    private CustomerNameValidator mockPersonalDetailsValidator;
    private UserCredentialsValidator mockUserCredentialsValidator;

    private static final String INVALID_EMAIL_ADDRESS = "test-1@novacroft";

    @Before
    public void setUp() {
        validator = new CustomerRegistrationValidator();
        ContactDetailsValidator = new ContactDetailsValidator();
        mockAddressValidator = mock(AddressValidator.class);
        mockPersonalDetailsValidator = mock(CustomerNameValidator.class);
        mockUserCredentialsValidator = mock(UserCredentialsValidator.class);
        cmd = new CartCmdImpl();
        mockCustomerService = mock(CustomerService.class);

        validator.customerService = mockCustomerService;
        validator.contactDetailsValidator = ContactDetailsValidator;
        validator.addressValidator = mockAddressValidator;
        validator.customerNameValidator = mockPersonalDetailsValidator;
        validator.userCredentialsValidator = mockUserCredentialsValidator;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(CartCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CommonOrderCardCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_1);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(" ");
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_1);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, never()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(null);
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_1);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, never()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithInvalidEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(INVALID_EMAIL_ADDRESS);
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_1);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, never()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyConfirmEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(" ");
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullConfirmEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(null);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithInvalidConfirmEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(INVALID_EMAIL_ADDRESS);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithDifferentEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_2);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithAlreadyUsedEmailAddress() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_1);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(true);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithAlreadyUsedUserName() {
        cmd.setHomePhone(VALUE_1);
        cmd.setEmailAddress(EMAIL_ADDRESS_1);
        cmd.setConfirmEmailAddress(EMAIL_ADDRESS_1);
        cmd.setUsername(USERNAME_1);

        when(mockCustomerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerService.isUsernameAlreadyUsed(anyString())).thenReturn(true);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);

        verify(mockCustomerService, atLeastOnce()).isEmailAddressAlreadyUsed(anyString());
        verify(mockCustomerService, atLeastOnce()).isUsernameAlreadyUsed(anyString());
        assertTrue(errors.hasErrors());
    }

}
