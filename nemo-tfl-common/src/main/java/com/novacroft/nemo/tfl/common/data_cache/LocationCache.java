package com.novacroft.nemo.tfl.common.data_cache;

import com.novacroft.nemo.common.data_cache.DataCache;

/**
 * System parameter data cache specification
 */
public interface LocationCache extends DataCache {
    String getValue(Long code);
}
