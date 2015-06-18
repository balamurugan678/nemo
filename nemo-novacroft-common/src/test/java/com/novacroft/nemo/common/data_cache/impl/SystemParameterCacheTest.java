package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.DataCacheKey;
import com.novacroft.nemo.common.data_cache.SystemParameterCache;
import com.novacroft.nemo.common.data_service.impl.SystemParameterDataServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * SystemParameterCache unit tests
 */
public class SystemParameterCacheTest {

    @Test
    public void shouldInitialiseAttributesOnInstantiation() {
        CachingDataService mockCachingDataService = mock(SystemParameterDataServiceImpl.class);
        SystemParameterCache systemParameterCache = new SystemParameterCacheImpl(1024, mockCachingDataService);
        assertNotNull(((SystemParameterCacheImpl) systemParameterCache).cacheRefreshIntervalInSeconds);
        assertNotNull(((SystemParameterCacheImpl) systemParameterCache).cachingDataService);
    }

    @Test
    public void shouldGetValue() {
        final String SYSTEM_PARAMETER_CODE = "test-code";
        final String SYSTEM_PARAMETER_VALUE = "test-value";

        SystemParameterKeyImpl key = new SystemParameterKeyImpl(SYSTEM_PARAMETER_CODE);
        SystemParameterValueImpl value = new SystemParameterValueImpl(SYSTEM_PARAMETER_VALUE);

        SystemParameterCacheImpl systemParameterCache = mock(SystemParameterCacheImpl.class);
        when(systemParameterCache.getValue(anyString())).thenCallRealMethod();
        when(systemParameterCache.getValue(any(DataCacheKey.class))).thenReturn(value);

        String result = systemParameterCache.getValue(SYSTEM_PARAMETER_CODE);
        assertEquals(SYSTEM_PARAMETER_VALUE, result);
    }
}
