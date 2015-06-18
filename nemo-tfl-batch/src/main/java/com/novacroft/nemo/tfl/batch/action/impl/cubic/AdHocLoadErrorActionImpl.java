package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Action that is performed when a CUBIC batch import ad-hoc distribution file refund in error record is encountered.
 */
@Component("adHocLoadErrorAction")
public class AdHocLoadErrorActionImpl implements ImportRecordAction {
    @Autowired
    protected AdHocLoadErrorStatusService adHocLoadErrorStatusService;

    @Override
    public void handle(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        this.adHocLoadErrorStatusService.updateStatusToFailed(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
