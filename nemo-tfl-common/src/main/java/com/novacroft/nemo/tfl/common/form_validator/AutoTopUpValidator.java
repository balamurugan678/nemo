package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.MANDATORY_FIELD_EMPTY;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.AUTO_TOP_UP_PENDING_AMOUNT_FOR_EXISTING_OYSTER_CARD;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAYMENT_CARD_REQUIRED;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AUTO_TOP_UP_PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AUTO_TOP_UP_STATE;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;

/**
 * AutoTop Up  validation
 */
@Component("autoTopupValidator")
public class AutoTopUpValidator  implements Validator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return ManageCardCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	ManageCardCmd  cmd = (ManageCardCmd) target;
    	validateAutoTopUpStateAmountIsValidAmount(cmd, errors);
    	validateAutoTopUpStateExistingPendingAmountForExistingOysterCard(cmd, errors);
        if ( null!= cmd.getAutoTopUpState() && cmd.getAutoTopUpState() >0 && cmd.getPaymentCardID() == null) {
            errors.rejectValue(FIELD_AUTO_TOP_UP_PAYMENT_CARD, PAYMENT_CARD_REQUIRED.errorCode());
        }
    }
    protected void validateAutoTopUpStateExistingPendingAmountForExistingOysterCard(ManageCardCmd cmd, Errors errors) {
        if (!(StringUtils.isEmpty(cmd.getAutoTopUpStateExistingPendingAmount())) && (cmd.getAutoTopUpStateExistingPendingAmount() == cmd.getAutoTopUpState())) {
        	cmd.setAutoTopUpState(cmd.getAutoTopUpStateExistingPendingAmount());
        	errors.rejectValue(FIELD_AUTO_TOP_UP_STATE, AUTO_TOP_UP_PENDING_AMOUNT_FOR_EXISTING_OYSTER_CARD.errorCode());
        }
    }
    
    protected void validateAutoTopUpStateAmountIsValidAmount(ManageCardCmd cmd, Errors errors) {
        if (cmd.getAutoTopUpState()==0) {
        	rejectIfMandatoryFieldEmpty(errors, FIELD_AUTO_TOP_UP_STATE);
        	if(hasNoFieldError(errors, FIELD_AUTO_TOP_UP_STATE)){
                errors.rejectValue(FIELD_AUTO_TOP_UP_STATE, INVALID_AMOUNT.errorCode());
            }
        }
    }
    public void rejectIfMandatoryFieldEmpty(Errors errors, String field) {
        rejectIfEmptyOrWhitespace(errors, field, MANDATORY_FIELD_EMPTY.errorCode(), new Object[] { new DefaultMessageSourceResolvable(field
                        + ".label") });
    }
    protected boolean hasNoFieldError(Errors errors, String field) {
        return !errors.hasFieldErrors(field);
    }
}
