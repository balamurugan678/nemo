package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpHistory;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;

public interface FailedAutoTopUpHistoryDataService extends BaseDataService<FailedAutoTopUpHistory, FailedAutoTopUpHistoryDTO> {
	public List<FailedAutoTopUpHistoryDTO> findByCustomerId(FailedAutoTopUpHistory failedAutoTopUpHistory);
	public List<FailedAutoTopUpHistoryDTO> findFailedAutoTopUpHistoryByCustomerIdAndCaseId(FailedAutoTopUpHistory failedAutoTopUpHistory);

}
