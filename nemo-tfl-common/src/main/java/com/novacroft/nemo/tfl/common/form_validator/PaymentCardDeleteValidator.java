package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Delete payment card validator
 */
@Component("paymentCardDeleteValidator")
public class PaymentCardDeleteValidator extends BaseValidator implements Validator {

    @Autowired
    protected PaymentCardService paymentCardService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PaymentCardCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentCardCmdImpl cmd = (PaymentCardCmdImpl) target;
        if (this.paymentCardService.isCardInUse(cmd.getPaymentCardDTO().getId())) {
            errors.reject(ContentCode.LINKED_PAYMENT_CARD_CANNOT_BE_DELETED.errorCode());
        }
    }
}
