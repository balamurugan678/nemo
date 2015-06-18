package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickedUpStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Action that is performed when a CUBIC batch import ad-hoc distribution file picked up record is encountered.
 */
@Component("adHocLoadPickedUpAction")
public class AdHocLoadPickedUpActionImpl implements ImportRecordAction {
    @Autowired
    protected AdHocLoadPickedUpStatusService adHocLoadPickedUpStatusService;

    @Override
    public void handle(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        this.adHocLoadPickedUpStatusService.updateStatusToPickedUp(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
