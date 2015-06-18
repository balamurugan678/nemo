package com.novacroft.nemo.common.data_cache;

import java.util.Date;

/**
 * Generic cache data service specification.
 */
public interface CachingDataService {
    Date findLatestCreatedDateTime();

    Date findLatestModifiedDateTime();
}
