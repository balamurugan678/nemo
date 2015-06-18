package com.novacroft.nemo.tfl.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.DataCacheKey;
import com.novacroft.nemo.common.data_cache.DataCacheValue;
import com.novacroft.nemo.common.data_cache.impl.AbstractDataCache;
import com.novacroft.nemo.tfl.common.data_cache.LocationCache;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Location data cache implementation
 */
public class LocationCacheImpl extends AbstractDataCache implements LocationCache {
    protected Map<Long, String> cache = new HashMap<Long, String>();

    public LocationCacheImpl() {
        super();
    }

    public LocationCacheImpl(Integer cacheRefreshIntervalInSeconds, CachingDataService cachingDataService) {
        super(cacheRefreshIntervalInSeconds, cachingDataService);
    }

    @Override
    public String getValue(Long code) {
        LocationKeyImpl key = new LocationKeyImpl(code);
        LocationValueImpl value = (LocationValueImpl) getValue(key);
        return value.getValue();
    }

    @Override
    protected void loadCache() {
        synchronized (this.cache) {
            this.cache = new HashMap<Long, String>();
            List<LocationDTO> locations = ((LocationDataService) this.cachingDataService).findAll();
            for (LocationDTO location : locations) {
                this.cache.put(location.getId(), location.getName());
            }
        }
    }

    @Override
    protected DataCacheValue getValueFromCache(DataCacheKey key) {
        Long code = ((LocationKeyImpl) key).getCode();
        String value = this.cache.get(code);
        return new LocationValueImpl(value);
    }
}
