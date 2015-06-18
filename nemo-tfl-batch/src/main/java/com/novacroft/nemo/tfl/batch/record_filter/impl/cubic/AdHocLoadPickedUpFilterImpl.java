package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.constant.cubic.CubicActionStatus;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters records from the CUBIC batch import ad-hoc distribution file that have have been picked up
 */
@Component("adHocLoadPickedUpFilter")
public class AdHocLoadPickedUpFilterImpl implements ImportRecordFilter {
    @Autowired
    protected AdHocLoadPickedUpStatusService adHocLoadPickedUpStatusService;

    @Override
    public Boolean matches(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        return isOk(adHocDistributionRecord) && hasRefundBeenRequested(adHocDistributionRecord);
    }

    protected boolean isOk(AdHocDistributionRecord adHocDistributionRecord) {
        return CubicActionStatus.OK.name().equals(adHocDistributionRecord.getStatusOfAttemptedAction());
    }

    protected boolean hasRefundBeenRequested(AdHocDistributionRecord adHocDistributionRecord) {
        return this.adHocLoadPickedUpStatusService.hasAdHocLoadBeenRequested(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
