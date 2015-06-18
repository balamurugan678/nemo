package com.novacroft.nemo.tfl.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.DataCacheKey;

/**
 * Location data cache key implementation
 */
public class LocationKeyImpl implements DataCacheKey {
    protected Long code;

    public LocationKeyImpl(Long code) {
        this.code = code;
    }

    public LocationKeyImpl() {

    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
