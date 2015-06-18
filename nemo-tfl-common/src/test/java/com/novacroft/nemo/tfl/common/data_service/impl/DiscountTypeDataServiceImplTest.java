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

import com.novacroft.nemo.tfl.common.converter.impl.DiscountTypeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.DiscountTypeDAO;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;

public class DiscountTypeDataServiceImplTest {
    
    private DiscountTypeDataServiceImpl service;
    private DiscountTypeDAO mockDao;
    private DiscountTypeConverterImpl mockConverter;

    @Before
    public void setUp() throws Exception {
        service = new DiscountTypeDataServiceImpl();
        mockDao = mock(DiscountTypeDAO.class);
        mockConverter = mock(DiscountTypeConverterImpl.class);
        
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
        when(mockDao.findByQueryUniqueResult(anyString(), any(Object[].class))).thenReturn(new DiscountType());
        when(mockConverter.convertEntityToDto(any(DiscountType.class))).thenReturn(new DiscountTypeDTO());
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
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(new DiscountType());
        when(mockConverter.convertEntityToDto(any(DiscountType.class))).thenReturn(new DiscountTypeDTO());
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
        ArrayList<DiscountType> discountTypes = new ArrayList<DiscountType>();
        discountTypes.add(new DiscountType());
        when(mockDao.findByQueryUsingNamedParameters(anyString(), (java.util.Map<String, Object>) any(Map.class))).thenReturn(discountTypes);
        when(mockConverter.convertEntityToDto(any(DiscountType.class))).thenReturn(new DiscountTypeDTO());
        List<DiscountTypeDTO> dtos = service.findAll();
        assertNotNull(dtos);
        assertTrue(dtos.size() > 0);
    }
}
