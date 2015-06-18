package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.FIRSTNAME_CHARACTERS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.LASTNAME_CHARACTERS;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AGENT_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_AGENT_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_BACS_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CHEQUE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CUSTOMER_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CUSTOMER_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_SAP_NUMBER;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;

@Component("refundSearchValidator")
public class RefundSearchValidator extends BaseValidator {
    private static final int FIRST_NAME_MIN_LENGTH = 2;
    private static final int LAST_NAME_MIN_LENGTH = 2;

    @Override
    public boolean supports(Class<?> targetClass) {
        return RefundSearchCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RefundSearchCmd cmd = (RefundSearchCmd) target;

        validateCaseNumber(cmd.getCaseNumber(), errors);
        validateFirstName(cmd.getAgentFirstName(), errors, FIELD_AGENT_FIRST_NAME);
        validateLastName(cmd.getAgentLastName(), errors, FIELD_AGENT_LAST_NAME);

        validateSapNumber(cmd.getSapNumber(), errors);
        validateFirstName(cmd.getCustomerFirstName(), errors, FIELD_CUSTOMER_FIRST_NAME);
        validateLastName(cmd.getCustomerLastName(), errors, FIELD_CUSTOMER_LAST_NAME);
        validateCardNumber(cmd.getCardNumber(), errors);

        validateBacsNumber(cmd.getBacsNumber(), errors);
        validateChequeNumber(cmd.getChequeNumber(), errors);
    }

    private void validateCaseNumber(String caseNumber, Errors errors) {
        if (!StringUtils.isEmpty(caseNumber)) {
            rejectIfNotNumeric(errors, FIELD_CASE_NUMBER);
        }
    }

    private void validateFirstName(String firstName, Errors errors, String field) {
        if (!StringUtils.isEmpty(firstName)) {
            if (firstName.length() <= FIRST_NAME_MIN_LENGTH) {
                errors.rejectValue(field, FIRSTNAME_CHARACTERS.errorCode());
            }
            rejectIfNotAlphabetic(errors, field);
        }
    }

    private void validateLastName(String lastName, Errors errors, String field) {
        if (!StringUtils.isEmpty(lastName)) {
            if (lastName.length() <= LAST_NAME_MIN_LENGTH) {
                errors.rejectValue(field, LASTNAME_CHARACTERS.errorCode());
            }
            rejectIfNotAlphabetic(errors, field);
        }
    }

    private void validateSapNumber(String sapNumber, Errors errors) {
        if (!StringUtils.isEmpty(sapNumber)) {
            rejectIfNotNumeric(errors, FIELD_SAP_NUMBER);
        }
    }

    private void validateCardNumber(String cardNumber, Errors errors) {
        if (!StringUtils.isEmpty(cardNumber)) {
            rejectIfNotNumeric(errors, FIELD_CARD_NUMBER);
        }
    }

    private void validateBacsNumber(String bacsNumber, Errors errors) {
        if (!StringUtils.isEmpty(bacsNumber)) {
            rejectIfNotNumeric(errors, FIELD_BACS_NUMBER);
        }
    }

    private void validateChequeNumber(String chequeNumber, Errors errors) {
        if (!StringUtils.isEmpty(chequeNumber)) {
            rejectIfNotNumeric(errors, FIELD_CHEQUE_NUMBER);
        }
    }
}
