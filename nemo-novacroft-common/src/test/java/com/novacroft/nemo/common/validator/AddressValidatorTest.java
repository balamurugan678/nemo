package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.test_support.AddressTestUtil.HOUSE_NAME_NUMBER_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.ContactDetailsCmd;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * AddressDetailsValidator unit tests
 */
public class AddressValidatorTest {
    private AddressValidator validator;
    private PostcodeValidator mockPostcodeValidator;
    private CommonOrderCardCmd cmd;
    private CountryDataService mockCountryDataService;
    private CountryDTO ukCountryDTO = new CountryDTO();

    @Before
    public void setUp() {
    	
    	ukCountryDTO.setCode("GB");
        validator = new AddressValidator();
        cmd = new CommonOrderCardCmd();
        mockPostcodeValidator = mock(PostcodeValidator.class);
        mockCountryDataService = mock(CountryDataService.class);
        validator.countryDataService = mockCountryDataService;
        validator.postcodeValidator = mockPostcodeValidator;
        when(mockCountryDataService.findCountryByCode(BaseValidator.ISO_UK_CODE)).thenReturn(ukCountryDTO);
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(AddressCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(ContactDetailsCmd.class));
    }

    @Test
    public void shouldValidate() {
        cmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        cmd.setStreet(STREET_1);
        cmd.setTown(TOWN_1);
        cmd.setCountry(ukCountryDTO);
        cmd.setPostcode(POSTCODE_1);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyHouseNameNumber() {
        cmd.setHouseNameNumber(" ");
        cmd.setStreet(STREET_1);
        cmd.setTown(TOWN_1);
        cmd.setCountry(ukCountryDTO);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullHouseNameNumber() {
        cmd.setHouseNameNumber(null);
        cmd.setStreet(STREET_1);
        cmd.setTown(TOWN_1);
        cmd.setCountry(ukCountryDTO);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyStreet() {
        cmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        cmd.setStreet(" ");
        cmd.setTown(TOWN_1);
        cmd.setCountry(ukCountryDTO);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullStreet() {
    	
        cmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        cmd.setStreet(null);
        cmd.setTown(TOWN_1);
        cmd.setCountry(ukCountryDTO);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithEmptyTown() {
        cmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        cmd.setStreet(STREET_1);
        cmd.setTown(" ");
        cmd.setCountry(ukCountryDTO);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullTown() {
        cmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        cmd.setStreet(STREET_1);
        cmd.setTown(null);
        cmd.setCountry(ukCountryDTO);
        
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullCountry() {
        cmd.setHouseNameNumber(HOUSE_NAME_NUMBER_1);
        cmd.setStreet(STREET_1);
        cmd.setTown(TOWN_1);
        cmd.setCountry(null);

        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
}
