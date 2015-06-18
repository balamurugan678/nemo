package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostCmd;

import java.util.Map;

/**
 * CyberSource Secure Acceptance transaction service
 */
public interface PostTransactionService {
    PostCmd getRequestAndConfiguration(Map model);
}
