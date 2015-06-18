package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO3;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;

/**
 * Cart validator unit tests
 */
public class DeactivatedWebAccountOnlineLoginValidatorTest {
    private DeactivatedWebAccountOnlineLoginValidator validator;
    private LoginCmdImpl cmd;
    private CustomerDataService mockCustomerDataService;
    
    @Before
    public void setUp() {
        validator = new DeactivatedWebAccountOnlineLoginValidator();
        cmd = new LoginCmdImpl();
        mockCustomerDataService = mock(CustomerDataService.class);
        
        validator.customerDataService = mockCustomerDataService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(LoginCmdImpl.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(CartCmdImpl.class));
    }
    
    @Test
    public void shouldValidateIfWebAccountActivate() {
        cmd.setUsername(USERNAME_1);
        
        when(mockCustomerDataService.findByUsernameOrEmail(USERNAME_1)).thenReturn(getTestCustomerDTO1());
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        
        validator.validate(cmd, errors);
        
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateIfWebAccountDeactivate() {
        cmd.setUsername(USERNAME_1);
        
        when(mockCustomerDataService.findByUsernameOrEmail(USERNAME_1)).thenReturn(getTestCustomerDTO3());
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        
        validator.validate(cmd, errors);
        
        assertTrue(errors.hasErrors());
    }
}
