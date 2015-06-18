package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;

/**
 * Transaction web service
 */
public interface SoapTransactionService {
    ReplyMessage fabricateReplyMessage(RequestMessage requestMessage);
}
