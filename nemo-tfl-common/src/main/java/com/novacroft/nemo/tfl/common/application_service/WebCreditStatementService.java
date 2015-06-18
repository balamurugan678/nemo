package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementCmdImpl;

/**
 * Service specification to assemble web account credit statement
 */
public interface WebCreditStatementService {
    WebCreditStatementCmdImpl getStatement(Long customerId);
}
