package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.DataCacheKey;
import com.novacroft.nemo.common.data_cache.DataCacheValue;
import com.novacroft.nemo.common.data_cache.SystemParameterCache;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * System parameter data cache implementation
 */
public class SystemParameterCacheImpl extends AbstractDataCache implements SystemParameterCache {
    protected Map<String, String> cache = new HashMap<String, String>();

    public SystemParameterCacheImpl() {
        super();
    }

    public SystemParameterCacheImpl(Integer cacheRefreshIntervalInSeconds, CachingDataService cachingDataService) {
        super(cacheRefreshIntervalInSeconds, cachingDataService);
    }

    @Override
    public String getValue(String code) {
        SystemParameterKeyImpl key = new SystemParameterKeyImpl(code);
        SystemParameterValueImpl value = (SystemParameterValueImpl) getValue(key);
        return value.getValue();
    }

    @Override
    protected void loadCache() {
        synchronized (this.cache) {
            this.cache = new HashMap<String, String>();
            List<SystemParameterDTO> systemParameters = ((SystemParameterDataService) this.cachingDataService).findAll();
            for (SystemParameterDTO systemParameter : systemParameters) {
                this.cache.put(systemParameter.getCode(), systemParameter.getValue());
            }
        }
    }

    @Override
    protected DataCacheValue getValueFromCache(DataCacheKey key) {
        String code = ((SystemParameterKeyImpl) key).getCode();
        String value = this.cache.get(code);
        return new SystemParameterValueImpl(value);
    }
}
