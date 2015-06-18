package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.JourneyCompletedRefundItem;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;
@Component
public class JourneyCompletedRefundItemConverterImpl extends BaseDtoEntityConverterImpl<JourneyCompletedRefundItem, JourneyCompletedRefundItemDTO> {
	@Override
	protected JourneyCompletedRefundItemDTO getNewDto() {
		return new JourneyCompletedRefundItemDTO();
	}
}
