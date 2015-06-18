package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_CHARACTER_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REJECT_REASON;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REJECT_REASON_FREE_TEXT;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;

@Component("approvalsRejectionValidator")
public class ApprovalsRejectionValidator extends BaseValidator {
    protected static final int CHARACTER_LIMIT = 150;

    @Override
    public boolean supports(Class<?> targetClass) {
        return WorkflowRejectCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkflowRejectCmd cmd = (WorkflowRejectCmd) target;

        validateRejectReason(errors);
        validateRejectionFreeText(cmd, errors);
    }

    private void validateRejectReason(Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_REJECT_REASON);
    }

    private void validateRejectionFreeText(WorkflowRejectCmd cmd, Errors errors) {
        if (RefundConstants.OTHER.equalsIgnoreCase(cmd.getRejectReason()) && StringUtils.isEmpty(cmd.getRejectFreeText())) {
            rejectIfMandatoryFieldEmpty(errors, FIELD_REJECT_REASON_FREE_TEXT);
        } else if (!StringUtils.isEmpty(cmd.getRejectFreeText()) && cmd.getRejectFreeText().length() > CHARACTER_LIMIT) {
            errors.rejectValue(FIELD_REJECT_REASON_FREE_TEXT, GREATER_THAN_CHARACTER_LIMIT.errorCode());
        }
    }
}
