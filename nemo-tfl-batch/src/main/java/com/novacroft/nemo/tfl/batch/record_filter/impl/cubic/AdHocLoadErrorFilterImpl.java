package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.constant.cubic.CubicFailureReasonCode;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters records from the CUBIC batch import ad-hoc distribution file that have errors.
 */
@Component("adHocLoadErrorFilter")
public class AdHocLoadErrorFilterImpl implements ImportRecordFilter {
    @Autowired
    protected AdHocLoadErrorStatusService adHocLoadErrorStatusService;

    @Override
    public Boolean matches(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        return hasFailed(adHocDistributionRecord) && hasError(adHocDistributionRecord) &&
                hasRefundBeenRequested(adHocDistributionRecord);
    }

    protected boolean hasFailed(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicActionStatus.FAILED.name().equals(adHocDistributionRecord.getStatusOfAttemptedAction());
    }

    protected boolean hasError(AdHocDistributionRecord adHocDistributionRecord) {
        return adHocDistributionRecord.getFailureReasonCode() != null &&
                !CubicFailureReasonCode.PICK_UP_WINDOW_EXPIRED.code().equals(adHocDistributionRecord.getFailureReasonCode());
    }

    protected boolean hasRefundBeenRequested(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadErrorStatusService.hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
