package com.novacroft.nemo.common.data_service.impl;

import static com.novacroft.nemo.test_support.CountryTestUtil.TEST_COUNTRY_UK_CODE;
import static com.novacroft.nemo.test_support.CountryTestUtil.TEST_COUNTRY_UK_NAME;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryUK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.common.converter.impl.CountryConverterImpl;
import com.novacroft.nemo.common.data_access.CountryDAO;
import com.novacroft.nemo.common.domain.Country;

@RunWith(MockitoJUnitRunner.class)
public class CountryDataServiceImplTest {

	private CountryDataServiceImpl countryDataServiceImpl;
	@Mock
	private CountryDAO mockCountryDAO;
	private Country testCountry;
	
	@Before
	public void setUp() {
	    countryDataServiceImpl = new CountryDataServiceImpl();
	    countryDataServiceImpl.setDao(mockCountryDAO);
        countryDataServiceImpl.setConverter(new CountryConverterImpl());
        
        testCountry = getTestCountryUK();
	}
	
	@Test
	public void testFindCountryByCodeReturnsTheCountry(){
		when(mockCountryDAO.findByExampleUniqueResult(any(Country.class))).thenReturn(testCountry);
		assertEquals(TEST_COUNTRY_UK_CODE, 
		                countryDataServiceImpl.findCountryByCode(TEST_COUNTRY_UK_CODE).getCode());
		verify(mockCountryDAO).findByExampleUniqueResult(any(Country.class));
	}
	
	@Test
	public void getNewEntityShouldNotNull() {
	    assertNotNull(countryDataServiceImpl.getNewEntity());
	}
	
	@Test
	public void testFindCountryByName() {
	    when(mockCountryDAO.findByExampleUniqueResult(any(Country.class))).thenReturn(testCountry);
        assertEquals(TEST_COUNTRY_UK_CODE, 
                        countryDataServiceImpl.findCountryByName(TEST_COUNTRY_UK_NAME).getCode());
        verify(mockCountryDAO).findByExampleUniqueResult(any(Country.class));
	}
}
