package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsFailureRecordService;
import com.novacroft.nemo.tfl.batch.constant.financial_services_centre.FieldName;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Validate Financial Services Centre BACS failure record
 */
@Component("bacsFailureValidator")
public class BacsPaymentFailureValidatorImpl extends BaseFinancialServicesCentreFieldValidator implements Validator {

    @Autowired
    protected BacsFailureRecordService bacsFailureRecordService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validateReferenceNumber(errors, this.bacsFailureRecordService.getFinancialServicesReferenceNumber(record));
        validateAmount(errors, this.bacsFailureRecordService.getAmount(record));
        validateRejectCode(errors, this.bacsFailureRecordService.getBacsRejectCode(record));
        validateFailedPaymentDate(errors, this.bacsFailureRecordService.getPaymentFailureDate(record));
    }

    protected void validateRejectCode(Errors errors, String bacsRejectCode) {
        if (StringUtils.isBlank(bacsRejectCode) || StringUtils.trim(bacsRejectCode).length() != 1) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(),
                    new String[]{FieldName.BACS_REJECT_REASON, bacsRejectCode}, null);
        }
    }

    protected void validateFailedPaymentDate(Errors errors, String paymentFailedDate) {
        if (!isValidDate(paymentFailedDate)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(),
                    new String[]{FieldName.PAYMENT_FAILED_DATE, paymentFailedDate}, null);
        }
    }

    protected boolean isValidDate(String value) {
        return isNotBlank(value) && isValidDate(value, DateConstant.SHORT_DATE_PATTERN);
    }
}
