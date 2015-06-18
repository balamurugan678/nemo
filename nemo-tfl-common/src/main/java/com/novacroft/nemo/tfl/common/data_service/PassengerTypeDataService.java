package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;

public interface PassengerTypeDataService extends BaseDataService<PassengerType, PassengerTypeDTO> {
    PassengerTypeDTO findByName(String name);

	PassengerTypeDTO findByCode(String name, Date effectiveDate);
	
	List<PassengerTypeDTO> findAll();

}
