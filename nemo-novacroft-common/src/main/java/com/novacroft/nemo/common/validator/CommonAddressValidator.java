package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_COUNTRY;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_HOUSE_NAME_NUMBER;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_STREET;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_TOWN;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.constant.CommonContentCode;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;

@Component("commonAddressValidator")
public class CommonAddressValidator extends BaseValidator {
    
    protected static final String[] MANDATORY_FIELDS = {FIELD_HOUSE_NAME_NUMBER,FIELD_STREET,FIELD_TOWN,FIELD_POSTCODE};
    
    @Autowired
    protected PostcodeValidator postcodeValidator;
 
    @Autowired
    protected  CountryDataService  countryDataService;
    

    @Override
    public boolean supports(Class<?> targetClass) {
        return AddressDTO.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddressDTO addressDTO = (AddressDTO)target;
        validateMandatoryFields(errors, MANDATORY_FIELDS);
        validateCountrySelected(errors, addressDTO.getCountry());
        if(StringUtils.isNotBlank(addressDTO.getPostcode())){
        	validatePostCode(errors, addressDTO.getPostcode(), addressDTO.getCountry());
        }
    }
    
    protected void validateMandatoryFields(Errors errors, String[] fields){
        for(String field: fields){
            rejectIfMandatoryFieldEmpty(errors, field);
        }
    }
    
    protected void validatePostCode(Errors errors, String postcode, CountryDTO countryDTO){
    	CountryDTO ukCountryDTO = countryDataService.findCountryByCode(ISO_UK_CODE);
    	if (ObjectUtils.equals(countryDTO, ukCountryDTO)) {
    		postcodeValidator.validate(errors, postcode);
    	}
    }
    
    protected void validateCountrySelected(Errors errors, CountryDTO countryDTO) {
        if (countryDTO == null || StringUtils.isBlank(countryDTO.getCode())) {
            errors.rejectValue(FIELD_COUNTRY, CommonContentCode.MANDATORY_SELECT_FIELD_EMPTY.errorCode());
        }
    }
}
