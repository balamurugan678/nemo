package com.novacroft.nemo.tfl.batch.converter.impl.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AdHocDistributionRecord;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.tfl.batch.util.cubic.AdHocDistributionRecordUtil.*;

/**
 * Convert a ad-hoc distribution "raw" record to a bean
 */
@Component("adHocDistributionRecordConverter")
public class AdHocDistributionConverterImpl extends BaseConverter implements ImportRecordConverter {
    @Override
    public ImportRecord convert(String[] record) {
        return new AdHocDistributionRecord(getPrestigeId(record), getPickUpLocationAsInteger(record),
                getPickUpTimeAsDate(record), getRequestSequenceNumberAsInteger(record), getProductCodeAsInteger(record),
                getPptStartDateAsDate(record), getPptExpiryDateAsDate(record), getPrePayValueAsInteger(record),
                getCurrencyAsInteger(record), getStatusOfAttemptedAction(record), getFailureReasonCodeAsInteger(record));
    }
}
