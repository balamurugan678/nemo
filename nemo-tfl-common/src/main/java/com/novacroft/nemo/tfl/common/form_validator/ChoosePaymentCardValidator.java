package com.novacroft.nemo.tfl.common.form_validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;

@Component(value = "choosePaymentCardValidator")
public class ChoosePaymentCardValidator extends BaseValidator implements Validator {

	@Override
	public boolean supports(Class<?> targetClass) {
		return ManageCardCmd.class.equals(targetClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, "paymentCardID");
	}

}