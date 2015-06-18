package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTONullPostCode;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;

@RunWith(Parameterized.class)
public class CommonAddressValidatorTest {
    
    private CommonAddressValidator validator;
    private PostcodeValidator mockPostcodeValidator;
    private AddressDTO addressDTO;
    private boolean hasErrors;
    private boolean validPostCode;
    private CountryDataService mockCountryDataService;
    private Errors errors;
    private CountryDTO ukCountryDTO = new CountryDTO();

    @Before
    public void setUp() throws Exception {
        validator = new CommonAddressValidator();
        errors = new BeanPropertyBindingResult(addressDTO, "addressDTO");
        mockPostcodeValidator = mock(PostcodeValidator.class);
        validator.postcodeValidator = mockPostcodeValidator;

        ukCountryDTO.setCode("GB");
        mockCountryDataService = mock(CountryDataService.class);
        validator.countryDataService = mockCountryDataService;
        when(mockCountryDataService.findCountryByCode(BaseValidator.ISO_UK_CODE)).thenReturn(ukCountryDTO);

    }

    public CommonAddressValidatorTest(String testName, AddressDTO addressDTO, boolean validPostCode, boolean hasErrors) {
        this.addressDTO = addressDTO;
        this.validPostCode = validPostCode;
        this.hasErrors = hasErrors;
    }
    
    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][]{
                        {"shouldValidate",getTestAddressDTO1(), true, false},
                        {"shouldNotValidateWithMissingMandoatoryField", getTestAddressDTONullPostCode(), false, true}
        });
    }
    @Test
    public void validate() {
        doCallRealMethod().when(mockPostcodeValidator).validate((Errors)any(),anyString());
        when(mockPostcodeValidator.validate(anyString())).thenReturn(validPostCode).thenCallRealMethod();
        when(mockPostcodeValidator.validatePostcode(anyString())).thenReturn(validPostCode).thenCallRealMethod();
        validator.validate(addressDTO, errors);
        assertEquals(this.hasErrors, errors.hasErrors());
    }

}
