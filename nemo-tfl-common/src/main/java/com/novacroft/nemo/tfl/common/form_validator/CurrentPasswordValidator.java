package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.CommonCurrentPasswordValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

/**
 * New password validator
 */
@Component(value = "currentPasswordValidator")
public class CurrentPasswordValidator extends CommonCurrentPasswordValidator implements Validator {
}
