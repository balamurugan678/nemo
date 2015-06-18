package com.novacroft.nemo.tfl.batch.validator.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.FieldName;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import org.springframework.validation.Errors;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Validation for the fields used in CUBIC files
 */
public abstract class BaseCubicFieldValidator extends BaseCubicDataTypeValidator {

    protected void validatePrestigeId(Errors errors, String prestigeId) {
        if (!isValidPrestigeId(prestigeId)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.PRESTIGE_ID, prestigeId},
                    null);
        }
    }

    protected void validateRequestSequenceNumber(Errors errors, String requestSequenceNumber) {
        if (!isValidRequestSequenceNumber(requestSequenceNumber)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.REQUEST_SEQUENCE_NUMBER, requestSequenceNumber}, null);
        }
    }

    protected void validateProductCode(Errors errors, String productCode) {
        if (!isValidProductCode(productCode)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.PRODUCT_CODE, productCode},
                    null);
        }
    }

    protected void validateFarePaid(Errors errors, String farePaid) {
        if (!isValidFarePaid(farePaid)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.FARE_PAID, farePaid}, null);
        }
    }

    protected void validateCurrency(Errors errors, String currency) {
        if (!isValidCurrency(currency)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.CURRENCY, currency}, null);
        }
    }

    protected void validatePaymentMethodCode(Errors errors, String paymentMethodCode) {
        if (!isValidPaymentMethodCode(paymentMethodCode)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.PAYMENT_METHOD_CODE, paymentMethodCode}, null);
        }
    }

    protected void validatePrePayValue(Errors errors, String prePayValue) {
        if (!isValidPrePayValue(prePayValue)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.PRE_PAY_VALUE, prePayValue},
                    null);
        }
    }

    protected void validatePickUpLocation(Errors errors, String pickUpLocation) {
        if (!isValidPickUpLocation(pickUpLocation)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.PICK_UP_LOCATION, pickUpLocation}, null);
        }
    }

    protected void validatePptStartDate(Errors errors, String pptStartDate) {
        if (!isValidPptStartDate(pptStartDate)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.PPT_START_DATE, pptStartDate}, null);
        }
    }

    protected void validatePptExpiryDate(Errors errors, String pptExpiryDate) {
        if (!isValidPptExpiryDate(pptExpiryDate)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.PPT_EXPIRY_DATE, pptExpiryDate}, null);
        }
    }

    protected void validateAutoLoadState(Errors errors, String autoLoadState) {
        if (!isValidAutoLoadState(autoLoadState)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.AUTO_LOAD_STATE, autoLoadState}, null);
        }
    }

    protected void validatePickUpTime(Errors errors, String pickUpTime) {
        if (!isValidPickUpTime(pickUpTime)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.PICK_UP_TIME, pickUpTime},
                    null);
        }
    }

    protected void validateActionStatus(Errors errors, String actionStatus) {
        if (!isValidActionStatus(actionStatus)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.ACTION_STATUS, actionStatus},
                    null);
        }
    }

    protected void validateFailureReasonCode(Errors errors, String failureReasonCode) {
        if (!isValidFailureReasonCode(failureReasonCode)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.FAILURE_REASON_CODE, failureReasonCode}, null);
        }
    }

    protected void validateAutoLoadConfiguration(Errors errors, String autoLoadConfiguration) {
        if (!isValidAutoLoadConfiguration(autoLoadConfiguration)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(),
                    new String[]{FieldName.AUTO_LOAD_CONFIGURATION, autoLoadConfiguration}, null);
        }
    }

    protected void validateBusRouteId(Errors errors, String busRouteId) {
        if (!isValidBusRouteId(busRouteId)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.BUS_ROUTE_ID, busRouteId},
                    null);
        }
    }

    protected void validateTopUpAmount(Errors errors, String topUpAmount) {
        if (!isValidTopUpAmount(topUpAmount)) {
            errors.reject(ContentCode.INVALID_CUBIC_FILE_FIELD.errorCode(), new String[]{FieldName.TOP_UP_AMOUNT, topUpAmount},
                    null);
        }
    }

    protected boolean isValidPrestigeId(String value) {
        return isNotBlank(value) && isValidDigits(value);
    }

    protected boolean isValidRequestSequenceNumber(String value) {
        return isNotBlank(value) && isValidInteger(value);
    }

    protected boolean isValidProductCode(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidFarePaid(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidCurrency(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidPaymentMethodCode(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidPrePayValue(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidPickUpLocation(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidPptStartDate(String value) {
        return isBlank(value) || isValidDate(value);
    }

    protected boolean isValidPptExpiryDate(String value) {
        return isBlank(value) || isValidDate(value);
    }

    protected boolean isValidAutoLoadState(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidPickUpTime(String value) {
        return isBlank(value) || isValidDateAndTime(value);
    }

    protected boolean isValidActionStatus(String value) {
        try {
            return isBlank(value) || Arrays.asList(CubicActionStatus.values()).contains(CubicActionStatus.valueOf(value));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    protected boolean isValidFailureReasonCode(String value) {
        return isBlank(value) || isValidInteger(value);
    }

    protected boolean isValidAutoLoadConfiguration(String value) {
        return isBlank(value) || (isValidInteger(value) && AutoLoadState.getStates().contains(value));
    }

    protected boolean isValidBusRouteId(String value) {
        // all values are valid
        return true;
    }

    protected boolean isValidTopUpAmount(String value) {
        return isNotBlank(value) && isValidDigits(value);
    }
}
