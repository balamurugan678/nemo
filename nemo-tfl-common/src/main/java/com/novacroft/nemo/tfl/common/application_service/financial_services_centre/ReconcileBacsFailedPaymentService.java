package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

import java.util.Date;

/**
 * Reconcile BACS Failed Payment Failed by the the Financial Services Centre (FSC).
 */
public interface ReconcileBacsFailedPaymentService {

	 void reconcileBacsFailedPayment(String bacsRejectCode, Integer amount, Date paymentFailedDate,  Long financialServiceReference);
}
