package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicFailureReasonCode;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredNotificationService;

/**
 * Filters CUBIC ad-hoc distribution file records that have an expired pick up window.
 */
@Component("adHocLoadPickUpWindowExpiredNotificationFilter")
public class AdHocLoadPickUpWindowExpiredNotificationFilterImpl implements ImportRecordFilter {
    @Autowired
    protected AdHocLoadPickUpWindowExpiredNotificationService adHocLoadPickUpWindowExpiredNotificationService;

    @Override
    public Boolean matches(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        return hasFailed(adHocDistributionRecord) && hasPickUpWindowExpired(adHocDistributionRecord) &&
                hasRefundBeenRequested(adHocDistributionRecord) && isNotificationEmailRequired(adHocDistributionRecord);
    }

    protected boolean hasFailed(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicActionStatus.FAILED.name().equals(adHocDistributionRecord.getStatusOfAttemptedAction());
    }

    protected boolean hasPickUpWindowExpired(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code().equals(adHocDistributionRecord.getFailureReasonCode());
    }

    protected boolean hasRefundBeenRequested(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadPickUpWindowExpiredNotificationService
                .hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                        adHocDistributionRecord.getPrestigeId());
    }
    
    protected boolean isNotificationEmailRequired(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadPickUpWindowExpiredNotificationService.isNotificationEmailRequired(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
