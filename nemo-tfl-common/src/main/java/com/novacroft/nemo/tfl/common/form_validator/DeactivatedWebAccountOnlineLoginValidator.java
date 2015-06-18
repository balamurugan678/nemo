package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.EMPTY_STRING;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.CUSTOMER_DEACTIVATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
/**
 * Deactivate Web Account validation
 */
@Component("deactivatedWebAccountOnlineLoginValidator")
public class DeactivatedWebAccountOnlineLoginValidator extends BaseValidator {
	@Autowired
	protected CustomerDataService customerDataService;
	
    @Override
    public boolean supports(Class<?> targetClass) {
        return LoginCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	LoginCmdImpl cmd = (LoginCmdImpl) target;
    	
    	if (customerExists(cmd.getUsername())) {
    		validateCustomerDeactive(cmd, errors);
    	} else {
    		rejectAsBadCredentialsError(cmd, errors);
    	}
    }
    
    protected void validateCustomerDeactive(LoginCmdImpl cmd, Errors errors) {
    	if (accountIsDeactivated(cmd.getUsername())) {
        	cmd.setUsername(EMPTY_STRING);
            cmd.setPassword(EMPTY_STRING);
            errors.rejectValue(PASSWORD, ContentCode.ACCOUNT_DEACTIVATED.errorCode());
        }
    }
    
    protected void rejectAsBadCredentialsError(LoginCmdImpl cmd, Errors errors) {
    	cmd.setUsername(EMPTY_STRING);
        cmd.setPassword(EMPTY_STRING);
        errors.rejectValue(PASSWORD, ContentCode.BAD_CREDENTIALS.errorCode());
    }
    
    protected boolean customerExists(String userNameOrEmail) {
    	return customerDataService.findByUsernameOrEmail(userNameOrEmail) != null ? true : false;
    }
    
    protected boolean accountIsDeactivated(String userNameOrEmail) {
    	CustomerDTO customerDTO = customerDataService.findByUsernameOrEmail(userNameOrEmail);
    	return (customerDTO.getDeactivated() == CUSTOMER_DEACTIVATED) ? true : false;
    }
}
