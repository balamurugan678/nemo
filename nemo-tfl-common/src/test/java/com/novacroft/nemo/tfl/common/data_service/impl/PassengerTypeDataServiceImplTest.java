package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Map;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.PassengerTypeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PassengerTypeDAO;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;

public class PassengerTypeDataServiceImplTest {
    
    private PassengerTypeDataServiceImpl service;
    private PassengerTypeDAO mockDao;
    private PassengerTypeConverterImpl mockConverter;

    @Before
    public void setUp() throws Exception {
        service = new PassengerTypeDataServiceImpl();
        mockDao = mock(PassengerTypeDAO.class);
        mockConverter = mock(PassengerTypeConverterImpl.class);
        
        service.setDao(mockDao);
        
        service.setConverter(mockConverter);
    }

    @Test
    public void testGetNewEntity() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void testFindByNameReturnNull(){
        when(mockDao.findByQueryUniqueResult(anyString(), any(Object[].class))).thenReturn(null);
        assertNull(service.findByName(""));
    }
    
    @Test
    public void testFindByName(){
        when(mockDao.findByQueryUniqueResult(anyString(), any(Object[].class))).thenReturn(new PassengerType());
        when(mockConverter.convertEntityToDto(any(PassengerType.class))).thenReturn(new PassengerTypeDTO());
        assertNotNull(service.findByName(""));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testFindByCodeReturnNull(){
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(null);
        assertNull(service.findByCode("", new Date()));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testFindByCode(){
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(new PassengerType());
        when(mockConverter.convertEntityToDto(any(PassengerType.class))).thenReturn(new PassengerTypeDTO());
        assertNotNull(service.findByCode("", null));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testFindAllReturnNull(){
        when(mockDao.findByQueryUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(null);
        assertNull(service.findAll());
        
        when(mockDao.findByQueryUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(new ArrayList<DiscountType>());
        assertNull(service.findAll());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindAll(){
        ArrayList<PassengerType> passengerTypes = new ArrayList<PassengerType>();
        passengerTypes.add(new PassengerType());
        when(mockDao.findByQueryUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(passengerTypes);
        when(mockConverter.convertEntityToDto(any(PassengerType.class))).thenReturn(new PassengerTypeDTO());
        List<PassengerTypeDTO> dtos = service.findAll();
        assertNotNull(dtos);
        assertTrue(dtos.size() > 0);
    }

}
