package com.novacroft.nemo.common.data_cache;

/**
 * System parameter data cache specification
 */
public interface SystemParameterCache extends DataCache {
    String getValue(String code);
}
