package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
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

import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

public class CustomerValidatorTest {
	
    private CustomerValidator validator;
    private Errors mockErrors;
    private CustomerDTO mockCustomerDTO;

    @Before
    public void setUp() {
        this.validator = mock(CustomerValidator.class);
        this.mockErrors = mock(Errors.class);
        this.mockCustomerDTO = mock(CustomerDTO.class);
        doNothing().when(this.mockErrors).reject(anyString());
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(CustomerDTO.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(NewPasswordCmd.class));
    }

    @Test
    public void shouldValidate() {
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        when(this.mockCustomerDTO.getId()).thenReturn(CUSTOMER_ID_1);

        this.validator.validate(this.mockCustomerDTO, this.mockErrors);

        verify(this.mockCustomerDTO).getId();
        verify(this.mockErrors, never()).reject(anyString());
    }

    @Test
    public void shouldNotValidate() {
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        when(this.mockCustomerDTO.getId()).thenReturn(null);

        this.validator.validate(this.mockCustomerDTO, this.mockErrors);

        verify(this.mockCustomerDTO).getId();
        verify(this.mockErrors).reject(anyString());
    }
    
    @Test
    public void instantiationTest(){
        this.validator = new CustomerValidator();
        assertNotNull(validator);
    }

}
