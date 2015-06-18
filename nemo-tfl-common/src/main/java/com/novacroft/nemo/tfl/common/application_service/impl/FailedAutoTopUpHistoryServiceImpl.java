package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.FailedAutoTopUpHistoryService;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpHistoryDataService;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpHistory;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;

/**
 * Get the oyster card information from the cubic
 */

@Service("failedAutoTopUpHistoryService")
public class FailedAutoTopUpHistoryServiceImpl implements FailedAutoTopUpHistoryService {

	@Autowired
    protected FailedAutoTopUpHistoryDataService failedAutoTopUpHistoryDataService;
	

	@Override
	public List<FailedAutoTopUpHistoryDTO> findByCustomerId(FailedAutoTopUpHistory failedAutoTopUpHistory) {
		return failedAutoTopUpHistoryDataService.findByCustomerId(failedAutoTopUpHistory);
	}

	@Override
	public List<FailedAutoTopUpHistoryDTO> findFailedAutoTopUpHistoryByCustomerIdAndCaseId(FailedAutoTopUpHistory failedAutoTopUpHistory) {
		return failedAutoTopUpHistoryDataService.findFailedAutoTopUpHistoryByCustomerIdAndCaseId(failedAutoTopUpHistory);
	}
    
}
