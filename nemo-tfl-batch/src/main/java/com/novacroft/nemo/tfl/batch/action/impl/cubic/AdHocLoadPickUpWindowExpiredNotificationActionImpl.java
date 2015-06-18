package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadPickUpWindowExpiredNotificationService;

/**
 * Action that is performed when a CUBIC batch import ad-hoc distribution file pick up window expired record is encountered.
 */
@Component("adHocLoadPickUpWindowExpiredNotificationAction")
public class AdHocLoadPickUpWindowExpiredNotificationActionImpl implements ImportRecordAction {
    @Autowired
    protected AdHocLoadPickUpWindowExpiredNotificationService adHocLoadPickUpWindowExpiredNotificationService;

    @Override
    public void handle(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        this.adHocLoadPickUpWindowExpiredNotificationService
                .notifyCustomer(adHocDistributionRecord.getRequestSequenceNumber(),
                        adHocDistributionRecord.getPrestigeId());
    }
}
