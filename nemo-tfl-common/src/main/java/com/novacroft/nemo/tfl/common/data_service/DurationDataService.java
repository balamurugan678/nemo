package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Duration;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;

public interface DurationDataService extends BaseDataService<Duration, DurationDTO> {

	DurationDTO findByCode(String code, Date effectiveDate);

}
