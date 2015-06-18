package com.novacroft.nemo.tfl.batch.validator.impl.financial_services_centre;

import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeSettlementRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validate Financial Services Centre cheque settlement record
 */
@Component("chequeSettlementValidator")
public class ChequeSettlementValidatorImpl extends BaseFinancialServicesCentreFieldValidator implements Validator {
    @Autowired
    protected ChequeSettlementRecordService chequeSettlementRecordService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return String[].class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String[] record = (String[]) target;
        validateChequeSerialNumber(errors, this.chequeSettlementRecordService.getChequeSerialNumber(record));
        validateReferenceNumber(errors, this.chequeSettlementRecordService.getPaymentReferenceNumber(record));
        validateCustomerName(errors, this.chequeSettlementRecordService.getCustomerName(record));
        validateClearedOn(errors, this.chequeSettlementRecordService.getClearedOn(record));
        validateCurrency(errors, this.chequeSettlementRecordService.getCurrency(record));
        validateAmount(errors, this.chequeSettlementRecordService.getAmount(record));
    }
}
