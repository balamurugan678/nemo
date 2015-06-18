package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.AddressConverterImpl;
import com.novacroft.nemo.common.data_access.AddressDAO;
import com.novacroft.nemo.common.domain.Address;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for AddressDataService
 */
public class AddressDataServiceTest {
    private AddressDataServiceImpl dataService;
    private AddressConverterImpl mockConverter;
    private AddressDAO mockDao;
    
    @Before
    public void setUp() {
        dataService = new AddressDataServiceImpl();
        mockConverter = mock(AddressConverterImpl.class);
        mockDao = mock(AddressDAO.class);
        dataService.setConverter(mockConverter);
        dataService.setDao(mockDao);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }

    @Test
    public void findByEmailAddressAndPostcodeShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        
        assertNull(dataService.findByEmailAddressAndPostcode("", ""));
    }

    @Test
    public void shouldFindByEmailAddressAndPostcode() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString(), anyString(), anyString())).thenReturn(getTestAddress1());
        when(mockConverter.convertEntityToDto(any(Address.class))).thenReturn(getTestAddressDTO1());
        assertNotNull(dataService.findByEmailAddressAndPostcode("", ""));
    }
}
