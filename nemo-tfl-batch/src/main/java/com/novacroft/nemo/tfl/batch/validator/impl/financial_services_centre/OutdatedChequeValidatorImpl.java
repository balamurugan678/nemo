package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.OutdatedChequeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validate Financial Services Centre outdated cheque record
 */
@Component("outdatedChequeValidator")
public class OutdatedChequeValidatorImpl extends BaseFinancialServicesCentreFieldValidator implements Validator {

    @Autowired
    protected OutdatedChequeRecordService outdatedChequeRecordService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validateReferenceNumber(errors, this.outdatedChequeRecordService.getReferenceNumber(record));
        validateAmount(errors, this.outdatedChequeRecordService.getAmount(record));
        validateCustomerName(errors, this.outdatedChequeRecordService.getCustomerName(record));
        validateChequeSerialNumber(errors, this.outdatedChequeRecordService.getChequeSerialNumber(record));
        validateOutdatedOn(errors, this.outdatedChequeRecordService.getOutdatedOn(record));
    }

}
