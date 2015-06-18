package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;

@Component(value = "discountTypeConverter")
public class DiscountTypeConverterImpl extends	BaseDtoEntityConverterImpl<DiscountType, DiscountTypeDTO> {

	@Override
	protected DiscountTypeDTO getNewDto() {
		return new DiscountTypeDTO();
	}

	
}
