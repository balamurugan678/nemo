package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.AlternativeZoneMapping;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;

/**
 * Alternative zone mapping data service specification
 */
public interface AlternativeZoneMappingDataService extends BaseDataService<AlternativeZoneMapping, AlternativeZoneMappingDTO> {
	AlternativeZoneMappingDTO findByStartZoneAndEndZone(Integer startZone, Integer endZone);
}
