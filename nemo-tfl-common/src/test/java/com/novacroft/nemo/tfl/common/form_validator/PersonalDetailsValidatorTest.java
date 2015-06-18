package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.validator.AddressValidator;
import com.novacroft.nemo.common.validator.ContactDetailsValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;

public class PersonalDetailsValidatorTest {
	private PersonalDetailsValidator validator;

	private AddressValidator mockAddressValidator;
	private CustomerNameValidator mockCustomerNameValidator;
    private ContactDetailsValidator mockContactDetailsValidator;
	private PersonalDetailsCmdImpl cmd;
    private CustomerDataService mockCustomerDataService;
    private Errors errors;


	@Before
	public  void setUp() {
		validator = new PersonalDetailsValidator();

		mockAddressValidator = mock(AddressValidator.class);
		mockCustomerNameValidator = mock(CustomerNameValidator.class);
        mockContactDetailsValidator = mock(ContactDetailsValidator.class);
        mockCustomerDataService = mock(CustomerDataService.class);

		cmd = new PersonalDetailsCmdImpl();
        errors = new BeanPropertyBindingResult(cmd, "cmd");

		validator.addressValidator = mockAddressValidator;
		validator.customerNameValidator = mockCustomerNameValidator;
        validator.contactDetailsValidator = mockContactDetailsValidator;
        validator.customerDataService = mockCustomerDataService;
	}

	@Test
	public void shouldSupportClass() {
		assertTrue(validator.supports(PersonalDetailsCmdImpl.class));
	}

	@Test
	public void shouldNotSupportClass() {
		assertFalse(validator.supports(CommonOrderCardCmd.class));
	}

	@Test
    public void shouldCallAddressValidator() {
		validator.validate(cmd, errors);
        verify(mockAddressValidator).validate(any(PersonalDetailsCmdImpl.class), any(Errors.class));
	}

	@Test
    public void shouldValidateExistingCustomer() {
        cmd.setEmailAddress(CustomerTestUtil.EMAIL_ADDRESS_1);
        cmd.setCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        when(mockCustomerDataService.findById((Long) any())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());

        validator.validate(cmd, errors);
        verify(mockCustomerNameValidator).validate(any(PersonalDetailsCmdImpl.class), any(Errors.class));
        verify(mockCustomerDataService, never()).findByUsernameOrEmail(anyString());
	}

    @Test
    public void shouldNotValidateExistingCustomer() {
        cmd.setEmailAddress(CustomerTestUtil.EMAIL_ADDRESS_3);
        cmd.setCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
        when(mockCustomerDataService.findById((Long) any())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());

        validator.validate(cmd, errors);
        verify(mockCustomerNameValidator).validate(any(PersonalDetailsCmdImpl.class), any(Errors.class));
        verify(mockCustomerDataService).findByUsernameOrEmail(anyString());
    }

	@Test
    public void shouldCallContactValidator() {
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());
        validator.validate(cmd, errors);
        verify(mockContactDetailsValidator).validate(any(PersonalDetailsCmdImpl.class), any(Errors.class));
	}
	
	@Test
    public void shouldNotValidateIfEmailIsTheSame() {
	    cmd.setEmailAddress(CustomerTestUtil.EMAIL_ADDRESS_1);
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());
	    validator.validate(cmd, errors);
        verify(mockCustomerDataService).findByUsernameOrEmail(anyString());
	}
	
	@Test
    public void shouldNotValidateIfCustomerDoesntExist() {
        cmd.setEmailAddress(CustomerTestUtil.EMAIL_ADDRESS_3);
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(null);
        validator.validate(cmd, errors);
        verify(mockCustomerDataService).findByUsernameOrEmail(anyString());
    }

    @Test
    public void shouldValidateIfEmailIsNotAlreadyInUse(){
        cmd.setEmailAddress(CustomerTestUtil.EMAIL_ADDRESS_1);
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());
        validator.validate(cmd, errors);
        verify(mockCustomerDataService).findByUsernameOrEmail(anyString());
    }
}
