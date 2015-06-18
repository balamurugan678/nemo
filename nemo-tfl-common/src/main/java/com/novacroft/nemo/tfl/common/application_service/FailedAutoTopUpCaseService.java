package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;


public interface FailedAutoTopUpCaseService {
    boolean isOysterCardWithFailedAutoTopUpCase(String cardNumber);
	FailedAutoTopUpCaseDTO findByCardNumber(String oysterCardNumber);
	FailedAutoTopUpCaseCmdImpl updateFailedAutoTopUpCaseDetails(FailedAutoTopUpCaseCmdImpl cmd);
}
