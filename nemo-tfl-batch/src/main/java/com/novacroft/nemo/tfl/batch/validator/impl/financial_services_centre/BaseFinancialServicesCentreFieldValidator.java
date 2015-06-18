package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.constant.financial_services_centre.FieldName;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import org.springframework.validation.Errors;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Validation for Financial Services Centre
 */
public abstract class BaseFinancialServicesCentreFieldValidator extends BaseFinancialServicesCentreDataTypeValidator {

    protected void validateReferenceNumber(Errors errors, String referenceNumber) {
        if (!isValidReferenceNumber(referenceNumber)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(),
                    new String[]{FieldName.REFERENCE_NUMBER, referenceNumber}, null);
        }
    }

    protected void validateAmount(Errors errors, String amount) {
        if (!isValidAmount(amount)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.AMOUNT, amount}, null);
        }
    }

    protected void validateCustomerName(Errors errors, String customerName) {
        if (!isValidCustomerName(customerName)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(),
                    new String[]{FieldName.CUSTOMER_NAME, customerName}, null);
        }
    }

    protected void validateChequeSerialNumber(Errors errors, String chequeSerialNumber) {
        if (!isValidChequeSerialNumber(chequeSerialNumber)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(),
                    new String[]{FieldName.CHEQUE_SERIAL_NUMBER, chequeSerialNumber}, null);
        }
    }

    protected void validatePrintedOn(Errors errors, String printedOn) {
        if (!isValidPrintedOn(printedOn)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.PRINTED_ON, printedOn},
                    null);
        }
    }

    protected void validateOutdatedOn(Errors errors, String outdatedOn) {
        if (!isValidOutdatedOn(outdatedOn)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.OUTDATED_ON, outdatedOn},
                    null);
        }
    }

    protected void validateClearedOn(Errors errors, String clearedOn) {
        if (!isValidClearedOn(clearedOn)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.CLEARED_ON, clearedOn},
                    null);
        }
    }

    protected void validateCurrency(Errors errors, String currency) {
        if (!isValidCurrency(currency)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.CURRENCY, currency}, null);
        }
    }

    protected boolean isValidReferenceNumber(String value) {
        return isNotBlank(value) && isValidDigits(value);
    }

    protected boolean isValidAmount(String value) {
        return isNotBlank(value) && isValidMonetaryValue(value);
    }

    protected boolean isValidCustomerName(String value) {
        return isNotBlank(value);
    }

    protected boolean isValidChequeSerialNumber(String value) {
        return isNotBlank(value) && isValidDigits(value);
    }

    protected boolean isValidPrintedOn(String value) {
        return isNotBlank(value) && isValidDate(value, DateConstant.SHORT_DATE_PATTERN);
    }

    protected boolean isValidClearedOn(String value) {
        return isNotBlank(value) && isValidDate(value, SHORT_DATE_PATTERN);
    }

    protected boolean isValidCurrency(String value) {
        return isNotBlank(value);
    }

    protected boolean isValidOutdatedOn(String value) {
        return isNotBlank(value) && isValidDate(value, DateConstant.SHORT_DATE_PATTERN);
    }
}
