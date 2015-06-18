package com.novacroft.nemo.tfl.batch.converter.impl.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadChangeRecord;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadChangeRecordUtil.*;

/**
 * Convert an auto load change "raw" record to a bean
 */
@Component("autoLoadChangeRecordConverter")
public class AutoLoadChangeConverterImpl extends BaseConverter implements ImportRecordConverter {
    @Override
    public ImportRecord convert(String[] record) {
        return new AutoLoadChangeRecord(getPrestigeId(record), getPickUpLocationAsInteger(record), getPickUpTimeAsDate(record),
                getRequestSequenceNumberAsInteger(record), getPreviousAutoLoadConfigurationAsInteger(record),
                getNewAutoLoadConfigurationAsInteger(record), getStatusOfAttemptedAction(record),
                getFailureReasonCodeAsInteger(record));
    }
}
