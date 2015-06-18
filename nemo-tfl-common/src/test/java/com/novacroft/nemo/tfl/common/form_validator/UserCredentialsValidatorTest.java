package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.command.UserCredentialsCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.form_validator.NewPasswordValidator;
import com.novacroft.nemo.tfl.common.form_validator.UserCredentialsValidator;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * UserCredentialsValidator unit tests
 */
public class UserCredentialsValidatorTest {
    @Test
    public void shouldSupportClass() {
        UserCredentialsValidator validator = new UserCredentialsValidator();
        assertTrue(validator.supports(UserCredentialsCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        UserCredentialsValidator validator = new UserCredentialsValidator();
        assertFalse(validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidate() {
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setUsername(USERNAME_1);

        NewPasswordValidator mockNewPasswordValidator = mock(NewPasswordValidator.class);
        doNothing().when(mockNewPasswordValidator).validate(any(UserCredentialsCmd.class), any(Errors.class));

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        UserCredentialsValidator validator = new UserCredentialsValidator();
        validator.newPasswordValidator = mockNewPasswordValidator;

        validator.validate(cmd, errors);

        assertFalse(errors.hasErrors());
        verify(mockNewPasswordValidator).validate(any(UserCredentialsCmd.class), any(Errors.class));
    }

    @Test
    public void shouldNotValidateWithEmptyUsername() {
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setUsername("  ");

        NewPasswordValidator mockNewPasswordValidator = mock(NewPasswordValidator.class);
        doNothing().when(mockNewPasswordValidator).validate(any(UserCredentialsCmd.class), any(Errors.class));

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");

        UserCredentialsValidator validator = new UserCredentialsValidator();
        validator.newPasswordValidator = mockNewPasswordValidator;

        validator.validate(cmd, errors);

        assertTrue(errors.hasErrors());
        verify(mockNewPasswordValidator).validate(any(UserCredentialsCmd.class), any(Errors.class));
    }
}
