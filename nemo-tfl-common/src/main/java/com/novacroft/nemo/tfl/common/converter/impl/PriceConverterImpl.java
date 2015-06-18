package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Price;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;
@Component("priceConverter")
public class PriceConverterImpl extends BaseDtoEntityConverterImpl<Price, PriceDTO> {

	@Override
	protected PriceDTO getNewDto() {
		return new PriceDTO();
	}

}
