package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

import java.util.Date;


/**
 * Reconcile Bacs Payment Settled by the the Financial Services Centre (FSC).
 */
public interface ReconcileBacsSettledPaymentService {

	 void reconcileBacsSettledPayment(Long paymentReferenceNumber, String customerName, Integer amount, Date settlementDate,  Long financialServiceReference);
}
