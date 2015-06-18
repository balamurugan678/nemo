package com.novacroft.nemo.tfl.batch.converter.impl.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import com.novacroft.nemo.tfl.batch.domain.cubic.AutoLoadPerformedRecord;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.tfl.batch.util.cubic.AutoLoadPerformedRecordUtil.*;

/**
 * Convert an auto load performed "raw" record to a bean
 */
@Component("autoLoadPerformedRecordConverter")
public class AutoLoadPerformedConverterImpl extends BaseConverter implements ImportRecordConverter {
    @Override
    public ImportRecord convert(String[] record) {
        return new AutoLoadPerformedRecord(getPrestigeId(record), getPickUpLocationAsIntegerIgnoringUnknown(record),
                getBusRouteIdIgnoringUnknown(record), getPickUpTimeAsDate(record),
                getAutoLoadConfigurationAsIntegerIgnoringUnknown(record), getTopUpAmountAddedAsInteger(record),
                getCurrencyAsIntegerIgnoringUnknown(record));
    }
}
