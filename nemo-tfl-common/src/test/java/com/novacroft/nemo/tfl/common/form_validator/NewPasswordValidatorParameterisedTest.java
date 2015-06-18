package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertEquals;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

@RunWith(Parameterized.class)
public class NewPasswordValidatorParameterisedTest {
    
    private NewPasswordValidator validator;
    private Errors errors;
    private CartCmdImpl cmd;
    
    private String newPassword;
    private String newPasswordConfirmation;
    private boolean hasErrors;

    @Before
    public void setUp() throws Exception {
        validator = new NewPasswordValidator();
        cmd = new CartCmdImpl();
        errors = new BeanPropertyBindingResult(cmd, "cmd");
    }
    
    public NewPasswordValidatorParameterisedTest(String testName, String newPassword, String newPasswordConfirmation, boolean hasErrors) {
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
        this.hasErrors = hasErrors;
    }
    
    @Parameters(name="{index}: test{0} expected={1}")
    public static Collection<?> teetParameters(){
        return Arrays.asList(new Object[][]{
                        {"shoudValidate", VALID_PASSWORD, VALID_PASSWORD, false},
                        {"shouldNotValidateEmptyPassword", "", VALID_PASSWORD, true},
                        {"shouldNotValidatePasswordsDoNotMatch", " 1", "2 ", true},
                        {"shouldNotValidateBlankConfirmationPassword", VALID_PASSWORD, "", true},
                        {"shouldNotValdidatePasswordTooShort", SHORT_PASSWORD, SHORT_PASSWORD, true},
                        {"shouldNotValidatePasswordDoesNotContainAnUpperCase", PASSWORD_1, PASSWORD_1, true},
                        {"shouldNotValidatePasswordDoesNotcontainALowerCase", UPPERCASE_PASSWORD,UPPERCASE_PASSWORD, true},
                        {"shouldNotValidatePasswordDoesNotcontainADigit", NO_DIGIT_PASSWORD,NO_DIGIT_PASSWORD, true},
                        {"shouldNotValidatePasswordDoesContainSpecialCharacters", SPECIAL_CHARACTER_PASSWORD,SPECIAL_CHARACTER_PASSWORD, true}
                        
        });
    }

    @Test
    public void validate() {
        cmd.setNewPassword(this.newPassword);
        cmd.setNewPasswordConfirmation(this.newPasswordConfirmation);
        
        validator.validate(cmd, errors);
        
        assertEquals(this.hasErrors, errors.hasErrors());
    }

}
