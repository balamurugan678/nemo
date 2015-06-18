package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Duration;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;

@Component("durationConverter")
public class DurationConverterImpl extends BaseDtoEntityConverterImpl<Duration, DurationDTO> {

	@Override
	protected DurationDTO getNewDto() {
		return new DurationDTO();
	}
	
	

}
