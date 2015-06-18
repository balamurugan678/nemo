package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_WEB_CREDIT_AVAILABLE_OR_ORDER_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.WEB_CREDIT_APPLY_AMOUNT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.WebCreditPurchaseCmd;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;

/**
 * Validate use of web credit against a purchase
 */
@Component(value = "webCreditPurchaseValidator")
public class WebCreditPurchaseValidator extends BaseValidator implements Validator {
    @Autowired
    protected WebCreditSettlementDataService webAccountCreditSettlementDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return WebCreditPurchaseCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WebCreditPurchaseCmd cmd = (WebCreditPurchaseCmd) target;
        validateWebCreditApplyAmountIsNotNull(errors);
        validateWebCreditApplyAmountIsNotLessThanZero(cmd, errors);
        validateWebCreditApplyAmount(cmd, errors);
    }

    protected void validateWebCreditApplyAmountIsNotNull(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, WEB_CREDIT_APPLY_AMOUNT);
    }

    protected void validateWebCreditApplyAmountIsNotLessThanZero(WebCreditPurchaseCmd cmd, Errors errors) {
    		rejectIfPenceAmountBelowMinimumValue(errors, WEB_CREDIT_APPLY_AMOUNT, cmd.getWebCreditApplyAmount(), 0);
    }

    protected void validateWebCreditApplyAmountIsNotMoreThanAvailableAmount(WebCreditPurchaseCmd cmd, Errors errors) {
        if ((cmd.getWebCreditApplyAmount() > cmd.getWebCreditAvailableAmount()) || (cmd.getWebCreditApplyAmount() > cmd.getTotalAmt())) {
            errors.rejectValue(WEB_CREDIT_APPLY_AMOUNT, GREATER_THAN_WEB_CREDIT_AVAILABLE_OR_ORDER_AMOUNT.errorCode());
        }
    }

    protected void validateWebCreditApplyAmount(WebCreditPurchaseCmd cmd, Errors errors) {
        if (cmd.getWebCreditAvailableAmount() <= ZERO) {
            validateWCApplyAmountWhenWCAvailableAmountIsZero(cmd, errors);
        } else {
            validateWebCreditApplyAmountIsNotMoreThanAvailableAmount(cmd, errors);
        }
    }

    protected void validateWCApplyAmountWhenWCAvailableAmountIsZero(WebCreditPurchaseCmd cmd, Errors errors) {
            rejectIfPenceAmountIsZero(errors, WEB_CREDIT_APPLY_AMOUNT, cmd.getWebCreditApplyAmount(), cmd.getWebCreditAvailableAmount());
    }
}
