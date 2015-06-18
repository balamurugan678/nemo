package com.novacroft.nemo.common.data_cache.impl;

import com.novacroft.nemo.common.data_cache.DataCacheKey;

/**
 * System parameter data cache key implementation
 */
public class SystemParameterKeyImpl implements DataCacheKey {
    protected String code;

    public SystemParameterKeyImpl(String code) {
        this.code = code;
    }

    public SystemParameterKeyImpl() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
