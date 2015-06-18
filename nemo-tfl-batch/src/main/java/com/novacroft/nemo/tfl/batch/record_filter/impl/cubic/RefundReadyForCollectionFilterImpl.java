package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadReadyForCollectionNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters records from the CUBIC batch import current action list file that have refunds ready for collection.
 */
@Component("refundReadyForCollectionFilter")
public class RefundReadyForCollectionFilterImpl implements ImportRecordFilter {

    @Autowired
    protected AdHocLoadReadyForCollectionNotificationService adHocLoadReadyForCollectionNotificationService;

    @Override
    public Boolean matches(ImportRecord record) {
        CurrentActionRecord currentActionRecord = (CurrentActionRecord) record;
        return this.adHocLoadReadyForCollectionNotificationService
                .hasAdHocLoadBeenRequested(Integer.valueOf(currentActionRecord.getRequestSequenceNumber()),
                        currentActionRecord.getPrestigeId());
    }
}
