package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsSettlementRecordService;
import com.novacroft.nemo.tfl.batch.constant.financial_services_centre.FieldName;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Validate Financial Services Centre bacs settlement record
 */
@Component("bacsPaymentSettlementValidator")
public class BacsPaymentSettlementValidatorImpl extends BaseFinancialServicesCentreFieldValidator implements Validator {
    @Autowired
    protected BacsSettlementRecordService bacsSettlementRecordService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validateReferenceNumber(errors, this.bacsSettlementRecordService.getPaymentReferenceNumber(record));
        validateAmount(errors, this.bacsSettlementRecordService.getAmount(record));
        validateCustomerName(errors, this.bacsSettlementRecordService.getCustomerName(record));
        valiatedatePaymentDate(errors, this.bacsSettlementRecordService.getPaymentDate(record));
    }

    protected void valiatedatePaymentDate(Errors errors, String paymentDate) {
        if (!isValidPaymentDate(paymentDate)) {
            errors.reject(ContentCode.INVALID_IMPORT_FILE_FIELD.errorCode(), new String[]{FieldName.PAYMENT_DATE, paymentDate},
                    null);
        }

    }

    private boolean isValidPaymentDate(String value) {
        return isNotBlank(value) && isValidDate(value, DateConstant.SHORT_DATE_PATTERN);
    }
}
