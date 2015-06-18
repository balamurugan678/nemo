package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.constant.WorkflowFields;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.*;

/**
 * Approval Search validation
 */
@Component("approvalSearchValidator")
public class ApprovalSearchValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> targetClass) {
        return ApprovalListCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ApprovalListCmdImpl cmd = (ApprovalListCmdImpl) target;

        validateCaseNumber(cmd.getCaseNumber(), errors);
        validateDateCreated(cmd.getDateCreated(), errors);
        validatePaymentMethod(cmd.getPaymentMethod(), errors);
        validateTimeInQueue(cmd.getTimeOnQueue(), errors);
        validateAmount(cmd.getAmount(), errors);
        validateAgent(cmd.getAgent(), errors);
        validateStatus(cmd.getStatus(), errors);
        validateReason(cmd.getReason(), errors);
    }

    private void validateDateCreated(String dateCreated, Errors errors) {
        if (StringUtils.isNotEmpty(dateCreated)) {
            if (!errors.hasFieldErrors(WorkflowFields.DATE_CREATED)) {
                rejectIfNotShortDate(errors, WorkflowFields.DATE_CREATED);
            }

            if (!errors.hasFieldErrors(WorkflowFields.DATE_CREATED)) {
                rejectIfInvalidDate(errors, WorkflowFields.DATE_CREATED, dateCreated);
            }
        }
    }

    private void validateCaseNumber(String caseNumber, Errors errors) {
        if (StringUtils.isNotEmpty(caseNumber)) {
            rejectIfNotNumeric(errors, FIELD_CASE_NUMBER);
        }
    }

    private void validatePaymentMethod(String paymentMethod, Errors errors) {
        if (StringUtils.isNotEmpty(paymentMethod)) {
            rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(errors, FIELD_PAYMENT_METHOD);
        }
    }

    private void validateTimeInQueue(String timeOnQueue, Errors errors) {
        if (StringUtils.isNotEmpty(timeOnQueue)) {
            rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(errors, FIELD_TIME_ON_QUEUE);
        }
    }

    private void validateAmount(String amount, Errors errors) {
        if (StringUtils.isNotEmpty(amount)) {
            rejectIfNotDecimal(errors, FIELD_AMOUNT);
        }
    }

    private void validateAgent(String agent, Errors errors) {
        if (StringUtils.isNotEmpty(agent)) {
            rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(errors, FIELD_AGENT);
        }
    }

    private void validateStatus(String status, Errors errors) {
        if (StringUtils.isNotEmpty(status)) {
            rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(errors, FIELD_STATUS);
        }
    }

    private void validateReason(String reason, Errors errors) {
        if (StringUtils.isNotEmpty(reason)) {
            rejectIfNotAlphaNumericWithCurrencyWhiteSpaceBetweenWordsOnly(errors, FIELD_REASON);
        }
    }
}
