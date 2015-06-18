package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTOWithEmail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

@RunWith(Parameterized.class)
public class CommonCustomerRegistrationValidatorTest {

    private CommonCustomerRegistrationValidator validator;
    private CustomerDTO customerDTO;
    private Errors errors;
    private boolean hasErrors;
    private boolean isEmailAlreadyUsed;

    private CustomerService customerService;
    private CustomerNameValidator customerNameValidator;

    @Before
    public void setUp() throws Exception {
        validator = new CommonCustomerRegistrationValidator();
        customerService = mock(CustomerService.class);
        customerNameValidator = mock(CustomerNameValidator.class);
        errors = new BeanPropertyBindingResult(customerDTO, "customerDTO");
        validator.customerNameValidator = customerNameValidator;
        validator.customerService = customerService;
    }

    public CommonCustomerRegistrationValidatorTest(String testName, CustomerDTO customerDTO, boolean isEmailAlreadyUsed, boolean hasErrors) {
        this.customerDTO = customerDTO;
        this.isEmailAlreadyUsed = isEmailAlreadyUsed;
        this.hasErrors = hasErrors;
    }

    @Parameterized.Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters() {
        return Arrays.asList(new Object[][] { 
                        {"shouldValidate", getTestCustomerDTOWithEmail(), false, false},
                        {"shouldNotValidateIfEmailAlreadyUsed", getTestCustomerDTOWithEmail(), true, true},
                        {"shouldNotValidateIfEmailIsNull", getTestCustomerDTO1(), true, true},
        });
    }

    @Test
    public void validate() {
        doCallRealMethod().when(customerNameValidator).validate(customerDTO, errors);
        when(customerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(isEmailAlreadyUsed);
        validator.validate(customerDTO, errors);
        assertEquals(this.hasErrors, errors.hasErrors());
    }

}
