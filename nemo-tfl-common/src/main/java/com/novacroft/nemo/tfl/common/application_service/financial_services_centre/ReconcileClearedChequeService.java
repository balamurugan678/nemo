package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

import java.util.Date;

/**
 * Reconcile cheques cleared from the Financial Services Centre (FSC) against cheques produced
 */
public interface ReconcileClearedChequeService {
    void reconcileClearedCheque(Long chequeSerialNumber, Long paymentReferenceNumber, String customerName, Date clearedOn,
                                Integer amount);
}
