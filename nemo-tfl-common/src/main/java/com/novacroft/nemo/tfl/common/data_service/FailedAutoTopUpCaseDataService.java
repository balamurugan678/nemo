package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpCase;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;

public interface FailedAutoTopUpCaseDataService extends BaseDataService<FailedAutoTopUpCase, FailedAutoTopUpCaseDTO> {

	void create(FailedAutoTopUpCaseDTO failedAutoTopUpCaseDTO);
    FailedAutoTopUpCaseDTO findByFATUCaseId(Long caseId);
    List<FailedAutoTopUpCaseDTO> findByCustomerId(FailedAutoTopUpCase failedAutoTopUpCase);
    List<FailedAutoTopUpCaseDTO> findPendingItemsByCustomerId(Long customerId);
    int findPendingAmountByCustomerId(Long customerId);
    boolean isOysterCardWithFailedAutoTopUpCase(String cardNumber);
	FailedAutoTopUpCaseDTO findByCardNumber(String oysterCardNumber);
	FailedAutoTopUpCaseDTO findByCustomerIdWithCaseDetails(Long customerId);
}
