package com.novacroft.nemo.common.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.AddressCmd;

/**
 * Customer address validation
 */
@Component("addressValidator")
public class AddressValidator extends CommonAddressValidator {
    
    @Override
    public boolean supports(Class<?> targetClass) {
        return AddressCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddressCmd cmd = (AddressCmd) target;
        validateMandatoryFields(errors, MANDATORY_FIELDS);
        validateCountrySelected(errors, cmd.getCountry());
        if(StringUtils.isNotBlank(cmd.getPostcode())){
        	validatePostCode(errors, cmd.getPostcode(), cmd.getCountry());
        }
    }

}
