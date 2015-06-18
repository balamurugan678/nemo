package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.AddPaymentCardActionCmd;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.PAYMENT_CARD_ACTION;

/**
 * Validate add payment card action
 */
@Component(value = "addPaymentCardActionValidator")
public class AddPaymentCardActionValidator extends BaseValidator implements Validator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return AddPaymentCardActionCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, PAYMENT_CARD_ACTION);
    }
}
