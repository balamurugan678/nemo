package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * Specification for security question service
 */
public interface SecurityQuestionService {
	
	SecurityQuestionCmdImpl getSecurityQuestionDetails(Long cardId);

    boolean verifySecurityQuestionDetails(SecurityQuestionCmd cmd, Long cardId);
    
    void addSecurityQuestionDetails(SecurityQuestionCmd cmd, Long webaccountId, CartSessionData cartSessionData);

    SecurityQuestionCmdImpl updateSecurityQuestionDetails(SecurityQuestionCmdImpl cmd);
}
