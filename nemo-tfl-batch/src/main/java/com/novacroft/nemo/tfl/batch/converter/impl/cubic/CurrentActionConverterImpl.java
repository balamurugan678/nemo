package com.novacroft.nemo.tfl.batch.converter.impl.cubic;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.BaseConverter;
import com.novacroft.nemo.tfl.batch.domain.cubic.CurrentActionRecord;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.tfl.batch.util.cubic.CurrentActionRecordUtil.*;

/**
 * Convert a "raw" current action list record to a bean
 */
@Component("currentActionRecordConverter")
public class CurrentActionConverterImpl extends BaseConverter implements ImportRecordConverter {

    @Override
    public CurrentActionRecord convert(String[] record) {
        return new CurrentActionRecord(getPrestigeId(record), getRequestSequenceNumberAsInteger(record),
                getProductCodeAsInteger(record), getFarePaidAsInteger(record), getCurrencyAsInteger(record),
                getPaymentMethodCodeAsInteger(record), getPrePayValueAsInteger(record), getPickUpLocationAsInteger(record),
                getPptStartDateAsDate(record), getPptExpiryDateAsDate(record), getAutoLoadStateAsInteger(record));
    }
}

