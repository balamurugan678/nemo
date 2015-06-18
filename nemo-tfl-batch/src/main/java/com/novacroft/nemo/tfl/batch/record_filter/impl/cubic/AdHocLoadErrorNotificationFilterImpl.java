package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicFailureReasonCode;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorNotificationService;

/**
 * Filters records from the CUBIC batch import ad-hoc distribution file that have errors.
 */
@Component("adHocLoadErrorNotificationFilter")
public class AdHocLoadErrorNotificationFilterImpl implements ImportRecordFilter {
    @Autowired
    protected AdHocLoadErrorNotificationService adHocLoadErrorNotificationService;

    @Override
    public Boolean matches(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        return hasFailed(adHocDistributionRecord) && hasError(adHocDistributionRecord) &&
                hasRefundBeenRequested(adHocDistributionRecord) && isNotificationEmailRequired(adHocDistributionRecord);
    }

    protected boolean hasFailed(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicActionStatus.FAILED.name().equals(adHocDistributionRecord.getStatusOfAttemptedAction());
    }

    protected boolean hasError(AdHocDistributionRecord adHocDistributionRecord) {
        return adHocDistributionRecord.getFailureReasonCode() != null &&
                !CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code().equals(adHocDistributionRecord.getFailureReasonCode());
    }

    protected boolean hasRefundBeenRequested(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadErrorNotificationService.hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
    
    protected boolean isNotificationEmailRequired(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadErrorNotificationService.isNotificationEmailRequired(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
