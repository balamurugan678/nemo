package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_PAYMENT_TYPE;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

/**
 * Refund Payment validation
 */
@Component("paymentMethodValidator")
public class PaymentMethodValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return CartCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_PAYMENT_TYPE);
    }
    
}
