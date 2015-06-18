package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.PayeeSettlementDTO;

import static com.novacroft.nemo.common.utils.CurrencyUtil.reverseSign;

/**
 * Base functionality for reconciling Financial Services Centre (FSC) import records
 *
 * Reconciliation assumes that the amount in the settlement record has the opposite sign to the amount being imported.  The
 * settlement will contain a negative value for a refund and a positive value for a payment.
 */
public abstract class BaseReconciliationService {
    protected void validatePayeeAndAmount(PayeeSettlementDTO chequeSettlementDTO, String customerName, Integer amount,
                                          PrivateError errorMessage) {
        if (doAmountAndPayeeNotMatch(chequeSettlementDTO, customerName, amount)) {
            throw new ApplicationServiceException(
                    String.format(errorMessage.message(), reverseSign(chequeSettlementDTO.getAmount()), amount,
                            chequeSettlementDTO.getPayeeName(), customerName));
        }
    }

    protected boolean doAmountAndPayeeMatch(PayeeSettlementDTO payeeSettlementDTO, String customerName, Integer amount) {
        return payeeSettlementDTO.getPayeeName().equals(customerName) &&
                payeeSettlementDTO.getAmount().equals(reverseSign(amount));
    }

    protected boolean doAmountAndPayeeNotMatch(PayeeSettlementDTO payeeSettlementDTO, String customerName, Integer amount) {
        return !doAmountAndPayeeMatch(payeeSettlementDTO, customerName, amount);
    }
}
