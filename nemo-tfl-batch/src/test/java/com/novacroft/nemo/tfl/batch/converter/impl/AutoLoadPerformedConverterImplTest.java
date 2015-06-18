package com.novacroft.nemo.tfl.batch.converter.impl;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.converter.impl.cubic.AutoLoadPerformedConverterImpl;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AutoLoadPerformedRecordTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * AutoLoadPerformedConverterImpl unit tests
 */
public class AutoLoadPerformedConverterImplTest {
    @Test
    public void shouldConvert() {
        ImportRecordConverter converter = new AutoLoadPerformedConverterImpl();
        assertEquals(getAutoLoadPerformedTestRecord1(), converter.convert(getAutoLoadPerformedRawTestRecord1()));
    }

    @Test
    public void shouldToCsv() {
        ImportRecordConverter converter = new AutoLoadPerformedConverterImpl();
        assertEquals(getAutoLoadPerformedCsvTestRecord1(), converter.toCsv(getAutoLoadPerformedRawTestRecord1()).trim());
    }
}
