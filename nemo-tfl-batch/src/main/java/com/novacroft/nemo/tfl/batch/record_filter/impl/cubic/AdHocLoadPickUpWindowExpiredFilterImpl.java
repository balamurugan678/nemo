package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicFailureReasonCode;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters CUBIC ad-hoc distribution file records that have an expired pick up window.
 */
@Component("adHocLoadPickUpWindowExpiredFilter")
public class AdHocLoadPickUpWindowExpiredFilterImpl implements ImportRecordFilter {
    @Autowired
    protected AdHocLoadPickUpWindowExpiredStatusService adHocLoadPickUpWindowExpiredStatusService;

    @Override
    public Boolean matches(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        return hasFailed(adHocDistributionRecord) && hasPickUpWindowExpired(adHocDistributionRecord) &&
                hasRefundBeenRequested(adHocDistributionRecord);
    }

    protected boolean hasFailed(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicActionStatus.FAILED.name().equals(adHocDistributionRecord.getStatusOfAttemptedAction());
    }

    protected boolean hasPickUpWindowExpired(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code().equals(adHocDistributionRecord.getFailureReasonCode());
    }

    protected boolean hasRefundBeenRequested(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadPickUpWindowExpiredStatusService
                .hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                        adHocDistributionRecord.getPrestigeId());
    }
}
