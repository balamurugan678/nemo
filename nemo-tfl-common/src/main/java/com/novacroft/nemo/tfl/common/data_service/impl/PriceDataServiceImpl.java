package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PriceConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PriceDAO;
import com.novacroft.nemo.tfl.common.data_service.PriceDataService;
import com.novacroft.nemo.tfl.common.domain.Price;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;

@Service(value = "priceDataService")
@Transactional(readOnly = true)
public class PriceDataServiceImpl extends BaseDataServiceImpl<Price, PriceDTO>
		implements PriceDataService {

	@Override
	public Price getNewEntity() {
		return new Price();
	}

	@Autowired
    public void setDao(PriceDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PriceConverterImpl converter) {
        this.converter = converter;
    }

}
