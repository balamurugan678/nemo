package com.novacroft.nemo.tfl.common.service_access.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;

/**
 * CyberSource payment gateway transaction service access
 */
public interface CyberSourceTransactionServiceAccess {
    ReplyMessage runTransaction(RequestMessage requestMessage);
}
