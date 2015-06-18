package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.DataCacheValue;

/**
 * System parameter data cache value implementation
 */
public class SystemParameterValueImpl implements DataCacheValue {
    protected String value;

    public SystemParameterValueImpl() {
    }

    public SystemParameterValueImpl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
