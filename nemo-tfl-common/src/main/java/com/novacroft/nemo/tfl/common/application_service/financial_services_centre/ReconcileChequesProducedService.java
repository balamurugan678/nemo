package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

import java.util.Date;

/**
 * Reconcile cheques produced by the Financial Services Centre against cheques requested
 */
public interface ReconcileChequesProducedService {
    void reconcileChequeProduced(Long orderNumber, Integer amount, String customerName, Long chequeSerialNumber,
                                 Date printedOn);
}
