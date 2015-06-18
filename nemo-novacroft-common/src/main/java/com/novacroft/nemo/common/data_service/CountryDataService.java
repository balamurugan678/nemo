package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * Country data service
 */
public interface CountryDataService extends BaseDataService<Country, CountryDTO> {
	
	CountryDTO findCountryByCode(String code);
	
	CountryDTO findCountryByName(String name);
}
