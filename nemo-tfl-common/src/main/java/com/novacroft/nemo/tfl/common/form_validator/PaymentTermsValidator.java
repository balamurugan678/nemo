package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.PaymentTermsCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.TERMS_NOT_ACCEPTED;

/**
 * Payment terms and conditions validation
 */
@Component("paymentTermsValidator")
public class PaymentTermsValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> targetClass) {
        return PaymentTermsCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartCmdImpl cmd = (CartCmdImpl) target;
        if (!cmd.getPaymentTermsAccepted()) {
            errors.rejectValue("paymentTermsAccepted", TERMS_NOT_ACCEPTED.errorCode());
        }
    }
}
