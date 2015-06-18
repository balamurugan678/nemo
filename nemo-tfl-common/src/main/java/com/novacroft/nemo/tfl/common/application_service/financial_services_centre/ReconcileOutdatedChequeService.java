package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

import java.util.Date;

/**
 * Reconcile outdated cheques from the Financial Services Centre (FSC) against cheques issued
 */
public interface ReconcileOutdatedChequeService {
    void reconcileOutdatedCheque(Long orderNumber, Integer amount, String customerName, Long chequeSerialNumber,
                                 Date outdatedOn);
}
