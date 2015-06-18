package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpHistory;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;


public interface FailedAutoTopUpHistoryService {
	public List<FailedAutoTopUpHistoryDTO> findByCustomerId(FailedAutoTopUpHistory failedAutoTopUpHistory);
	public List<FailedAutoTopUpHistoryDTO> findFailedAutoTopUpHistoryByCustomerIdAndCaseId(FailedAutoTopUpHistory failedAutoTopUpHistory);
}
