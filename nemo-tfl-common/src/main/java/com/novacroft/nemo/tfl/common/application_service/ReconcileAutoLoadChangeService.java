package com.novacroft.nemo.tfl.common.application_service;

/**
 * Reconcile auto load changes reported by CUBIC, via the Auto Load Change batch file, with auto load change requests
 */
public interface ReconcileAutoLoadChangeService {
    void reconcileChange(Integer requestSequenceNumber, String cardNumber, Integer newAutoLoadConfiguration,
                         String statusOfAttemptedAction, Integer failureReasonCode);
}


