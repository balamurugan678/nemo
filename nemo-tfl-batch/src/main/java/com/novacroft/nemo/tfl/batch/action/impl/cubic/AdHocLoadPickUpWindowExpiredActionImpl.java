package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Action that is performed when a CUBIC batch import ad-hoc distribution file pick up window expired record is encountered.
 */
@Component("adHocLoadPickUpWindowExpiredAction")
public class AdHocLoadPickUpWindowExpiredActionImpl implements ImportRecordAction {
    @Autowired
    protected AdHocLoadPickUpWindowExpiredStatusService adHocLoadPickUpWindowExpiredStatusService;

    @Override
    public void handle(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        this.adHocLoadPickUpWindowExpiredStatusService
                .updateStatusToPickUpExpired(adHocDistributionRecord.getRequestSequenceNumber(),
                        adHocDistributionRecord.getPrestigeId());
    }
}
