package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MINIMUM_AUTO_TOP_UP_AMT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AUTO_TOP_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AUTO_TOP_UP_CREDIT_BALANCE;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;

/**
 * Pay as you go validation
 */
@Component("autoTopUpPayAsYouGoValidator")
public class AutoTopUpPayAsYouGoValidator extends PayAsYouGoValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return PayAsYouGoCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PayAsYouGoCmd cmd = (PayAsYouGoCmd) target;
        rejectIfMandatoryFieldEmpty(errors, FIELD_AUTO_TOP_UP_CREDIT_BALANCE);
        rejectIfMandatoryFieldEmpty(errors, FIELD_AUTO_TOP_AMOUNT);
        int minimumAutoTopUpAmount = systemParameterService.getIntegerParameterValue(MINIMUM_AUTO_TOP_UP_AMT);
        if (hasNoFieldError(errors, FIELD_AUTO_TOP_UP_CREDIT_BALANCE) &&
                cmd.getAutoTopUpCreditBalance() < minimumAutoTopUpAmount) {
            errors.rejectValue(FIELD_AUTO_TOP_UP_CREDIT_BALANCE, INVALID_AMOUNT.errorCode());
        }
        if(hasNoFieldError(errors, FIELD_AUTO_TOP_AMOUNT) && cmd.getAutoTopUpAmt() == 0){
            errors.rejectValue(FIELD_AUTO_TOP_AMOUNT, INVALID_AMOUNT.errorCode());
        }
        rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystem(errors, FIELD_AUTO_TOP_UP_CREDIT_BALANCE, cmd.getCardId(),
                cmd.getAutoTopUpCreditBalance(), cmd.getExistingCreditBalance());
    }
}
