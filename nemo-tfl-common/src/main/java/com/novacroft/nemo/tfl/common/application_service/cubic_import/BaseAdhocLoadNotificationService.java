/*
 * $Header$
 *
 * Copyright (c) 2014 Novacroft. All Rights Reserved.
 */
package com.novacroft.nemo.tfl.common.application_service.cubic_import;

/**
 * Base Interface for Adhoc Load Notification
 * 
 */
public interface BaseAdhocLoadNotificationService {
    
  Boolean hasAdHocLoadBeenRequested(Integer requestSequenceNumber, String cardNumber);
    
  Boolean isNotificationEmailRequired(Integer requestSequenceNumber, String cardNumber);

  void notifyCustomer(Integer requestSequenceNumber, String cardNumber);

}
