package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpNotificationService;

/**
 * Filters records from the CUBIC batch import ad-hoc distribution file that have have been picked up
 */
@Component("adHocLoadPickedUpNotificationFilter")
public class AdHocLoadPickedUpNotificationFilterImpl implements ImportRecordFilter {
    @Autowired
    protected AdHocLoadPickedUpNotificationService adHocLoadPickedUpNotificationService;

    @Override
    public Boolean matches(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        return isActionStatusOk(adHocDistributionRecord) && hasRefundBeenRequested(adHocDistributionRecord) && isNotificationEmailRequired(adHocDistributionRecord);
    }

    protected boolean isActionStatusOk(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicActionStatus.OK.name().equals(adHocDistributionRecord.getStatusOfAttemptedAction());
    }

    protected boolean hasRefundBeenRequested(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadPickedUpNotificationService.hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
    
    protected boolean isNotificationEmailRequired(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadPickedUpNotificationService.isNotificationEmailRequired(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
