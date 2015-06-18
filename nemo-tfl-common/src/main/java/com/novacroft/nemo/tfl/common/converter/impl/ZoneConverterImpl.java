package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Zone;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

@Component("zoneConverter")
public class ZoneConverterImpl extends BaseDtoEntityConverterImpl<Zone, ZoneDTO> {

	@Override
	protected ZoneDTO getNewDto() {
		return new ZoneDTO();
	}
	

}
