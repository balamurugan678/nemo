package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Zone;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

public interface ZoneDataService extends BaseDataService<Zone,ZoneDTO>{

	ZoneDTO findByCode(String code, Date effectiveDate);
}
