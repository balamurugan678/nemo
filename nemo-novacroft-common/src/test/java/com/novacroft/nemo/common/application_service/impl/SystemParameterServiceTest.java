package com.novacroft.nemo.common.application_service.impl;

import static com.novacroft.nemo.test_support.SystemParameterTestUtil.BOOLEAN_CODE;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.BOOLEAN_FALSE_VALUE;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.BOOLEAN_TRUE_VALUE;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.CODE_1;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.CODE_2;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.CODE_FLOAT;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.CODE_INTEGER;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.PURPOSE_2;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.VALUE_2;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.VALUE_FLOAT;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.VALUE_INTEGER;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.getTestSystemParameterDTO2;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.getTestSystemParameterDTOList2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_cache.SystemParameterCache;
import com.novacroft.nemo.common.data_cache.impl.SystemParameterCacheImpl;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.data_service.impl.SystemParameterDataServiceImpl;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;

/**
 * Unit tests for SystemParameterService
 */
public class SystemParameterServiceTest {
    
    private SystemParameterServiceImpl mockService;

    @Before
    public void setUp() {
        mockService = mock(SystemParameterServiceImpl.class);
        
        when(mockService.getBooleanParameterValue(anyString())).thenCallRealMethod();
    }
    
    @Test
    public void shouldInitialiseCache() {
        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.cacheRefreshIntervalInSeconds = 1024;
        service.systemParameterDataService = new SystemParameterDataServiceImpl();
        assertNull(service.cache);
        service.initialiseCache();
        assertNotNull(service.cache);
    }

    @Test
    public void shouldGetSystemParameterFromDB() {
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findByCode(CODE_2)).thenReturn(getTestSystemParameterDTO2());

        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(null);

        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.systemParameterDataService = mockSystemParameterDataService;
        service.applicationProperties = mockApplicationProperties;

        SystemParameterDTO result = service.getSystemParameterFromDB(CODE_2);
        assertEquals(VALUE_2, result.getValue());
    }

    @Test
    public void shouldGetParameterPurpose() {
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findByCode(CODE_2)).thenReturn(getTestSystemParameterDTO2());

        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.systemParameterDataService = mockSystemParameterDataService;

        assertEquals(PURPOSE_2, service.getParameterPurpose(CODE_2));
    }

    @Test
    public void shouldGetParameterValueFromCache() {
        SystemParameterCache mockSystemParameterCache = mock(SystemParameterCacheImpl.class);
        when(mockSystemParameterCache.getValue(CODE_1)).thenReturn(VALUE_1);

        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(null);

        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.cache = mockSystemParameterCache;
        service.setUseCache(Boolean.TRUE);
        service.applicationProperties = mockApplicationProperties;

        assertEquals(VALUE_1, service.getParameterValue(CODE_1));
    }

    @Test
    public void shouldGetParameterValueFromDB() {
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findByCode(CODE_2)).thenReturn(getTestSystemParameterDTO2());

        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(null);

        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.systemParameterDataService = mockSystemParameterDataService;
        service.setUseCache(Boolean.FALSE);
        service.applicationProperties = mockApplicationProperties;

        assertEquals(VALUE_2, service.getParameterValue(CODE_2));
    }

    @Test
    public void shouldGetParameterValueFromProperties() {
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findByCode(CODE_2)).thenReturn(getTestSystemParameterDTO2());

        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(VALUE_2);

        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.systemParameterDataService = mockSystemParameterDataService;
        service.setUseCache(Boolean.FALSE);
        service.applicationProperties = mockApplicationProperties;

        assertEquals(VALUE_2, service.getParameterValue(CODE_2));
    }

    @Test
    public void getIntegerParameterValueShouldReturnNull() {
        SystemParameterServiceImpl service = mock(SystemParameterServiceImpl.class);
        when(service.getParameterValue(anyString())).thenReturn(null);
        when(service.getIntegerParameterValue(anyString())).thenCallRealMethod();
        assertNull(service.getIntegerParameterValue(CODE_INTEGER));
    }

    @Test
    public void getIntegerParameterValueShouldReturnValue() {
        SystemParameterServiceImpl service = mock(SystemParameterServiceImpl.class);
        when(service.getParameterValue(anyString())).thenReturn(VALUE_INTEGER);
        when(service.getIntegerParameterValue(anyString())).thenCallRealMethod();
        assertTrue(99 == service.getIntegerParameterValue(CODE_INTEGER));
    }

    @Test
    public void getFloatParameterValueShouldReturnNull() {
        SystemParameterServiceImpl service = mock(SystemParameterServiceImpl.class);
        when(service.getParameterValue(anyString())).thenReturn(null);
        when(service.getFloatParameterValue(anyString())).thenCallRealMethod();
        assertNull(service.getFloatParameterValue(CODE_FLOAT));
    }

    @Test
    public void getFloatParameterValueShouldReturnValue() {
        SystemParameterServiceImpl service = mock(SystemParameterServiceImpl.class);
        when(service.getParameterValue(anyString())).thenReturn(VALUE_FLOAT);
        when(service.getFloatParameterValue(anyString())).thenCallRealMethod();
        assertEquals(Float.valueOf(VALUE_FLOAT), service.getFloatParameterValue(CODE_FLOAT));
    }

    @Test
    public void getParameterValueFromPropertiesShould() {
        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(VALUE_1);

        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.applicationProperties = mockApplicationProperties;

        assertTrue(VALUE_1.equals(service.getParameterValueFromProperties(CODE_1)));
    }
    
    @Test
    public void getAllProperties(){
        Set<String> propertyNames = new TreeSet<String>();
        propertyNames.add(CODE_1);
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findByCode(CODE_2)).thenReturn(getTestSystemParameterDTO2());
        when(mockSystemParameterDataService.findAll()).thenReturn(getTestSystemParameterDTOList2());

        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(VALUE_1);
        when(mockApplicationProperties.stringPropertyNames()).thenReturn(propertyNames);
        
        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.applicationProperties = mockApplicationProperties;
        service.systemParameterDataService = mockSystemParameterDataService;
        
        List<SystemParameterDTO> allProperties = service.getAllProperties();
        assertEquals(CODE_1, allProperties.get(0).getCode());
    }
    
    @Test
    public void getFileProperties(){
        Set<String> propertyNames = new TreeSet<String>();
        propertyNames.add(CODE_1);

        Properties mockApplicationProperties = mock(Properties.class);
        when(mockApplicationProperties.getProperty(anyString())).thenReturn(VALUE_1);
        when(mockApplicationProperties.stringPropertyNames()).thenReturn(propertyNames);
        
        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.applicationProperties = mockApplicationProperties;
        
        List<SystemParameterDTO> fileProperties = service.getFileProperties();
        assertEquals(CODE_1, fileProperties.get(0).getCode());
    }
    
    @Test
    public void getDbProperties(){
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findAll()).thenReturn(getTestSystemParameterDTOList2());
        
        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.systemParameterDataService = mockSystemParameterDataService;
        
        List<SystemParameterDTO> dbProperties = service.getDbProperties();
        assertEquals(CODE_1, dbProperties.get(0).getCode());
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void saveDbProperties(){
        SystemParameterDataService mockSystemParameterDataService = mock(SystemParameterDataService.class);
        when(mockSystemParameterDataService.findAll()).thenReturn(getTestSystemParameterDTOList2());
        when(mockSystemParameterDataService.createOrUpdateAll(any(List.class))).thenReturn(getTestSystemParameterDTOList2());
        SystemParameterServiceImpl service = new SystemParameterServiceImpl();
        service.systemParameterDataService = mockSystemParameterDataService;
        service.saveDbProperties(getTestSystemParameterDTOList2());
        verify(mockSystemParameterDataService).createOrUpdateAll(any(List.class));
    }
    
    @Test
    public void getBooleanParameterValueShouldReturnFalseIfNull() {
        when(mockService.getParameterValue(anyString())).thenReturn(null);
        assertFalse(mockService.getBooleanParameterValue(BOOLEAN_CODE));
    }
    
    @Test
    public void getBooleanParameterValueShouldReturnFalse() {
        when(mockService.getParameterValue(anyString())).thenReturn(BOOLEAN_FALSE_VALUE);
        assertFalse(mockService.getBooleanParameterValue(BOOLEAN_CODE));
    }
    
    @Test
    public void getBooleanParameterValueShouldReturnTrue() {
        when(mockService.getParameterValue(anyString())).thenReturn(BOOLEAN_TRUE_VALUE);
        assertTrue(mockService.getBooleanParameterValue(BOOLEAN_CODE));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void savePropertiesShouldCallSaveDbProperties() {
        doCallRealMethod().when(mockService).saveProperties(any(List.class));
        doNothing().when(mockService).saveDbProperties(any(List.class));
        
        List<SystemParameterDTO> testList = getTestSystemParameterDTOList2();
        mockService.saveProperties(testList);
        
        verify(mockService).saveDbProperties(testList);
    }
}
