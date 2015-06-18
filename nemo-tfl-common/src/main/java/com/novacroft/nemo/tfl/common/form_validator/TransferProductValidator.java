package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.SELECT_CARD_FIELD_EMPTY;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_ID;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.common.command.SelectCardCmd;

/**
 * Transfer Product Validator
 */
@Component(value = "transferProductValidator")
public class TransferProductValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> targetClass) {
        return SelectCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfEmptyOrWhitespace(errors, FIELD_CARD_ID, SELECT_CARD_FIELD_EMPTY.errorCode(), new Object[] { new DefaultMessageSourceResolvable(
                        "selectCardFieldEmpty.error") });
    }

}
