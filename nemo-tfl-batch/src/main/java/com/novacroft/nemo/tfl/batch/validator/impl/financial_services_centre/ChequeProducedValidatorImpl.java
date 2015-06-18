package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeProducedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validate Financial Services Centre cheques produced record
 */
@Component("chequeProducedValidator")
public class ChequeProducedValidatorImpl extends BaseFinancialServicesCentreFieldValidator implements Validator {
    @Autowired
    protected ChequeProducedRecordService chequeProducedRecordService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validateReferenceNumber(errors, this.chequeProducedRecordService.getReferenceNumber(record));
        validateAmount(errors, this.chequeProducedRecordService.getAmount(record));
        validateCustomerName(errors, this.chequeProducedRecordService.getCustomerName(record));
        validateChequeSerialNumber(errors, this.chequeProducedRecordService.getChequeSerialNumber(record));
        validatePrintedOn(errors, this.chequeProducedRecordService.getPrintedOn(record));
    }
}
