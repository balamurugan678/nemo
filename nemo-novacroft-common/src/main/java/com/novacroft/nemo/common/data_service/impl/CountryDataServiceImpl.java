package com.novacroft.nemo.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.converter.impl.CountryConverterImpl;
import com.novacroft.nemo.common.data_access.CountryDAO;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * Country data service
 */
@Service(value = "countryDataService")
@Transactional(readOnly = true)
public class CountryDataServiceImpl extends BaseDataServiceImpl<Country, CountryDTO> implements CountryDataService {
    @Autowired
    public void setDao(CountryDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CountryConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Country getNewEntity() {
        return new Country();
    }

    
	@Override
	public CountryDTO findCountryByCode(String code) {
		Country country = new Country();
		country.setCode(code);
		return converter.convertEntityToDto(dao.findByExampleUniqueResult(country));
	}

    @Override
    public CountryDTO findCountryByName(String name) {
        Country country = new Country();
        country.setName(name);
        return converter.convertEntityToDto(dao.findByExampleUniqueResult(country));
    }
    
    
}
