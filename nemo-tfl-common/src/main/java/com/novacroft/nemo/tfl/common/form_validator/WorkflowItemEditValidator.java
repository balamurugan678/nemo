package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_COUNTRY;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_FIRST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_HOUSE_NAME_NUMBER;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_LAST_NAME;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_STREET;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_TITLE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_TOWN;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.GREATER_THAN_CHARACTER_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_REFUND_SCENARIO_SUB_TYPE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_REFUND_SCENARIO_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_INITIALS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REASON;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REFUND_SCENARIO_SUB_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_REFUND_SCENARIO_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_START_OF_REFUNDABLE_DATE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_USERNAME;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.constant.CommonContentCode;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;

@Component("workflowItemEditValidator")
public class WorkflowItemEditValidator extends BaseValidator {
    protected static final int CHARACTER_LIMIT_150 = 150;
    protected static final String EXCEED_CHARACTER_LIMIT_MESSAGE = "Reason greater than character limit";
    
    @Override
    public boolean supports(Class<?> targetClass) {
        return WorkflowEditCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkflowEditCmd cmd = (WorkflowEditCmd) target;

        validateCustomerName(cmd, errors);
        validateUsername(cmd, errors);
        validateCustomerAddress(cmd, errors);

        validateScenarioType(cmd.getRefundScenarioType(), errors);
        validateScenarioSubType(cmd.getRefundScenarioSubType(), errors);
        validateStartOfRefundablePeriod(cmd.getRefundablePeriodStartDate(), errors);

        validateReason(cmd.getReason(), errors);
    }

    private void validateCustomerName(WorkflowEditCmd cmd, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_TITLE);
        rejectIfMandatoryFieldEmpty(errors, FIELD_FIRST_NAME);
        rejectIfMandatoryFieldEmpty(errors, FIELD_LAST_NAME);

        if (isNotBlank(cmd.getTitle())) {
            rejectIfNotAlphabetic(errors, FIELD_TITLE);
        }

        if (isNotBlank(cmd.getFirstName())) {
        	rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(errors, FIELD_FIRST_NAME);
        }

        if (isNotBlank(cmd.getInitials())) {
            rejectIfNotAlphabetic(errors, FIELD_INITIALS);
        }

        if (isNotBlank(cmd.getLastName())) {
        	rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(errors, FIELD_LAST_NAME);
        }
    }

    private void validateUsername(WorkflowEditCmd cmd, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_USERNAME);

        if (isNotBlank(cmd.getUsername())) {
            rejectIfNotValidUsername(errors, FIELD_USERNAME);
        }
    }

    private void validateCustomerAddress(WorkflowEditCmd cmd, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_HOUSE_NAME_NUMBER);
        rejectIfMandatoryFieldEmpty(errors, FIELD_STREET);
        rejectIfMandatoryFieldEmpty(errors, FIELD_TOWN);

        if (isNotBlank(cmd.getHouseNameNumber())) {
            rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(errors, FIELD_HOUSE_NAME_NUMBER);
        }

        if (isNotBlank(cmd.getStreet())) {
            rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(errors, FIELD_STREET);
        }

        if (isNotBlank(cmd.getTown())) {
        	rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(errors, FIELD_TOWN);
        }

        if (cmd.getCountry() != null && isBlank(cmd.getCountry().getCode())) {
            errors.rejectValue(FIELD_COUNTRY, CommonContentCode.MANDATORY_SELECT_FIELD_EMPTY.errorCode());
        }
    }

    private void validateScenarioType(String refundScenarioType, Errors errors) {
        if (RefundDepartmentEnum.find(refundScenarioType) == null) {
            errors.rejectValue(FIELD_REFUND_SCENARIO_TYPE, INVALID_REFUND_SCENARIO_TYPE.errorCode());
        }
    }

    private void validateScenarioSubType(String refundScenarioSubType, Errors errors) {
        if (RefundScenarioEnum.find(refundScenarioSubType) == null) {
            errors.rejectValue(FIELD_REFUND_SCENARIO_SUB_TYPE, INVALID_REFUND_SCENARIO_SUB_TYPE.errorCode());
        }
    }

    private void validateStartOfRefundablePeriod(String refundablePeriodStartDate, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_START_OF_REFUNDABLE_DATE);
        if (!errors.hasFieldErrors(FIELD_START_OF_REFUNDABLE_DATE)) {
            rejectIfNotShortDate(errors, FIELD_START_OF_REFUNDABLE_DATE);
        }

        if (!errors.hasFieldErrors(FIELD_START_OF_REFUNDABLE_DATE)) {
            rejectIfInvalidDate(errors, FIELD_START_OF_REFUNDABLE_DATE, refundablePeriodStartDate);
        }
    }

    private void validateReason(String reason, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_REASON);
        if (!errors.hasFieldErrors(FIELD_REASON) && reason.length() > CHARACTER_LIMIT_150) {
            errors.rejectValue(FIELD_REASON, GREATER_THAN_CHARACTER_LIMIT.errorCode(),new String[]{FIELD_REASON, Integer.toString(CHARACTER_LIMIT_150)}, EXCEED_CHARACTER_LIMIT_MESSAGE);
        }
    }
    
}
