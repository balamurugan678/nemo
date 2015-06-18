package com.novacroft.nemo.common.data_cache;

/**
 * Generic data cache specification
 */
public interface DataCache {
    DataCacheValue getValue(DataCacheKey key);
}
