package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadReadyForCollectionNotificationService;

/**
 * Filters records from the CUBIC batch import current action list file that have AdHocLoad requests ready for collection.
 */
@Component("adHocLoadReadyForCollectionNotificationFilter")
public class AdHocLoadReadyForCollectionNotificationFilterImpl implements ImportRecordFilter {

    @Autowired
    protected AdHocLoadReadyForCollectionNotificationService adHocLoadReadyForCollectionNotificationService;

    @Override
    public Boolean matches(ImportRecord record) {
        CurrentActionRecord currentActionRecord = (CurrentActionRecord) record;
       return hasRefundBeenRequested(currentActionRecord) && isNotificationEmailRequired(currentActionRecord);
    }
    
    protected boolean hasRefundBeenRequested(CurrentActionRecord adHocDistributionRecord) {
        return this.adHocLoadReadyForCollectionNotificationService
                .hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                        adHocDistributionRecord.getPrestigeId());
    }
    
    protected boolean isNotificationEmailRequired(CurrentActionRecord adHocDistributionRecord) {
        return this.adHocLoadReadyForCollectionNotificationService.isNotificationEmailRequired(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
