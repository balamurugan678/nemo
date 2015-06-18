package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.ADMINSTRATION_FEE_MUST_BE_GREATER_THAN_ZERO;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_ADMINISTRATION_FEE_VALUE;
import static com.novacroft.nemo.tfl.common.constant.Refund.ADMINISTRATION_FEE_MINIMUM_AMOUNT;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

/**
 * Failed Card Products validation
 */
@Component("administrationFeeValidator")
public class AdministrationFeeValidator extends BaseValidator {
		
    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;
  		rejectIfAdminFeeAmountIsLessThanZero(cmd, errors);
    }
    
    protected void rejectIfAdminFeeAmountIsLessThanZero(CartCmdImpl cmd, Errors errors) {
        if(cmd.getAdministrationFeeValue() < ADMINISTRATION_FEE_MINIMUM_AMOUNT) {
            errors.rejectValue(FIELD_ADMINISTRATION_FEE_VALUE, ADMINSTRATION_FEE_MUST_BE_GREATER_THAN_ZERO.errorCode());
        }
    }    
}
