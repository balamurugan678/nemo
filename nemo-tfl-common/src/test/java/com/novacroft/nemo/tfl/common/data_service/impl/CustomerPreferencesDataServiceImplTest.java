package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CustomerPreferencesTestUtil.CUSTOMER_PREFERENCES_ID_1;
import static com.novacroft.nemo.test_support.CustomerPreferencesTestUtil.getTestCustomerPreferences1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.converter.impl.CustomerPreferencesConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CustomerPreferencesDAO;
import com.novacroft.nemo.tfl.common.domain.CustomerPreferences;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;

public class CustomerPreferencesDataServiceImplTest {
    private CustomerPreferencesDataServiceImpl service;
    private CustomerPreferencesDAO mockDAO;
    
    @Before
    public void setUp() {
        service = new CustomerPreferencesDataServiceImpl();
        mockDAO = mock(CustomerPreferencesDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new CustomerPreferencesConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test(expected=DataServiceException.class)
    public void findByCustomerIdShouldThrowException() {
        List<CustomerPreferences> twoPreferencesList = 
                        Arrays.asList(new CustomerPreferences(), new CustomerPreferences());
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(twoPreferencesList);
        service.findByCustomerId(CUSTOMER_ID_1);
    }
    
    @Test
    public void findByCustomerIdShouldReturnNull() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(new ArrayList<>());
        assertNull(service.findByCustomerId(CUSTOMER_ID_1));
    }
    
    @Test
    public void findByCustomerIdShouldReturnDTO() {
        when(mockDAO.findByQuery(anyString(), anyVararg()))
                .thenReturn(Arrays.asList(getTestCustomerPreferences1()));
        CustomerPreferencesDTO actualDTO = service.findByCustomerId(CUSTOMER_ID_1);
        assertNotNull(actualDTO);
        assertEquals(CUSTOMER_PREFERENCES_ID_1, actualDTO.getId());
    }
    
}
