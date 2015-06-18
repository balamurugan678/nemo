package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.DataCache;
import com.novacroft.nemo.common.data_cache.DataCacheKey;
import com.novacroft.nemo.common.data_cache.DataCacheValue;

import java.util.Date;

import static com.novacroft.nemo.common.constant.DateConstant.FIVE_MINUTES_IN_SECONDS;
import static com.novacroft.nemo.common.constant.DateConstant.MILLISECONDS_IN_A_SECOND;

/**
 * <p>Generic data cache abstract implementation.</p>
 *
 * <p>This abstract implementation provides infrastructure for caching data.  The structure of the cache is left to the
 * implementing class (likely to be a map).  The underlying data is checked periodically and the cache is refreshed if any
 * changes are found.</p>
 *
 * <p><code>cacheRefreshIntervalInSeconds</code> controls how often the underlying data is checked for changes.</p>
 *
 * <p><code>cachingDataService</code> exposes methods that allow the underlying data to be checked for creates and updates..</p>
 */
public abstract class AbstractDataCache implements DataCache {
    protected Integer cacheRefreshIntervalInSeconds = FIVE_MINUTES_IN_SECONDS;
    protected CachingDataService cachingDataService;
    protected Date nextCacheRefreshAt = null;
    protected Date lastCacheRefreshAt = null;

    public AbstractDataCache() {
    }

    public AbstractDataCache(Integer cacheRefreshIntervalInSeconds, CachingDataService cachingDataService) {
        this.cachingDataService = cachingDataService;
        this.cacheRefreshIntervalInSeconds = cacheRefreshIntervalInSeconds;
    }

    /**
     * Refresh the cache if a refresh is due and then retrieve a cached value.
     *
     * @param key Key for cached value of interest.
     * @return Cached value.
     */
    public DataCacheValue getValue(DataCacheKey key) {
        refreshCacheIfNeeded();
        return getValueFromCache(key);
    }

    /**
     * <p>Force a cache refresh.</p>
     */
    public void forceCacheRefresh() {
        this.nextCacheRefreshAt = new Date();
    }

    protected void refreshCacheIfNeeded() {
        if (isTimeToRefreshCache()) {
            refreshCache();
        }
    }

    protected boolean isTimeToRefreshCache() {
        return this.nextCacheRefreshAt == null || System.currentTimeMillis() >= this.nextCacheRefreshAt.getTime();
    }

    protected void refreshCache() {
        Date refreshedAt = new Date();
        if (isRefreshRequired()) {
            loadCache();
        }
        this.lastCacheRefreshAt = refreshedAt;
        this.nextCacheRefreshAt =
                new Date(refreshedAt.getTime() + (this.cacheRefreshIntervalInSeconds * MILLISECONDS_IN_A_SECOND));
    }

    protected boolean isRefreshRequired() {
        Date latestCreatedOn = this.cachingDataService.findLatestCreatedDateTime();
        Date latestModifiedOn = this.cachingDataService.findLatestModifiedDateTime();
        return (latestCreatedOn == null || latestModifiedOn == null ||
                this.lastCacheRefreshAt == null || this.nextCacheRefreshAt == null ||
                latestCreatedOn.getTime() > this.lastCacheRefreshAt.getTime() ||
                latestModifiedOn.getTime() > this.lastCacheRefreshAt.getTime());
    }

    /**
     * <p>Implement this method to load the data cache.  Will be called on cache initial load and cache refresh.</p>
     *
     * <p>Note: the <code>loadCache</code> implementation should probably synchronize the cache to avoid cache accesses when in
     * an indeterminate state.</p>
     */
    protected abstract void loadCache();

    /**
     * Implement this method to retrieve a cached value.
     *
     * @param key Key for cached value of interest.
     * @return Cached value.
     */
    protected abstract DataCacheValue getValueFromCache(DataCacheKey key);

    /**
     * <p>Sets the length of time in seconds between cache refreshes.</p>
     *
     * @param cacheRefreshIntervalInSeconds Length of time between cache refreshes.  Defaults to five minutes.
     */
    public void setCacheRefreshIntervalInSeconds(Integer cacheRefreshIntervalInSeconds) {
        this.cacheRefreshIntervalInSeconds = cacheRefreshIntervalInSeconds;
    }

    /**
     * <p>Data service mutator.</p>
     *
     * @param cachingDataService Cached data service.
     */
    public void setCachingDataService(CachingDataService cachingDataService) {
        this.cachingDataService = cachingDataService;
    }
}
