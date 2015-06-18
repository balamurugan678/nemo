package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.DataCacheKey;
import com.novacroft.nemo.common.data_cache.DataCacheValue;
import org.junit.Test;

import static com.novacroft.nemo.test_support.DateTestUtil.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * AbstractDataCache unit tests
 */
public class AbstractDataCacheTest {

    @Test
    public void isRefreshRequiredShouldReturnTrueWithNullLastCacheRefreshAt() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        when(mockCachingDataService.findLatestCreatedDateTime()).thenReturn(getAug19());
        when(mockCachingDataService.findLatestModifiedDateTime()).thenReturn(getAug19());

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.lastCacheRefreshAt = null;
        mockDataCache.nextCacheRefreshAt = getAug19();

        assertTrue(mockDataCache.isRefreshRequired());
    }

    @Test
    public void isRefreshRequiredShouldReturnTrueWithNullNextCacheRefreshAt() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        when(mockCachingDataService.findLatestCreatedDateTime()).thenReturn(getAug19());
        when(mockCachingDataService.findLatestModifiedDateTime()).thenReturn(getAug19());

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.lastCacheRefreshAt = getAug19();
        mockDataCache.nextCacheRefreshAt = null;

        assertTrue(mockDataCache.isRefreshRequired());
    }

    @Test
    public void isRefreshRequiredShouldReturnTrueWithNullLatestCreatedOn() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        when(mockCachingDataService.findLatestCreatedDateTime()).thenReturn(null);
        when(mockCachingDataService.findLatestModifiedDateTime()).thenReturn(getAug19());

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.lastCacheRefreshAt = getAug19();
        mockDataCache.nextCacheRefreshAt = getAug19();

        assertTrue(mockDataCache.isRefreshRequired());
    }

    @Test
    public void isRefreshRequiredShouldReturnTrueWithNullLatestModifiedOn() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        when(mockCachingDataService.findLatestCreatedDateTime()).thenReturn(getAug19());
        when(mockCachingDataService.findLatestModifiedDateTime()).thenReturn(null);

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.lastCacheRefreshAt = getAug19();
        mockDataCache.nextCacheRefreshAt = getAug19();

        assertTrue(mockDataCache.isRefreshRequired());
    }

    @Test
    public void isRefreshRequiredShouldReturnTrueWithCreatedData() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        when(mockCachingDataService.findLatestCreatedDateTime()).thenReturn(getAug21());
        when(mockCachingDataService.findLatestModifiedDateTime()).thenReturn(getAug19());

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.lastCacheRefreshAt = getAug20();
        mockDataCache.nextCacheRefreshAt = getAug20();

        assertTrue(mockDataCache.isRefreshRequired());
    }

    @Test
    public void isRefreshRequiredShouldReturnTrueWithUpdatedData() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        when(mockCachingDataService.findLatestCreatedDateTime()).thenReturn(getAug19());
        when(mockCachingDataService.findLatestModifiedDateTime()).thenReturn(getAug21());

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.lastCacheRefreshAt = getAug20();
        mockDataCache.nextCacheRefreshAt = getAug20();

        assertTrue(mockDataCache.isRefreshRequired());
    }

    @Test
    public void shouldRefresh() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        doNothing().when(mockDataCache).loadCache();
        mockDataCache.cachingDataService = mockCachingDataService;
        mockDataCache.cacheRefreshIntervalInSeconds = 1024;

        mockDataCache.refreshCache();

        verify(mockDataCache).loadCache();
    }

    @Test
    public void isTimeToRefreshCacheShouldReturnTrueWithNullNextCacheRefreshAt() {
        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.nextCacheRefreshAt = null;
        assertTrue(mockDataCache.isTimeToRefreshCache());
    }

    @Test
    public void isTimeToRefreshCacheShouldReturnTrueWithPastNextCacheRefreshAt() {
        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        mockDataCache.nextCacheRefreshAt = getAug19();
        assertTrue(mockDataCache.isTimeToRefreshCache());
    }

    @Test
    public void shouldGetValueWithoutCacheRefresh() {
        DataCacheKey mockKey = mock(DataCacheKey.class, CALLS_REAL_METHODS);
        DataCacheValue mockValue = mock(DataCacheValue.class, CALLS_REAL_METHODS);

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class);
        when(mockDataCache.getValue(mockKey)).thenCallRealMethod();
        when(mockDataCache.isRefreshRequired()).thenReturn(Boolean.FALSE);
        when(mockDataCache.getValueFromCache(mockKey)).thenReturn(mockValue);

        DataCacheValue result = mockDataCache.getValue(mockKey);

        verify(mockDataCache, atLeastOnce()).getValue(mockKey);
    }

    @Test
    public void shouldGetValueWithCacheRefresh() {
        DataCacheKey mockKey = mock(DataCacheKey.class, CALLS_REAL_METHODS);
        DataCacheValue mockValue = mock(DataCacheValue.class, CALLS_REAL_METHODS);

        AbstractDataCache mockDataCache = mock(AbstractDataCache.class);
        when(mockDataCache.getValue(mockKey)).thenCallRealMethod();
        when(mockDataCache.isRefreshRequired()).thenReturn(Boolean.TRUE);
        when(mockDataCache.getValueFromCache(mockKey)).thenReturn(mockValue);
        doNothing().when(mockDataCache).refreshCache();

        DataCacheValue result = mockDataCache.getValue(mockKey);

        verify(mockDataCache, atLeastOnce()).getValue(mockKey);
    }

    @Test
    public void shouldForceRefresh() {
        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        assert (null == mockDataCache.nextCacheRefreshAt);
        mockDataCache.forceCacheRefresh();
        assert (null != mockDataCache.nextCacheRefreshAt);
    }

    @Test
    public void shouldSetCacheRefreshIntervalInSeconds() {
        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        assert (null == mockDataCache.cacheRefreshIntervalInSeconds);
        mockDataCache.setCacheRefreshIntervalInSeconds(42);
        assert (42 == mockDataCache.cacheRefreshIntervalInSeconds);
    }

    @Test
    public void shouldSetCachingDataService() {
        CachingDataService mockCachingDataService = mock(CachingDataService.class);
        AbstractDataCache mockDataCache = mock(AbstractDataCache.class, CALLS_REAL_METHODS);
        assert (null == mockDataCache.cachingDataService);
        mockDataCache.setCachingDataService(mockCachingDataService);
        assert (null != mockDataCache.cachingDataService);
    }
}
