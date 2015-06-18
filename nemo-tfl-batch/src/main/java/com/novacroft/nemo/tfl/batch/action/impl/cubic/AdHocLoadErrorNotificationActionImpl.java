package com.novacroft.nemo.tfl.batch.action.impl.cubic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.batch.action.cubic.ImportRecordAction;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadErrorNotificationService;

/**
 * Action that is performed when a CUBIC batch import ad-hoc distribution file refund in error record is encountered.
 */
@Component("adHocLoadErrorNotificationAction")
public class AdHocLoadErrorNotificationActionImpl implements ImportRecordAction {
    @Autowired
    protected AdHocLoadErrorNotificationService adHocLoadErrorNotificationService;

    @Override
    public void handle(ImportRecord record) {
        AdHocDistributionRecord adHocDistributionRecord = (AdHocDistributionRecord) record;
        this.adHocLoadErrorNotificationService.notifyCustomer(adHocDistributionRecord.getRequestSequenceNumber(),
                adHocDistributionRecord.getPrestigeId());
    }
}
