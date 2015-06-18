package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import java.util.Map;

/**
 * CyberSource Secure Acceptance reply signing service
 */
public interface PostSigningService {
    void signPostReply(Map<String, String> replyModel);
}
