package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;

public interface RefundCaseService {
    RefundCaseCmd getRefundCase(String caseNumber);
}
