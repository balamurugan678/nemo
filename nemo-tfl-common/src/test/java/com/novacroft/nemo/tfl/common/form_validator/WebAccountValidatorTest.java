package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.tfl.common.command.NewPasswordCmd;
import com.novacroft.nemo.tfl.common.form_validator.WebAccountValidator;
import com.novacroft.nemo.tfl.common.transfer.WebAccountDTO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.test_support.WebAccountTestUtil.WEB_ACCOUNT_ID_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * WebAccountValidator unit tests
 */
public class WebAccountValidatorTest {
    private WebAccountValidator validator;
    private Errors mockErrors;
    private WebAccountDTO mockWebAccountDTO;

    @Before
    public void setUp() {
        this.validator = mock(WebAccountValidator.class);
        this.mockErrors = mock(Errors.class);
        this.mockWebAccountDTO = mock(WebAccountDTO.class);
        doNothing().when(this.mockErrors).reject(anyString());
    }

    @Test
    public void shouldSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertTrue(validator.supports(WebAccountDTO.class));
    }

    @Test
    public void shouldNotSupportClass() {
        when(this.validator.supports(any(Class.class))).thenCallRealMethod();
        assertFalse(validator.supports(NewPasswordCmd.class));
    }

    @Test
    public void shouldValidate() {
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        when(this.mockWebAccountDTO.getId()).thenReturn(WEB_ACCOUNT_ID_1);

        this.validator.validate(this.mockWebAccountDTO, this.mockErrors);

        verify(this.mockWebAccountDTO).getId();
        verify(this.mockErrors, never()).reject(anyString());
    }

    @Test
    public void shouldNotValidate() {
        doCallRealMethod().when(this.validator).validate(any(), any(Errors.class));
        when(this.mockWebAccountDTO.getId()).thenReturn(null);

        this.validator.validate(this.mockWebAccountDTO, this.mockErrors);

        verify(this.mockWebAccountDTO).getId();
        verify(this.mockErrors).reject(anyString());
    }
    
    @Test
    public void instantiationTest(){
        this.validator= new WebAccountValidator();
        assertNotNull(validator);
    }

}
