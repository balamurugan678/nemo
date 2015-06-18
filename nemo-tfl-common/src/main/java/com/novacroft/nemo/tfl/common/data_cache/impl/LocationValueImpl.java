package com.novacroft.nemo.tfl.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.DataCacheValue;

/**
 * Location data cache value implementation
 */
public class LocationValueImpl implements DataCacheValue {
    protected String value;

    public LocationValueImpl() {
    }

    public LocationValueImpl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
