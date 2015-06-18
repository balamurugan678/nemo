package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.AdHocLoadReadyForCollectionStatusService;

/**
 * Filters records from the CUBIC batch import current action list file that have AdHocLoad requests ready for collection.
 */
@Component("adHocLoadReadyForCollectionFilter")
public class AdHocLoadReadyForCollectionFilterImpl implements ImportRecordFilter {

    @Autowired
    protected AdHocLoadReadyForCollectionStatusService adHocLoadReadyForCollectionStatusService;

    @Override
    public Boolean matches(ImportRecord record) {
        CurrentActionRecord currentActionRecord = (CurrentActionRecord) record;
        return this.adHocLoadReadyForCollectionStatusService
                .hasAdHocLoadBeenRequested(Integer.valueOf(currentActionRecord.getRequestSequenceNumber()),
                        currentActionRecord.getPrestigeId());
    }
}
