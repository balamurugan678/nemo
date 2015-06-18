package com.novacroft.nemo.tfl.batch.record_filter.impl.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadPerformedRecord;
import com.novacroft.nemo.tfl.batch.record_filter.ImportRecordFilter;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.PayForAutoLoadPerformedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filters records from the CUBIC batch import auto load performed file that are for cards in the DB.
 */
@Component("autoLoadPerformedForExistingCardRecordFilter")
public class AutoLoadPerformedForExistingCardRecordFilterImpl implements ImportRecordFilter {

    @Autowired
    protected PayForAutoLoadPerformedService payForAutoLoadPerformedService;

    @Override
    public Boolean matches(ImportRecord record) {
        AutoLoadPerformedRecord autoLoadPerformedRecord = (AutoLoadPerformedRecord) record;
        return this.payForAutoLoadPerformedService.isExistingCard(autoLoadPerformedRecord.getPrestigeId());
    }
}
