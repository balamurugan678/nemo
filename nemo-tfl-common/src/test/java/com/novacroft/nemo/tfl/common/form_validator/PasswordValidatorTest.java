package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

public class PasswordValidatorTest {
    
    private PasswordValidator validator;
    private CurrentPasswordValidator currentPasswordValidator;
    private NewPasswordValidator newPasswordValidator;
    private Errors errors;
    private CartCmdImpl cmd;

    @Before
    public void setUp() throws Exception {
        validator = new PasswordValidator();
        cmd = new CartCmdImpl();
        currentPasswordValidator = mock(CurrentPasswordValidator.class);
        newPasswordValidator = new NewPasswordValidator();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
        
        validator.currentPasswordValidator = currentPasswordValidator;
        validator.newPasswordValidator = newPasswordValidator;
    }

    @Test
    public void supportsClass() {
        assertTrue(validator.supports(NewPasswordCmd.class));
    }
    
    @Test 
    public void doesNotSupportClass(){
        assertFalse(validator.supports(BusPassCmd.class));
    }
    
    @Test
    public void shouldValidate(){
        cmd.setUsername(USERNAME_1);
        cmd.setNewPassword(VALID_PASSWORD);
        cmd.setNewPasswordConfirmation(VALID_PASSWORD);
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidate(){
        cmd.setUsername(" ");
        cmd.setNewPassword("12");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

}
